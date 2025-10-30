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
import java.nio.IntBuffer;

public class TestPersistentMappedIBO implements PersistentMappedBuffer {
    private final int flags;
    private IntBuffer mappedBuffer;
    private int bufferId;
    private int gpuMemorySize;
    private int commandCount;

    public TestPersistentMappedIBO(int flags, int initialCapacity) {
        this.flags = flags;
        this.commandCount = 0;

        allocationBufferStorageCommands(initialCapacity);
    }

    private void allocationBufferStorageCommands(int capacity) {
        allocationBufferStorage(getCommandsSizeByte(capacity));
    }

    private void allocationBufferStorage(int capacity) {
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
        mappedBuffer = byteBuffer.asIntBuffer();
    }

    public TestPersistentMappedIBO addCommand(DrawElementsIndirectCommand cmd) {
        ensureCapacityForCommands(commandCount + 1);

        int base = commandCount * DrawElementsIndirectCommand.COMMAND_COUNT;
        writeCommand(mappedBuffer, base, cmd);

        commandCount++;

        return this;
    }

    public TestPersistentMappedIBO syncToGPU() {
        flushMappedRange(0, getCommandsSizeByte(commandCount));

        return this;
    }

    private void flushMappedRange(long byteOffset, long byteLength) {
        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) != GL_MAP_COHERENT_BIT) {
            GL44.glFlushMappedBufferRange(GL40.GL_DRAW_INDIRECT_BUFFER, byteOffset, byteLength);
        }
    }

    private void ensureCapacityForCommands(int requiredIndices) {
        int requiredBytes = getCommandsSizeByte(requiredIndices);
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

    private void writeCommand(IntBuffer buffer, int base, DrawElementsIndirectCommand command) {
        long memoryAddress = MemoryUtil.memAddress(buffer);
        long dst = memoryAddress + (long) base * Integer.BYTES;

        MemoryUtil.memPutInt(dst, command.count);
        MemoryUtil.memPutInt(dst + (long) Integer.BYTES, command.instanceCount);
        MemoryUtil.memPutInt(dst + 2L * Integer.BYTES, command.firstIndex);
        MemoryUtil.memPutInt(dst + 3L * Integer.BYTES, command.baseVertex);
        MemoryUtil.memPutInt(dst + 4L * Integer.BYTES, command.baseInstance);
    }

    private void growBuffer(int newGpuMemorySizeBytes) {
        if (newGpuMemorySizeBytes <= gpuMemorySize) {
            return;
        }

        new LogGenerator(
                "Grow Buffer Start",
                "OldSize: " + gpuMemorySize + " bytes",
                "NewSize: " + newGpuMemorySizeBytes + " bytes",
                "CommandCount: " + commandCount
        ).logging(getClass(), DebugLevel.INFO, true, true);

        final int intsToCopy = commandCount * DrawElementsIndirectCommand.COMMAND_COUNT;
        int[] backup = new int[Math.max(0, intsToCopy)];

        if (mappedBuffer != null && intsToCopy > 0) {
            try {
                IntBuffer reader = mappedBuffer.duplicate();
                reader.position(0);
                int safeLimit = Math.min(reader.capacity(), intsToCopy);
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
            GLStateCache.bindIndirectBuffer(bufferId);
            boolean unmapped = GL30.glUnmapBuffer(GL40.GL_DRAW_INDIRECT_BUFFER);
            if (!unmapped) {
                DebugLog.error(getClass(), "glUnmapBuffer returned false (may indicate corruption).");
            } else {
                DebugLog.info(getClass(), "Buffer unmapped successfully.");
            }

            GLStateCache.deleteIndirectBuffer(bufferId);
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
                mappedBuffer.position(commandCount);
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

    private int getCommandsSizeByte(int commands) {
        return commands * DrawElementsIndirectCommand.BYTES;
    }

    public int getCommandCount() {
        return commandCount;
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
            GLStateCache.deleteIndirectBuffer(bufferId);
            bufferId = 0;
        }
        mappedBuffer = null;
    }
}