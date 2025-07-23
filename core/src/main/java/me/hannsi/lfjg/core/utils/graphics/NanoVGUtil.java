package me.hannsi.lfjg.core.utils.graphics;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.Util;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import org.joml.Vector2f;

import java.nio.ByteBuffer;

import static me.hannsi.lfjg.core.Core.frameBufferSize;
import static me.hannsi.lfjg.core.Core.nanoVGContext;

public class NanoVGUtil extends Util {
    public static void nvgFramePush() {
        Core.GL11.glPushAttrib(Core.GL11.GL_ALL_ATTRIB_BITS);
        nvgBeginFrame(frameBufferSize.x(), frameBufferSize.y(), Core.devicePixelRatio);

        nvgSave();
    }

    public static void nvgFramePop() {
        nvgRestore();
        nvgEndFrame();
        Core.GL11.glPopAttrib();
    }

    public static Vector2f conversionCoordinate(Vector2f openGLCoordinate) {
        float ox = openGLCoordinate.x();
        float oy = openGLCoordinate.y();
        float ny = frameBufferSize.y() - oy;
        return new Vector2f(ox, ny);
    }

    public static void nvgCurrentTransform(float[] xform) {
        Core.NanoVG.nvgCurrentTransform(nanoVGContext, xform);
    }

    public static void nvgRestore() {
        Core.NanoVG.nvgRestore(nanoVGContext);
    }

    public static void nvgTranslate(float x, float y) {
        Vector2f nvgCoordinate = conversionCoordinate(new Vector2f(x, y));
        Core.NanoVG.nvgTranslate(nanoVGContext, nvgCoordinate.x(), nvgCoordinate.y());
    }

    public static void nvgScale(float x, float y) {
        Core.NanoVG.nvgScale(nanoVGContext, x, y);
    }

    public static void nvgSave() {
        Core.NanoVG.nvgSave(nanoVGContext);
    }

    public static void nvgBeginFrame(float windowWidth, float windowHeight, float devicePixelRatio) {
        Core.NanoVG.nvgBeginFrame(nanoVGContext, windowWidth, windowHeight, devicePixelRatio);
    }

    public static void nvgEndFrame() {
        Core.NanoVG.nvgEndFrame(nanoVGContext);
    }

    public static void nvgBeginPath() {
        Core.NanoVG.nvgBeginPath(nanoVGContext);
    }

    public static void nvgClosePath() {
        Core.NanoVG.nvgClosePath(nanoVGContext);
    }

    public static void nvgRotate(float angle) {
        Core.NanoVG.nvgRotate(nanoVGContext, angle);
    }

    public static void nvgFillColor(Core.NVGColor color) {
        Core.NanoVG.nvgFillColor(nanoVGContext, color);
    }

    public static void nvgFontSize(float size) {
        Core.NanoVG.nvgFontSize(nanoVGContext, size);
    }

    public static void nvgTextAlign(int align) {
        Core.NanoVG.nvgTextAlign(nanoVGContext, align);
    }

    public static void nvgFontFace(CharSequence font) {
        Core.NanoVG.nvgFontFace(nanoVGContext, font);
    }

    public static void nvgTransform(float a, float b, float c, float d, float e, float f) {
        Core.NanoVG.nvgTransform(nanoVGContext, a, b, c, d, e, f);
    }

    public static void nvgResetTransform() {
        Core.NanoVG.nvgResetTransform(nanoVGContext);
    }

    public static void nvgTransform(float[] matrix) {
        Core.NanoVG.nvgTransform(nanoVGContext, matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]);
    }

    public static float nvgTextBounds(float x, float y, CharSequence string, float[] bounds) {
        Vector2f nvgCoordinate = conversionCoordinate(new Vector2f(x, y));
        return Core.NanoVG.nvgTextBounds(nanoVGContext, nvgCoordinate.x(), nvgCoordinate.y(), string, bounds);
    }

    public static void nvgTextMetrics(float[] ascender, float[] descender, float[] lineh) {
        Core.NanoVG.nvgTextMetrics(nanoVGContext, ascender, descender, lineh);
    }

    public static void nvgText(float x, float y, String text) {
        Vector2f nvgCoordinate = conversionCoordinate(new Vector2f(x, y));
        Core.NanoVG.nvgText(nanoVGContext, nvgCoordinate.x(), nvgCoordinate.y(), text);
    }

    public static void nvgFill() {
        Core.NanoVG.nvgFill(nanoVGContext);
    }

    public static int nvgCreateFontMem(CharSequence name, ByteBuffer data, boolean freeData) {
        return Core.NanoVG.nvgCreateFontMem(nanoVGContext, name, data, freeData);
    }

    public static void nvgMoveTo(float x, float y) {
        Vector2f nvgCoordinate = conversionCoordinate(new Vector2f(x, y));
        Core.NanoVG.nvgMoveTo(nanoVGContext, nvgCoordinate.x(), nvgCoordinate.y());
    }

    public static void nvgLineTo(float x, float y) {
        Vector2f nvgCoordinate = conversionCoordinate(new Vector2f(x, y));
        Core.NanoVG.nvgLineTo(nanoVGContext, nvgCoordinate.x(), nvgCoordinate.y());
    }

    public static void nvgStrokeWidth(float size) {
        Core.NanoVG.nvgStrokeWidth(nanoVGContext, size);
    }

    public static void nvgStrokeColor(Core.NVGColor color) {
        Core.NanoVG.nvgStrokeColor(nanoVGContext, color);
    }

    public static void nvgStroke() {
        Core.NanoVG.nvgStroke(nanoVGContext);
    }

    public static void nvgFontBlur(float blur) {
        Core.NanoVG.nvgFontBlur(nanoVGContext, blur);
    }

    public static Core.NVGColor colorToNVG(Color color) {
        return new Core.NVGColor(color.getRedF(), color.getGreenF(), color.getBlueF(), color.getAlphaF());
    }
}
