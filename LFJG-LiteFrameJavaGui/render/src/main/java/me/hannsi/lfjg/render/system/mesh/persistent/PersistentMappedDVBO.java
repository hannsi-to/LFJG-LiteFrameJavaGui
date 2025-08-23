package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.render.system.mesh.AttributeType;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

import static me.hannsi.lfjg.render.system.mesh.MeshConstants.DEFAULT_BUFFER_COUNT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glMapBufferRange;
import static org.lwjgl.opengl.GL41.glVertexAttribLPointer;
import static org.lwjgl.opengl.GL42.glMemoryBarrier;
import static org.lwjgl.opengl.GL44.GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class PersistentMappedDVBO implements PersistentMappedBuffer {
    private final int[] bufferIds = new int[DEFAULT_BUFFER_COUNT];
    private final DoubleBuffer[] mappedBuffers = new DoubleBuffer[DEFAULT_BUFFER_COUNT];
    private final int sizeInBytes;

    private int currentIndex = 0;

    public PersistentMappedDVBO(int size, int flags) {
        this.sizeInBytes = size * Double.BYTES;

        for (int i = 0; i < DEFAULT_BUFFER_COUNT; i++) {
            bufferIds[i] = glGenBuffers();

            glBindBuffer(GL_ARRAY_BUFFER, bufferIds[i]);
            glBufferStorage(GL_ARRAY_BUFFER, sizeInBytes, flags);

            ByteBuffer byteBuffer = glMapBufferRange(GL_ARRAY_BUFFER, 0, sizeInBytes, flags);
            if (byteBuffer == null) {
                throw new NullPointerException("glMapBufferRange failed at index " + i);
            }

            mappedBuffers[i] = byteBuffer.asDoubleBuffer();
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public PersistentMappedDVBO update(double[] newData) {
        DoubleBuffer buffer = mappedBuffers[currentIndex];
        if (newData.length > buffer.capacity()) {
            throw new IllegalArgumentException("Data exceeds buffer capacity.");
        }

        buffer.position(0);
        buffer.put(newData);

        glMemoryBarrier(GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT);

        return this;
    }

    public PersistentMappedDVBO attribute(AttributeType attributeType) {
        int bufferId = bufferIds[currentIndex];
        glBindBuffer(GL_ARRAY_BUFFER, bufferId);
        glEnableVertexAttribArray(attributeType.getIndex());
        glVertexAttribLPointer(attributeType.getIndex(), attributeType.getSize(), GL_DOUBLE, 0, 0L);
        return this;
    }

    public void finishFrame() {
        currentIndex = (currentIndex + 1) % DEFAULT_BUFFER_COUNT;
    }

    @Override
    public void cleanup() {
        for (int i = 0; i < DEFAULT_BUFFER_COUNT; i++) {
            glDeleteBuffers(bufferIds[i]);
        }
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
}
