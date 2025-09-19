package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.render.system.mesh.AttributeType;
import me.hannsi.lfjg.render.system.mesh.MeshConstants;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

public class PersistentMappedDVBO implements PersistentMappedBuffer {
    private final int[] bufferIds = new int[MeshConstants.DEFAULT_BUFFER_COUNT];
    private final DoubleBuffer[] mappedBuffers = new DoubleBuffer[MeshConstants.DEFAULT_BUFFER_COUNT];
    private final int sizeInBytes;

    private int currentIndex = 0;
    private int flags;

    public PersistentMappedDVBO(int size, int flags) {
        this.flags = flags;
        this.sizeInBytes = size * Double.BYTES;

        for (int i = 0; i < MeshConstants.DEFAULT_BUFFER_COUNT; i++) {
            bufferIds[i] = GL15.glGenBuffers();

            GLStateCache.bindArrayBuffer(bufferIds[i]);
            GL44.glBufferStorage(GL15.GL_ARRAY_BUFFER, sizeInBytes, flags);

            ByteBuffer byteBuffer = GL30.glMapBufferRange(GL15.GL_ARRAY_BUFFER, 0, sizeInBytes, flags);
            if (byteBuffer == null) {
                throw new NullPointerException("glMapBufferRange failed at index " + i);
            }

            mappedBuffers[i] = byteBuffer.asDoubleBuffer();
        }
    }

    public PersistentMappedDVBO update(double[] newData) {
        return updatePartial(newData, 0, newData.length);
    }

    public PersistentMappedDVBO updatePartial(double[] newData, int offset, int length) {
        DoubleBuffer buffer = mappedBuffers[currentIndex];
        if (offset + length > buffer.capacity()) {
            throw new IllegalArgumentException("Data exceeds buffer capacity.");
        }

        buffer.position(offset);
        buffer.put(newData, 0, length);

        if ((flags & GL44.GL_MAP_COHERENT_BIT) == 0) {
            GL42.glMemoryBarrier(GL44.GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT);
        }
        return this;
    }

    public PersistentMappedDVBO attribute(AttributeType attributeType, int stride, int pointer) {
        int bufferId = bufferIds[currentIndex];
        GLStateCache.bindArrayBuffer(bufferId);
        GL20.glEnableVertexAttribArray(attributeType.getIndex());
        GL41.glVertexAttribLPointer(attributeType.getIndex(), attributeType.getSize(), GL11.GL_DOUBLE, stride, pointer);
        return this;
    }

    public void finishFrame() {
        currentIndex = (currentIndex + 1) % MeshConstants.DEFAULT_BUFFER_COUNT;
    }

    @Override
    public void cleanup() {
        for (int i = 0; i < MeshConstants.DEFAULT_BUFFER_COUNT; i++) {
            GL15.glDeleteBuffers(bufferIds[i]);
        }
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int[] getBufferIds() {
        return bufferIds;
    }

    public DoubleBuffer[] getMappedBuffers() {
        return mappedBuffers;
    }

    public int getSizeInBytes() {
        return sizeInBytes;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
}
