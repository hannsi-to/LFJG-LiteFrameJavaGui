package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.render.system.mesh.BufferObjectType;
import me.hannsi.lfjg.render.system.mesh.MeshConstants;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static me.hannsi.lfjg.render.LFJGRenderContext.GL_STATE_CACHE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glMapBufferRange;
import static org.lwjgl.opengl.GL42.glMemoryBarrier;
import static org.lwjgl.opengl.GL44.*;

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
            bufferIds[i] = glGenBuffers();

            GL_STATE_CACHE.bindArrayBuffer(bufferIds[i]);
            glBufferStorage(GL_ARRAY_BUFFER, sizeInBytes, flags);

            ByteBuffer byteBuffer = glMapBufferRange(
                    GL_ARRAY_BUFFER,
                    0,
                    sizeInBytes,
                    flags
            );

            if (byteBuffer == null) {
                throw new NullPointerException("glMapBufferRange failed at index " + i);
            }

            mappedBuffers[i] = byteBuffer.asFloatBuffer();
        }
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

        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            glMemoryBarrier(GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT);
        }
        return this;
    }

    public PersistentMappedVBO attribute(BufferObjectType bufferObjectType, int stride, int pointer) {
        int bufferId = bufferIds[currentIndex];
        GL_STATE_CACHE.bindArrayBuffer(bufferId);
        glEnableVertexAttribArray(bufferObjectType.getAttributeIndex());
        glVertexAttribPointer(bufferObjectType.getAttributeIndex(), bufferObjectType.getAttributeSize(), GL_FLOAT, false, stride, pointer);
        return this;
    }

    public void finishFrame() {
        currentIndex = (currentIndex + 1) % MeshConstants.DEFAULT_BUFFER_COUNT;
    }

    @Override
    public void cleanup() {
        for (int i = 0; i < MeshConstants.DEFAULT_BUFFER_COUNT; i++) {
            GL_STATE_CACHE.deleteArrayBuffer(bufferIds[i]);
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
