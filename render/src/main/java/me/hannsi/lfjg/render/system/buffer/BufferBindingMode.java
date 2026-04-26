package me.hannsi.lfjg.render.system.buffer;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import org.lwjgl.opengl.GLCapabilities;

public enum BufferBindingMode implements IEnumTypeBase {
    AUTO(0, "Auto"),
    LEGACY(1, "Legacy"),
    ATTRIB_BINDING(2, "AttribBinding"),
    DSA(3, "DSA"),
    NV_UNIFIED_MEMORY(4, "NVUnifiedMemory");

    final int id;
    final String name;

    BufferBindingMode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static BufferBindingMode choose(GLCapabilities caps) {
        if (NV_UNIFIED_MEMORY.supported(caps)) {
            return NV_UNIFIED_MEMORY;
        }

        if (DSA.supported(caps)) {
            return DSA;
        }

        if (ATTRIB_BINDING.supported(caps)) {
            return ATTRIB_BINDING;
        }

        return LEGACY;
    }

    public boolean supported(GLCapabilities caps) {
        switch (this) {
            case AUTO -> {
                return true;
            }
            case LEGACY -> {
                return caps.OpenGL20;
            }
            case ATTRIB_BINDING -> {
                return caps.OpenGL43 || caps.GL_ARB_vertex_attrib_binding;
            }
            case DSA -> {
                return caps.OpenGL45 || caps.GL_ARB_direct_state_access;
            }
            case NV_UNIFIED_MEMORY -> {
                return caps.GL_NV_vertex_buffer_unified_memory;
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
