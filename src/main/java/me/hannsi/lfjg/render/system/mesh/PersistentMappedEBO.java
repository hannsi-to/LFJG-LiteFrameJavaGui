package me.hannsi.lfjg.render.system.mesh;

import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glMapBufferRange;
import static org.lwjgl.opengl.GL42.glMemoryBarrier;
import static org.lwjgl.opengl.GL44.GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT;
import static org.lwjgl.opengl.GL44.glBufferStorage;

@Getter
public class PersistentMappedEBO {
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
        mappedBuffer.clear();
        mappedBuffer.put(newData);
        mappedBuffer.flip();

        glMemoryBarrier(GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT);

        return this;
    }

    public void cleanup() {
        glDeleteBuffers(bufferId);
    }
}