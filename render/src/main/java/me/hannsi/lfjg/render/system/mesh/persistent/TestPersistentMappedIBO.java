package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.system.mesh.DrawElementsIndirectCommand;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL44;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static me.hannsi.lfjg.core.Core.UNSAFE;

public class TestPersistentMappedIBO implements TestPersistentMappedBuffer {
    private static final int[] TEMP_BUFFER = new int[DrawElementsIndirectCommand.COMMAND_COUNT];
    private static final long INT_BASE = UNSAFE.arrayBaseOffset(int[].class);
    private final int flags;
    private ByteBuffer mappedBuffer;
    private long mappedAddress;
    private int bufferId;
    private long gpuMemorySize;
    private int commandCount;

    public TestPersistentMappedIBO(int flags, int initialCapacity) {
        this.flags = flags;
        this.commandCount = 0;

        allocationBufferStorageCommands(initialCapacity);
    }

    private void allocationBufferStorageCommands(int capacity) {
        allocationBufferStorage(getCommandsSizeByte(capacity));
    }

    @Override
    public void allocationBufferStorage(long capacity) {
        gpuMemorySize = capacity;
        if (bufferId != 0) {
            GLStateCache.deleteIndirectBuffer(bufferId);
            bufferId = 0;
        }

        bufferId = GL15.glGenBuffers();
        GLStateCache.bindIndirectBuffer(bufferId);
        GL44.glBufferStorage(GL40.GL_DRAW_INDIRECT_BUFFER, gpuMemorySize, flags);

        ByteBuffer byteBuffer = GL30.glMapBufferRange(
                GL40.GL_DRAW_INDIRECT_BUFFER,
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

    public TestPersistentMappedIBO add(DrawElementsIndirectCommand cmd) {
        ensureCapacityForCommands(commandCount + 1);

        long baseOffset = getCommandsSizeByte(commandCount);
        writeCommand(baseOffset, cmd);

        commandCount++;

        return this;
    }

    public TestPersistentMappedIBO remove(int[] removeIndices) {
        if (removeIndices == null || removeIndices.length == 0) {
            return this;
        }

        int[] sorted = Arrays.stream(removeIndices)
                .distinct()
                .sorted()
                .filter(i -> i >= 0 && i < commandCount)
                .toArray();

        if (sorted.length == 0) {
            return this;
        }

        long vertexSizeBytes = getCommandsSizeByte(1);

        int writeIndex = sorted[0];
        int readIndex = writeIndex;

        int removePointer = 0;
        int removeCount = sorted.length;

        while (readIndex < commandCount) {
            if (removePointer < removeCount && readIndex == sorted[removePointer]) {
                removePointer++;
            } else {
                if (writeIndex != readIndex) {
                    long src = mappedAddress + getCommandsSizeByte(readIndex);
                    long dst = mappedAddress + getCommandsSizeByte(writeIndex);

                    MemoryUtil.memCopy(src, dst, vertexSizeBytes);
                }

                writeIndex++;
            }

            readIndex++;
        }

        commandCount = writeIndex;

        return this;
    }

    @Override
    public TestPersistentMappedIBO syncToGPU() {
        flushMappedRange(0, getCommandsSizeByte(commandCount));

        return this;
    }

    @Override
    public void flushMappedRange(long byteOffset, long byteLength) {
        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) != GL_MAP_COHERENT_BIT) {
            GL44.glFlushMappedBufferRange(GL40.GL_DRAW_INDIRECT_BUFFER, byteOffset, byteLength);
        }
    }

    private void ensureCapacityForCommands(int requiredIndices) {
        long requiredBytes = getCommandsSizeByte(requiredIndices);
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

    public void writeCommand(long baseByteOffset, DrawElementsIndirectCommand cmd) {
        TEMP_BUFFER[0] = cmd.count;
        TEMP_BUFFER[1] = cmd.instanceCount;
        TEMP_BUFFER[2] = cmd.firstIndex;
        TEMP_BUFFER[3] = cmd.baseVertex;
        TEMP_BUFFER[4] = cmd.baseInstance;

        long dst = mappedAddress + baseByteOffset;

        UNSAFE.copyMemory(
                TEMP_BUFFER,
                INT_BASE,
                null,
                dst,
                getCommandsSizeByte(1)
        );
    }

    public void directWriteCommand(long baseByteOffset, int offset, int value) {
        if (mappedAddress == 0 || mappedBuffer == null) {
            DebugLog.error(getClass(), "directWriteCommand: buffer not mapped (base=" + baseByteOffset + ", off=" + offset + ")");
            return;
        }

        long dst = mappedAddress + baseByteOffset + ((long) offset * Integer.BYTES);
        long end = mappedAddress + gpuMemorySize;
        if (dst < mappedAddress || dst + Integer.BYTES > end) {
            DebugLog.error(getClass(), String.format("directWriteCommand out of bounds. mappedAddress=%d gpuMemorySize=%d base=%d offset=%d dst=%d end=%d", mappedAddress, gpuMemorySize, baseByteOffset, offset, dst, end));
            return;
        }

        int current = UNSAFE.getInt(dst);
        if (current == value) {
            return;
        }

        UNSAFE.putInt(dst, value);
    }

    @Override
    public void growBuffer(long newGPUMemorySizeBytes) {
        if (newGPUMemorySizeBytes <= gpuMemorySize) {
            return;
        }

        long oldAddr = mappedAddress;
        long oldSize = gpuMemorySize;

        long bytesToCopy = (long) commandCount * DrawElementsIndirectCommand.BYTES;
        if (bytesToCopy > oldSize) {
            bytesToCopy = oldSize;
        }

        if (oldAddr == 0 || mappedBuffer == null) {
            DebugLog.warning(getClass(), "No existing mapped buffer to backup from.");
        } else if (bytesToCopy > 0) {
            long tmp = MemoryUtil.nmemAllocChecked(bytesToCopy);
            try {
                MemoryUtil.memCopy(oldAddr, tmp, bytesToCopy);

                boolean unmapped = GL30.glUnmapBuffer(GL40.GL_DRAW_INDIRECT_BUFFER);
                if (!unmapped) {
                    DebugLog.error(getClass(), "glUnmapBuffer returned false (may indicate corruption).");
                }
                GLStateCache.deleteIndirectBuffer(bufferId);
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
            GL30.glUnmapBuffer(GL40.GL_DRAW_INDIRECT_BUFFER);
            GLStateCache.deleteIndirectBuffer(bufferId);
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
                .kv("commandCount", commandCount)
                .logging(getClass(), DebugLevel.INFO);
    }

    public long getCommandsSizeByte(int commands) {
        return commands * DrawElementsIndirectCommand.BYTES;
    }

    public int getCommandCount() {
        return commandCount;
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
            GLStateCache.deleteIndirectBuffer(bufferId);
            bufferId = 0;
        }
        mappedBuffer = null;
    }

    public long getGpuMemorySize() {
        return gpuMemorySize;
    }
}