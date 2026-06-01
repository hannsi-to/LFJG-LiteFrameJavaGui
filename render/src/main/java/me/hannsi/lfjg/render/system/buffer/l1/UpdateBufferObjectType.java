package me.hannsi.lfjg.render.system.buffer.l1;

import me.hannsi.lfjg.render.util.IEnumTypeSupportedBase;

import static me.hannsi.lfjg.render.LFJGRenderContext.GL_CAPABILITIES;

public enum UpdateBufferObjectType implements IEnumTypeSupportedBase {
    LEGACY(0, "LEGACY"),
    DIRECT_STATE_ACCESS(1, "DirectStateAccess"),
    MAP(2, "Mapping"),
    DIRECT_STATE_ACCESS_MAP(3, "DirectStateAccessMapping"),
    ;

    final int id;
    final String name;

    UpdateBufferObjectType(int id, String name) {
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
            case DIRECT_STATE_ACCESS, DIRECT_STATE_ACCESS_MAP -> {
                return GL_CAPABILITIES.OpenGL45;
            }
            case MAP -> {
                return GL_CAPABILITIES.OpenGL30;
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
