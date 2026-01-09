package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.persistent.PersistentMappedException;
import me.hannsi.lfjg.render.renderers.ObjectParameter;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL44;
import org.lwjgl.system.MemoryUtil;

import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static me.hannsi.lfjg.render.RenderSystemSetting.PERSISTENT_MAPPED_SSBO_ALIGNMENT;
import static me.hannsi.lfjg.render.RenderSystemSetting.PERSISTENT_MAPPED_SSBO_DEBUG;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL43.GL_SHADER_STORAGE_BUFFER;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class Test2PersistentMappedSSBO {
    private final int flags;
    private final long initialSSBOCapacity;
    private final Map<Integer, ShaderStorageBufferData> ssboDatum;
    private int bufferId;
    private long mappedAddress;
    private long memorySize;
    private boolean needFlush;

    public Test2PersistentMappedSSBO(int flags, long initialCapacity, long initialSSBOCapacity) {
        this.flags = flags;
        this.initialSSBOCapacity = initialSSBOCapacity;
        this.ssboDatum = new HashMap<>();

        allocationBufferStorage(initialCapacity);
    }

    public void allocationBufferStorage(long capacity) {
        if (capacity % PERSISTENT_MAPPED_SSBO_ALIGNMENT != 0) {
            throw new PersistentMappedException("capacity must be multiple of " + PERSISTENT_MAPPED_SSBO_ALIGNMENT);
        }

        memorySize = capacity;

        if (bufferId != 0) {
            glStateCache.deleteShaderStorageBuffer(bufferId);
            debug("Delete element shader storage buffer. BufferId: " + bufferId);
            bufferId = 0;
        }
        bufferId = glGenBuffers();
        debug("Generate element shader storage buffer. BufferId: " + bufferId);
        glStateCache.bindShaderStorageBufferForce(bufferId);

        glBufferStorage(GL_SHADER_STORAGE_BUFFER, memorySize, flags);
        long newMappedAddress = nglMapBufferRange(
                GL_SHADER_STORAGE_BUFFER,
                0,
                memorySize,
                flags
        );
        if (newMappedAddress == 0L) {
            throw new PersistentMappedException("glMapBufferRange failed");
        }
        mappedAddress = newMappedAddress;
        debug("glMapBufferRange complete. Target: GL_SHADER_STORAGE_BUFFER | offset: 0 | length: " + memorySize + " | access: " + flags);

        new LogGenerator(
                Test2PersistentMappedSSBO.class.getSimpleName() + ": AllocationBufferStorage",
                "BufferId: " + bufferId,
                "MappedAddress: " + mappedAddress,
                "MemorySize: " + memorySize
        ).logging(getClass(), DebugLevel.INFO);
    }

    public Test2PersistentMappedSSBO addFloat(int bindingPoint, float value) {
        if (!ssboDatum.containsKey(bindingPoint)) {
            long offset = 0L;
            for (ShaderStorageBufferData entry : ssboDatum.values()) {
                offset = Math.max(offset, entry.getLastAddress());
            }

            offset = (offset + (PERSISTENT_MAPPED_SSBO_ALIGNMENT - 1)) & -PERSISTENT_MAPPED_SSBO_ALIGNMENT;

            ssboDatum.put(bindingPoint, new ShaderStorageBufferData(offset, initialSSBOCapacity, 0L));
        }
        ShaderStorageBufferData ssboData = ssboDatum.get(bindingPoint);

        ensureSSBODatum(ssboData, Float.BYTES);

        memPutFloat(ssboData.offset + ssboData.pointer, value);
        ssboData.pointer += Float.BYTES;

        return this;
    }

    public Test2PersistentMappedSSBO addVec4(int bindingPoint, float x, float y, float z, float w) {
        float[] values = new float[]{x, y, z, w};
        for (float value : values) {
            addFloat(bindingPoint, value);
        }

        return this;
    }

    public Test2PersistentMappedSSBO addObjectParameter(int bindingPoint, ObjectParameter objectParameter) {
        if (!ssboDatum.containsKey(bindingPoint)) {
            long offset = 0L;
            for (ShaderStorageBufferData entry : ssboDatum.values()) {
                offset = Math.max(offset, entry.getLastAddress());
            }

            offset = (offset + (PERSISTENT_MAPPED_SSBO_ALIGNMENT - 1)) & -PERSISTENT_MAPPED_SSBO_ALIGNMENT;

            ssboDatum.put(bindingPoint, new ShaderStorageBufferData(offset, initialSSBOCapacity, 0L));
        }
        ShaderStorageBufferData ssboData = ssboDatum.get(bindingPoint);

        ensureSSBODatum(ssboData, ObjectParameter.BYTES);

        Matrix4f currentVP = (objectParameter.getProjectionType() == ProjectionType.PERSPECTIVE_PROJECTION) ? precomputedViewProjection3D : precomputedViewProjection2D;
        memPutObjectParameter(ssboData.offset + ssboData.pointer, objectParameter, currentVP);
        ssboData.pointer += ObjectParameter.BYTES;

        return this;
    }

    public Test2PersistentMappedSSBO updateObjectParameter(int bindingPoint, int index, ObjectParameter objectParameter) {
        ShaderStorageBufferData ssboData = ssboDatum.get(bindingPoint);
        if (ssboData == null) {
            throw new IndexOutOfBoundsException("Invalid binding point");
        }

        Matrix4f currentVP = (objectParameter.getProjectionType() == ProjectionType.PERSPECTIVE_PROJECTION) ? precomputedViewProjection3D : precomputedViewProjection2D;
        memPutObjectParameter(ssboData.offset + ((long) index * ObjectParameter.BYTES), objectParameter, currentVP);

        return this;
    }


    public Test2PersistentMappedSSBO syncToGPU() {
        if (!needFlush) {
            return this;
        }

        needFlush = false;

        long lastPointer = 0L;
        for (Map.Entry<Integer, ShaderStorageBufferData> entry : ssboDatum.entrySet()) {
            if (lastPointer < entry.getValue().getLastAddress()) {
                lastPointer = entry.getValue().getLastAddress();
            }
        }

        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            glFlushMappedBufferRange(GL_SHADER_STORAGE_BUFFER, 0, lastPointer);
            debug("Flushed " + lastPointer + " bytes to GPU");
        } else {
            debug("Coherent mapping - no flush needed");
        }

        return this;
    }

    public Test2PersistentMappedSSBO bindBufferRange() {
        for (Map.Entry<Integer, ShaderStorageBufferData> entry : ssboDatum.entrySet()) {
            ShaderStorageBufferData ssboData = entry.getValue();
            if (ssboData.offset < 0 || ssboData.size <= 0) {
                return this;
            }

            glBindBufferRange(GL_SHADER_STORAGE_BUFFER, entry.getKey(), bufferId, ssboData.offset, ssboData.size);
        }

        return this;
    }

    public Test2PersistentMappedSSBO resetBindingPoint(int bindingPoint) {
        ShaderStorageBufferData ssboData = ssboDatum.get(bindingPoint);

        if (ssboData != null) {
            ssboData.pointer = 0;
            MemoryUtil.memSet(mappedAddress + ssboData.offset, 0, ssboData.size);
        } else {
            new LogGenerator("SSBO Reset Info")
                    .kv("Binding Point", bindingPoint)
                    .kv("Message", "Binding point does not exist. No reset performed.")
                    .logging(getClass(), DebugLevel.INFO);
        }

        return this;
    }

    private Test2PersistentMappedSSBO memPutFloat(long pointer, float value) {
        if (pointer + Integer.BYTES > memorySize) {
            throw new PersistentMappedException("Write would exceed mapped memory");
        }

        MemoryUtil.memPutFloat(mappedAddress + pointer, value);

        needFlush = true;

        return this;
    }

    private Test2PersistentMappedSSBO memPutObjectParameter(long pointer, ObjectParameter objectParameter, Matrix4f vp) {
        if (pointer + ObjectParameter.BYTES > memorySize) {
            throw new PersistentMappedException("Write would exceed mapped memory");
        }

        objectParameter.getToAddress(mappedAddress + pointer, vp);

        needFlush = true;

        return this;
    }

    private void ensureSSBODatum(ShaderStorageBufferData ssboData, long addSize) {
        long requiredSize = ssboData.pointer + addSize;
        if (ssboData.offset + requiredSize <= memorySize && requiredSize <= ssboData.size) {
            return;
        }

        long oldSize = ssboData.size;
        long newSize = (requiredSize + (PERSISTENT_MAPPED_SSBO_ALIGNMENT - 1)) & -PERSISTENT_MAPPED_SSBO_ALIGNMENT;
        long delta = newSize - oldSize;

        long totalRequired = ssboDatum.values().stream().mapToLong(d -> d.size).sum() + delta;
        ensure(totalRequired);

        ssboDatum.entrySet().stream()
                .filter(e -> e.getValue().offset > ssboData.offset)
                .sorted((a, b) -> Long.compare(b.getValue().offset, a.getValue().offset))
                .forEach(e -> {
                    ShaderStorageBufferData data = e.getValue();
                    MemoryUtil.memCopy(mappedAddress + data.offset, mappedAddress + data.offset + delta, data.size);
                    data.offset += delta;
                });

        ssboData.size = newSize;

        bindBufferRange();
    }

    private void ensure(long requiredSize) {
        if (requiredSize <= memorySize) {
            return;
        }

        debug("Capacity exceeded. Expanding buffer... Current: " + memorySize + " bytes, Required: " + requiredSize + " bytes");

        long newCapacity = memorySize;
        while (newCapacity < requiredSize) {
            newCapacity += newCapacity >> 1;
        }
        newCapacity = (newCapacity + (PERSISTENT_MAPPED_SSBO_ALIGNMENT - 1)) & -PERSISTENT_MAPPED_SSBO_ALIGNMENT;

        debug("New capacity: " + newCapacity + " bytes");

        long oldAddress = mappedAddress;
        long oldSize = memorySize;

        long copySize = memorySize;
        if (copySize > 0) {
            long temp = MemoryUtil.nmemAllocChecked(copySize);
            debug("Copying " + copySize + " bytes to temporary buffer");
            MemoryUtil.memCopy(mappedAddress, temp, copySize);

            boolean unmapped = glUnmapBuffer(GL_SHADER_STORAGE_BUFFER);
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

    private void debug(String text) {
        if (PERSISTENT_MAPPED_SSBO_DEBUG) {
            DebugLog.debug(getClass(), text);
        }
    }

    public void cleanup() {
        if (bufferId != 0) {
            glStateCache.deleteShaderStorageBuffer(bufferId);
            bufferId = 0;
        }
        mappedAddress = 0L;
        memorySize = 0L;
    }

    public void link() {
        glStateCache.bindShaderStorageBufferForce(bufferId);
    }

    public static class ShaderStorageBufferData {
        public long offset;
        public long size;
        public long pointer;

        public ShaderStorageBufferData(long offset, long size, long pointer) {
            this.offset = offset;
            this.size = size;
            this.pointer = pointer;
        }

        public long getLastAddress() {
            return offset + size;
        }
    }
}
