package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.persistent.PersistentMappedException;
import me.hannsi.lfjg.render.system.mesh.BufferObjectType;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import org.lwjgl.opengl.GL44;
import org.lwjgl.system.MemoryUtil;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static me.hannsi.lfjg.render.RenderSystemSetting.PERSISTENT_MAPPED_VBO_ALIGNMENT;
import static me.hannsi.lfjg.render.RenderSystemSetting.PERSISTENT_MAPPED_VBO_DEBUG;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glFlushMappedBufferRange;
import static org.lwjgl.opengl.GL30.nglMapBufferRange;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class Test2PersistentMappedVBO implements Test2PersistentMappedBuffer {
    private final int flags;
    private int bufferId;
    private long mappedAddress;
    private long memorySize;
    private long pointer;
    private boolean needFlush;

    public Test2PersistentMappedVBO(int flags, long initialCapacity) {
        this.flags = flags;

        allocationBufferStorage(initialCapacity);
    }

    @Override
    public void allocationBufferStorage(long capacity) {
        if (capacity % PERSISTENT_MAPPED_VBO_ALIGNMENT != 0) {
            throw new IllegalArgumentException("capacity must be multiple of " + PERSISTENT_MAPPED_VBO_ALIGNMENT);
        }

        memorySize = capacity;

        if (bufferId != 0) {
            glStateCache.deleteArrayBuffer(bufferId);
            debug("Deleted array buffer. BufferId: " + bufferId);
            bufferId = 0;

        }
        bufferId = glGenBuffers();
        debug("Generate array buffer. BufferId: " + bufferId);
        glStateCache.bindArrayBufferForce(bufferId);

        glBufferStorage(GL_ARRAY_BUFFER, memorySize, flags);
        long newMappedAddress = nglMapBufferRange(
                GL_ARRAY_BUFFER,
                0,
                memorySize,
                flags
        );
        if (newMappedAddress == 0L) {
            throw new PersistentMappedException("glMapBufferRange failed");
        }
        mappedAddress = newMappedAddress;
        debug("glMapBufferRange complete. Target: GL_ARRAY_BUFFER | offset: 0 | length: " + memorySize + " | access: " + flags);

        pointer = 0L;
        debug("Set pointer: " + pointer);

        new LogGenerator(
                Test2PersistentMappedVBO.class.getSimpleName() + ": AllocationBufferStorage",
                "BufferId: " + bufferId,
                "MappedAddress: " + mappedAddress,
                "MemorySize: " + memorySize
        ).logging(getClass(), DebugLevel.INFO);
    }

    public Test2PersistentMappedVBO reset() {
        pointer = 0;

        return this;
    }

    public Test2PersistentMappedVBO add(Vertex... vertices) {
        ensure(Vertex.BYTES * vertices.length);

        for (Vertex vertex : vertices) {
            memPutFloat(vertex.x)
                    .memPutFloat(vertex.y)
                    .memPutFloat(vertex.z)
                    .memPutFloat(vertex.red)
                    .memPutFloat(vertex.green)
                    .memPutFloat(vertex.blue)
                    .memPutFloat(vertex.alpha)
                    .memPutFloat(vertex.u)
                    .memPutFloat(vertex.v)
                    .memPutFloat(vertex.normalsX)
                    .memPutFloat(vertex.normalsY)
                    .memPutFloat(vertex.normalsZ);
        }

        return this;
    }

    public Test2PersistentMappedVBO add(Vertex vertex) {
        ensure(Vertex.BYTES);

        memPutFloat(vertex.x)
                .memPutFloat(vertex.y)
                .memPutFloat(vertex.z)
                .memPutFloat(vertex.red)
                .memPutFloat(vertex.green)
                .memPutFloat(vertex.blue)
                .memPutFloat(vertex.alpha)
                .memPutFloat(vertex.u)
                .memPutFloat(vertex.v)
                .memPutFloat(vertex.normalsX)
                .memPutFloat(vertex.normalsY)
                .memPutFloat(vertex.normalsZ);

        return this;
    }

    @Override
    public Test2PersistentMappedVBO syncToGPU() {
        if (!needFlush) {
            return this;
        }

        needFlush = false;

        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            glFlushMappedBufferRange(GL_ARRAY_BUFFER, 0, pointer);
            debug("Flushed " + pointer + " bytes to GPU");
        } else {
            debug("Coherent mapping - no flush needed");
        }

        return this;
    }

    @Override
    public Test2PersistentMappedVBO link() {
        glStateCache.bindArrayBufferForce(bufferId);

        long stride = Vertex.BYTES;
        int pointer = 0;
        for (BufferObjectType objectType : new BufferObjectType[]{BufferObjectType.POSITION_BUFFER, BufferObjectType.COLOR_BUFFER, BufferObjectType.TEXTURE_BUFFER, BufferObjectType.NORMAL_BUFFER}) {
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

    private Test2PersistentMappedVBO memPutFloat(float value) {
        if (pointer + Float.BYTES > memorySize) {
            throw new PersistentMappedException("Write would exceed mapped memory");
        }

        MemoryUtil.memPutFloat(mappedAddress + pointer, value);
        pointer += Float.BYTES;

        debug("Wrote float: " + value + ", new pointer: " + pointer);

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
        newCapacity = (newCapacity + (PERSISTENT_MAPPED_VBO_ALIGNMENT - 1)) & -PERSISTENT_MAPPED_VBO_ALIGNMENT;

        debug("New capacity: " + newCapacity + " bytes");

        long oldAddress = mappedAddress;
        long oldSize = memorySize;

        long oldPointer = pointer;
        long copySize = pointer;
        if (copySize > 0) {
            long temp = MemoryUtil.nmemAllocChecked(copySize);
            debug("Copying " + copySize + " bytes to temporary buffer");
            MemoryUtil.memCopy(mappedAddress, temp, copySize);

            boolean unmapped = glUnmapBuffer(GL_ARRAY_BUFFER);
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

            debug("Pointer after adding Vertex: " + pointer + " / MemorySize: " + memorySize);
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
        if (PERSISTENT_MAPPED_VBO_DEBUG) {
            DebugLog.debug(getClass(), text);
        }
    }

    @Override
    public void cleanup() {
        if (bufferId != 0) {
            glStateCache.deleteArrayBuffer(bufferId);
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
}
