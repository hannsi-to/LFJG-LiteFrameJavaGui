package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static me.hannsi.lfjg.core.Core.OPEN_GL_PARAMETER_NAME_MAP;

public enum ColorFormatType implements IEnumTypeBase {
    RGB(OPEN_GL_PARAMETER_NAME_MAP.get("GL_RGB"), "RGB", 3),
    RGBA(OPEN_GL_PARAMETER_NAME_MAP.get("GL_RGBA"), "RGBA", 4),
    RED(OPEN_GL_PARAMETER_NAME_MAP.get("GL_RED"), "RED", 1),
    RG(OPEN_GL_PARAMETER_NAME_MAP.get("GL_RG"), "RG", 2),
    RGB8(OPEN_GL_PARAMETER_NAME_MAP.get("GL_RGB8"), "RGB8", 3),
    RGBA8(OPEN_GL_PARAMETER_NAME_MAP.get("GL_RGBA8"), "RGBA8", 4),
    RGB16F(OPEN_GL_PARAMETER_NAME_MAP.get("GL_RGB16F"), "RGB16F", 3),
    RGBA16F(OPEN_GL_PARAMETER_NAME_MAP.get("GL_RGBA16F"), "RGBA16F", 4),
    RGB32F(OPEN_GL_PARAMETER_NAME_MAP.get("GL_RGB32F"), "RGB32F", 3),
    RGBA32F(OPEN_GL_PARAMETER_NAME_MAP.get("GL_RGBA32F"), "RGBA32F", 4),
    DEPTH_COMPONENT(OPEN_GL_PARAMETER_NAME_MAP.get("GL_DEPTH_COMPONENT"), "DEPTH_COMPONENT", 1),
    DEPTH_STENCIL(OPEN_GL_PARAMETER_NAME_MAP.get("GL_DEPTH_STENCIL"), "DEPTH_STENCIL", 2);

    final int id;
    final String name;
    final int channels;

    ColorFormatType(int id, String name, int channels) {
        this.id = id;
        this.name = name;
        this.channels = channels;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getChannels() {
        return channels;
    }
}
