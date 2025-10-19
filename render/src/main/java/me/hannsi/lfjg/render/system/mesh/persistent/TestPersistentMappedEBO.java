package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL44;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TestPersistentMappedEBO implements PersistentMappedBuffer {
    private final int flags;
    private IntBuffer mappedBuffer;
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
        gpuMemorySize = getIndicesSizeByte(capacity);
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
    }

    public TestPersistentMappedEBO linkVertexArrayObject(int vaoId) {
        GLStateCache.bindVertexArray(vaoId);
        GLStateCache.bindElementArrayBuffer(bufferId);
        GL30.glBindVertexArray(0);

        return this;
    }

    public TestPersistentMappedEBO add(int index) {
        ensureCapacityForIndices(indexCount + 1);

        int base = indexCount;
        mappedBuffer.put(base, index);

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
            GL42.glMemoryBarrier(GL42.GL_ELEMENT_ARRAY_BARRIER_BIT);
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

        new LogGenerator(
                "Grow Buffer Start",
                "OldSize: " + gpuMemorySize + " bytes",
                "NewSize: " + newGpuMemorySizeBytes + " bytes",
                "IndexCount: " + indexCount
        ).logging(getClass(), DebugLevel.INFO, true, true);

        final int floatsToCopy = indexCount;
        int[] backup = new int[Math.max(0, floatsToCopy)];

        if (mappedBuffer != null && floatsToCopy > 0) {
            try {
                IntBuffer reader = mappedBuffer.duplicate();
                reader.position(0);
                int safeLimit = Math.min(reader.capacity(), floatsToCopy);
                reader.limit(safeLimit);
                reader.get(backup, 0, safeLimit);
                DebugLog.info(getClass(), String.format(
                        "Backup success: %d int copied (%.2f KB)",
                        safeLimit, safeLimit * Integer.BYTES / 1024.0
                ));
            } catch (Exception e) {
                DebugLog.error(getClass(), e);
                DebugLog.error(getClass(), "Backup failed: " + e.getMessage());
            }
        } else {
            DebugLog.warning(getClass(), "MappedBuffer is null or no indices to copy.");
        }

        if (bufferId != 0) {
            GLStateCache.bindElementArrayBuffer(bufferId);
            boolean unmapped = GL30.glUnmapBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER);
            if (!unmapped) {
                DebugLog.error(getClass(), "glUnmapBuffer returned false (may indicate corruption).");
            } else {
                DebugLog.info(getClass(), "Buffer unmapped successfully.");
            }

            GLStateCache.deleteElementArrayBuffer(bufferId);
            DebugLog.info(getClass(), "Old buffer deleted (ID: " + bufferId + ")");
            bufferId = 0;
        }
        mappedBuffer = null;

        DebugLog.info(getClass(), "Allocating new GPU buffer...");
        allocationBufferStorage(newGpuMemorySizeBytes);
        DebugLog.info(getClass(), "New buffer allocated (ID: " + bufferId + ", " + newGpuMemorySizeBytes + " bytes)");

        if (mappedBuffer != null && backup.length > 0) {
            try {
                mappedBuffer.position(0);
                mappedBuffer.put(backup, 0, backup.length);
                mappedBuffer.position(indexCount);
                DebugLog.info(getClass(), String.format(
                        "Restored %d int to GPU buffer.", backup.length
                ));
            } catch (Exception e) {
                DebugLog.error(getClass(), e);
                DebugLog.error(getClass(), "Data restore failed: " + e.getMessage());
            }
        }

        gpuMemorySize = newGpuMemorySizeBytes;

        new LogGenerator(
                "Grow Buffer Complete",
                "OldSize: " + gpuMemorySize + " bytes",
                "MappedBufferCapacity: " + (mappedBuffer != null ? mappedBuffer.capacity() : -1) + " bytes"
        ).logging(getClass(), DebugLevel.INFO, true, false);
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
