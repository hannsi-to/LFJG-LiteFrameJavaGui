package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum ColorFormatType implements IEnumTypeBase {
    RGB(Core.GL11.GL_RGB, "RGB", 3),
    RGBA(Core.GL11.GL_RGBA, "RGBA", 4),
    RED(Core.GL11.GL_RED, "RED", 1),
    RG(Core.GL30.GL_RG, "RG", 2),
    RGB8(Core.GL11.GL_RGB8, "RGB8", 3),
    RGBA8(Core.GL11.GL_RGBA8, "RGBA8", 4),
    RGB16F(Core.GL30.GL_RGB16F, "RGB16F", 3),
    RGBA16F(Core.GL30.GL_RGBA16F, "RGBA16F", 4),
    RGB32F(Core.GL30.GL_RGB32F, "RGB32F", 3),
    RGBA32F(Core.GL30.GL_RGBA32F, "RGBA32F", 4),
    DEPTH_COMPONENT(Core.GL11.GL_DEPTH_COMPONENT, "DEPTH_COMPONENT", 1),
    DEPTH_STENCIL(Core.GL30.GL_DEPTH_STENCIL, "DEPTH_STENCIL", 2);

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
