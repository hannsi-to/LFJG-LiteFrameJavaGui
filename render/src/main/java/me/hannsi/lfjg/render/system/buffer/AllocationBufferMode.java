package me.hannsi.lfjg.render.system.buffer;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import org.lwjgl.opengl.GLCapabilities;

public enum AllocationBufferMode implements IEnumTypeBase {
    AUTO(0, "Auto"),
    LEGACY(1, "Legacy"),
    PERSISTENT_MAP(2, "PersistentMap"),
    DSA(3, "DSA"),
    NV_UNIFIED_MEMORY(4, "NVUnifiedMemory");

    final int id;
    final String name;

    AllocationBufferMode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static AllocationBufferMode choose(GLCapabilities caps) {
        if (NV_UNIFIED_MEMORY.supported(caps)) {
            return NV_UNIFIED_MEMORY;
        }

        if (DSA.supported(caps)) {
            return DSA;
        }

        if (PERSISTENT_MAP.supported(caps)) {
            return PERSISTENT_MAP;
        }

        return LEGACY;
    }

    public boolean supported(GLCapabilities caps) {
        switch (this) {
            case AUTO -> {
                return true;
            }
            case LEGACY -> {
                return caps.OpenGL15;
            }
            case PERSISTENT_MAP -> {
                return caps.OpenGL44;
            }
            case DSA -> {
                return caps.OpenGL45;
            }
            case NV_UNIFIED_MEMORY -> {
                return caps.GL_NV_shader_buffer_load && caps.GL_NV_vertex_buffer_unified_memory;
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
