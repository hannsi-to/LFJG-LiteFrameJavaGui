package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.render.system.mesh.AttributeType;
import me.hannsi.lfjg.render.system.mesh.MeshConstants;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class PersistentMappedVBO implements PersistentMappedBuffer {
    private final int[] bufferIds = new int[MeshConstants.DEFAULT_BUFFER_COUNT];
    private final FloatBuffer[] mappedBuffers = new FloatBuffer[MeshConstants.DEFAULT_BUFFER_COUNT];
    private final int sizeInBytes;

    private int currentIndex = 0;
    private int flags;

    public PersistentMappedVBO(int size, int flags) {
        this.flags = flags;
        this.sizeInBytes = size * Float.BYTES;

        for (int i = 0; i < MeshConstants.DEFAULT_BUFFER_COUNT; i++) {
            bufferIds[i] = GL15.glGenBuffers();

            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferIds[i]);
            GL44.glBufferStorage(GL15.GL_ARRAY_BUFFER, sizeInBytes, flags);

            ByteBuffer byteBuffer = GL30.glMapBufferRange(
                    GL15.GL_ARRAY_BUFFER,
                    0,
                    sizeInBytes,
                    flags
            );

            if (byteBuffer == null) {
                throw new NullPointerException("glMapBufferRange failed at index " + i);
            }

            mappedBuffers[i] = byteBuffer.asFloatBuffer();
        }

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public PersistentMappedVBO update(float[] newData) {
        return updatePartial(newData, 0, newData.length);
    }

    public PersistentMappedVBO updatePartial(float[] newData, int offset, int length) {
        FloatBuffer buffer = mappedBuffers[currentIndex];
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

    public PersistentMappedVBO attribute(AttributeType attributeType, int stride, int pointer) {
        int bufferId = bufferIds[currentIndex];
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferId);
        GL20.glEnableVertexAttribArray(attributeType.getIndex());
        GL20.glVertexAttribPointer(attributeType.getIndex(), attributeType.getSize(), GL11.GL_FLOAT, false, stride, pointer);
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

    public FloatBuffer[] getMappedBuffers() {
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
