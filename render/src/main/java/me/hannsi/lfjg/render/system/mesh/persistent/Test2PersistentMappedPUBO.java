package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.persistent.PersistentMappedException;
import org.lwjgl.opengl.GL44;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static me.hannsi.lfjg.render.RenderSystemSetting.*;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;
import static org.lwjgl.opengl.GL21.GL_PIXEL_UNPACK_BUFFER;
import static org.lwjgl.opengl.GL30.glFlushMappedBufferRange;
import static org.lwjgl.opengl.GL30.nglMapBufferRange;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class Test2PersistentMappedPUBO implements Test2PersistentMappedBuffer {
    private final int flags;
    private final long initialPUBOCapacity;
    private final Map<Integer, PixelUnpackBufferData> puboDatum;
    private int bufferId;
    private long mappedAddress;
    private long memorySize;
    private boolean needFlush;

    public Test2PersistentMappedPUBO(int flags, long initialCapacity, long initialPUBOCapacity) {
        this.flags = flags;
        this.initialPUBOCapacity = initialPUBOCapacity;
        this.puboDatum = new HashMap<>();

        allocationBufferStorage(initialCapacity);
    }

    @Override
    public void allocationBufferStorage(long capacity) {
        if (capacity % PERSISTENT_MAPPED_PUBO_ALIGNMENT != 0) {
            throw new PersistentMappedException("capacity must be multiple of " + PERSISTENT_MAPPED_PUBO_ALIGNMENT);
        }

        memorySize = capacity;

        if (bufferId != 0) {
            glStateCache.deletePixelUnpackBuffer(bufferId);
            debug("Delete pixel unpack buffer. BufferId: " + bufferId);
            bufferId = 0;
        }
        bufferId = glGenBuffers();
        debug("Generate pixel unpack buffer. BufferId: " + bufferId);
        glStateCache.bindPixelUnpackBufferForce(bufferId);

        glBufferStorage(GL_PIXEL_UNPACK_BUFFER, memorySize, flags);
        long newMappedAddress = nglMapBufferRange(
                GL_PIXEL_UNPACK_BUFFER,
                0,
                memorySize,
                flags
        );
        if (newMappedAddress == 0L) {
            throw new PersistentMappedException("glMapBufferRange failed");
        }
        mappedAddress = newMappedAddress;
        debug("glMapBufferRange complete. Target: GL_PIXEL_UNPACK_BUFFER | offset: 0 | length: " + memorySize + " | access: " + flags);

        new LogGenerator(
                Test2PersistentMappedPUBO.class.getSimpleName() + ": AllocationBufferStorage",
                "BufferId: " + bufferId,
                "MappedAddress: " + mappedAddress,
                "MemorySize: " + memorySize
        ).logging(getClass(), DebugLevel.INFO);
    }

    public long addByteBuffer(int segmentId, ByteBuffer value) {
        if (!puboDatum.containsKey(segmentId)) {
            long offset = 0L;
            for (PixelUnpackBufferData entry : puboDatum.values()) {
                offset = Math.max(offset, entry.getLastAddress());
            }

            offset = (offset + (PERSISTENT_MAPPED_SSBO_ALIGNMENT - 1)) & -PERSISTENT_MAPPED_SSBO_ALIGNMENT;

            puboDatum.put(segmentId, new PixelUnpackBufferData(offset, initialPUBOCapacity, 0L));
        }
        PixelUnpackBufferData puboData = puboDatum.get(segmentId);

        ensurePUBODatum(puboData, value.remaining());

        long offset = puboData.offset + puboData.pointer;
        memPutByteBuffer(offset, value);

        puboData.pointer += value.remaining();

        return offset;
    }

    public Test2PersistentMappedPUBO updateByteBuffer(int segmentId, long offset, ByteBuffer value) {
        PixelUnpackBufferData puboData = puboDatum.get(segmentId);
        if (puboData == null) {
            throw new IndexOutOfBoundsException("Invalid segment id");
        }

        memPutByteBuffer(offset, value);

        return this;
    }

    @Override
    public Test2PersistentMappedPUBO syncToGPU() {
        if (!needFlush) {
            return this;
        }

        needFlush = false;

        long lastPointer = 0L;
        for (Map.Entry<Integer, PixelUnpackBufferData> entry : puboDatum.entrySet()) {
            if (lastPointer < entry.getValue().getLastAddress()) {
                lastPointer = entry.getValue().getLastAddress();
            }
        }

        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            glFlushMappedBufferRange(GL_PIXEL_UNPACK_BUFFER, 0, lastPointer);
            debug("Flushed " + lastPointer + " bytes to GPU");
        } else {
            debug("Coherent mapping - no flush needed");
        }

        return this;
    }

    public Test2PersistentMappedPUBO resetSegmentId(int segmentId) {
        PixelUnpackBufferData puboData = puboDatum.get(segmentId);

        if (puboData != null) {
            puboData.pointer = 0;
            MemoryUtil.memSet(mappedAddress + puboData.offset, 0, puboData.size);
        } else {
            new LogGenerator("PUBO Reset Info")
                    .kv("Binding Point", segmentId)
                    .kv("Message", "Binding point does not exist. No reset performed.")
                    .logging(getClass(), DebugLevel.INFO);
        }

        return this;
    }

    private Test2PersistentMappedPUBO memPutByteBuffer(long pointer, ByteBuffer value) {
        if (pointer + value.remaining() > memorySize) {
            throw new PersistentMappedException("Write would exceed mapped memory");
        }

        long src = MemoryUtil.memAddress(value);
        long dst = mappedAddress + pointer;
        long size = value.remaining();

        MemoryUtil.memCopy(src, dst, size);

        needFlush = true;

        return this;
    }

    private void ensurePUBODatum(PixelUnpackBufferData puboData, long addSize) {
        long requiredSize = puboData.pointer + addSize;
        if (puboData.offset + requiredSize <= memorySize && requiredSize <= puboData.size) {
            return;
        }

        long oldSize = puboData.size;
        long newSize = (requiredSize + (PERSISTENT_MAPPED_PUBO_ALIGNMENT - 1)) & -PERSISTENT_MAPPED_PUBO_ALIGNMENT;
        long delta = newSize - oldSize;

        long totalRequired = puboDatum.values().stream().mapToLong(d -> d.size).sum() + delta;
        ensure(totalRequired);

        puboDatum.entrySet().stream()
                .filter(e -> e.getValue().offset > puboData.offset)
                .sorted((a, b) -> Long.compare(b.getValue().offset, a.getValue().offset))
                .forEach(e -> {
                    PixelUnpackBufferData data = e.getValue();
                    MemoryUtil.memCopy(mappedAddress + data.offset, mappedAddress + data.offset + delta, data.size);
                    data.offset += delta;
                });

        puboData.size = newSize;
    }

    @Override
    public void ensure(long requiredSize) {
        if (requiredSize <= memorySize) {
            return;
        }

        debug("Capacity exceeded. Expanding buffer... Current: " + memorySize + " bytes, Required: " + requiredSize + " bytes");

        long newCapacity = memorySize;
        while (newCapacity < requiredSize) {
            newCapacity += newCapacity >> 1;
        }
        newCapacity = (newCapacity + (PERSISTENT_MAPPED_PUBO_ALIGNMENT - 1)) & -PERSISTENT_MAPPED_PUBO_ALIGNMENT;

        debug("New capacity: " + newCapacity + " bytes");

        long oldAddress = mappedAddress;
        long oldSize = memorySize;

        long copySize = memorySize;
        if (copySize > 0) {
            long temp = MemoryUtil.nmemAllocChecked(copySize);
            debug("Copying " + copySize + " bytes to temporary buffer");
            MemoryUtil.memCopy(mappedAddress, temp, copySize);

            boolean unmapped = glUnmapBuffer(GL_PIXEL_UNPACK_BUFFER);
            if (!unmapped) {
                throw new PersistentMappedException("glUnmapBuffer returned false (may indicate corruption).");
            } else {
                debug("Successfully unmapped buffer");
            }

            allocationBufferStorage(newCapacity);
            debug("Buffer re-allocated and mapped at address: " + mappedAddress);

            MemoryUtil.memCopy(temp, mappedAddress, copySize);

            if (temp != 0L) {
                MemoryUtil.nmemFree(temp);
            }
            debug("Temporary buffer freed");
        }

        new LogGenerator("Ensure Buffer")
                .kvHex("oldAddress", oldAddress)
                .kvBytes("oldSize", oldSize)
                .text("\n")
                .kvHex("newAddress", mappedAddress)
                .kvBytes("newSize", memorySize)
                .text("\n")
                .kvBytes("copiedSize", copySize)
                .logging(getClass(), DebugLevel.INFO);
    }

    public long getSegmentOffset(int segmentId) {
        PixelUnpackBufferData data = puboDatum.get(segmentId);
        return (data != null) ? data.offset : -1L;
    }

    @Override
    public void debug(String text) {
        if (PERSISTENT_MAPPED_PUBO_DEBUG) {
            DebugLog.debug(getClass(), text);
        }
    }

    @Override
    public void cleanup() {
        if (bufferId != 0) {
            glStateCache.deletePixelUnpackBuffer(bufferId);
            bufferId = 0;
        }
        mappedAddress = 0L;
        memorySize = 0L;
    }

    @Override
    public Test2PersistentMappedPUBO link() {
        glStateCache.bindPixelUnpackBufferForce(bufferId);

        return this;
    }

    @Override
    public int getFlags() {
        return flags;
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
    public long getMemorySize() {
        return memorySize;
    }

    @Override
    public boolean isNeedFlush() {
        return needFlush;
    }

    public static class PixelUnpackBufferData {
        public long offset;
        public long size;
        public long pointer;

        public PixelUnpackBufferData(long offset, long size, long pointer) {
            this.offset = offset;
            this.size = size;
            this.pointer = pointer;
        }

        public long getLastAddress() {
            return offset + size;
        }
    }
}
