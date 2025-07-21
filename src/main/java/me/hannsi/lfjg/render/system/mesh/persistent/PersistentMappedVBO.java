package me.hannsi.lfjg.render.system.mesh.persistent;

import lombok.Getter;
import me.hannsi.lfjg.core.utils.type.types.AttributeType;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static me.hannsi.lfjg.render.system.mesh.MeshConstants.DEFAULT_BUFFER_COUNT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL44.*;

@Getter
public class PersistentMappedVBO implements PersistentMappedBuffer {
    private final int[] bufferIds = new int[DEFAULT_BUFFER_COUNT];
    private final FloatBuffer[] mappedBuffers = new FloatBuffer[DEFAULT_BUFFER_COUNT];
    private final long[] fenceSyncs = new long[DEFAULT_BUFFER_COUNT];
    private final int sizeInBytes;

    private int currentIndex = 0;

    public PersistentMappedVBO(int size, int flags) {
        this.sizeInBytes = size * Float.BYTES;

        for (int i = 0; i < DEFAULT_BUFFER_COUNT; i++) {
            bufferIds[i] = glGenBuffers();

            glBindBuffer(GL_ARRAY_BUFFER, bufferIds[i]);
            glBufferStorage(GL_ARRAY_BUFFER, sizeInBytes, flags);

            ByteBuffer byteBuffer = glMapBufferRange(GL_ARRAY_BUFFER, 0, sizeInBytes, flags);
            if (byteBuffer == null) {
                throw new NullPointerException("glMapBufferRange failed at index " + i);
            }

            mappedBuffers[i] = byteBuffer.asFloatBuffer();
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public PersistentMappedVBO update(float[] newData) {
        waitForGPU(currentIndex);

        FloatBuffer buffer = mappedBuffers[currentIndex];
        if (newData.length > buffer.capacity()) {
            throw new IllegalArgumentException("Data exceeds buffer capacity.");
        }

        buffer.position(0);
        buffer.put(newData);

        glMemoryBarrier(GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT);

        return this;
    }

    private void waitForGPU(int index) {
        long fence = fenceSyncs[index];
        if (fence != 0L) {
            int waitResult = glClientWaitSync(fence, GL_SYNC_FLUSH_COMMANDS_BIT, 1_000_000);
            if (waitResult == GL_TIMEOUT_EXPIRED) {
                System.err.println("GPU still processing buffer " + index + ", consider blocking or skipping.");
            }
            glDeleteSync(fence);
            fenceSyncs[index] = 0L;
        }
    }

    public PersistentMappedVBO attribute(AttributeType attributeType) {
        int bufferId = bufferIds[currentIndex];
        glBindBuffer(GL_ARRAY_BUFFER, bufferId);
        glEnableVertexAttribArray(attributeType.getIndex());
        glVertexAttribPointer(attributeType.getIndex(), attributeType.getSize(), GL_FLOAT, false, 0, 0);
        return this;
    }

    public void finishFrame() {
        if (fenceSyncs[currentIndex] != 0L) {
            glDeleteSync(fenceSyncs[currentIndex]);
        }

        fenceSyncs[currentIndex] = glFenceSync(GL_SYNC_GPU_COMMANDS_COMPLETE, 0);

        currentIndex = (currentIndex + 1) % DEFAULT_BUFFER_COUNT;
    }

    @Override
    public void cleanup() {
        for (int i = 0; i < DEFAULT_BUFFER_COUNT; i++) {
            if (fenceSyncs[i] != 0L) {
                glDeleteSync(fenceSyncs[i]);
            }
            glDeleteBuffers(bufferIds[i]);
        }
    }
}
