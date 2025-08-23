package me.hannsi.lfjg.render.system.mesh.persistent;


import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glMapBufferRange;
import static org.lwjgl.opengl.GL42.glMemoryBarrier;
import static org.lwjgl.opengl.GL44.GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class PersistentMappedEBO implements PersistentMappedBuffer {
    private final int bufferId;
    private final IntBuffer mappedBuffer;
    private final int sizeInBytes;

    public PersistentMappedEBO(int size, int flags) {
        this.sizeInBytes = size * Integer.BYTES;

        bufferId = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, bufferId);
        glBufferStorage(
                GL_ARRAY_BUFFER,
                sizeInBytes,
                flags
        );

        ByteBuffer byteBuffer = glMapBufferRange(
                GL_ARRAY_BUFFER,
                0,
                sizeInBytes,
                flags
        );

        if (byteBuffer == null) {
            throw new NullPointerException();
        }
        mappedBuffer = byteBuffer.asIntBuffer();

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public PersistentMappedEBO update(int[] newData) {
        if (newData.length > mappedBuffer.capacity()) {
            throw new IllegalArgumentException("Data exceeds buffer size. New data: " + newData.length + " > " + "Capacity: " + mappedBuffer.capacity());
        }

        mappedBuffer.clear();
        mappedBuffer.put(newData);

        glMemoryBarrier(GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT);

        return this;
    }

    @Override
    public void cleanup() {
        glDeleteBuffers(bufferId);
    }

    public int getBufferId() {
        return bufferId;
    }

    public IntBuffer getMappedBuffer() {
        return mappedBuffer;
    }

    public int getSizeInBytes() {
        return sizeInBytes;
    }
}