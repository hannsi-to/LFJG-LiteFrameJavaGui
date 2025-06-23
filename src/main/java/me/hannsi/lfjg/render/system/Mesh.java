package me.hannsi.lfjg.render.system;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.utils.type.types.AttributeType;
import me.hannsi.lfjg.utils.type.types.BufferObjectType;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import me.hannsi.lfjg.utils.type.types.UpdateBufferType;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
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
    private final EnumMap<BufferObjectType, int[]> bufferIds;
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

        this.bufferIds = new EnumMap<>(BufferObjectType.class);
    }

    public static Mesh initMesh() {
        return new Mesh();
    }

    public Mesh createBufferObjects2D(float[] positions) {
        return createBufferObjects2D(positions, null);
    }

    public Mesh createBufferObjects2D(float[] positions, float[] colors) {
        return createBufferObjects2D(positions, colors, null);
    }

    public Mesh createBufferObjects2D(float[] positions, float[] colors, float[] texture) {
        this.positions = positions;
        this.colors = colors;
        this.textures = texture;

        if (useElementBufferObject) {
            createVectorBufferObjectAndElementBufferObject(AttributeType.POSITION_2D);
        } else {
            createVertexBufferObject(BufferObjectType.POSITIONS_BUFFER, AttributeType.POSITION_2D, positions);
        }

        if (colors != null) {
            createVertexBufferObject(BufferObjectType.COLORS_BUFFER, AttributeType.COLOR, colors);
        }

        if (texture != null) {
            createVertexBufferObject(BufferObjectType.TEXTURE_BUFFER, AttributeType.TEXTURE, texture);
        }

        if (useIndirect) {
            createIndirectBuffer();
        }

        return this;
    }

    public Mesh createBufferObjects3D(float[] positions, float[] normals, float[] textures, int[] indices) {
        this.positions = positions;
        this.textures = textures;
        this.colors = null;
        this.useElementBufferObject = true;

        numVertices = indices.length;

        createVertexBufferObject(BufferObjectType.POSITIONS_BUFFER, AttributeType.POSITION_3D, positions);
        createVertexBufferObject(BufferObjectType.NORMALS_BUFFER, AttributeType.NORMAL_3D, normals);
        createVertexBufferObject(BufferObjectType.TEXTURE_BUFFER, AttributeType.TEXTURE, textures);

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

    public void updateVBOData(UpdateBufferType updateBufferType, BufferObjectType bufferObjectType, float[] newValues) {
        rotateBuffer();

        ElementPair elementPair = updateElementPairIfNeeded(bufferObjectType, newValues);

        for (Map.Entry<BufferObjectType, int[]> bufferObjectTypeEntry : bufferIds.entrySet()) {
            BufferObjectType entryBufferObjectType = bufferObjectTypeEntry.getKey();
            int bufferId = bufferObjectTypeEntry.getValue()[0];

            if (entryBufferObjectType == BufferObjectType.ELEMENT_ARRAY_BUFFER && elementPair != null) {
                uploadElementBuffer(updateBufferType, elementPair, currentBufferIndex);
            }

            if (bufferObjectType != entryBufferObjectType) {
                continue;
            }

            uploadVertexBufferObject(updateBufferType, entryBufferObjectType, bufferId, newValues);
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

        int bufferId = bufferIds.get(BufferObjectType.ELEMENT_ARRAY_BUFFER)[0];
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
        for (Map.Entry<BufferObjectType, int[]> entry : bufferIds.entrySet()) {
            BufferObjectType key = entry.getKey();
            Integer value = entry.getValue()[0];
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
        bufferIds.put(BufferObjectType.INDIRECT_BUFFER, new int[]{indirectBufferId});

        glBindBuffer(GL_DRAW_INDIRECT_BUFFER, indirectBufferId);
        glBufferData(GL_DRAW_INDIRECT_BUFFER, drawCommand, usageHint);
        glBindBuffer(GL_DRAW_INDIRECT_BUFFER, 0);
    }

    private void createVectorBufferObjectAndElementBufferObject(AttributeType attributeType) {
        int stride = getStride();
        ElementPair elementPair = getElementPositions(stride, positions);
        createVertexBufferObject(BufferObjectType.POSITIONS_BUFFER, attributeType, elementPair.positions);
        createElementBufferObject(elementPair.indices);
        numVertices = elementPair.indices.length;
    }

    private ElementPair getElementPositions(int stride, float[] positions) {
        Map<VertexKey, Integer> uniqueVertices = new HashMap<>();
        int[] indices = new int[positions.length / stride];
        float[] uniquePositions = new float[positions.length];
        int uniqueCount = 0;

        for (int i = 0, idx = 0; i < positions.length; i += stride, idx++) {
            VertexKey key = new VertexKey(positions, i, stride);
            Integer existingIndex = uniqueVertices.get(key);
            if (existingIndex != null) {
                indices[idx] = existingIndex;
            } else {
                uniqueVertices.put(key, uniqueCount);
                indices[idx] = uniqueCount;
                System.arraycopy(positions, i, uniquePositions, uniqueCount * stride, stride);
                uniqueCount++;
            }
        }

        float[] finalPositions = Arrays.copyOf(uniquePositions, uniqueCount * stride);
        return new ElementPair(finalPositions, indices);
    }

    private void createElementBufferObject(int[] indices) {
        elementBufferObjectId = glGenBuffers();
        bufferIds.put(BufferObjectType.ELEMENT_ARRAY_BUFFER, new int[]{elementBufferObjectId});

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

    private void createVertexBufferObject(BufferObjectType bufferObjectType, AttributeType attributeType, float[] values) {
        int bufferId = glGenBuffers();
        bufferIds.put(bufferObjectType, new int[]{bufferId});

        long byteSize = (long) values.length * Float.BYTES * bufferCount;

        FloatBuffer valuesBuffer = MemoryUtil.memCallocFloat(values.length);
        valuesBuffer.put(values);
        valuesBuffer.flip();

        glBindBuffer(GL_ARRAY_BUFFER, bufferId);
        glBufferData(GL_ARRAY_BUFFER, byteSize, usageHint);
        glBufferSubData(GL_ARRAY_BUFFER, 0, valuesBuffer);
        setAttribute(attributeType);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        MemoryUtil.memFree(valuesBuffer);
    }

    private void setAttribute(AttributeType attributeType) {
        int index = attributeType.getIndex();
        int size = attributeType.getSize();

        glEnableVertexAttribArray(index);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
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
