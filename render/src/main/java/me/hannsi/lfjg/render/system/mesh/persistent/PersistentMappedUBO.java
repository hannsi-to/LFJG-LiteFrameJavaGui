package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.joml.Matrix4f;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class PersistentMappedUBO implements PersistentMappedBuffer {
    private final int bufferId;
    private final ByteBuffer mappedBuffer;
    private final int sizeInBytes;

    private final UBOData uboData;
    private int flags;

    public PersistentMappedUBO(UBOData uboData, int flags) {
        this.uboData = uboData;
        this.flags = flags;
        this.sizeInBytes = 3 * 16 * Float.BYTES;

        bufferId = GL15.glGenBuffers();
        GLStateCache.bindUniformBuffer(bufferId);
        GL44.glBufferStorage(GL31.GL_UNIFORM_BUFFER, sizeInBytes, flags);
        bind();

        this.mappedBuffer = GL30.glMapBufferRange(
                GL31.GL_UNIFORM_BUFFER,
                0,
                sizeInBytes,
                flags
        );

        if (mappedBuffer == null) {
            throw new NullPointerException("Failed to map indirect buffer");
        }
    }

    public void bind() {
        GL30.glBindBufferBase(GL31.GL_UNIFORM_BUFFER, uboData.getBinding(), bufferId);
    }

    public PersistentMappedUBO update(Matrix4f projectionMatrix, Matrix4f viewMatrix, Matrix4f modelMatrix) {
        FloatBuffer floatBuffer = mappedBuffer.asFloatBuffer();
        projectionMatrix.get((FloatBuffer) floatBuffer.position(0));
        viewMatrix.get((FloatBuffer) floatBuffer.position(16));
        modelMatrix.get((FloatBuffer) floatBuffer.position(32));

        if ((flags & GL44.GL_MAP_COHERENT_BIT) == 0) {
            GL42.glMemoryBarrier(GL44.GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT);
        }

        return this;
    }

    @Override
    public void cleanup() {
        GLStateCache.bindUniformBuffer(bufferId);
        GL30.glUnmapBuffer(GL31.GL_UNIFORM_BUFFER);
        GL15.glDeleteBuffers(bufferId);
    }

    public UBOData getUboData() {
        return uboData;
    }

    public int getBufferId() {
        return bufferId;
    }

    public ByteBuffer getMappedBuffer() {
        return mappedBuffer;
    }

    public int getSizeInBytes() {
        return sizeInBytes;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public static class UBOData {
        private final String name;
        private final int binding;

        public UBOData(String name, int binding) {
            this.name = name;
            this.binding = binding;
        }

        public String getName() {
            return name;
        }

        public int getBinding() {
            return binding;
        }
    }
}
