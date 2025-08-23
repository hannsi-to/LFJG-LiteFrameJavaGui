package me.hannsi.lfjg.render.renderers;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;

/**
 * Enumeration representing different types of blend modes.
 */
public enum BlendType {
    NORMAL("Normal", 0, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD),
    ADD("Add", 1, GL_ONE, GL_ONE, GL_FUNC_ADD),
    ALPHA_ADD("AlphaAdd", 2, GL_SRC_ALPHA, GL_ONE, GL_FUNC_ADD),
    SUBTRACT("Subtract", 3, GL_ONE, GL_ONE, GL_FUNC_REVERSE_SUBTRACT),
    REVERSE_SUBTRACT("ReverseSubtract", 4, GL_ONE, GL_ONE, GL_FUNC_SUBTRACT),
    MULTIPLY("Multiply", 5, GL_DST_COLOR, GL_ZERO, GL_FUNC_ADD),
    SCREEN("Screen", 6, GL_ONE, GL_ONE_MINUS_DST_COLOR, GL_FUNC_ADD),
    LIGHTEN("Lighten", 7, GL_ONE, GL_ONE, GL_MAX),
    DARKEN("Darken", 8, GL_ONE, GL_ONE, GL_MIN),
    SHADE("Shade", 9, GL_DST_COLOR, GL_ZERO, GL_FUNC_ADD),
    DIFFERENCE("Difference", 10, GL_ONE, GL_ONE, GL_FUNC_REVERSE_SUBTRACT),
    EXCLUSION("Exclusion", 11, GL_ONE, GL_ONE, GL_FUNC_REVERSE_SUBTRACT),
    DODGE("Dodge", 12, GL_ONE, GL_ONE_MINUS_SRC_COLOR, GL_FUNC_ADD),
    BURN("Burn", 13, GL_ONE_MINUS_DST_COLOR, GL_ONE, GL_FUNC_ADD),
    INVERT("Invert", 14, GL_ONE_MINUS_DST_COLOR, GL_ONE_MINUS_SRC_COLOR, GL_FUNC_ADD),
    PREMULTIPLIED_ALPHA("PremultipliedAlpha", 15, GL_ONE, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD),
    HARD_MIX("HardMix", 16, GL_ONE, GL_ONE, GL_FUNC_ADD),
    LINEAR_LIGHT("LinearLight", 17, GL_ONE, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD),
    VIVID_LIGHT("VividLight", 18, GL_ONE, GL_ONE_MINUS_SRC_COLOR, GL_FUNC_ADD),
    PIN_LIGHT("PinLight", 19, GL_ONE, GL_ONE_MINUS_SRC_COLOR, GL_FUNC_ADD),
    HARD_LIGHT("HardLight", 20, GL_SRC_ALPHA, GL_DST_COLOR, GL_FUNC_ADD),
    SOFT_LIGHT("SoftLight", 21, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD),
    OVERLAY("Overlay", 22, GL_SRC_ALPHA, GL_ONE, GL_FUNC_ADD),
    LUMINANCE("Luminance", 23, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD),
    COLOR("Color", 24, GL_ONE, GL_ONE_MINUS_SRC_COLOR, GL_FUNC_ADD),
    SATURATION("Saturation", 25, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD);

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
