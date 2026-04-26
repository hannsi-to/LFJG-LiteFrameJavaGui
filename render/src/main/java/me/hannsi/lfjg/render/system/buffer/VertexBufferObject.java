package me.hannsi.lfjg.render.system.buffer;

import me.hannsi.lfjg.render.system.memory.Allocator;
import me.hannsi.lfjg.render.system.memory.AllocatorSystem;

import static me.hannsi.lfjg.render.LFJGRenderContext.ALLOCATION_BUFFER_MODE;
import static me.hannsi.lfjg.render.RenderSystemSetting.VERTEX_BUFFER_OBJECT_ALIGNMENT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.nglMapBuffer;
import static org.lwjgl.opengl.GL30.GL_MAP_WRITE_BIT;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.nglMapNamedBufferRange;
import static org.lwjgl.opengl.NVShaderBufferLoad.*;

public class VertexBufferObject {
    private final AllocatorSystem allocatorSystem;

    public VertexBufferObject(int ringBufferCount, int bufferId, int legacyUsage, int legacyAccess, int nvUnifiedMemoryAccess, Allocator... allocators) {
        allocatorSystem = new AllocatorSystem(
                memorySizeBytes -> {
                    int flags = GL_MAP_WRITE_BIT | GL_MAP_PERSISTENT_BIT | GL_MAP_COHERENT_BIT;

                    long address;
                    switch (ALLOCATION_BUFFER_MODE) {
                        case LEGACY -> {
                            glBufferData(GL_ARRAY_BUFFER, memorySizeBytes, legacyUsage);
                            address = nglMapBuffer(GL_ARRAY_BUFFER, legacyAccess);
                        }
                        case PERSISTENT_MAP -> {
                            glBufferStorage(GL_ARRAY_BUFFER, memorySizeBytes, flags);
                            address = nglMapBufferRange(GL_ARRAY_BUFFER, 0, memorySizeBytes, flags);
                        }
                        case DSA -> {
                            glNamedBufferStorage(bufferId, memorySizeBytes, flags);
                            address = nglMapNamedBufferRange(bufferId, 0, memorySizeBytes, flags);
                        }
                        case NV_UNIFIED_MEMORY -> {
                            glNamedBufferStorage(bufferId, memorySizeBytes, flags);
                            long gpuAddress = glGetBufferParameterui64NV(GL_ARRAY_BUFFER, GL_BUFFER_GPU_ADDRESS_NV);
                            glMakeBufferResidentNV(GL_ARRAY_BUFFER, nvUnifiedMemoryAccess);
                            return gpuAddress;
                        }
                        default ->
                                throw new IllegalStateException("Unexpected value: " + ALLOCATION_BUFFER_MODE);
                    }

                    return address;
                },
                VERTEX_BUFFER_OBJECT_ALIGNMENT,
                ringBufferCount,
                allocators
        );
    }
}