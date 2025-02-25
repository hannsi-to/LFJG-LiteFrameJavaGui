package me.hannsi.lfjg.utils.type.types;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;

/**
 * Enumeration representing different types of blend modes.
 */
public enum BlendType {
    Normal("Normal", 0, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD),
    Add("Add", 1, GL_ONE, GL_ONE, GL_FUNC_ADD),
    AlphaAdd("AlphaAdd", 2, GL_SRC_ALPHA, GL_ONE, GL_FUNC_ADD),
    Subtract("Subtract", 3, GL_ONE, GL_ONE, GL_FUNC_REVERSE_SUBTRACT),
    ReverseSubtract("ReverseSubtract", 4, GL_ONE, GL_ONE, GL_FUNC_SUBTRACT),
    Multiply("Multiply", 5, GL_DST_COLOR, GL_ZERO, GL_FUNC_ADD),
    Screen("Screen", 6, GL_ONE, GL_ONE_MINUS_DST_COLOR, GL_FUNC_ADD),
    Lighten("Lighten", 7, GL_ONE, GL_ONE, GL_MAX),
    Darken("Darken", 8, GL_ONE, GL_ONE, GL_MIN),
    Shade("Shade", 9, GL_DST_COLOR, GL_ZERO, GL_FUNC_ADD),
    Difference("Difference", 10, GL_ONE, GL_ONE, GL_FUNC_REVERSE_SUBTRACT),
    Exclusion("Exclusion", 11, GL_ONE, GL_ONE, GL_FUNC_REVERSE_SUBTRACT),
    Dodge("Dodge", 12, GL_ONE, GL_ONE_MINUS_SRC_COLOR, GL_FUNC_ADD),
    Burn("Burn", 13, GL_ONE_MINUS_DST_COLOR, GL_ONE, GL_FUNC_ADD),
    Invert("Invert", 14, GL_ONE_MINUS_DST_COLOR, GL_ONE_MINUS_SRC_COLOR, GL_FUNC_ADD),
    PremultipliedAlpha("PremultipliedAlpha", 15, GL_ONE, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD),
    HardMix("HardMix", 16, GL_ONE, GL_ONE, GL_FUNC_ADD),
    LinearLight("LinearLight", 17, GL_ONE, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD),
    VividLight("VividLight", 18, GL_ONE, GL_ONE_MINUS_SRC_COLOR, GL_FUNC_ADD),
    PinLight("PinLight", 19, GL_ONE, GL_ONE_MINUS_SRC_COLOR, GL_FUNC_ADD),
    HardLight("HardLight", 20, GL_SRC_ALPHA, GL_DST_COLOR, GL_FUNC_ADD),
    SoftLight("SoftLight", 21, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD),
    Overlay("Overlay", 22, GL_SRC_ALPHA, GL_ONE, GL_FUNC_ADD),
    Luminance("Luminance", 23, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD),
    Color("Color", 24, GL_ONE, GL_ONE_MINUS_SRC_COLOR, GL_FUNC_ADD),
    Saturation("Saturation", 25, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD);

    final int id;
    final String name;
    final int sfactor;
    final int dfactor;
    final int equation;

    BlendType(String name, int id, int sfactor, int dfactor, int equation) {
        this.id = id;
        this.name = name;
        this.sfactor = sfactor;
        this.dfactor = dfactor;
        this.equation = equation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSfactor() {
        return sfactor;
    }

    public int getDfactor() {
        return dfactor;
    }

    public int getEquation() {
        return equation;
    }
}
