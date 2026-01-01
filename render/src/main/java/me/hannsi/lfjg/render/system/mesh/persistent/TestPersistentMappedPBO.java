package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import org.lwjgl.opengl.GL44;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.core.Core.UNSAFE;
import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;
import static org.lwjgl.opengl.GL21.GL_PIXEL_UNPACK_BUFFER;
import static org.lwjgl.opengl.GL30.glFlushMappedBufferRange;
import static org.lwjgl.opengl.GL30.glMapBufferRange;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class TestPersistentMappedPBO implements TestPersistentMappedBuffer {
    private final int flags;
    private Map<Integer, PBOSegmentData> segments;
    private int bufferId;
    private long gpuMemorySize;
    private long mappedAddress;
    private long lastAddress;

    public TestPersistentMappedPBO(int flags, int initialCapacityBytes) {
        this.flags = flags;
        this.segments = new HashMap<>();

        allocationBufferStorage(initialCapacityBytes);
    }

    @Override
    public void allocationBufferStorage(long capacity) {
        this.gpuMemorySize = capacity;
        if (bufferId != 0) {
            glStateCache.bindPixelUnpackBuffer(bufferId);
            bufferId = 0;
        }

        bufferId = glGenBuffers();
        glStateCache.bindPixelUnpackBuffer(bufferId);
        glBufferStorage(GL_PIXEL_UNPACK_BUFFER, gpuMemorySize, flags);

        ByteBuffer byteBuffer = glMapBufferRange(
                GL_PIXEL_UNPACK_BUFFER,
                0,
                gpuMemorySize,
                flags
        );
        if (byteBuffer == null) {
            throw new RuntimeException("glMapBufferRange failed!");
        }
        mappedAddress = MemoryUtil.memAddress(byteBuffer);
    }

    public long uploadToSegment(int segmentId, ByteBuffer data) {
        PBOSegmentData segment = getOrCreateSegment(segmentId);
        int size = data.remaining();

        ensureSpaceAndShift(segment, segment.currentSize + size);

        long targetOffset = segment.offset + segment.currentSize;
        long dst = mappedAddress + targetOffset;
        long src = MemoryUtil.memAddress(data);

        UNSAFE.copyMemory(src, dst, size);
        segment.currentSize += size;

        lastAddress = Math.max(lastAddress, segment.offset + segment.allocationSize);

        return targetOffset;
    }

    public TestPersistentMappedPBO updateSegment(int segmentId, long offsetInSegment, ByteBuffer data) {
        PBOSegmentData segment = segments.get(segmentId);
        if (segment == null) {
            throw new IllegalArgumentException("Segment ID " + segmentId + " does not exist.");
        }

        int size = data.remaining();
        if (offsetInSegment + size > segment.allocationSize) {
            throw new IndexOutOfBoundsException("Update range out of segment allocation.");
        }

        long dst = mappedAddress + segment.offset + offsetInSegment;
        long src = MemoryUtil.memAddress(data);

        UNSAFE.copyMemory(src, dst, size);

        if (offsetInSegment + size > segment.currentSize) {
            segment.currentSize = offsetInSegment + size;
        }

        return this;
    }

    public void resetSegment(int segmentId) {
        PBOSegmentData segmentData = segments.get(segmentId);

        if (segmentData != null) {
            segmentData.currentSize = 0;
        } else {
            new LogGenerator("PBO Reset Info")
                    .kv("Segment Id", segmentId)
                    .kv("Message", "Segment Id does not exist. No reset performed.")
                    .logging(getClass(), DebugLevel.INFO);
        }
    }

    public long getSegmentOffset(int segmentId) {
        PBOSegmentData segment = segments.get(segmentId);
        return (segment != null) ? segment.offset : 0;
    }

    private PBOSegmentData getOrCreateSegment(int segment) {
        return segments.computeIfAbsent(
                segment, id -> {
                    long startOffset = lastAddress;
                    return new PBOSegmentData(startOffset, 0);
                }
        );
    }

    private void ensureSpaceAndShift(PBOSegmentData segment, long requiredBytes) {
        if (requiredBytes <= segment.allocationSize) {
            return;
        }

        long oldAllocated = segment.allocationSize;
        long newAllocated = max(requiredBytes, oldAllocated + (oldAllocated >> 1));
        if (newAllocated == 0) {
            newAllocated = requiredBytes;
        }

        long delta = newAllocated - oldAllocated;
        ensureCapacity(lastAddress + delta);

        for (PBOSegmentData other : segments.values()) {
            if (other.offset > segment.offset) {
                UNSAFE.copyMemory(mappedAddress + other.offset, mappedAddress + other.offset + delta, other.currentSize);
                other.offset += delta;
            }
        }

        segment.allocationSize = newAllocated;
        lastAddress += delta;
    }

    private void ensureCapacity(long requiredBytes) {
        if (requiredBytes > gpuMemorySize) {
            growBuffer(max(requiredBytes, gpuMemorySize + (gpuMemorySize >> 1)));
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
            glFlushMappedBufferRange(GL_PIXEL_UNPACK_BUFFER, 0, lastAddress);
        }
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

                boolean unmapped = glUnmapBuffer(GL_PIXEL_UNPACK_BUFFER);
                if (!unmapped) {
                    DebugLog.error(getClass(), "glUnmapBuffer returned false (my indicate corruption).");
                }
                glStateCache.deletePixelUnpackBuffer(bufferId);
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
            glUnmapBuffer(GL_PIXEL_UNPACK_BUFFER);
            glStateCache.deletePixelUnpackBuffer(bufferId);
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
                .kv("pboSegmentCount", segments.size())
                .logging(getClass(), DebugLevel.INFO);
    }

    @Override
    public void cleanup() {
        if (bufferId != 0) {
            glStateCache.deletePixelUnpackBuffer(bufferId);
            bufferId = 0;
        }
        segments.clear();
        segments = null;
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

    private static class PBOSegmentData {
        public long offset;
        public long allocationSize;
        public long currentSize;

        public PBOSegmentData(long offset, long allocationSize) {
            this.offset = offset;
            this.allocationSize = allocationSize;
        }
    }
}
