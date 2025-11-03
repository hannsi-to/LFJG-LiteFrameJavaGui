package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL44;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TestPersistentMappedEBO implements PersistentMappedBuffer {
    private final int flags;
    private IntBuffer mappedBuffer;
    private long mappedAddress;
    private int bufferId;
    private int gpuMemorySize;
    private int indexCount;

    public TestPersistentMappedEBO(int flags, int initialCapacity) {
        this.flags = flags;
        this.indexCount = 0;

        allocationBufferStorageIndices(initialCapacity);
    }

    private void allocationBufferStorageIndices(int capacity) {
        allocationBufferStorage(getIndicesSizeByte(capacity));
    }

    private void allocationBufferStorage(int capacity) {
        gpuMemorySize = capacity;
        if (bufferId != 0) {
            GLStateCache.deleteElementArrayBuffer(bufferId);
            bufferId = 0;
        }

        bufferId = GL15.glGenBuffers();
        GLStateCache.bindElementArrayBuffer(bufferId);
        GL44.glBufferStorage(GL15.GL_ELEMENT_ARRAY_BUFFER, gpuMemorySize, flags);

        ByteBuffer byteBuffer = GL30.glMapBufferRange(
                GL15.GL_ELEMENT_ARRAY_BUFFER,
                0,
                gpuMemorySize,
                flags
        );
        if (byteBuffer == null) {
            throw new RuntimeException("glMapBufferRange failed");
        }
        mappedBuffer = byteBuffer.asIntBuffer();
        mappedAddress = MemoryUtil.memAddress(byteBuffer);
    }

    public TestPersistentMappedEBO linkVertexArrayObject(int vaoId) {
        GLStateCache.bindVertexArray(vaoId);
        GLStateCache.bindElementArrayBuffer(bufferId);
        GL30.glBindVertexArray(0);

        return this;
    }

    public TestPersistentMappedEBO add(int index) {
        ensureCapacityForIndices(indexCount + 1);

        MemoryUtil.memPutInt(mappedAddress + getIndicesSizeByte(indexCount), index);

        indexCount++;

        return this;
    }

    public TestPersistentMappedEBO syncToGPU() {
        flushMappedRange(0, getIndicesSizeByte(indexCount));

        return this;
    }

    private void flushMappedRange(long byteOffset, long byteLength) {
        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            GL44.glFlushMappedBufferRange(GL15.GL_ELEMENT_ARRAY_BUFFER, byteOffset, byteLength);
        }
    }

    private void ensureCapacityForIndices(int requiredIndices) {
        int requiredBytes = getIndicesSizeByte(requiredIndices);
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

    private void growBuffer(int newGpuMemorySizeBytes) {
        if (newGpuMemorySizeBytes <= gpuMemorySize) {
            return;
        }

        long oldAddr = mappedAddress;
        int oldSize = gpuMemorySize;
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

                boolean unmapped = GL30.glUnmapBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER);
                if (!unmapped) {
                    DebugLog.error(getClass(), "glUnmapBuffer returned false (may indicate corruption).");
                }
                GLStateCache.deleteElementArrayBuffer(bufferId);
                bufferId = 0;
                mappedBuffer = null;
                mappedAddress = 0;

                allocationBufferStorage(newGpuMemorySizeBytes);
                if (mappedAddress == 0) {
                    throw new RuntimeException("New buffer mappedAddress is 0");
                }

                MemoryUtil.memCopy(tmp, mappedAddress, bytesToCopy);
            } finally {
                MemoryUtil.nmemFree(tmp);
            }
        } else {
            GL30.glUnmapBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER);
            GLStateCache.deleteElementArrayBuffer(bufferId);
            bufferId = 0;
            mappedBuffer = null;
            mappedAddress = 0;

            allocationBufferStorage(newGpuMemorySizeBytes);
        }

        gpuMemorySize = newGpuMemorySizeBytes;

        new LogGenerator("Grow Buffer")
                .kvHex("oldAddress", oldAddr)
                .kvBytes("oldSize", oldSize)
                .text("\n")
                .kvHex("newAddress", mappedAddress)
                .kvBytes("newSize", newGpuMemorySizeBytes)
                .text("\n")
                .kvBytes("copiedBytes", bytesToCopy)
                .kv("indexCount", indexCount)
                .logging(getClass(), DebugLevel.INFO);
    }

    private int getIndicesSizeByte(int indices) {
        return indices * Integer.BYTES;
    }

    public int getIndexCount() {
        return indexCount;
    }

    public int getBufferId() {
        return bufferId;
    }

    public IntBuffer getMappedBuffer() {
        return mappedBuffer;
    }

    @Override
    public void cleanup() {
        if (bufferId != 0) {
            GLStateCache.deleteElementArrayBuffer(bufferId);
            bufferId = 0;
        }
        mappedBuffer = null;
    }
}
