package me.hannsi.lfjg.render.system.mesh.persistent;

import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glMapBufferRange;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL42.glMemoryBarrier;
import static org.lwjgl.opengl.GL44.GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT;
import static org.lwjgl.opengl.GL44.glBufferStorage;

@Getter
public class PersistentMappedEBO implements PersistentMappedBuffer {
    private final int bufferId;
    private final IntBuffer mappedBuffer;
    private final int sizeInBytes;
    private long fenceSync = 0L;

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

        waitForGPU();

        mappedBuffer.clear();
        mappedBuffer.put(newData);

        glMemoryBarrier(GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT);

        if (fenceSync != 0L) {
            glDeleteSync(fenceSync);
        }
        fenceSync = glFenceSync(GL_SYNC_GPU_COMMANDS_COMPLETE, 0);

        return this;
    }

    private void waitForGPU() {
        if (fenceSync != 0L) {
            int waitResult = glClientWaitSync(fenceSync, GL_SYNC_FLUSH_COMMANDS_BIT, 1_000_000);
            if (waitResult == GL_TIMEOUT_EXPIRED) {
                System.err.println("GPU still processing, consider waiting longer or using double-buffering.");
            }
            glDeleteSync(fenceSync);
            fenceSync = 0L;
        }
    }

    @Override
    public void cleanup() {
        if (fenceSync != 0L) {
            glDeleteSync(fenceSync);
            fenceSync = 0L;
        }
        glDeleteBuffers(bufferId);
    }
}