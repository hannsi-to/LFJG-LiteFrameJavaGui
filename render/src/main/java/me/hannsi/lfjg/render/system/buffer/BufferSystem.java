package me.hannsi.lfjg.render.system.buffer;

import me.hannsi.lfjg.render.system.memory.Allocation;
import me.hannsi.lfjg.render.system.memory.AllocatorSystem;
import me.hannsi.lfjg.render.system.memory.GPUHeap;
import org.lwjgl.system.MemoryUtil;

import java.util.List;

import static me.hannsi.lfjg.core.Core.NULL_PTR;
import static me.hannsi.lfjg.core.Core.UNSAFE;
import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.GL_MAP_WRITE_BIT;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.opengl.NVShaderBufferLoad.*;

public class BufferSystem {
    private final BufferConfig bufferConfig;
    private final long memorySizeBytes;
    private GPUHeap gpuHeap;
    private long baseCPUAddress;
    private long baseGPUAddress;

    public BufferSystem(BufferConfig bufferConfig) {
        this.bufferConfig = bufferConfig;
        long memorySizeBytes = 0L;
        for (long sizeByte : bufferConfig.writeConfig().getSizeBytes()) {
            memorySizeBytes += sizeByte;
        }
        this.memorySizeBytes = memorySizeBytes;
        this.baseCPUAddress = NULL_PTR;
        this.baseGPUAddress = NULL_PTR;
    }

    public void allocate(int bufferId, int target) {
        switch (bufferConfig.allocationMode()) {
            case BUFFER_DATA -> {
                int usage = bufferConfig.deviceLocal() ? GL_STATIC_DRAW : GL_DYNAMIC_DRAW;
                switch (bufferConfig.bufferProperty()) {
                    case BIND_BUFFER -> {
                        glStateCache.bindBuffer(target, bufferId);
                        glBufferData(target, memorySizeBytes, usage);
                    }
                    case DIRECT_STATE_ACCESS, NV_UNIFIED_BINDLESS ->
                            glNamedBufferData(bufferId, memorySizeBytes, usage);
                    default ->
                            throw new IllegalStateException("Unexpected value: " + bufferConfig.bufferProperty());
                }
            }
            case BUFFER_STORAGE -> {
                int flags = 0;
                List<BufferConfig.WriteMode> modes = bufferConfig.writeConfig().getWriteModes();

                if (modes.contains(BufferConfig.WriteMode.BUFFER_SUB)) {
                    flags |= GL_DYNAMIC_STORAGE_BIT;
                }

                if (modes.contains(BufferConfig.WriteMode.MAP) || modes.contains(BufferConfig.WriteMode.MAP_RANGE)) {
                    flags |= GL_MAP_WRITE_BIT;
                }

                if (modes.contains(BufferConfig.WriteMode.PERSISTENT)) {
                    flags |= GL_MAP_WRITE_BIT | GL_MAP_PERSISTENT_BIT;
                    flags |= GL_MAP_COHERENT_BIT;
                }

                switch (bufferConfig.bufferProperty()) {
                    case BIND_BUFFER -> {
                        glStateCache.bindBuffer(target, bufferId);
                        glBufferStorage(target, memorySizeBytes, flags);
                    }
                    case DIRECT_STATE_ACCESS, NV_UNIFIED_BINDLESS ->
                            glNamedBufferStorage(bufferId, memorySizeBytes, flags);
                    default ->
                            throw new IllegalStateException("Unexpected value: " + bufferConfig.bufferProperty());
                }
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + bufferConfig.allocationMode());
        }

        if (bufferConfig.bufferProperty() == BufferConfig.BufferProperty.NV_UNIFIED_BINDLESS) {
            glMakeNamedBufferResidentNV(bufferId, GL_READ_WRITE);

            long[] gpuAddress = {0};
            glGetNamedBufferParameterui64vNV(bufferId, GL_BUFFER_GPU_ADDRESS_NV, gpuAddress);
            baseGPUAddress = gpuAddress[0];
        }
        long totalUseBytes = 0L;
        for (int i = 0; i < bufferConfig.writeConfig().getWriteModes().size(); i++) {
            BufferConfig.WriteMode writeMode = bufferConfig.writeConfig().getWriteMode(i);
            long sizeBytes = bufferConfig.writeConfig().getSizeByte(i);
            if (totalUseBytes + sizeBytes > memorySizeBytes) {
                throw new OutOfMemoryError();
            }

            long ptr;
            switch (writeMode) {
                case BUFFER_SUB ->
                        ptr = MemoryUtil.memAddress(MemoryUtil.memAlloc((int) sizeBytes));
                case MAP, MAP_RANGE, PERSISTENT, PERSISTENT_COHERENT -> {
                    int mapFlags = bufferConfig.writeConfig().getMapFlags(writeMode);
                    switch (bufferConfig.bufferProperty()) {
                        case BIND_BUFFER -> {
                            glStateCache.bindBuffer(target, bufferId);
                            ptr = nglMapBufferRange(target, totalUseBytes, sizeBytes, mapFlags);
                        }
                        case DIRECT_STATE_ACCESS, NV_UNIFIED_BINDLESS ->
                                ptr = nglMapNamedBufferRange(bufferId, totalUseBytes, sizeBytes, mapFlags);
                        default ->
                                throw new IllegalStateException("Unexpected value: " + bufferConfig.bufferProperty());
                    }
                }
                default ->
                        throw new IllegalStateException("Unexpected value: " + writeMode);
            }
            if (ptr == NULL_PTR) {
                throw new RuntimeException("Failed to map buffer range.");
            }

            if (i == 0) {
                baseCPUAddress = ptr;
            }

            totalUseBytes += sizeBytes;
        }

        gpuHeap = new GPUHeap(baseCPUAddress, totalUseBytes);

        for (AllocatorSystem allocatorSystem : bufferConfig.writeConfig().getAllocatorSystems()) {
            allocatorSystem.init(gpuHeap);
        }
    }

    public void putInt(int index, int value) {
        Allocation allocation = bufferConfig.writeConfig().getAllocatorSystem(index).alloc(Integer.BYTES, bufferConfig.writeConfig().getAlignment(index));
        if (allocation == null) {
            return;
        }

        UNSAFE.putInt(allocation.address() + allocation.offset(), value);
    }

    public void startFrame() {
        for (AllocatorSystem allocatorSystem : bufferConfig.writeConfig().getAllocatorSystems()) {
            allocatorSystem.startFrame();
        }
    }

    public void endFrame() {
        for (AllocatorSystem allocatorSystem : bufferConfig.writeConfig().getAllocatorSystems()) {
            allocatorSystem.endFrame();
        }
    }

    public long getMemorySizeBytes() {
        return memorySizeBytes;
    }

    public BufferConfig getBufferConfig() {
        return bufferConfig;
    }
}
