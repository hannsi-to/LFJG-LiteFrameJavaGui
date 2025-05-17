package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public enum ColorFormatType implements IEnumTypeBase {
    RGB(GL_RGB, "RGB", 3),
    RGBA(GL_RGBA, "RGBA", 4),
    RED(GL_RED, "RED", 1),
    RG(GL_RG, "RG", 2),
    RGB8(GL_RGB8, "RGB8", 3),
    RGBA8(GL_RGBA8, "RGBA8", 4),
    RGB16F(GL_RGB16F, "RGB16F", 3),
    RGBA16F(GL_RGBA16F, "RGBA16F", 4),
    RGB32F(GL_RGB32F, "RGB32F", 3),
    RGBA32F(GL_RGBA32F, "RGBA32F", 4),
    BGR(GL_BGR, "BGR", 3),
    BGRA(GL_BGRA, "BGRA", 4),
    DEPTH_COMPONENT(GL_DEPTH_COMPONENT, "DEPTH_COMPONENT", 1),
    DEPTH_STENCIL(GL_DEPTH_STENCIL, "DEPTH_STENCIL", 2);

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
