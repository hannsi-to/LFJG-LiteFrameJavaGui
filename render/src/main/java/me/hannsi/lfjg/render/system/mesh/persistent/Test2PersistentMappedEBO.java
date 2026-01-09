package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.persistent.PersistentMappedException;
import org.lwjgl.opengl.GL44;
import org.lwjgl.system.MemoryUtil;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static me.hannsi.lfjg.render.RenderSystemSetting.PERSISTENT_MAPPED_EBO_ALIGNMENT;
import static me.hannsi.lfjg.render.RenderSystemSetting.PERSISTENT_MAPPED_EBO_DEBUG;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glFlushMappedBufferRange;
import static org.lwjgl.opengl.GL30.nglMapBufferRange;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class Test2PersistentMappedEBO {
    private final int flags;
    private int bufferId;
    private long mappedAddress;
    private long memorySize;
    private long pointer;
    private boolean needFlush;

    public Test2PersistentMappedEBO(int flags, long initialCapacity) {
        this.flags = flags;

        allocationBufferStorage(initialCapacity);
    }

    public void allocationBufferStorage(long capacity) {
        if (capacity % PERSISTENT_MAPPED_EBO_ALIGNMENT != 0) {
            throw new PersistentMappedException("capacity must be multiple of " + PERSISTENT_MAPPED_EBO_ALIGNMENT);
        }

        memorySize = capacity;

        if (bufferId != 0) {
            glStateCache.deleteElementArrayBuffer(bufferId);
            debug("Delete element array buffer. BufferId: " + bufferId);
            bufferId = 0;
        }
        bufferId = glGenBuffers();
        debug("Generate element array buffer. BufferId: " + bufferId);
        glStateCache.bindElementArrayBufferForce(bufferId);

        glBufferStorage(GL_ELEMENT_ARRAY_BUFFER, memorySize, flags);
        long newMappedAddress = nglMapBufferRange(
                GL_ELEMENT_ARRAY_BUFFER,
                0,
                memorySize,
                flags
        );
        if (newMappedAddress == 0L) {
            throw new PersistentMappedException("glMapBufferRange failed");
        }
        mappedAddress = newMappedAddress;
        debug("glMapBufferRange complete. Target: GL_ELEMENT_ARRAY_BUFFER | offset: 0 | length: " + memorySize + " | access: " + flags);

        pointer = 0;
        debug("Set pointer: " + pointer);

        new LogGenerator(
                Test2PersistentMappedEBO.class.getSimpleName() + ": AllocationBufferStorage",
                "BufferId: " + bufferId,
                "MappedAddress: " + mappedAddress,
                "MemorySize: " + memorySize
        ).logging(getClass(), DebugLevel.INFO);
    }

    public Test2PersistentMappedEBO reset() {
        pointer = 0;

        return this;
    }

    public Test2PersistentMappedEBO add(int... indices) {
        ensure((long) Integer.BYTES * indices.length);

        for (int index : indices) {
            memPutInt(index);
        }

        return this;
    }

    public Test2PersistentMappedEBO add(int index) {
        ensure(Integer.BYTES);

        memPutInt(index);

        return this;
    }

    public Test2PersistentMappedEBO syncToGPU() {
        if (!needFlush) {
            return this;
        }

        needFlush = false;

        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            glFlushMappedBufferRange(GL_ELEMENT_ARRAY_BUFFER, 0, pointer);
            debug("Flushed " + pointer + " bytes to GPU");
        } else {
            debug("Coherent mapping - no flush needed");
        }

        return this;
    }

    public Test2PersistentMappedEBO link() {
        glStateCache.bindElementArrayBufferForce(bufferId);

        return this;
    }

    private Test2PersistentMappedEBO memPutInt(int value) {
        if (pointer + Integer.BYTES > memorySize) {
            throw new PersistentMappedException("Write would exceed mapped memory");
        }

        MemoryUtil.memPutInt(mappedAddress + pointer, value);
        pointer += Integer.BYTES;

        debug("Wrote int: " + value + ", new pointer: " + pointer);

        needFlush = true;

        return this;
    }

    private void ensure(long addSize) {
        long requiredSize = pointer + addSize;
        if (requiredSize <= memorySize) {
            return;
        }

        debug("Capacity exceeded. Expanding buffer... Current: " + memorySize + " bytes, Required: " + requiredSize + " bytes");

        long newCapacity = memorySize;
        while (newCapacity < requiredSize) {
            newCapacity += newCapacity >> 1;
        }
        newCapacity = (newCapacity + (PERSISTENT_MAPPED_EBO_ALIGNMENT - 1)) & -PERSISTENT_MAPPED_EBO_ALIGNMENT;

        debug("New capacity: " + newCapacity + " bytes");

        long oldAddress = mappedAddress;
        long oldSize = memorySize;

        long oldPointer = pointer;
        long copySize = pointer;
        if (copySize > 0) {
            long temp = MemoryUtil.nmemAllocChecked(copySize);
            debug("Copying " + copySize + " bytes to temporary buffer");
            MemoryUtil.memCopy(mappedAddress, temp, copySize);

            boolean unmapped = glUnmapBuffer(GL_ELEMENT_ARRAY_BUFFER);
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

    private void debug(String text) {
        if (PERSISTENT_MAPPED_EBO_DEBUG) {
            DebugLog.debug(getClass(), text);
        }
    }

    public void cleanup() {
        if (bufferId != 0) {
            glStateCache.deleteElementArrayBuffer(bufferId);
            bufferId = 0;
        }
        mappedAddress = 0L;
        memorySize = 0L;
        pointer = 0L;
    }

    public int getFlags() {
        return flags;
    }

    public int getBufferId() {
        return bufferId;
    }

    public long getMappedAddress() {
        return mappedAddress;
    }

    public long getMemorySize() {
        return memorySize;
    }

    public long getPointer() {
        return pointer;
    }
}
