package me.hannsi.lfjg.render.system.buffer;

import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.core.utils.math.MathHelper.align;
import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static me.hannsi.lfjg.render.LFJGRenderContext.GL_CAPABILITIES;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class BufferManager {
    public Builder builder;

    private BufferManager(Builder builder) {
        this.builder = builder;
    }

    public BufferPool[] createBufferPools() {
        boolean isModernEnvironment = GL_CAPABILITIES.OpenGL44 && GL_CAPABILITIES.GL_ARB_buffer_storage;

        BufferPool[] bufferPools = new BufferPool[builder.bufferObjectTypes.size()];
        int index = 0;
        long offset = 0;
        int geometryBufferId = isModernEnvironment ? glGenBuffers() : -1;
        int constantBufferId = isModernEnvironment ? glGenBuffers() : -1;
        long geometryOffset = 0;
        long constantOffset = 0;
        for (Map.Entry<BufferManagerConfig.BufferObjectType, Long> entry : builder.bufferObjectTypes.entrySet()) {
            BufferManagerConfig.BufferObjectType type = entry.getKey();
            long requestedSize = align(entry.getValue(), type.getAlignment());

            if (!isModernEnvironment) {
                bufferPools[index] = new BufferPool(glGenBuffers(), type, 0, requestedSize);
            } else {
                switch (type) {
                    case VERTEX_BUFFER_OBJECT, ELEMENT_BUFFER_OBJECT, DRAW_INDIRECT_BUFFER_OBJECT -> {
                        geometryOffset = align(geometryOffset, type.getAlignment());

                        bufferPools[index] = new BufferPool(geometryBufferId, type, geometryOffset, requestedSize);

                        geometryOffset += requestedSize;
                    }
                    case UNIFORM_BUFFER_OBJECT, SHADER_STORAGE_BUFFER_OBJECT -> {
                        constantOffset = align(constantOffset, type.getAlignment());

                        bufferPools[index] = new BufferPool(constantBufferId, type, constantOffset, requestedSize);

                        constantOffset += requestedSize;
                    }
                    default ->
                            throw new IllegalStateException("Unexpected value: " + type);
                }
            }

            index++;
        }

        return bufferPools;
    }

    public static class BufferPool {
        public int bufferId;
        public BufferManagerConfig.BufferObjectType bufferObjectType;
        public long offset;
        public long bufferSize;

        public BufferPool(int bufferId, BufferManagerConfig.BufferObjectType bufferObjectType, long offset, long bufferSize) {
            this.bufferId = bufferId;
            this.bufferObjectType = bufferObjectType;
            this.offset = offset;
            this.bufferSize = bufferSize;
        }
    }

    public static class Builder {
        public HashMap<BufferManagerConfig.BufferObjectType, Long> bufferObjectTypes;

        private Builder() {
            this.bufferObjectTypes = new HashMap<>();
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public Builder addBufferObjectType(BufferManagerConfig.BufferObjectType bufferObjectType, long bufferSize) {
            if (bufferObjectTypes.containsKey(bufferObjectType)) {
                bufferSize = max(bufferSize, bufferObjectTypes.get(bufferObjectType));
            }

            bufferObjectTypes.put(bufferObjectType, bufferSize);

            return this;
        }

        public BufferManager build() {
            return new BufferManager(this);
        }
    }
}