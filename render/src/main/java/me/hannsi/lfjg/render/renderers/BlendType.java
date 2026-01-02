package me.hannsi.lfjg.render.renderers;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;

/**
 * Enumeration representing different types of blend modes.
 */
public enum BlendType implements IEnumTypeBase {
    NORMAL(0, "Normal", false, GL_ONE, GL_ZERO, GL_ONE, GL_ZERO, GL_FUNC_ADD, GL_FUNC_ADD, false, false),
    ALPHA(1, "Alpha", true, GL_ONE, GL_ZERO, GL_ONE, GL_ZERO, GL_FUNC_ADD, GL_FUNC_ADD, false, false),
    //    ALPHA(1, "Alpha", true, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD, GL_FUNC_ADD, true, true),
    ADD(2, "Add", true, GL_SRC_ALPHA, GL_ONE, GL_ONE, GL_ONE, GL_FUNC_ADD, GL_FUNC_ADD, true, true),
    MULTIPLY(3, "Multiply", true, GL_DST_COLOR, GL_ZERO, GL_DST_ALPHA, GL_ZERO, GL_FUNC_ADD, GL_FUNC_ADD, true, true),
    SCREEN(4, "Screen", true, GL_ONE, GL_ONE_MINUS_SRC_COLOR, GL_ONE, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD, GL_FUNC_ADD, true, true),
    SUBTRACT(5, "Subtract", true, GL_SRC_ALPHA, GL_ONE, GL_ONE, GL_ONE, GL_FUNC_REVERSE_SUBTRACT, GL_FUNC_ADD, true, true),
    MAX(6, "Max", true, GL_ONE, GL_ONE, GL_ONE, GL_ONE, GL_MAX, GL_MAX, false, false),
    MIN(7, "Min", true, GL_ONE, GL_ONE, GL_ONE, GL_ONE, GL_MIN, GL_MIN, false, false),
    INVERT(8, "Invert", true, GL_ONE_MINUS_DST_COLOR, GL_ZERO, GL_ONE, GL_ZERO, GL_FUNC_ADD, GL_FUNC_ADD, true, true),
    PREMULTIPLIED_ALPHA(9, "PremultipliedAlpha", true, GL_ONE, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD, GL_FUNC_ADD, true, true),
    ADD_NO_ALPHA(10, "AddNoAlpha", true, GL_ONE, GL_ONE, GL_ONE, GL_ONE, GL_FUNC_ADD, GL_FUNC_ADD, true, true),
    SUBTRACT_NORMAL(11, "SubtractNormal", true, GL_ONE, GL_ONE, GL_ONE, GL_ONE, GL_FUNC_SUBTRACT, GL_FUNC_SUBTRACT, true, true),
    DARKEN(12, "Darken", true, GL_ONE, GL_ONE, GL_ONE, GL_ONE, GL_MIN, GL_MIN, true, true),
    LIGHTEN(13, "Lighten", true, GL_ONE, GL_ONE, GL_ONE, GL_ONE, GL_MAX, GL_MAX, true, true),
    COLOR_ONLY(14, "ColorOnly", true, GL_ONE, GL_ZERO, GL_ZERO, GL_ONE, GL_FUNC_ADD, GL_FUNC_ADD, true, true),
    ALPHA_ONLY(15, "AlphaOnly", true, GL_ZERO, GL_ONE, GL_ONE, GL_ZERO, GL_FUNC_ADD, GL_FUNC_ADD, true, true),
    MULTIPLY_ALPHA(16, "MultiplyAlpha", true, GL_DST_COLOR, GL_ONE_MINUS_SRC_ALPHA, GL_DST_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_FUNC_ADD, GL_FUNC_ADD, true, true),
    ;

    final int id;
    final String name;
    final boolean blend;
    final int srcRGB;
    final int dstRGB;
    final int srcA;
    final int dstA;
    final int eqRGB;
    final int eqA;
    final boolean depthWrite;
    final boolean depthTest;

    BlendType(int id, String name, boolean blend, int srcRGB, int dstRGB, int srcA, int dstA, int eqRGB, int eqA, boolean depthWrite, boolean depthTest) {
        this.id = id;
        this.name = name;
        this.blend = blend;
        this.srcRGB = srcRGB;
        this.dstRGB = dstRGB;
        this.srcA = srcA;
        this.dstA = dstA;
        this.eqRGB = eqRGB;
        this.eqA = eqA;
        this.depthWrite = depthWrite;
        this.depthTest = depthTest;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean isBlend() {
        return blend;
    }

    public int getSrcRGB() {
        return srcRGB;
    }

    public int getDstRGB() {
        return dstRGB;
    }

    public int getSrcA() {
        return srcA;
    }

    public int getDstA() {
        return dstA;
    }

    public int getEqRGB() {
        return eqRGB;
    }

    public int getEqA() {
        return eqA;
    }

    public boolean isDepthWrite() {
        return depthWrite;
    }

    public boolean isDepthTest() {
        return depthTest;
    }
}
