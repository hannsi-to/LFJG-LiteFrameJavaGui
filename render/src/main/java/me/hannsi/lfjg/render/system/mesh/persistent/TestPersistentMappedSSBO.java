package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import org.lwjgl.opengl.GL44;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.TreeMap;

import static me.hannsi.lfjg.core.Core.UNSAFE;
import static me.hannsi.lfjg.render.LFJGRenderContext.GL_STATE_CACHE;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL43.GL_SHADER_STORAGE_BUFFER;
import static org.lwjgl.opengl.GL43.GL_SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class TestPersistentMappedSSBO implements TestPersistentMappedBuffer {
    private final int flags;
    private final int initialCapacity;
    private final int ssboOffsetAlignment;
    private Map<Integer, SSBOBindingData> bindingDatum;
    private ByteBuffer mappedBuffer;
    private long mappedAddress;
    private long lastAddress;
    private int bufferId;
    private long gpuMemorySize;

    public TestPersistentMappedSSBO(int flags, int initialCapacity) {
        this.flags = flags;
        this.initialCapacity = initialCapacity;
        this.ssboOffsetAlignment = glGetInteger(GL_SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT);
        this.bindingDatum = new TreeMap<>();

        allocationBufferStorage((long) initialCapacity * Float.BYTES);
    }

    @Override
    public void allocationBufferStorage(long capacity) {
        gpuMemorySize = capacity;
        if (bufferId != 0) {
            GL_STATE_CACHE.deleteShaderStorageBuffer(bufferId);
            bufferId = 0;
        }

        bufferId = glGenBuffers();
        GL_STATE_CACHE.bindShaderStorageBuffer(bufferId);
        glBufferStorage(GL_SHADER_STORAGE_BUFFER, gpuMemorySize, flags);

        ByteBuffer byteBuffer = glMapBufferRange(
                GL_SHADER_STORAGE_BUFFER,
                0,
                gpuMemorySize,
                flags
        );
        if (byteBuffer == null) {
            throw new RuntimeException("glMapBufferRange failed!");
        }
        mappedBuffer = byteBuffer;
        mappedAddress = MemoryUtil.memAddress(byteBuffer);
    }

    public TestPersistentMappedSSBO addInt(int bindingPoint, int data) {
        SSBOBindingData ssboData = getOrCreateSSBOData(bindingPoint);

        ensureSpaceAndShift(
                ssboData,
                (long) (ssboData.dataCount + 1) * Integer.BYTES
        );

        long dst = mappedAddress + ssboData.offset + (long) ssboData.dataCount * Integer.BYTES;
        UNSAFE.putInt(dst, data);
        ssboData.dataCount++;

        return this;
    }

    public TestPersistentMappedSSBO addFloat(int bindingPoint, float data) {
        SSBOBindingData ssboData = getOrCreateSSBOData(bindingPoint);

        ensureSpaceAndShift(
                ssboData,
                (long) (ssboData.dataCount + 1) * Float.BYTES
        );

        long dst = mappedAddress + ssboData.offset + (long) ssboData.dataCount * Float.BYTES;
        UNSAFE.putFloat(dst, data);
        ssboData.dataCount++;

        return this;
    }

    public TestPersistentMappedSSBO addVec4(int bindingPoint, float x, float y, float z, float w) {
        float[] values = new float[]{x, y, z, w};
        for (float value : values) {
            addFloat(bindingPoint, value);
        }

        return this;
    }

    public TestPersistentMappedSSBO addMatrix4f(int bindingPoint, float[] matrixElements) {
        if (matrixElements.length != 16) {
            throw new IllegalArgumentException("Matrix4f requires exactly 16 float elements.");
        }

        final int ELEMENT_COUNT = 16;
        final int MATRIX_BYTES = ELEMENT_COUNT * Float.BYTES;

        SSBOBindingData ssboData = getOrCreateSSBOData(bindingPoint);

        ensureSpaceAndShift(
                ssboData,
                (long) ssboData.dataCount * Float.BYTES + MATRIX_BYTES
        );

        long dst = mappedAddress + ssboData.offset + (long) ssboData.dataCount * Float.BYTES;

        for (int i = 0; i < ELEMENT_COUNT; i++) {
            UNSAFE.putFloat(dst + (long) i * Float.BYTES, matrixElements[i]);
        }

        ssboData.dataCount += ELEMENT_COUNT;

        return this;
    }

    private SSBOBindingData getOrCreateSSBOData(int bindingPoint) {
        SSBOBindingData ssboData = bindingDatum.get(bindingPoint);

        if (ssboData == null) {
            long alignedAddress = alignValue(this.lastAddress, this.ssboOffsetAlignment);

            ssboData = new SSBOBindingData(alignedAddress, initialCapacity);
            bindingDatum.put(bindingPoint, ssboData);

            ensureCapacity(alignedAddress + initialCapacity);
            this.lastAddress = alignedAddress + initialCapacity;
        }
        return ssboData;
    }

    private long alignValue(long value, int alignment) {
        long mask = alignment - 1;
        return (value + mask) & ~mask;
    }

    private void ensureSpaceAndShift(SSBOBindingData ssboData, long requiredTotalBytes) {
        long delta = ssboData.updateSize(requiredTotalBytes);

        if (delta > 0) {
            ensureCapacity(lastAddress + delta);

            for (Map.Entry<Integer, SSBOBindingData> entry : bindingDatum.entrySet()) {
                SSBOBindingData otherData = entry.getValue();

                if (otherData.offset > ssboData.offset) {
                    long src = mappedAddress + otherData.offset;
                    long dst = mappedAddress + otherData.offset + delta;
                    long bytesToCopy = otherData.size;

                    UNSAFE.copyMemory(src, dst, bytesToCopy);
                    otherData.offset += delta;
                }
            }
            lastAddress += delta;
        }
    }

    @Override
    public TestPersistentMappedBuffer syncToGPU() {
        flushMappedRange(0, lastAddress);

        return this;
    }

    @Override
    public void flushMappedRange(long byteOffset, long byteLength) {
        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            glFlushMappedBufferRange(GL_SHADER_STORAGE_BUFFER, byteOffset, byteLength);
        }
    }

    private void ensureCapacity(long requiredBytes) {
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

        growBuffer(newCapacity);
    }

    @Override
    public void growBuffer(long newGPUMemorySizeBytes) {
        if (newGPUMemorySizeBytes <= gpuMemorySize) {
            return;
        }

        long oldAddr = mappedAddress;
        long oldSize = gpuMemorySize;
        long bytesToCopy = lastAddress;

        if (bytesToCopy > oldSize) {
            bytesToCopy = oldSize;
        }

        if (oldAddr == 0 || mappedBuffer == null) {
            DebugLog.warning(getClass(), "No existing mapped buffer to backup from.");
        } else if (bytesToCopy > 0) {
            long tmp = MemoryUtil.nmemAllocChecked(bytesToCopy);
            try {
                MemoryUtil.memCopy(oldAddr, tmp, bytesToCopy);

                boolean unmapped = glUnmapBuffer(GL_SHADER_STORAGE_BUFFER);
                if (!unmapped) {
                    DebugLog.error(getClass(), "glUnmapBuffer returned false (may indicate corruption).");
                }
                GL_STATE_CACHE.deleteShaderStorageBuffer(bufferId);
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
            glUnmapBuffer(GL_SHADER_STORAGE_BUFFER);
            GL_STATE_CACHE.deleteShaderStorageBuffer(bufferId);
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
                .kv("ssboDataCount", bindingDatum.size())
                .logging(getClass(), DebugLevel.INFO);
    }

    @Override
    public void cleanup() {
        if (bufferId != 0) {
            GL_STATE_CACHE.deleteShaderStorageBuffer(bufferId);
            bufferId = 0;
        }
        bindingDatum.clear();
        bindingDatum = null;
        mappedBuffer = null;
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

    public int getInitialCapacity() {
        return initialCapacity;
    }

    public int getSsboOffsetAlignment() {
        return ssboOffsetAlignment;
    }

    public Map<Integer, SSBOBindingData> getBindingDatum() {
        return bindingDatum;
    }

    public long getLastAddress() {
        return lastAddress;
    }

    public static class SSBOBindingData {
        public long offset;
        public long size;
        public int dataCount;

        public SSBOBindingData(long offset, long size) {
            this.offset = offset;
            this.size = size;
            this.dataCount = 0;
        }

        public long updateSize(long requiredBytes) {
            if (requiredBytes <= size) {
                return 0;
            }

            long newCapacity = size + (size >> 1);
            if (newCapacity < requiredBytes) {
                newCapacity = requiredBytes;
            }

            if (newCapacity == 0) {
                newCapacity = requiredBytes;
            }

            long delta = newCapacity - size;
            size = newCapacity;
            return delta;
        }
    }
}
