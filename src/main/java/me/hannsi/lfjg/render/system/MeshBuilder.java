package me.hannsi.lfjg.render.system;

import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.debug.DebugLevel;
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

public class MeshBuilder {
    public static ProjectionType DEFAULT_PROJECTION_TYPE = ProjectionType.ORTHOGRAPHIC_PROJECTION;
    public static boolean DEFAULT_USE_ELEMENT_BUFFER_OBJECT = true;
    public static boolean DEFAULT_USE_INDIRECT = true;
    public static int DEFAULT_USAGE_HINT = GL_DYNAMIC_DRAW;
    private final int vertexArrayObjectId;
    private final Map<BufferObjectType, Integer> bufferIds;
    protected float[] positions;
    protected float[] colors;
    protected float[] textures;
    protected int numVertices;
    private ProjectionType projectionType;
    private boolean useElementBufferObject;
    private boolean useIndirect;
    private int usageHint;
    private int elementBufferObjectId;
    private int indirectBufferId;

    MeshBuilder() {
        this.projectionType = DEFAULT_PROJECTION_TYPE;
        this.useElementBufferObject = DEFAULT_USE_ELEMENT_BUFFER_OBJECT;
        this.useIndirect = DEFAULT_USE_INDIRECT;
        this.usageHint = DEFAULT_USAGE_HINT;

        this.vertexArrayObjectId = glGenVertexArrays();
        glBindVertexArray(this.vertexArrayObjectId);

        this.bufferIds = new HashMap<>();
    }

    public static MeshBuilder builderCreate() {
        return new MeshBuilder();
    }

    public MeshBuilder createBufferObjects(float[] positions) {
        return createBufferObjects(positions, null);
    }

    public MeshBuilder createBufferObjects(float[] positions, float[] colors) {
        return createBufferObjects(positions, colors, null);
    }

    public MeshBuilder createBufferObjects(float[] positions, float[] colors, float[] texture) {
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

    public MeshBuilder createBufferObjects(float[] positions, float[] normals, float[] textures, int[] indices) {
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

    public MeshBuilder projectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;
        return this;
    }

    public MeshBuilder useElementBufferObject(boolean useElementBufferObject) {
        this.useElementBufferObject = useElementBufferObject;
        return this;
    }

    public MeshBuilder useIndirect(boolean useIndirect) {
        this.useIndirect = useIndirect;
        return this;
    }

    public MeshBuilder usageHint(int usageHint) {
        this.usageHint = usageHint;
        return this;
    }

    public MeshBuilder builderClose() {
        glBindVertexArray(0);
        return this;
    }

    public void updateVBOData(BufferObjectType bufferObjectType, float[] newValues) {
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

            FloatBuffer buffer = BufferUtils.createFloatBuffer(newValues.length);
            glBindBuffer(glId, bufferId);
            buffer.put(newValues).flip();
            glBufferData(glId, buffer, usageHint);
            glBindBuffer(glId, 0);
        }
    }

    public void updateVBOSubData(BufferObjectType bufferObjectType, float[] newValues) {
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

                IntBuffer indicesBuffer = MemoryUtil.memCallocInt(indices.length);
                indicesBuffer.put(0, indices).flip();
                glBindBuffer(glId, bufferId);
                glBufferSubData(glId, 0, indicesBuffer);
                glBindBuffer(glId, 0);

                numVertices = elementPair.indices.length;
            }

            if (bufferObjectType != entryBufferObjectType) {
                continue;
            }

            FloatBuffer buffer = BufferUtils.createFloatBuffer(newValues.length);
            glBindBuffer(glId, bufferId);
            buffer.put(newValues).flip();
            glBufferSubData(glId, 0, buffer);
            glBindBuffer(glId, 0);
        }
    }

    public void updateVBOMapBufferRange(BufferObjectType bufferObjectType, float[] newValues) {
        ElementPair elementPair = null;
        if (useElementBufferObject && bufferObjectType == BufferObjectType.POSITIONS_BUFFER) {
            elementPair = getElementPositions(getSize(), newValues);
        }

        for (Map.Entry<BufferObjectType, Integer> bufferObjectTypeEntry : bufferIds.entrySet()) {
            BufferObjectType entryBufferObjectType = bufferObjectTypeEntry.getKey();
            int glId = entryBufferObjectType.getGlId();
            int bufferId = bufferObjectTypeEntry.getValue();
            int access = GL_MAP_WRITE_BIT | GL_MAP_INVALIDATE_BUFFER_BIT;

            if (entryBufferObjectType == BufferObjectType.ELEMENT_ARRAY_BUFFER && elementPair != null) {
                int[] indices = elementPair.indices;
                newValues = elementPair.positions;

                glBindBuffer(glId, bufferId);
                ByteBuffer mappedBuffer = glMapBufferRange(
                        glId,
                        0,
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
            if (bufferObjectType != entryBufferObjectType) {
                continue;
            }

            glBindBuffer(glId, bufferId);
            ByteBuffer mapped = glMapBufferRange(
                    glId,
                    0,
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
            ids.append(key.getName()).append(": ").append(value).append(", ");
        }
        bufferIds.clear();
        ids.append("VertexArrayObject").append(": ").append(vertexArrayObjectId);
        glDeleteVertexArrays(vertexArrayObjectId);

        LogGenerator logGenerator = new LogGenerator(
                "MeshBuilder",
                "Source: MeshBuilder",
                "Type: Cleanup",
                "ID: " + ids,
                "Severity: Debug",
                "Message: MeshBuilder cleanup is complete."
        );
        logGenerator.logging(DebugLevel.DEBUG);
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
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, usageHint);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private void createVertexBufferObject(BufferObjectType bufferObjectType, float[] values, int index, int size) {
        int bufferId = glGenBuffers();
        bufferIds.put(bufferObjectType, bufferId);
        FloatBuffer valuesBuffer = MemoryUtil.memCallocFloat(values.length);
        valuesBuffer.put(0, values);
        glBindBuffer(GL_ARRAY_BUFFER, bufferId);
        glBufferData(GL_ARRAY_BUFFER, valuesBuffer, usageHint);
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

    public ProjectionType getProjectionType() {
        return projectionType;
    }

    public void setProjectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;
    }

    public boolean isUseElementBufferObject() {
        return useElementBufferObject;
    }

    public void setUseElementBufferObject(boolean useElementBufferObject) {
        this.useElementBufferObject = useElementBufferObject;
    }

    public boolean isUseIndirect() {
        return useIndirect;
    }

    public void setUseIndirect(boolean useIndirect) {
        this.useIndirect = useIndirect;
    }

    public int getUsageHint() {
        return usageHint;
    }

    public void setUsageHint(int usageHint) {
        this.usageHint = usageHint;
    }

    public Map<BufferObjectType, Integer> getBufferIds() {
        return bufferIds;
    }

    public int getIndirectBufferId() {
        return indirectBufferId;
    }

    public int getVertexArrayObjectId() {
        return vertexArrayObjectId;
    }

    public int getElementBufferObjectId() {
        return elementBufferObjectId;
    }

    public float[] getPositions() {
        return positions;
    }

    public float[] getColors() {
        return colors;
    }

    public float[] getTextures() {
        return textures;
    }

    public int getNumVertices() {
        return numVertices;
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
