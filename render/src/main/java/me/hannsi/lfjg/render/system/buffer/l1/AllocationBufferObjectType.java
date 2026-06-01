package me.hannsi.lfjg.render.system.buffer.l1;

import me.hannsi.lfjg.render.util.IEnumTypeSupportedBase;

import static me.hannsi.lfjg.render.LFJGRenderContext.GL_CAPABILITIES;

public enum AllocationBufferObjectType implements IEnumTypeSupportedBase {
    LEGACY(0, "Legacy"),
    DIRECT_STATE_ACCESS(1, "DirectStateAccess"),
    STORAGE(2, "Storage"),
    DIRECT_STATE_ACCESS_STORAGE(3, "DirectStateAccessStorage");

    final int id;
    final String name;

    AllocationBufferObjectType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isSupported() {
        switch (this) {
            case LEGACY -> {
                return GL_CAPABILITIES.OpenGL15;
            }
            case DIRECT_STATE_ACCESS, DIRECT_STATE_ACCESS_STORAGE -> {
                return GL_CAPABILITIES.OpenGL45;
            }
            case STORAGE -> {
                return GL_CAPABILITIES.OpenGL44;
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
