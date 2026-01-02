package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.renderers.ObjectParameter;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL44;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.core.Core.UNSAFE;
import static me.hannsi.lfjg.render.LFJGRenderContext.*;
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
    private long mappedAddress;
    private long lastAddress;
    private int bufferId;
    private long gpuMemorySize;

    public TestPersistentMappedSSBO(int flags, int initialCapacity) {
        this.flags = flags;
        this.initialCapacity = initialCapacity;
        this.ssboOffsetAlignment = glGetInteger(GL_SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT);
        this.bindingDatum = new HashMap<>();

        allocationBufferStorage((long) initialCapacity * Float.BYTES);
    }

    @Override
    public void allocationBufferStorage(long capacity) {
        gpuMemorySize = capacity;
        if (bufferId != 0) {
            glStateCache.deleteShaderStorageBuffer(bufferId);
            bufferId = 0;
        }

        bufferId = glGenBuffers();
        glStateCache.bindShaderStorageBuffer(bufferId);
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

    public TestPersistentMappedSSBO updateInt(int bindingPoint, int index, int data) {
        SSBOBindingData ssboData = bindingDatum.get(bindingPoint);
        if (ssboData == null || index >= ssboData.dataCount) {
            throw new IndexOutOfBoundsException("Invalid binding point or index");
        }

        long dst = mappedAddress + ssboData.offset + (long) index * Integer.BYTES;
        UNSAFE.putInt(dst, data);
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

    public TestPersistentMappedSSBO updateFloat(int bindingPoint, int index, float data) {
        SSBOBindingData ssboData = bindingDatum.get(bindingPoint);
        if (ssboData == null || index >= ssboData.dataCount) {
            throw new IndexOutOfBoundsException("Invalid binding point or index");
        }

        long dst = mappedAddress + ssboData.offset + (long) index * Float.BYTES;
        UNSAFE.putFloat(dst, data);
        return this;
    }

    public TestPersistentMappedSSBO addVec4(int bindingPoint, float x, float y, float z, float w) {
        float[] values = new float[]{x, y, z, w};
        for (float value : values) {
            addFloat(bindingPoint, value);
        }

        return this;
    }

    public TestPersistentMappedSSBO updateVec4(int bindingPoint, int index, float x, float y, float z, float w) {
        SSBOBindingData ssboData = bindingDatum.get(bindingPoint);
        final int VEC4_FLOATS = 4;

        if (ssboData == null || (index * VEC4_FLOATS) + VEC4_FLOATS > ssboData.dataCount) {
            throw new IndexOutOfBoundsException("Invalid binding point or index");
        }

        long dst = mappedAddress + ssboData.offset + (long) index * VEC4_FLOATS * Float.BYTES;
        UNSAFE.putFloat(dst, x);
        UNSAFE.putFloat(dst + Float.BYTES, y);
        UNSAFE.putFloat(dst + (2L * Float.BYTES), z);
        UNSAFE.putFloat(dst + (3L * Float.BYTES), w);
        return this;
    }

    public TestPersistentMappedSSBO addMatrix4f(int bindingPoint, Matrix4f matrix4f) {
        final int MATRIX_FLOATS = 16;
        final int MATRIX_BYTES = MATRIX_FLOATS * Float.BYTES;

        SSBOBindingData ssboData = getOrCreateSSBOData(bindingPoint);

        ensureSpaceAndShift(ssboData, (long) ssboData.dataCount * Float.BYTES + MATRIX_BYTES);

        long dst = mappedAddress + ssboData.offset + (long) ssboData.dataCount * Float.BYTES;
        matrix4f.getToAddress(dst);

        ssboData.dataCount += MATRIX_FLOATS;
        return this;
    }

    public TestPersistentMappedSSBO updateMatrix4f(int bindingPoint, int index, Matrix4f matrix4f) {
        SSBOBindingData ssboData = bindingDatum.get(bindingPoint);
        final int MATRIX_FLOATS = 16;

        if (ssboData == null || (index * MATRIX_FLOATS) + MATRIX_FLOATS > ssboData.dataCount) {
            throw new IndexOutOfBoundsException("Invalid binding point or index");
        }

        long dst = mappedAddress + ssboData.offset + (long) index * MATRIX_FLOATS * Float.BYTES;
        matrix4f.getToAddress(dst);

        return this;
    }

    public TestPersistentMappedSSBO addObjectParameter(int bindingPoint, ObjectParameter objectParameter) {
        SSBOBindingData ssboData = getOrCreateSSBOData(bindingPoint);

        ensureSpaceAndShift(ssboData, (long) (ssboData.dataCount + 1) * ObjectParameter.BYTES);

        Matrix4f currentVP = (objectParameter.getProjectionType() == ProjectionType.PERSPECTIVE_PROJECTION) ? precomputedViewProjection3D : precomputedViewProjection2D;
        long dst = mappedAddress + ssboData.offset + (long) ssboData.dataCount * ObjectParameter.BYTES;
        objectParameter.getToAddress(dst, currentVP);

        ssboData.dataCount++;
        return this;
    }

    public TestPersistentMappedSSBO updateObjectParameter(int bindingPoint, int index, ObjectParameter objectParameter) {
        SSBOBindingData ssboData = bindingDatum.get(bindingPoint);
        if (ssboData == null) {
            throw new IndexOutOfBoundsException("Invalid binding point");
        }

        Matrix4f currentVP = (objectParameter.getProjectionType() == ProjectionType.PERSPECTIVE_PROJECTION) ? precomputedViewProjection3D : precomputedViewProjection2D;
        long dst = mappedAddress + ssboData.offset + (long) index * ObjectParameter.BYTES;
        objectParameter.getToAddress(dst, currentVP);

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

                    otherData.offset = alignValue(otherData.offset, this.ssboOffsetAlignment);
                }
            }

            this.lastAddress += delta;
            this.lastAddress = alignValue(this.lastAddress, this.ssboOffsetAlignment);
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

    public TestPersistentMappedSSBO bindBufferRange() {
        for (Map.Entry<Integer, TestPersistentMappedSSBO.SSBOBindingData> bindingDataEntry : bindingDatum.entrySet()) {
            TestPersistentMappedSSBO.SSBOBindingData ssboBindingData = bindingDataEntry.getValue();

            glStateCache.bindBufferRange(GL_SHADER_STORAGE_BUFFER, bindingDataEntry.getKey(), bufferId, ssboBindingData.offset, ssboBindingData.size);
        }
        return this;
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

        if (oldAddr == 0) {
            DebugLog.warning(getClass(), "No existing mapped buffer to backup from.");
        } else if (bytesToCopy > 0) {
            long tmp = MemoryUtil.nmemAllocChecked(bytesToCopy);
            try {
                MemoryUtil.memCopy(oldAddr, tmp, bytesToCopy);

                boolean unmapped = glUnmapBuffer(GL_SHADER_STORAGE_BUFFER);
                if (!unmapped) {
                    DebugLog.error(getClass(), "glUnmapBuffer returned false (may indicate corruption).");
                }
                glStateCache.deleteShaderStorageBuffer(bufferId);
                bufferId = 0;
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
            glStateCache.deleteShaderStorageBuffer(bufferId);
            bufferId = 0;
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

    public TestPersistentMappedSSBO resetBindingPoint(int bindingPoint) {
        SSBOBindingData ssboData = bindingDatum.get(bindingPoint);

        if (ssboData != null) {
            ssboData.offset = 0;
            ssboData.dataCount = 0;
        } else {
            new LogGenerator("SSBO Reset Info")
                    .kv("Binding Point", bindingPoint)
                    .kv("Message", "Binding point does not exist. No reset performed.")
                    .logging(getClass(), DebugLevel.INFO);
        }

        return this;
    }

    public int getDataCount(int bindingPoint) {
        SSBOBindingData data = bindingDatum.get(bindingPoint);
        return (data != null) ? data.dataCount : 0;
    }

    @Override
    public void cleanup() {
        if (bufferId != 0) {
            glStateCache.deleteShaderStorageBuffer(bufferId);
            bufferId = 0;
        }
        bindingDatum.clear();
        bindingDatum = null;
    }

    @Override
    public int getBufferId() {
        return bufferId;
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
