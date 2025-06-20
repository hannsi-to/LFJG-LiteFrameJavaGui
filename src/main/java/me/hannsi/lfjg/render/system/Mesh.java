package me.hannsi.lfjg.render.system;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.utils.type.types.BufferObjectType;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import me.hannsi.lfjg.utils.type.types.UpdateBufferType;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.GL_DRAW_INDIRECT_BUFFER;

public class Mesh {
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
    @Setter
    private int accessHint;
    @Getter
    private int elementBufferObjectId;
    @Getter
    private int indirectBufferId;
    private int bufferCount;
    private int currentBufferIndex;

    Mesh() {
        this.projectionType = MeshConstants.PROJECTION_TYPE;
        this.useElementBufferObject = MeshConstants.USE_ELEMENT_BUFFER_OBJECT;
        this.useIndirect = MeshConstants.USE_INDIRECT;
        this.usageHint = MeshConstants.USAGE_HINT;
        this.accessHint = MeshConstants.ACCESS_HINT;
        this.bufferCount = MeshConstants.BUFFER_COUNT;

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
            createVertexBufferObject(BufferObjectType.POSITIONS_BUFFER, positions, 0, getStride());
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

    public Mesh accessHint(int accessHint) {
        this.accessHint = accessHint;
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

        ElementPair elementPair = updateElementPairIfNeeded(bufferObjectType, newValues);

        for (Map.Entry<BufferObjectType, Integer> bufferObjectTypeEntry : bufferIds.entrySet()) {
            BufferObjectType entryBufferObjectType = bufferObjectTypeEntry.getKey();
            int bufferId = bufferObjectTypeEntry.getValue();

            if (entryBufferObjectType == BufferObjectType.ELEMENT_ARRAY_BUFFER && elementPair != null) {
                uploadElementBuffer(UpdateBufferType.BUFFER_DATA, elementPair, currentBufferIndex);
            }

            if (bufferObjectType != entryBufferObjectType) {
                continue;
            }

            uploadVertexBufferObject(UpdateBufferType.BUFFER_DATA, entryBufferObjectType, bufferId, newValues);
        }
    }

    public void updateVBOSubData(BufferObjectType bufferObjectType, float[] newValues) {
        rotateBuffer();

        ElementPair elementPair = updateElementPairIfNeeded(bufferObjectType, newValues);

        for (Map.Entry<BufferObjectType, Integer> bufferObjectTypeEntry : bufferIds.entrySet()) {
            BufferObjectType entryBufferObjectType = bufferObjectTypeEntry.getKey();
            int bufferId = bufferObjectTypeEntry.getValue();

            if (entryBufferObjectType == BufferObjectType.ELEMENT_ARRAY_BUFFER && elementPair != null) {
                uploadElementBuffer(UpdateBufferType.BUFFER_SUB_DATA, elementPair, currentBufferIndex);
            }

            if (bufferObjectType != entryBufferObjectType) {
                continue;
            }

            uploadVertexBufferObject(UpdateBufferType.BUFFER_SUB_DATA, entryBufferObjectType, bufferId, newValues);
        }
    }

    public void updateVBOMapBufferRange(BufferObjectType bufferObjectType, float[] newValues) {
        rotateBuffer();

        ElementPair elementPair = updateElementPairIfNeeded(bufferObjectType, newValues);

        for (Map.Entry<BufferObjectType, Integer> bufferObjectTypeEntry : bufferIds.entrySet()) {
            BufferObjectType entryBufferObjectType = bufferObjectTypeEntry.getKey();
            int bufferId = bufferObjectTypeEntry.getValue();

            if (entryBufferObjectType == BufferObjectType.ELEMENT_ARRAY_BUFFER && elementPair != null) {
                uploadElementBuffer(UpdateBufferType.MAP_BUFFER_RANGE, elementPair, currentBufferIndex);
            }

            if (bufferObjectType != entryBufferObjectType) {
                continue;
            }

            uploadVertexBufferObject(UpdateBufferType.MAP_BUFFER_RANGE, entryBufferObjectType, bufferId, newValues);
        }
    }

    private void uploadVertexBufferObject(UpdateBufferType updateBufferType, BufferObjectType bufferObjectType, int bufferId, float[] newValues) {
        int length = newValues.length;
        long byteOffset = (long) currentBufferIndex * length * Float.BYTES;

        FloatBuffer buffer = BufferUtils.createFloatBuffer(newValues.length);
        buffer.put(newValues).flip();

        glBindBuffer(bufferObjectType.getGlId(), bufferId);
        switch (updateBufferType) {
            case BUFFER_DATA -> glBufferData(bufferObjectType.getGlId(), buffer, usageHint);
            case BUFFER_SUB_DATA -> glBufferSubData(bufferObjectType.getGlId(), byteOffset, buffer);
            case MAP_BUFFER_RANGE -> {
                ByteBuffer mapped = glMapBufferRange(
                        bufferObjectType.getGlId(),
                        byteOffset,
                        (long) length * Float.BYTES,
                        accessHint
                );
                if (mapped != null) {
                    mapped.asFloatBuffer().put(newValues);
                    glUnmapBuffer(bufferObjectType.getGlId());
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + updateBufferType);
        }
        glBindBuffer(bufferObjectType.getGlId(), 0);
    }

    private ElementPair updateElementPairIfNeeded(BufferObjectType bufferObjectType, float[] newValues) {
        if (useElementBufferObject && bufferObjectType == BufferObjectType.POSITIONS_BUFFER) {
            return getElementPositions(getStride(), newValues);
        }
        return null;
    }

    private void uploadElementBuffer(UpdateBufferType updateBufferType, ElementPair elementPair, int currentBufferIndex) {
        if (elementPair == null) {
            return;
        }

        int[] indices = elementPair.indices;
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices).flip();

        int length = indices.length;
        long byteOffset = (long) currentBufferIndex * length * Integer.BYTES;

        int bufferId = bufferIds.get(BufferObjectType.ELEMENT_ARRAY_BUFFER);
        int glId = BufferObjectType.ELEMENT_ARRAY_BUFFER.getGlId();

        glBindBuffer(glId, bufferId);

        switch (updateBufferType) {
            case BUFFER_DATA -> glBufferData(glId, indicesBuffer, usageHint);
            case BUFFER_SUB_DATA -> glBufferSubData(glId, byteOffset, indicesBuffer);
            case MAP_BUFFER_RANGE -> {
                ByteBuffer mappedBuffer = glMapBufferRange(
                        glId,
                        byteOffset,
                        (long) indices.length * Integer.BYTES,
                        accessHint
                );
                if (mappedBuffer != null) {
                    mappedBuffer.asIntBuffer().put(indices);
                    glUnmapBuffer(glId);
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + updateBufferType);
        }

        glBindBuffer(glId, 0);

        numVertices = indices.length;
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
            int size = getStride();
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
        int stride = getStride();
        ElementPair elementPair = getElementPositions(stride, positions);
        createVertexBufferObject(BufferObjectType.POSITIONS_BUFFER, elementPair.positions, 0, stride);
        createElementBufferObject(elementPair.indices);
        numVertices = elementPair.indices.length;
    }

    private ElementPair getElementPositions(int stride, float[] positions) {
        Map<VertexKey, Integer> uniqueVertices = new HashMap<>();
        List<Integer> indices = new ArrayList<>();
        List<Float> uniquePositions = new ArrayList<>();
        int index = 0;

        for (int i = 0; i < positions.length; i += stride) {
            VertexKey key = new VertexKey(positions, i, stride);
            Integer existingIndex = uniqueVertices.get(key);
            if (existingIndex != null) {
                indices.add(existingIndex);
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

        long byteSize = (long) indices.length * Integer.BYTES * bufferCount;

        IntBuffer indicesBuffer = MemoryUtil.memCallocInt(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementBufferObjectId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, byteSize, usageHint);
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, indicesBuffer);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        MemoryUtil.memFree(indicesBuffer);
    }

    private void createVertexBufferObject(BufferObjectType bufferObjectType, float[] values, int index, int size) {
        int bufferId = glGenBuffers();
        bufferIds.put(bufferObjectType, bufferId);

        long byteSize = (long) values.length * Float.BYTES * bufferCount;

        FloatBuffer valuesBuffer = MemoryUtil.memCallocFloat(values.length);
        valuesBuffer.put(values);
        valuesBuffer.flip();

        glBindBuffer(GL_ARRAY_BUFFER, bufferId);
        glBufferData(GL_ARRAY_BUFFER, byteSize, usageHint);
        glBufferSubData(GL_ARRAY_BUFFER, 0, valuesBuffer);
        glEnableVertexAttribArray(index);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        MemoryUtil.memFree(valuesBuffer);
    }

    private int getStride() {
        return projectionType.getStride();
    }

    public record ElementPair(float[] positions, int[] indices) {
        public ElementPair(float[] positions, int[] indices) {
            this.positions = positions.clone();
            this.indices = indices.clone();
        }

        @Override
        public float[] positions() {
            return positions.clone();
        }

        @Override
        public int[] indices() {
            return indices.clone();
        }
    }

    private static class VertexKey {
        private final float[] data;
        private final int offset;
        private final int length;
        private final int hash;

        public VertexKey(float[] data, int offset, int length) {
            this.data = data;
            this.offset = offset;
            this.length = length;
            this.hash = computeHash();
        }

        private int computeHash() {
            int h = 1;
            for (int i = 0; i < length; i++) {
                h = 31 * h + Float.hashCode(data[offset + i]);
            }
            return h;
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof VertexKey other)) {
                return false;
            }
            if (this.length != other.length) {
                return false;
            }

            for (int i = 0; i < length; i++) {
                if (Float.compare(this.data[offset + i], other.data[other.offset + i]) != 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
