package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.system.mesh.BufferObjectType;
import me.hannsi.lfjg.render.system.mesh.MeshConstants;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class TestPersistentMappedVBO implements PersistentMappedBuffer {
    private final int flags;
    private FloatBuffer mappedBuffer;
    private int bufferId;
    private int gpuMemorySize;
    private int vertexCount;

    public TestPersistentMappedVBO(int flags, int initialCapacity) {
        this.flags = flags;
        this.vertexCount = 0;

        allocationBufferStorageVertices(initialCapacity);
    }

    private void allocationBufferStorageVertices(int capacity) {
        allocationBufferStorage(getVerticesSizeByte(capacity));
    }

    private void allocationBufferStorage(int capacity) {
        gpuMemorySize = capacity;
        if (bufferId != 0) {
            GLStateCache.deleteArrayBuffer(bufferId);
            bufferId = 0;
        }

        bufferId = GL15.glGenBuffers();
        GLStateCache.bindArrayBuffer(bufferId);
        GL44.glBufferStorage(GL15.GL_ARRAY_BUFFER, gpuMemorySize, flags);

        ByteBuffer byteBuffer = GL30.glMapBufferRange(
                GL15.GL_ARRAY_BUFFER,
                0,
                gpuMemorySize,
                flags
        );
        if (byteBuffer == null) {
            throw new RuntimeException("glMapBufferRange failed");
        }
        mappedBuffer = byteBuffer.asFloatBuffer();
    }

    public TestPersistentMappedVBO createVertexAttribute(int vaoId, BufferObjectType... bufferObjectType) {
        GLStateCache.bindVertexArray(vaoId);

        GLStateCache.bindArrayBuffer(bufferId);

        int stride = getVerticesSizeByte(1);
        int pointer = 0;
        for (BufferObjectType objectType : bufferObjectType) {
            GL20.glEnableVertexAttribArray(objectType.getAttributeIndex());
            GL20.glVertexAttribPointer(
                    objectType.getAttributeIndex(),
                    objectType.getAttributeSize(),
                    GL11.GL_FLOAT,
                    false,
                    stride,
                    (long) pointer * Float.BYTES
            );
            pointer += objectType.getAttributeSize();
        }

        GL30.glBindVertexArray(0);

        return this;
    }

    public TestPersistentMappedVBO add(Vertex vertex) {
        ensureCapacityForVertices(vertexCount + 1);

        int base = vertexCount * MeshConstants.FLOATS_PER_VERTEX;
        writeVertex(mappedBuffer, base, vertex);

        vertexCount++;
        return this;
    }

    public TestPersistentMappedVBO delete(int baseVertexIndex) {
        if (baseVertexIndex < 0 || baseVertexIndex >= vertexCount) {
            throw new IndexOutOfBoundsException("baseVertexIndex: " + baseVertexIndex + " >= vertexCount: " + vertexCount);
        }

        int base = baseVertexIndex * MeshConstants.FLOATS_PER_VERTEX;
        removeVertex(mappedBuffer, base);

        vertexCount--;
        return this;
    }

    public TestPersistentMappedVBO syncToGPU() {
        flushMappedRange(0, getVerticesSizeByte(vertexCount));

        return this;
    }

    private void flushMappedRange(long byteOffset, long byteLength) {
        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            GL44.glFlushMappedBufferRange(GL15.GL_ARRAY_BUFFER, byteOffset, byteLength);
        }
    }

    private void ensureCapacityForVertices(int requiredVertices) {
        int requiredBytes = getVerticesSizeByte(requiredVertices);
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
                "VertexCount: " + vertexCount
        ).logging(getClass(), DebugLevel.INFO, true, true);

        final int floatsToCopy = vertexCount * MeshConstants.FLOATS_PER_VERTEX;
        float[] backup = new float[Math.max(0, floatsToCopy)];

        if (mappedBuffer != null && floatsToCopy > 0) {
            try {
                FloatBuffer reader = mappedBuffer.duplicate();
                reader.position(0);
                int safeLimit = Math.min(reader.capacity(), floatsToCopy);
                reader.limit(safeLimit);
                reader.get(backup, 0, safeLimit);
                DebugLog.info(getClass(), String.format(
                        "Backup success: %d floats copied (%.2f KB)",
                        safeLimit, safeLimit * Float.BYTES / 1024.0
                ));
            } catch (Exception e) {
                DebugLog.error(getClass(), e);
                DebugLog.error(getClass(), "Backup failed: " + e.getMessage());
            }
        } else {
            DebugLog.warning(getClass(), "MappedBuffer is null or no vertices to copy.");
        }

        if (bufferId != 0) {
            GLStateCache.bindArrayBuffer(bufferId);
            boolean unmapped = GL30.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
            if (!unmapped) {
                DebugLog.error(getClass(), "glUnmapBuffer returned false (may indicate corruption).");
            } else {
                DebugLog.info(getClass(), "Buffer unmapped successfully.");
            }

            GLStateCache.deleteArrayBuffer(bufferId);
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
                mappedBuffer.position(vertexCount * MeshConstants.FLOATS_PER_VERTEX);
                DebugLog.info(getClass(), String.format(
                        "Restored %d floats to GPU buffer.", backup.length
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

    private void writeVertex(FloatBuffer buffer, int base, Vertex vertex) {
        long memoryAddress = MemoryUtil.memAddress(buffer);
        long dst = memoryAddress + (long) base * Float.BYTES;

        MemoryUtil.memPutFloat(dst, vertex.x);
        MemoryUtil.memPutFloat(dst + Float.BYTES, vertex.y);
        MemoryUtil.memPutFloat(dst + 2 * Float.BYTES, vertex.z);
        MemoryUtil.memPutFloat(dst + 3 * Float.BYTES, vertex.red);
        MemoryUtil.memPutFloat(dst + 4 * Float.BYTES, vertex.green);
        MemoryUtil.memPutFloat(dst + 5 * Float.BYTES, vertex.blue);
        MemoryUtil.memPutFloat(dst + 6 * Float.BYTES, vertex.alpha);
        MemoryUtil.memPutFloat(dst + 7 * Float.BYTES, vertex.u);
        MemoryUtil.memPutFloat(dst + 8 * Float.BYTES, vertex.v);
        MemoryUtil.memPutFloat(dst + 9 * Float.BYTES, vertex.normalsX);
        MemoryUtil.memPutFloat(dst + 10 * Float.BYTES, vertex.normalsY);
        MemoryUtil.memPutFloat(dst + 11 * Float.BYTES, vertex.normalsZ);
    }

    private void removeVertex(FloatBuffer buffer, int base) {
        int usedFloats = vertexCount * MeshConstants.FLOATS_PER_VERTEX;
        int tailStart = base + MeshConstants.FLOATS_PER_VERTEX;
        int remain = usedFloats - tailStart;

        if (remain > 0) {
            for (int i = 0; i < remain; i++) {
                buffer.put(base + i, buffer.get(tailStart + i));
            }
        }

        int endStart = (vertexCount - 1) * MeshConstants.FLOATS_PER_VERTEX;
        for (int i = 0; i < MeshConstants.FLOATS_PER_VERTEX; i++) {
            buffer.put(endStart + i, 0f);
        }
    }

    private int getVerticesSizeByte(int vertices) {
        return vertices * MeshConstants.FLOATS_PER_VERTEX * Float.BYTES;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getBufferId() {
        return bufferId;
    }

    public FloatBuffer getMappedBuffer() {
        return mappedBuffer;
    }

    @Override
    public void cleanup() {
        if (bufferId != 0) {
            GLStateCache.deleteArrayBuffer(bufferId);
            bufferId = 0;
        }
        mappedBuffer = null;
    }
}
