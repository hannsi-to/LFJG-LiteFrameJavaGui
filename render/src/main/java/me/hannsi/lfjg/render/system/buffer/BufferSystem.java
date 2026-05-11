package me.hannsi.lfjg.render.system.buffer;

import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.render.system.memory.Allocation;
import me.hannsi.lfjg.render.system.memory.AllocatorSystem;
import me.hannsi.lfjg.render.system.memory.GPUHeap;
import me.hannsi.lfjg.render.system.memory.allocator.Allocator;
import org.lwjgl.system.MemoryUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import static me.hannsi.lfjg.core.Core.NULL_PTR;
import static me.hannsi.lfjg.core.Core.UNSAFE;
import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.GL_MAP_WRITE_BIT;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.glUnmapNamedBuffer;
import static org.lwjgl.opengl.GL45.nglMapNamedBufferRange;
import static org.lwjgl.opengl.NVShaderBufferLoad.*;

public class BufferSystem {
    private final BufferAllocationMode bufferAllocationMode;
    private final BufferProperty bufferProperty;
    private final Map<Integer, BufferWriteConfig> bufferWriteConfigs;
    private final GPUHeap gpuHeap;
    private final boolean deviceLocal;
    private final int bufferId;
    private final int target;
    private final int alignment;

    public BufferSystem(Builder builder, int bufferId, int target, int alignment) {
        this(
                builder.bufferAllocationMode,
                builder.bufferProperty,
                new LinkedHashMap<>(builder.bufferWriteConfigs),
                builder.deviceLocal,
                NULL_PTR,
                bufferId,
                target,
                alignment
        );
    }

    private BufferSystem(BufferAllocationMode bufferAllocationMode, BufferProperty bufferProperty, Map<Integer, BufferWriteConfig> bufferWriteConfigs, boolean deviceLocal, long baseGPUAddress, int bufferId, int target, int alignment) {
        this.bufferAllocationMode = bufferAllocationMode;
        this.bufferProperty = bufferProperty;
        this.bufferWriteConfigs = bufferWriteConfigs;
        this.deviceLocal = deviceLocal;
        this.bufferId = bufferId;
        this.target = target;
        this.alignment = alignment;


        this.gpuHeap = new GPUHeap(memorySize, alignment);
        for (BufferWriteConfig bufferWriteConfig : bufferWriteConfigs.values()) {
            for (Allocator allocator : bufferWriteConfig.allocatorSystem.getAllocators()) {
                allocator.init(gpuHeap);
            }
        }

        switch (bufferAllocationMode) {
            case BUFFER_DATA -> {
                int usage = deviceLocal ? GL_STATIC_DRAW : GL_DYNAMIC_DRAW;
                switch (bufferProperty) {
                    case BIND_BUFFER -> {
                        glStateCache.bindBuffer(target, bufferId);
                        glBufferData(target, memorySize, usage);
                    }
                    case DIRECT_STATE_ACCESS, NV_UNIFIED_BINDLESS ->
                            glNamedBufferData(bufferId, memorySize, usage);
                    default ->
                            throw new IllegalStateException("Unexpected value: " + bufferProperty);
                }
            }
            case BUFFER_STORAGE -> {
                int flags = getStorageFlags();
                switch (bufferProperty) {
                    case BIND_BUFFER -> {
                        glStateCache.bindBuffer(target, bufferId);
                        glBufferStorage(target, memorySize, flags);
                    }
                    case DIRECT_STATE_ACCESS, NV_UNIFIED_BINDLESS ->
                            glNamedBufferStorage(bufferId, memorySize, flags);
                    default ->
                            throw new IllegalStateException("Unexpected value: " + bufferProperty);
                }
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + bufferAllocationMode);
        }

        if (bufferProperty == BufferProperty.NV_UNIFIED_BINDLESS) {
            glMakeNamedBufferResidentNV(bufferId, GL_READ_WRITE);

            long[] gpuAddress = {0};
            glGetNamedBufferParameterui64vNV(bufferId, GL_BUFFER_GPU_ADDRESS_NV, gpuAddress);
            baseGPUAddress = gpuAddress[0];
        }

        long totalUseBytes = 0L;
        for (BufferWriteConfig bufferWriteConfig : bufferWriteConfigs.values()) {
            long size = bufferWriteConfig.allocatorSystem.getMemorySize();
            switch (bufferWriteConfig.bufferWriteMode) {
                case BUFFER_SUB ->
                        bufferWriteConfig.cpuAddress = MemoryUtil.memAddress(MemoryUtil.memAlloc((int) size));
                case MAP, MAP_RANGE, PERSISTENT, PERSISTENT_COHERENT -> {
                    int mapFlags = getMapFlags(bufferWriteConfig.bufferWriteMode);
                    switch (bufferProperty) {
                        case BIND_BUFFER -> {
                            glStateCache.bindBuffer(target, bufferId);
                            bufferWriteConfig.cpuAddress = nglMapBufferRange(target, totalUseBytes, size, mapFlags);
                        }
                        case DIRECT_STATE_ACCESS, NV_UNIFIED_BINDLESS ->
                                bufferWriteConfig.cpuAddress = nglMapNamedBufferRange(bufferId, totalUseBytes, size, mapFlags);
                        default ->
                                throw new IllegalStateException("Unexpected value: " + bufferProperty);
                    }
                }
                default ->
                        throw new IllegalStateException("Unexpected value: " + bufferWriteConfig.bufferWriteMode);
            }

            if (bufferWriteConfig.cpuAddress == NULL_PTR) {
                throw new RuntimeException("Failed to map buffer range.");
            }

            if (baseGPUAddress != NULL_PTR) {
                bufferWriteConfig.gpuAddress = baseGPUAddress + totalUseBytes;
            }

            totalUseBytes += size;
        }
    }

    public void startFrame() {
        for (BufferWriteConfig bufferWriteConfig : bufferWriteConfigs.values()) {
            bufferWriteConfig.allocatorSystem.startFrame();
        }
    }

    public void endFrame() {
        for (BufferWriteConfig bufferWriteConfig : bufferWriteConfigs.values()) {
            bufferWriteConfig.allocatorSystem.endFrame();
        }
    }

    public void alloc(int writeBufferConfigPointer, long size, int alignment) {
        bufferWriteConfigs.get(writeBufferConfigPointer).allocatorSystem.alloc(size, alignment);
    }

    public void update(int writeBufferConfigPointer, long offset, long memorySize, int alignment) {
        bufferWriteConfigs.get(writeBufferConfigPointer).allocatorSystem.update(offset, memorySize, alignment);
    }

    public void free(int writeBufferConfigPointer, Allocation allocation) {
        bufferWriteConfigs.get(writeBufferConfigPointer).allocatorSystem.free(allocation);
    }

    public void reset(int writeBufferConfigPointer) {
        bufferWriteConfigs.get(writeBufferConfigPointer).allocatorSystem.reset();
    }

    public void destroy() {
        for (BufferWriteConfig config : bufferWriteConfigs.values()) {
            if (config.cpuAddress != NULL_PTR) {
                switch (config.bufferWriteMode) {
                    case BUFFER_SUB -> {
                        UNSAFE.freeMemory(config.cpuAddress);
                    }
                    case MAP, MAP_RANGE, PERSISTENT, PERSISTENT_COHERENT -> {
                        switch (bufferProperty) {
                            case BIND_BUFFER -> {
                                glStateCache.bindBuffer(target, bufferId);
                                glUnmapBuffer(target);
                            }
                            case DIRECT_STATE_ACCESS, NV_UNIFIED_BINDLESS -> {
                                glUnmapNamedBuffer(bufferId);
                            }
                        }
                    }
                }
                config.cpuAddress = NULL_PTR;
            }
        }

        if (bufferProperty == BufferProperty.NV_UNIFIED_BINDLESS) {
            glMakeNamedBufferNonResidentNV(bufferId);
        }

        glDeleteBuffers(bufferId);
    }

    public int getStorageFlags() {
        int flags = 0;

        if (bufferAllocationMode == BufferAllocationMode.BUFFER_STORAGE) {
            flags |= GL_DYNAMIC_STORAGE_BIT;
        }

        for (BufferWriteConfig config : bufferWriteConfigs.values()) {
            switch (config.bufferWriteMode) {
                case MAP, MAP_RANGE ->
                        flags |= GL_MAP_WRITE_BIT;
                case PERSISTENT ->
                        flags |= (GL_MAP_WRITE_BIT | GL_MAP_PERSISTENT_BIT);
                case PERSISTENT_COHERENT ->
                        flags |= (GL_MAP_WRITE_BIT | GL_MAP_PERSISTENT_BIT | GL_MAP_COHERENT_BIT);
                case BUFFER_SUB ->
                        flags |= GL_DYNAMIC_STORAGE_BIT;
            }
        }

        if (!deviceLocal) {
            flags |= GL_CLIENT_STORAGE_BIT;
        }

        return flags;
    }

    public int getMapFlags(BufferWriteMode mode) {
        int flags = 0;

        switch (mode) {
            case MAP, MAP_RANGE ->
                    flags |= GL_MAP_WRITE_BIT;
            case PERSISTENT ->
                    flags |= (GL_MAP_WRITE_BIT | GL_MAP_PERSISTENT_BIT);
            case PERSISTENT_COHERENT ->
                    flags |= (GL_MAP_WRITE_BIT | GL_MAP_PERSISTENT_BIT | GL_MAP_COHERENT_BIT);
            default -> {
            }
        }

        flags |= GL_MAP_UNSYNCHRONIZED_BIT;

        return flags;
    }

    public BufferAllocationMode getBufferAllocationMode() {
        return bufferAllocationMode;
    }

    public BufferProperty getBufferProperty() {
        return bufferProperty;
    }

    public Map<Integer, BufferWriteConfig> getBufferWriteConfigs() {
        return bufferWriteConfigs;
    }

    public GPUHeap getGpuHeap() {
        return gpuHeap;
    }

    public boolean isDeviceLocal() {
        return deviceLocal;
    }

    public int getBufferId() {
        return bufferId;
    }

    public int getTarget() {
        return target;
    }

    public int getAlignment() {
        return alignment;
    }

    public interface BufferAllocationModeStep {
        BufferPropertyStep bufferAllocationMode(BufferAllocationMode bufferAllocationMode);
    }

    public interface BufferPropertyStep {
        BufferWriteModesStep bufferProperty(BufferProperty bufferProperty);
    }

    public interface BufferWriteModesStep {
        BufferWriteModesStep bufferWriteMode(BufferWriteMode bufferWriteMode, AllocatorSystem allocatorSystem, IntRef pointer);

        Builder build(boolean deviceLocal);
    }

    public static final class BufferWriteConfig {
        public final BufferWriteMode bufferWriteMode;
        public final AllocatorSystem allocatorSystem;
        public long cpuAddress;
        public long gpuAddress;

        public BufferWriteConfig(BufferWriteMode bufferWriteMode, AllocatorSystem allocatorSystem, long cpuAddress, long gpuAddress) {
            this.bufferWriteMode = bufferWriteMode;
            this.allocatorSystem = allocatorSystem;
            this.cpuAddress = cpuAddress;
            this.gpuAddress = gpuAddress;
        }

        @Override
        public String toString() {
            return "BufferWriteConfig[" +
                    "bufferWriteMode=" + bufferWriteMode + ", " +
                    "address=" + cpuAddress + ']';
        }
    }

    public static class Builder implements BufferAllocationModeStep, BufferPropertyStep, BufferWriteModesStep {
        protected BufferAllocationMode bufferAllocationMode;
        protected BufferProperty bufferProperty;
        protected Map<Integer, BufferWriteConfig> bufferWriteConfigs;
        protected boolean deviceLocal;

        private Builder() {
            bufferWriteConfigs = new LinkedHashMap<>();
        }

        public static BufferAllocationModeStep createBuilder() {
            return new Builder();
        }

        @Override
        public BufferPropertyStep bufferAllocationMode(BufferAllocationMode bufferAllocationMode) {
            this.bufferAllocationMode = bufferAllocationMode;
            return this;
        }

        @Override
        public BufferWriteModesStep bufferProperty(BufferProperty bufferProperty) {
            switch (bufferAllocationMode) {
                case BUFFER_DATA -> {
                    if (bufferProperty == BufferProperty.NV_UNIFIED_BINDLESS) {
                        throw new BufferConfigException("NVIDIA's bindless buffers (GPU address acquisition) are designed on the assumption that the storage is immutable. Since " + BufferAllocationMode.BUFFER_DATA.getName() + " does not guarantee address immutability, " + BufferAllocationMode.BUFFER_STORAGE.getName() + " is typically required.");
                    }
                }
                case BUFFER_STORAGE -> {
                }
                default ->
                        throw new IllegalStateException("Unexpected value: " + bufferAllocationMode);
            }

            this.bufferProperty = bufferProperty;
            return this;
        }

        @Override
        public BufferWriteModesStep bufferWriteMode(BufferWriteMode bufferWriteMode, AllocatorSystem allocatorSystem, IntRef pointer) {
            switch (bufferAllocationMode) {
                case BUFFER_DATA -> {
                    if (bufferWriteMode == BufferWriteMode.PERSISTENT || bufferWriteMode == BufferWriteMode.PERSISTENT_COHERENT) {
                        throw new BufferConfigException(BufferWriteMode.PERSISTENT.getName() + " (" + BufferWriteMode.PERSISTENT_COHERENT.getName() + ") is a flag specific to " + BufferAllocationMode.BUFFER_STORAGE.getName() + ". You cannot set the persistent map flag when updating a buffer allocated with " + BufferAllocationMode.BUFFER_DATA + ".");
                    }
                }
                case BUFFER_STORAGE -> {
                }
                default ->
                        throw new IllegalStateException("Unexpected value: " + bufferAllocationMode);
            }

            int p = bufferWriteConfigs.size() + 1;
            this.bufferWriteConfigs.put(p, new BufferWriteConfig(bufferWriteMode, allocatorSystem, NULL_PTR, NULL_PTR));
            pointer.setValue(p);
            pointer.setFinal();

            return this;
        }

        @Override
        public Builder build(boolean deviceLocal) {
            this.deviceLocal = deviceLocal;

            boolean hasPersistent = false;
            for (BufferWriteConfig bufferWriteConfig : bufferWriteConfigs.values()) {
                BufferWriteMode bufferWriteMode = bufferWriteConfig.bufferWriteMode;
                if (bufferWriteMode == BufferWriteMode.PERSISTENT || bufferWriteMode == BufferWriteMode.PERSISTENT_COHERENT) {
                    hasPersistent = true;
                    break;
                }
            }
            if (deviceLocal && hasPersistent) {
                throw new BufferConfigException("DeviceLocal with Persistent Mapping may cause significant performance drops.");
            }

            return this;
        }
    }
}
