package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import org.lwjgl.opengl.GL44;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static me.hannsi.lfjg.core.Core.UNSAFE;
import static me.hannsi.lfjg.render.LFJGRenderContext.GL_STATE_CACHE;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glFlushMappedBufferRange;
import static org.lwjgl.opengl.GL30.glMapBufferRange;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class TestPersistentMappedEBO implements TestPersistentMappedBuffer {
    private final int flags;
    private ByteBuffer mappedBuffer;
    private long mappedAddress;
    private int bufferId;
    private long gpuMemorySize;
    private int indexCount;

    public TestPersistentMappedEBO(int flags, int initialCapacity) {
        this.flags = flags;
        this.indexCount = 0;

        allocationBufferStorageIndices(initialCapacity);
    }

    private void allocationBufferStorageIndices(int capacity) {
        allocationBufferStorage(getIndicesSizeByte(capacity));
    }

    @Override
    public void allocationBufferStorage(long capacity) {
        gpuMemorySize = capacity;
        if (bufferId != 0) {
            GL_STATE_CACHE.deleteElementArrayBuffer(bufferId);
            bufferId = 0;
        }

        bufferId = glGenBuffers();
        GL_STATE_CACHE.bindElementArrayBuffer(bufferId);
        glBufferStorage(GL_ELEMENT_ARRAY_BUFFER, gpuMemorySize, flags);

        ByteBuffer byteBuffer = glMapBufferRange(
                GL_ELEMENT_ARRAY_BUFFER,
                0,
                gpuMemorySize,
                flags
        );
        if (byteBuffer == null) {
            throw new RuntimeException("glMapBufferRange failed");
        }
        mappedBuffer = byteBuffer;
        mappedAddress = MemoryUtil.memAddress(byteBuffer);
    }

    public TestPersistentMappedEBO linkVertexArrayObject(int vaoId) {
        GL_STATE_CACHE.bindElementArrayBufferForce(bufferId);

        return this;
    }

    public TestPersistentMappedEBO add(int index) {
        ensureCapacityForIndices(indexCount + 1);

        writeIndex(getIndicesSizeByte(indexCount), index);

        indexCount++;

        return this;
    }

    public void writeIndex(long baseByteOffset, int index) {
        long dst = mappedAddress + baseByteOffset;

        UNSAFE.putInt(
                dst,
                index
        );
    }

    @Override
    public TestPersistentMappedEBO syncToGPU() {
        flushMappedRange(0, getIndicesSizeByte(indexCount));

        return this;
    }

    @Override
    public void flushMappedRange(long byteOffset, long byteLength) {
        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            glFlushMappedBufferRange(GL_ELEMENT_ARRAY_BUFFER, byteOffset, byteLength);
        }
    }

    private void ensureCapacityForIndices(int requiredIndices) {
        long requiredBytes = getIndicesSizeByte(requiredIndices);
        if (requiredBytes <= gpuMemorySize) {
            return;
        }

        long newCapacity = gpuMemorySize + (gpuMemorySize >> 1);
        if (newCapacity < requiredBytes) {
            newCapacity = requiredBytes;
        }

        if (newCapacity == 0) {
            newCapacity = requiredBytes;
        }

        growBuffer((int) newCapacity);
    }

    @Override
    public void growBuffer(long newGPUMemorySizeBytes) {
        if (newGPUMemorySizeBytes <= gpuMemorySize) {
            return;
        }

        long oldAddr = mappedAddress;
        long oldSize = gpuMemorySize;
        int floatsToCopy = indexCount;
        long bytesToCopy = (long) floatsToCopy * Float.BYTES;

        if (bytesToCopy > oldSize) {
            bytesToCopy = oldSize;
        }

        if (oldAddr == 0 || mappedBuffer == null) {
            DebugLog.warning(getClass(), "No existing mapped buffer to backup from.");
        } else if (bytesToCopy > 0) {
            long tmp = MemoryUtil.nmemAllocChecked(bytesToCopy);
            try {
                MemoryUtil.memCopy(oldAddr, tmp, bytesToCopy);

                boolean unmapped = glUnmapBuffer(GL_ELEMENT_ARRAY_BUFFER);
                if (!unmapped) {
                    DebugLog.error(getClass(), "glUnmapBuffer returned false (may indicate corruption).");
                }
                GL_STATE_CACHE.deleteElementArrayBuffer(bufferId);
                bufferId = 0;
                mappedBuffer = null;
                mappedAddress = 0;

                allocationBufferStorage(newGPUMemorySizeBytes);
                if (mappedAddress == 0) {
                    throw new RuntimeException("New buffer mappedAddress is 0");
                }

                MemoryUtil.memCopy(tmp, mappedAddress, bytesToCopy);
            } finally {
                MemoryUtil.nmemFree(tmp);
            }
        } else {
            glUnmapBuffer(GL_ELEMENT_ARRAY_BUFFER);
            GL_STATE_CACHE.deleteElementArrayBuffer(bufferId);
            bufferId = 0;
            mappedBuffer = null;
            mappedAddress = 0;

            allocationBufferStorage(newGPUMemorySizeBytes);
        }

        gpuMemorySize = newGPUMemorySizeBytes;

        new LogGenerator("Grow Buffer")
                .kvHex("oldAddress", oldAddr)
                .kvBytes("oldSize", oldSize)
                .text("\n")
                .kvHex("newAddress", mappedAddress)
                .kvBytes("newSize", newGPUMemorySizeBytes)
                .text("\n")
                .kvBytes("copiedBytes", bytesToCopy)
                .kv("indexCount", indexCount)
                .logging(getClass(), DebugLevel.INFO);
    }

    public long getIndicesSizeByte(int indices) {
        return (long) indices * Integer.BYTES;
    }

    public int getIndexCount() {
        return indexCount;
    }

    @Override
    public int getBufferId() {
        return bufferId;
    }

    @Override
    public ByteBuffer getMappedBuffer() {
        return mappedBuffer;
    }

    @Override
    public long getGPUMemorySize() {
        return gpuMemorySize;
    }

    @Override
    public long getMappedAddress() {
        return mappedAddress;
    }

    @Override
    public void cleanup() {
        if (bufferId != 0) {
            GL_STATE_CACHE.deleteElementArrayBuffer(bufferId);
            bufferId = 0;
        }
        mappedBuffer = null;
    }
}
