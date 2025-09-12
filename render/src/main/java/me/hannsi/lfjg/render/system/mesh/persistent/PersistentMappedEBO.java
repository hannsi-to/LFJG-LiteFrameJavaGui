package me.hannsi.lfjg.render.system.mesh.persistent;


import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL44;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class PersistentMappedEBO implements PersistentMappedBuffer {
    private final int bufferId;
    private final IntBuffer mappedBuffer;
    private final int sizeInBytes;

    private int flags;

    public PersistentMappedEBO(int size, int flags) {
        this.flags = flags;
        this.sizeInBytes = size * Integer.BYTES;

        bufferId = GL15.glGenBuffers();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferId);
        GL44.glBufferStorage(
                GL15.GL_ARRAY_BUFFER,
                sizeInBytes,
                flags
        );

        ByteBuffer byteBuffer = GL30.glMapBufferRange(
                GL15.GL_ARRAY_BUFFER,
                0,
                sizeInBytes,
                flags
        );

        if (byteBuffer == null) {
            throw new NullPointerException();
        }
        mappedBuffer = byteBuffer.asIntBuffer();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public PersistentMappedEBO update(int[] newData) {
        if (newData.length > mappedBuffer.capacity()) {
            throw new IllegalArgumentException("Data exceeds buffer size. New data: " + newData.length + " > " + "Capacity: " + mappedBuffer.capacity());
        }

        mappedBuffer.clear();
        mappedBuffer.put(newData);

        if ((flags & GL44.GL_MAP_COHERENT_BIT) == 0) {
            GL42.glMemoryBarrier(GL44.GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT);
        }

        return this;
    }

    @Override
    public void cleanup() {
        GL15.glDeleteBuffers(bufferId);
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
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