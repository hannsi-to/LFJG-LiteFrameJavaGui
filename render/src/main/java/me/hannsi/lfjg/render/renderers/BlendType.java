package me.hannsi.lfjg.render.renderers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

/**
 * Enumeration representing different types of blend modes.
 */
public enum BlendType {
    NORMAL("Normal", 0, GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL14.GL_FUNC_ADD),
    ADD("Add", 1, GL11.GL_ONE, GL11.GL_ONE, GL14.GL_FUNC_ADD),
    ALPHA_ADD("AlphaAdd", 2, GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL14.GL_FUNC_ADD),
    SUBTRACT("Subtract", 3, GL11.GL_ONE, GL11.GL_ONE, GL14.GL_FUNC_REVERSE_SUBTRACT),
    REVERSE_SUBTRACT("ReverseSubtract", 4, GL11.GL_ONE, GL11.GL_ONE, GL14.GL_FUNC_SUBTRACT),
    MULTIPLY("Multiply", 5, GL11.GL_DST_COLOR, GL11.GL_ZERO, GL14.GL_FUNC_ADD),
    SCREEN("Screen", 6, GL11.GL_ONE, GL11.GL_ONE_MINUS_DST_COLOR, GL14.GL_FUNC_ADD),
    LIGHTEN("Lighten", 7, GL11.GL_ONE, GL11.GL_ONE, GL14.GL_MAX),
    DARKEN("Darken", 8, GL11.GL_ONE, GL11.GL_ONE, GL14.GL_MIN),
    SHADE("Shade", 9, GL11.GL_DST_COLOR, GL11.GL_ZERO, GL14.GL_FUNC_ADD),
    DIFFERENCE("Difference", 10, GL11.GL_ONE, GL11.GL_ONE, GL14.GL_FUNC_REVERSE_SUBTRACT),
    EXCLUSION("Exclusion", 11, GL11.GL_ONE, GL11.GL_ONE, GL14.GL_FUNC_REVERSE_SUBTRACT),
    DODGE("Dodge", 12, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_COLOR, GL14.GL_FUNC_ADD),
    BURN("Burn", 13, GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE, GL14.GL_FUNC_ADD),
    INVERT("Invert", 14, GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR, GL14.GL_FUNC_ADD),
    PREMULTIPLIED_ALPHA("PremultipliedAlpha", 15, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA, GL14.GL_FUNC_ADD),
    HARD_MIX("HardMix", 16, GL11.GL_ONE, GL11.GL_ONE, GL14.GL_FUNC_ADD),
    LINEAR_LIGHT("LinearLight", 17, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA, GL14.GL_FUNC_ADD),
    VIVID_LIGHT("VividLight", 18, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_COLOR, GL14.GL_FUNC_ADD),
    PIN_LIGHT("PinLight", 19, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_COLOR, GL14.GL_FUNC_ADD),
    HARD_LIGHT("HardLight", 20, GL11.GL_SRC_ALPHA, GL11.GL_DST_COLOR, GL14.GL_FUNC_ADD),
    SOFT_LIGHT("SoftLight", 21, GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL14.GL_FUNC_ADD),
    OVERLAY("Overlay", 22, GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL14.GL_FUNC_ADD),
    LUMINANCE("Luminance", 23, GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL14.GL_FUNC_ADD),
    COLOR("Color", 24, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_COLOR, GL14.GL_FUNC_ADD),
    SATURATION("Saturation", 25, GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL14.GL_FUNC_ADD);

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
