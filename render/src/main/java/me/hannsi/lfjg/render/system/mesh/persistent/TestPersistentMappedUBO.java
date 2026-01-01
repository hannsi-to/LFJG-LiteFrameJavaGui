package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.math.RowColumn;
import me.hannsi.lfjg.render.system.shader.STD140UniformBlockType;
import org.lwjgl.opengl.GL44;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.core.Core.UNSAFE;
import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;
import static org.lwjgl.opengl.GL44.glBufferStorage;


public class TestPersistentMappedUBO implements TestPersistentMappedBuffer {
    public static long DEFAULT_UBO_CAPACITY = STD140UniformBlockType.MAT4.getByteSize() * 3L;
    private final int flags;
    private final Map<Integer, UBOData> uboDatum;
    private long mappedAddress;
    private int bufferId;
    private long gpuMemorySize;

    public TestPersistentMappedUBO(int flags, int initialCapacity) {
        this.flags = flags;
        this.uboDatum = new HashMap<>();

        allocationBufferStorageUBOs(initialCapacity);
    }

    private void allocationBufferStorageUBOs(int capacity) {
        allocationBufferStorage(DEFAULT_UBO_CAPACITY * capacity);
    }

    @Override
    public void allocationBufferStorage(long capacity) {
        gpuMemorySize = capacity;
        if (bufferId != 0) {
            glStateCache.deleteUniformBuffer(bufferId);
            bufferId = 0;
        }

        bufferId = glGenBuffers();
        glStateCache.bindUniformBuffer(bufferId);
        glBufferStorage(GL_UNIFORM_BUFFER, gpuMemorySize, flags);

        ByteBuffer byteBuffer = glMapBufferRange(
                GL_UNIFORM_BUFFER,
                0,
                gpuMemorySize,
                flags
        );
        if (byteBuffer == null) {
            throw new RuntimeException("glMapBufferRange failed");
        }
        mappedAddress = MemoryUtil.memAddress(byteBuffer);
    }

    public TestPersistentMappedUBO add(int bindingPoint, STD140UniformBlockType[] types, Object... values) {
        if (types.length != values.length) {
            throw new IllegalArgumentException("The length of the types does not match the length of the values. typesLength: " + types.length + " | valuesLength: " + values.length);
        }

        long currentTotalSize = getUBOsSizeBytes();
        final int BLOCK_BASE_ALIGNMENT = 16;
        long alignmentPadding = (BLOCK_BASE_ALIGNMENT - (currentTotalSize & (BLOCK_BASE_ALIGNMENT - 1))) & (BLOCK_BASE_ALIGNMENT - 1);
        long baseOffset = currentTotalSize + alignmentPadding;
        long uboSize = getUBOsSizeBytes(types);

        ensureCapacityForUBOs(baseOffset, uboSize);
        writeUBO(baseOffset, types, values);

        uboDatum.put(bindingPoint, new UBOData(baseOffset, uboSize));

        return this;
    }

    @Override
    public TestPersistentMappedUBO syncToGPU() {
        flushMappedRange(0, getUBOsSizeBytes() + 16);

        return null;
    }

    @Override
    public void flushMappedRange(long byteOffset, long byteLength) {
        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            glFlushMappedBufferRange(GL_UNIFORM_BUFFER, byteOffset, byteLength);
        }
    }

    private void ensureCapacityForUBOs(long baseOffset, long uboSize) {
        long requiredBytes = baseOffset + uboSize;

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

    public void bindUBO(int bindingPoint) {
        UBOData uboData = uboDatum.get(bindingPoint);
        if (uboData == null) {
            throw new RuntimeException("Not found this bindingPoint. bindingPoint: " + bindingPoint);
        }

        glBindBufferRange(GL_UNIFORM_BUFFER, bindingPoint, bufferId, uboData.offset, uboData.size);
    }

    @Override
    public void growBuffer(long newGPUMemorySizeBytes) {
        if (newGPUMemorySizeBytes <= gpuMemorySize) {
            return;
        }

        long oldAddr = mappedAddress;
        long oldSize = gpuMemorySize;
        long bytesToCopy = getUBOsSizeBytes();

        if (bytesToCopy > oldSize) {
            bytesToCopy = oldSize;
        }

        if (oldAddr == 0) {
            DebugLog.warning(getClass(), "No existing mapped buffer to backup from.");
        } else if (bytesToCopy > 0) {
            long tmp = MemoryUtil.nmemAllocChecked(bytesToCopy);
            try {
                MemoryUtil.memCopy(oldAddr, tmp, bytesToCopy);

                boolean unmapped = glUnmapBuffer(GL_UNIFORM_BUFFER);
                if (!unmapped) {
                    DebugLog.error(getClass(), "glUnmapBuffer returned false (may indicate corruption).");
                }
                glStateCache.deleteUniformBuffer(bufferId);
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
            glUnmapBuffer(GL_UNIFORM_BUFFER);
            glStateCache.deleteUniformBuffer(bufferId);
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
                .kv("UBO Count", uboDatum.size())
                .logging(getClass(), DebugLevel.INFO);
    }

    public void writeUBO(long baseByteOffset, STD140UniformBlockType[] std140UniformBlockTypes, Object[] values) {
        long dst = mappedAddress + baseByteOffset;
        int valueIndex = 0;

        for (STD140UniformBlockType member : std140UniformBlockTypes) {
            final int alignment = member.getAlignment();
            long alignmentPadding = (alignment - (dst & (alignment - 1))) & (alignment - 1);

            dst += alignmentPadding;

            Object value = values[valueIndex++];
            switch (member) {
                case FLOAT:
                case BOOL:
                case INT:
                case UINT:
                    UNSAFE.putInt(dst, ((Number) value).intValue());
                    dst += 4;
                    break;
                case DOUBLE:
                    UNSAFE.putDouble(dst, ((Number) value).doubleValue());
                    dst += 8;
                    break;
                case VEC2:
                case VEC3:
                case VEC4:
                case IVEC2:
                case IVEC3:
                case IVEC4: {
                    float[] floatValues = (float[]) value;
                    for (float f : floatValues) {
                        UNSAFE.putFloat(dst, f);
                        dst += 4;
                    }
                    break;
                }

                case MAT4: {
                    final int matrixStride = member.getMatrixStride();
                    final int componentSize = 4;
                    long matrixEndDst = dst + member.getByteSize();

                    float[] matrixValues = (float[]) value;
                    if (member.getMatrixOrder() == RowColumn.ROW) {
                        for (int i = 0; i < 4; i++) {
                            long rowStartDst = dst;
                            for (int j = 0; j < 4; j++) {
                                UNSAFE.putFloat(rowStartDst + (long) j * componentSize, matrixValues[i * 4 + j]);
                            }
                            dst += matrixStride;
                        }
                    } else {
                        for (int i = 0; i < 4; i++) {
                            long colStartDst = dst;
                            for (int j = 0; j < 4; j++) {
                                UNSAFE.putFloat(colStartDst + (long) j * componentSize, matrixValues[j * 4 + i]);
                            }
                            dst += matrixStride;
                        }
                    }
                    dst = matrixEndDst;
                    break;
                }

                case VEC4_4: {
                    float[] arrayValues = (float[]) value;
                    final int elementSize = member.getArrayStride();
                    final int elementComponentCount = 4;

                    for (int i = 0; i < arrayValues.length / elementComponentCount; i++) {
                        long elementStartDst = dst;
                        for (int j = 0; j < elementComponentCount; j++) {
                            UNSAFE.putFloat(elementStartDst + (long) j * 4, arrayValues[i * elementComponentCount + j]);
                        }
                        dst += elementSize;
                    }
                    break;
                }

                default:
                    throw new UnsupportedOperationException("Unsupported STD140 type for direct write: " + member.getName());
            }
        }
    }

    public long getUBOsSizeBytes(STD140UniformBlockType... std140UniformBlockTypes) {
        long currentOffset = 0L;

        for (STD140UniformBlockType member : std140UniformBlockTypes) {
            final int alignment = member.getAlignment();
            long alignmentPadding = (alignment - (currentOffset & (alignment - 1))) & (alignment - 1);

            currentOffset += alignmentPadding;
            currentOffset += member.getByteSize();
        }

        return currentOffset;
    }

    public long getUBOsSizeBytes() {
        long bytes = 0L;
        for (Map.Entry<Integer, UBOData> uboEntry : uboDatum.entrySet()) {
            bytes = Math.max(bytes, uboEntry.getValue().offset + uboEntry.getValue().size);
        }

        return bytes;
    }

    @Override
    public void cleanup() {
        if (bufferId != 0) {
            glStateCache.deleteUniformBuffer(bufferId);
            bufferId = 0;
        }
        uboDatum.clear();
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

    public static class UBOData {
        public long offset;
        public long size;

        public UBOData(long offset, long size) {
            this.offset = offset;
            this.size = size;
        }
    }
}