package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.persistent.PersistentMappedException;
import me.hannsi.lfjg.render.system.mesh.DrawElementsIndirectCommand;
import org.lwjgl.opengl.GL44;
import org.lwjgl.system.MemoryUtil;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static me.hannsi.lfjg.render.RenderSystemSetting.PERSISTENT_MAPPED_IBO_ALIGNMENT;
import static me.hannsi.lfjg.render.RenderSystemSetting.PERSISTENT_MAPPED_IBO_DEBUG;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;
import static org.lwjgl.opengl.GL30.glFlushMappedBufferRange;
import static org.lwjgl.opengl.GL30.nglMapBufferRange;
import static org.lwjgl.opengl.GL40.GL_DRAW_INDIRECT_BUFFER;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class Test2PersistentMappedIBO implements Test2PersistentMappedBuffer {
    private final int flags;
    private int bufferId;
    private long mappedAddress;
    private long memorySize;
    private long pointer;
    private boolean needFlush;

    public Test2PersistentMappedIBO(int flags, long initialCapacity) {
        this.flags = flags;

        allocationBufferStorage(initialCapacity);
    }

    @Override
    public void allocationBufferStorage(long capacity) {
        if (capacity % PERSISTENT_MAPPED_IBO_ALIGNMENT != 0) {
            throw new IllegalArgumentException("capacity must be multiple of " + PERSISTENT_MAPPED_IBO_ALIGNMENT);
        }

        memorySize = capacity;

        if (bufferId != 0) {
            glStateCache.deleteIndirectBuffer(bufferId);
            debug("Deleted indirect buffer. BufferId: " + bufferId);
            bufferId = 0;

        }
        bufferId = glGenBuffers();
        debug("Generate indirect buffer. BufferId: " + bufferId);
        glStateCache.bindIndirectBufferForce(bufferId);

        glBufferStorage(GL_DRAW_INDIRECT_BUFFER, memorySize, flags);
        long newMappedAddress = nglMapBufferRange(
                GL_DRAW_INDIRECT_BUFFER,
                0,
                memorySize,
                flags
        );
        if (newMappedAddress == 0L) {
            throw new PersistentMappedException("glMapBufferRange failed");
        }
        mappedAddress = newMappedAddress;
        debug("glMapBufferRange complete. Target: GL_DRAW_INDIRECT_BUFFER | offset: 0 | length: " + memorySize + " | access: " + flags);

        pointer = 0L;
        debug("Set pointer: " + pointer);

        new LogGenerator(
                Test2PersistentMappedIBO.class.getSimpleName() + ": AllocationBufferStorage",
                "BufferId: " + bufferId,
                "MappedAddress: " + mappedAddress,
                "MemorySize: " + memorySize
        ).logging(getClass(), DebugLevel.INFO);
    }

    public Test2PersistentMappedIBO reset() {
        pointer = 0;

        return this;
    }

    public Test2PersistentMappedIBO add(DrawElementsIndirectCommand... commands) {
        ensure(DrawElementsIndirectCommand.BYTES * commands.length);

        for (DrawElementsIndirectCommand command : commands) {
            add(command);
        }

        return this;
    }

    public Test2PersistentMappedIBO add(DrawElementsIndirectCommand command) {
        ensure(DrawElementsIndirectCommand.BYTES);

        memPutInt(command.count)
                .memPutInt(command.instanceCount)
                .memPutInt(command.firstIndex)
                .memPutInt(command.baseVertex)
                .memPutInt(command.baseInstance);

        return this;
    }

    public Test2PersistentMappedIBO update(long pointer, DrawElementsIndirectCommand command) {
        if (pointer >= memorySize - DrawElementsIndirectCommand.BYTES) {
            throw new PersistentMappedException("Pointer out of bounces: " + pointer + " >= " + (memorySize - DrawElementsIndirectCommand.BYTES));
        }

        memPutInt(pointer, command.count);
        pointer += Integer.BYTES;
        memPutInt(pointer, command.instanceCount);
        pointer += Integer.BYTES;
        memPutInt(pointer, command.firstIndex);
        pointer += Integer.BYTES;
        memPutInt(pointer, command.baseVertex);
        pointer += Integer.BYTES;
        memPutInt(pointer, command.baseInstance);

        return this;
    }

    public Test2PersistentMappedIBO update(long pointer, int offset, int value) {
        if (pointer >= memorySize - DrawElementsIndirectCommand.BYTES) {
            throw new PersistentMappedException("Pointer out of bounces: " + pointer + " >= " + (memorySize - DrawElementsIndirectCommand.BYTES));
        }

        memPutInt(pointer + offset, value);

        return this;
    }

    @Override
    public Test2PersistentMappedIBO syncToGPU() {
        if (!needFlush) {
            return this;
        }

        needFlush = false;

        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            glFlushMappedBufferRange(GL_DRAW_INDIRECT_BUFFER, 0, pointer);
            debug("Flushed " + pointer + " bytes to GPU");
        } else {
            debug("Coherent mapping - no flush needed");
        }

        return this;
    }

    private Test2PersistentMappedIBO memPutInt(int value) {
        memPutInt(pointer, value);
        pointer += Integer.BYTES;

        debug("Wrote int: " + value + ", new pointer: " + pointer);

        return this;
    }

    private Test2PersistentMappedIBO memPutInt(long pointer, int value) {
        if (pointer + Integer.BYTES > memorySize) {
            throw new PersistentMappedException("Write would exceed mapped memory");
        }

        MemoryUtil.memPutInt(mappedAddress + pointer, value);

        needFlush = true;

        return this;
    }

    @Override
    public void ensure(long addSize) {
        long requiredSize = pointer + addSize;
        if (requiredSize <= memorySize) {
            return;
        }

        debug("Capacity exceeded. Expanding buffer... Current: " + memorySize + " bytes, Required: " + requiredSize + " bytes");

        long newCapacity = memorySize;
        while (newCapacity < requiredSize) {
            newCapacity += newCapacity >> 1;
        }
        newCapacity = (newCapacity + (PERSISTENT_MAPPED_IBO_ALIGNMENT - 1)) & -PERSISTENT_MAPPED_IBO_ALIGNMENT;

        debug("New capacity: " + newCapacity + " bytes");

        long oldAddress = mappedAddress;
        long oldSize = memorySize;

        long oldPointer = pointer;
        long copySize = pointer;
        if (copySize > 0) {
            long temp = MemoryUtil.nmemAllocChecked(copySize);
            debug("Copying " + copySize + " bytes to temporary buffer");
            MemoryUtil.memCopy(mappedAddress, temp, copySize);

            boolean unmapped = glUnmapBuffer(GL_DRAW_INDIRECT_BUFFER);
            if (!unmapped) {
                throw new PersistentMappedException("glUnmapBuffer returned false (may indicate corruption).");
            } else {
                debug("Successfully unmapped buffer");
            }

            allocationBufferStorage(newCapacity);
            debug("Buffer re-allocated and mapped at address: " + mappedAddress);

            MemoryUtil.memCopy(temp, mappedAddress, copySize);
            pointer = oldPointer;
            debug("Set pointer: " + pointer);

            debug("Pointer after adding Index: " + pointer + " / MemorySize: " + memorySize);
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

    @Override
    public void debug(String text) {
        if (PERSISTENT_MAPPED_IBO_DEBUG) {
            DebugLog.debug(getClass(), text);
        }
    }

    @Override
    public void cleanup() {
        if (bufferId != 0) {
            glStateCache.deleteIndirectBuffer(bufferId);
            bufferId = 0;
        }
        mappedAddress = 0L;
        memorySize = 0L;
        pointer = 0L;
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

    public long getPointer() {
        return pointer;
    }

    public Test2PersistentMappedIBO link() {
        glStateCache.bindIndirectBufferForce(bufferId);

        return this;
    }
}
