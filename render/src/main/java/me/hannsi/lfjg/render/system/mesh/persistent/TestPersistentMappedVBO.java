package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.system.mesh.BufferObjectType;
import me.hannsi.lfjg.render.system.mesh.MeshConstants;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import org.lwjgl.opengl.GL44;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static me.hannsi.lfjg.core.Core.UNSAFE;
import static me.hannsi.lfjg.render.LFJGRenderContext.GL_STATE_CACHE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glFlushMappedBufferRange;
import static org.lwjgl.opengl.GL30.glMapBufferRange;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class TestPersistentMappedVBO implements TestPersistentMappedBuffer {
    private static final float[] TEMP_BUFFER = new float[MeshConstants.FLOATS_PER_VERTEX];
    private static final long FLOAT_BASE = UNSAFE.arrayBaseOffset(float[].class);
    private final int flags;
    private ByteBuffer mappedBuffer;
    private long mappedAddress;
    private int bufferId;
    private long gpuMemorySize;
    private int vertexCount;

    public TestPersistentMappedVBO(int flags, int initialCapacity) {
        this.flags = flags;
        this.vertexCount = 0;

        allocationBufferStorageVertices(initialCapacity);
    }

    private void allocationBufferStorageVertices(int capacity) {
        allocationBufferStorage(getVerticesSizeByte(capacity));
    }

    @Override
    public void allocationBufferStorage(long capacity) {
        gpuMemorySize = capacity;
        if (bufferId != 0) {
            GL_STATE_CACHE.deleteArrayBuffer(bufferId);
            bufferId = 0;
        }

        bufferId = glGenBuffers();
        GL_STATE_CACHE.bindArrayBuffer(bufferId);
        glBufferStorage(GL_ARRAY_BUFFER, gpuMemorySize, flags);

        ByteBuffer byteBuffer = glMapBufferRange(
                GL_ARRAY_BUFFER,
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
        GL_STATE_CACHE.bindArrayBuffer(bufferId);

        long stride = getVerticesSizeByte(1);
        int pointer = 0;
        for (BufferObjectType objectType : bufferObjectType) {
            glEnableVertexAttribArray(objectType.getAttributeIndex());
            glVertexAttribPointer(
                    objectType.getAttributeIndex(),
                    objectType.getAttributeSize(),
                    GL_FLOAT,
                    false,
                    (int) stride,
                    (long) pointer * Float.BYTES
            );
            pointer += objectType.getAttributeSize();
        }

        return this;
    }

    public TestPersistentMappedVBO add(Vertex vertex) {
        ensureCapacityForVertices(vertexCount + 1);

        long baseOffset = getVerticesSizeByte(vertexCount);
        writeVertex(baseOffset, vertex);

        vertexCount++;
        return this;
    }

    @Override
    public TestPersistentMappedVBO syncToGPU() {
        flushMappedRange(0, getVerticesSizeByte(vertexCount));

        return this;
    }

    @Override
    public void flushMappedRange(long byteOffset, long byteLength) {
        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            glFlushMappedBufferRange(GL_ARRAY_BUFFER, byteOffset, byteLength);
        }
    }

    private void ensureCapacityForVertices(int requiredVertices) {
        long requiredBytes = getVerticesSizeByte(requiredVertices);
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
        long floatsToCopy = (long) vertexCount * MeshConstants.FLOATS_PER_VERTEX;
        long bytesToCopy = floatsToCopy * Float.BYTES;

        if (bytesToCopy > oldSize) {
            bytesToCopy = oldSize;
        }

        if (oldAddr == 0 || mappedBuffer == null) {
            DebugLog.warning(getClass(), "No existing mapped buffer to backup from.");
        } else if (bytesToCopy > 0) {
            long tmp = MemoryUtil.nmemAllocChecked(bytesToCopy);
            try {
                MemoryUtil.memCopy(oldAddr, tmp, bytesToCopy);

                boolean unmapped = glUnmapBuffer(GL_ARRAY_BUFFER);
                if (!unmapped) {
                    DebugLog.error(getClass(), "glUnmapBuffer returned false (may indicate corruption).");
                }
                GL_STATE_CACHE.deleteArrayBuffer(bufferId);
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
            glUnmapBuffer(GL_ARRAY_BUFFER);
            GL_STATE_CACHE.deleteArrayBuffer(bufferId);
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
                .kv("vertexCount", vertexCount)
                .logging(getClass(), DebugLevel.INFO);
    }

    public void writeVertex(long baseByteOffset, Vertex vertex) {
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
                getVerticesSizeByte(1)
        );
    }

    public long getVerticesSizeByte(int vertices) {
        return (long) vertices * MeshConstants.FLOATS_PER_VERTEX * Float.BYTES;
    }

    public int getVertexCount() {
        return vertexCount;
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
    public long getMappedAddress() {
        return mappedAddress;
    }

    @Override
    public long getGPUMemorySize() {
        return gpuMemorySize;
    }

    @Override
    public void cleanup() {
        if (bufferId != 0) {
            GL_STATE_CACHE.deleteArrayBuffer(bufferId);
            bufferId = 0;
        }
        mappedBuffer = null;
        vertexCount = 0;
    }
}
