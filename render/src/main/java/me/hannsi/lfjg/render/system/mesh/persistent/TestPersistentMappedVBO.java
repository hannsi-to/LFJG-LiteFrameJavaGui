package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.system.mesh.BufferObjectType;
import me.hannsi.lfjg.render.system.mesh.MeshConstants;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;
import sun.misc.Unsafe;

import java.nio.ByteBuffer;

public class TestPersistentMappedVBO implements PersistentMappedBuffer {
    private static final float[] TEMP_BUFFER = new float[MeshConstants.FLOATS_PER_VERTEX];
    private static final Unsafe UNSAFE = Core.UNSAFE;
    private static final long FLOAT_BASE = UNSAFE.arrayBaseOffset(float[].class);
    private final int flags;
    private ByteBuffer mappedBuffer;
    private long mappedAddress;
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
        mappedBuffer = byteBuffer;
        mappedAddress = MemoryUtil.memAddress(byteBuffer);
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

        long baseOffset = (long) vertexCount * MeshConstants.FLOATS_PER_VERTEX * Float.BYTES;
        writeVertex(baseOffset, vertex);

        vertexCount++;
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

        long oldAddr = mappedAddress;
        int oldSize = gpuMemorySize;
        int floatsToCopy = vertexCount * MeshConstants.FLOATS_PER_VERTEX;
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

                boolean unmapped = GL30.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
                if (!unmapped) {
                    DebugLog.error(getClass(), "glUnmapBuffer returned false (may indicate corruption).");
                }
                GLStateCache.deleteArrayBuffer(bufferId);
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
            GL30.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
            GLStateCache.deleteArrayBuffer(bufferId);
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
                .kv("vertexCount", vertexCount)
                .logging(getClass(), DebugLevel.INFO);
    }

    private void writeVertex(long baseByteOffset, Vertex vertex) {
        TEMP_BUFFER[0] = vertex.x;
        TEMP_BUFFER[1] = vertex.y;
        TEMP_BUFFER[2] = vertex.z;
        TEMP_BUFFER[3] = vertex.red;
        TEMP_BUFFER[4] = vertex.green;
        TEMP_BUFFER[5] = vertex.blue;
        TEMP_BUFFER[6] = vertex.alpha;
        TEMP_BUFFER[7] = vertex.u;
        TEMP_BUFFER[8] = vertex.v;
        TEMP_BUFFER[9] = vertex.normalsX;
        TEMP_BUFFER[10] = vertex.normalsY;
        TEMP_BUFFER[11] = vertex.normalsZ;

        long dst = mappedAddress + baseByteOffset;

        UNSAFE.copyMemory(
                TEMP_BUFFER,
                FLOAT_BASE,
                null,
                dst,
                (long) MeshConstants.FLOATS_PER_VERTEX * Float.BYTES
        );
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

    public ByteBuffer getMappedBuffer() {
        return mappedBuffer;
    }

    public long getMappedAddress() {
        return mappedAddress;
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
