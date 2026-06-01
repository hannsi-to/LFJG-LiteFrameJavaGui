package me.hannsi.lfjg.render.system.buffer.l1;

import me.hannsi.lfjg.render.util.IEnumTypeSupportedBase;

import static me.hannsi.lfjg.render.LFJGRenderContext.GL_CAPABILITIES;

public enum AttributeVertexArrayObjectType implements IEnumTypeSupportedBase {
    LEGACY(0, "Legacy"),
    DIRECT_STATE_ACCESS(1, "DirectStateAccess");

    final int id;
    final String name;

    AttributeVertexArrayObjectType(int id, String name) {
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
                return GL_CAPABILITIES.OpenGL30;
            }
            case DIRECT_STATE_ACCESS -> {
                return GL_CAPABILITIES.OpenGL45;
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
