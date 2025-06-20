package me.hannsi.lfjg.render.system;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.utils.type.types.BufferObjectType;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.GL_DRAW_INDIRECT_BUFFER;

public class Mesh {
    public static final ProjectionType DEFAULT_PROJECTION_TYPE = ProjectionType.ORTHOGRAPHIC_PROJECTION;
    public static final boolean DEFAULT_USE_ELEMENT_BUFFER_OBJECT = true;
    public static final boolean DEFAULT_USE_INDIRECT = true;
    public static final int DEFAULT_USAGE_HINT = GL_DYNAMIC_DRAW;
    public static final int DEFAULT_BUFFER_COUNT = 3;

    @Getter
    private final int vertexArrayObjectId;
    @Getter
    private final Map<BufferObjectType, Integer> bufferIds;
    @Getter
    protected float[] positions;
    @Getter
    protected float[] colors;
    @Getter
    protected float[] textures;
    @Getter
    protected int numVertices;
    @Getter
    @Setter
    private ProjectionType projectionType;
    @Getter
    @Setter
    private boolean useElementBufferObject;
    @Getter
    @Setter
    private boolean useIndirect;
    @Getter
    @Setter
    private int usageHint;
    @Getter
    private int elementBufferObjectId;
    @Getter
    private int indirectBufferId;
    private int bufferCount;
    private int currentBufferIndex;

    Mesh() {
        this.projectionType = DEFAULT_PROJECTION_TYPE;
        this.useElementBufferObject = DEFAULT_USE_ELEMENT_BUFFER_OBJECT;
        this.useIndirect = DEFAULT_USE_INDIRECT;
        this.usageHint = DEFAULT_USAGE_HINT;
        this.bufferCount = DEFAULT_BUFFER_COUNT;

        this.currentBufferIndex = 0;

        this.vertexArrayObjectId = glGenVertexArrays();
        glBindVertexArray(this.vertexArrayObjectId);

        this.bufferIds = new HashMap<>();
    }

    public static Mesh initMesh() {
        return new Mesh();
    }

    public Mesh createBufferObjects(float[] positions) {
        return createBufferObjects(positions, null);
    }

    public Mesh createBufferObjects(float[] positions, float[] colors) {
        return createBufferObjects(positions, colors, null);
    }

    public Mesh createBufferObjects(float[] positions, float[] colors, float[] texture) {
        this.positions = positions;
        this.colors = colors;
        this.textures = texture;

        if (useElementBufferObject) {
            createVectorBufferObjectAndElementBufferObject();
        } else {
            createVertexBufferObject(BufferObjectType.POSITIONS_BUFFER, positions, 0, getSize());
        }

        if (colors != null) {
            createVertexBufferObject(BufferObjectType.COLORS_BUFFER, colors, 1, 4);
        }

        if (texture != null) {
            createVertexBufferObject(BufferObjectType.TEXTURE_BUFFER, texture, 2, 2);
        }

        if (useIndirect) {
            createIndirectBuffer();
        }

        return this;
    }

    public Mesh createBufferObjects(float[] positions, float[] normals, float[] textures, int[] indices) {
        this.positions = positions;
        this.textures = textures;
        this.colors = null;
        this.useElementBufferObject = true;

        numVertices = indices.length;

        createVertexBufferObject(BufferObjectType.POSITIONS_BUFFER, positions, 0, 3);
        createVertexBufferObject(BufferObjectType.NORMALS_BUFFER, normals, 1, 3);
        createVertexBufferObject(BufferObjectType.TEXTURE_BUFFER, textures, 2, 2);

        createElementBufferObject(indices);

        if (useIndirect) {
            createIndirectBuffer();
        }

        return this;
    }

    public Mesh projectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;
        return this;
    }

    public Mesh useElementBufferObject(boolean useElementBufferObject) {
        this.useElementBufferObject = useElementBufferObject;
        return this;
    }

    public Mesh useIndirect(boolean useIndirect) {
        this.useIndirect = useIndirect;
        return this;
    }

    public Mesh usageHint(int usageHint) {
        this.usageHint = usageHint;
        return this;
    }

    public Mesh bufferCount(int bufferCount) {
        this.bufferCount = bufferCount;
        return this;
    }

    public Mesh builderClose() {
        glBindVertexArray(0);
        return this;
    }

    private void rotateBuffer() {
        currentBufferIndex = (currentBufferIndex + 1) % bufferCount;
    }

    public void updateVBOData(BufferObjectType bufferObjectType, float[] newValues) {
        rotateBuffer();

        ElementPair elementPair = null;
        if (useElementBufferObject && bufferObjectType == BufferObjectType.POSITIONS_BUFFER) {
            elementPair = getElementPositions(getSize(), newValues);
        }

        for (Map.Entry<BufferObjectType, Integer> bufferObjectTypeEntry : bufferIds.entrySet()) {
            BufferObjectType entryBufferObjectType = bufferObjectTypeEntry.getKey();
            int glId = entryBufferObjectType.getGlId();
            int bufferId = bufferObjectTypeEntry.getValue();

            if (entryBufferObjectType == BufferObjectType.ELEMENT_ARRAY_BUFFER && elementPair != null) {
                int[] indices = elementPair.indices;
                newValues = elementPair.positions;
                int length = indices.length;
                long byteOffset = (long) currentBufferIndex * length * Float.BYTES;

                IntBuffer indicesBuffer = MemoryUtil.memCallocInt(indices.length);
                indicesBuffer.put(0, indices).flip();
                glBindBuffer(glId, bufferId);
                glBufferData(glId, indicesBuffer, usageHint);
                glBindBuffer(glId, 0);

                numVertices = elementPair.indices.length;
            }

            if (bufferObjectType != entryBufferObjectType) {
                continue;
            }

            int length = newValues.length;
            long byteOffset = (long) currentBufferIndex * length * Float.BYTES;

            FloatBuffer buffer = BufferUtils.createFloatBuffer(newValues.length);
            glBindBuffer(glId, bufferId);
            buffer.put(newValues).flip();
            glBufferData(glId, buffer, usageHint);
            glBufferSubData(glId, byteOffset, buffer);
            glBindBuffer(glId, 0);
        }
    }

    public void updateVBOSubData(BufferObjectType bufferObjectType, float[] newValues) {
        rotateBuffer();

        ElementPair elementPair = null;
        if (useElementBufferObject && bufferObjectType == BufferObjectType.POSITIONS_BUFFER) {
            elementPair = getElementPositions(getSize(), newValues);
        }

        for (Map.Entry<BufferObjectType, Integer> bufferObjectTypeEntry : bufferIds.entrySet()) {
            BufferObjectType entryBufferObjectType = bufferObjectTypeEntry.getKey();
            int glId = entryBufferObjectType.getGlId();
            int bufferId = bufferObjectTypeEntry.getValue();

            if (entryBufferObjectType == BufferObjectType.ELEMENT_ARRAY_BUFFER && elementPair != null) {
                int[] indices = elementPair.indices;
                newValues = elementPair.positions;
                int length = indices.length;
                long byteOffset = (long) currentBufferIndex * length * Float.BYTES;

                IntBuffer indicesBuffer = MemoryUtil.memCallocInt(indices.length);
                indicesBuffer.put(0, indices).flip();
                glBindBuffer(glId, bufferId);
                glBufferSubData(glId, byteOffset, indicesBuffer);
                glBindBuffer(glId, 0);

                numVertices = elementPair.indices.length;
            }

            if (bufferObjectType != entryBufferObjectType) {
                continue;
            }

            int length = newValues.length;
            long byteOffset = (long) currentBufferIndex * length * Float.BYTES;

            FloatBuffer buffer = BufferUtils.createFloatBuffer(newValues.length);
            glBindBuffer(glId, bufferId);
            buffer.put(newValues).flip();
            glBufferSubData(glId, byteOffset, buffer);
            glBindBuffer(glId, 0);
        }
    }

    public void updateVBOMapBufferRange(BufferObjectType bufferObjectType, float[] newValues) {
        rotateBuffer();

        ElementPair elementPair = null;
        if (useElementBufferObject && bufferObjectType == BufferObjectType.POSITIONS_BUFFER) {
            elementPair = getElementPositions(getSize(), newValues);
        }

        for (Map.Entry<BufferObjectType, Integer> bufferObjectTypeEntry : bufferIds.entrySet()) {
            BufferObjectType entryBufferObjectType = bufferObjectTypeEntry.getKey();
            int glId = entryBufferObjectType.getGlId();
            int bufferId = bufferObjectTypeEntry.getValue();
            int access = GL_MAP_WRITE_BIT | GL_MAP_INVALIDATE_BUFFER_BIT | GL_MAP_UNSYNCHRONIZED_BIT;

            if (entryBufferObjectType == BufferObjectType.ELEMENT_ARRAY_BUFFER && elementPair != null) {
                int[] indices = elementPair.indices;
                newValues = elementPair.positions;
                int length = indices.length;
                long byteOffset = (long) currentBufferIndex * length * Float.BYTES;

                glBindBuffer(glId, bufferId);
                ByteBuffer mappedBuffer = glMapBufferRange(
                        glId,
                        byteOffset,
                        (long) indices.length * Integer.BYTES,
                        access
                );
                if (mappedBuffer != null) {
                    mappedBuffer.asIntBuffer().put(indices);
                    glUnmapBuffer(glId);
                }
                glBindBuffer(glId, 0);

                numVertices = indices.length;
            }

            int length = newValues.length;
            long byteOffset = (long) currentBufferIndex * length * Float.BYTES;
            if (bufferObjectType != entryBufferObjectType) {
                continue;
            }

            glBindBuffer(glId, bufferId);
            ByteBuffer mapped = glMapBufferRange(
                    glId,
                    byteOffset,
                    (long) length * Float.BYTES,
                    access
            );
            if (mapped != null) {
                mapped.asFloatBuffer().put(newValues);
                glUnmapBuffer(glId);
            }
            glBindBuffer(glId, 0);
        }
    }

    public void cleanup() {
        StringBuilder ids = new StringBuilder();
        for (Map.Entry<BufferObjectType, Integer> entry : bufferIds.entrySet()) {
            BufferObjectType key = entry.getKey();
            Integer value = entry.getValue();
            glDeleteBuffers(value);
            ids.append(key.getName()).append(": ").append(value).append("| ");
        }
        bufferIds.clear();
        ids.append("VertexArrayObject: ").append(vertexArrayObjectId);
        glDeleteVertexArrays(vertexArrayObjectId);

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.toString(),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    private void createIndirectBuffer() {
        IntBuffer drawCommand;
        if (useElementBufferObject) {
            drawCommand = BufferUtils.createIntBuffer(5);
            drawCommand.put(numVertices);
            drawCommand.put(1);
            drawCommand.put(0);
        } else {
            int size = getSize();
            int count = positions.length / size;

            drawCommand = BufferUtils.createIntBuffer(4);
            drawCommand.put(count);
            drawCommand.put(1);
        }
        drawCommand.put(0);
        drawCommand.put(0);
        drawCommand.flip();

        indirectBufferId = glGenBuffers();
        bufferIds.put(BufferObjectType.INDIRECT_BUFFER, indirectBufferId);

        glBindBuffer(GL_DRAW_INDIRECT_BUFFER, indirectBufferId);
        glBufferData(GL_DRAW_INDIRECT_BUFFER, drawCommand, usageHint);
        glBindBuffer(GL_DRAW_INDIRECT_BUFFER, 0);
    }

    private void createVectorBufferObjectAndElementBufferObject() {
        int stride = getSize();
        ElementPair elementPair = getElementPositions(stride, positions);
        createVertexBufferObject(BufferObjectType.POSITIONS_BUFFER, elementPair.positions, 0, stride);
        createElementBufferObject(elementPair.indices);
        numVertices = elementPair.indices.length;
    }

    private ElementPair getElementPositions(int stride, float[] positions) {
        Map<String, Integer> uniqueVertices = new HashMap<>();
        List<Integer> indices = new ArrayList<>();
        List<Float> uniquePositions = new ArrayList<>();
        int index = 0;

        for (int i = 0; i < positions.length; i += stride) {
            String key = Arrays.toString(Arrays.copyOfRange(positions, i, i + stride));
            if (uniqueVertices.containsKey(key)) {
                indices.add(uniqueVertices.get(key));
            } else {
                uniqueVertices.put(key, index);
                indices.add(index);
                for (int j = 0; j < stride; j++) {
                    uniquePositions.add(positions[i + j]);
                }
                index++;
            }
        }

        float[] newPositions = new float[uniquePositions.size()];
        for (int i = 0; i < uniquePositions.size(); i++) {
            newPositions[i] = uniquePositions.get(i);
        }

        return new ElementPair(newPositions, indices.stream().mapToInt(i -> i).toArray());
    }

    private void createElementBufferObject(int[] indices) {
        elementBufferObjectId = glGenBuffers();
        bufferIds.put(BufferObjectType.ELEMENT_ARRAY_BUFFER, elementBufferObjectId);
        IntBuffer indicesBuffer = MemoryUtil.memCallocInt(indices.length);
        indicesBuffer.put(0, indices);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementBufferObjectId);
        long byteSize = (long) indices.length * Integer.BYTES * bufferCount;
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, byteSize, usageHint);
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, indicesBuffer);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private void createVertexBufferObject(BufferObjectType bufferObjectType, float[] values, int index, int size) {
        int bufferId = glGenBuffers();
        bufferIds.put(bufferObjectType, bufferId);
        FloatBuffer valuesBuffer = MemoryUtil.memCallocFloat(values.length);
        valuesBuffer.put(0, values);
        glBindBuffer(GL_ARRAY_BUFFER, bufferId);
        long byteSize = (long) values.length * Float.BYTES * bufferCount;
        glBufferData(GL_ARRAY_BUFFER, byteSize, usageHint);
        glBufferSubData(GL_ARRAY_BUFFER, 0, valuesBuffer);
        glEnableVertexAttribArray(index);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        MemoryUtil.memFree(valuesBuffer);
    }

    private int getSize() {
        int size;
        switch (projectionType) {
            case ORTHOGRAPHIC_PROJECTION -> {
                size = 2;
            }
            case PERSPECTIVE_PROJECTION -> {
                size = 3;
            }
            default -> throw new IllegalStateException("Unexpected value: " + projectionType);
        }

        return size;
    }

    public static class ElementPair {
        public float[] positions;
        public int[] indices;

        public ElementPair(float[] positions, int[] indices) {
            this.positions = positions;
            this.indices = indices;
        }
    }
}
