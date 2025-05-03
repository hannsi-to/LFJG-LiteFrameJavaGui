package me.hannsi.lfjg.utils.graphics;

import me.hannsi.lfjg.utils.graphics.color.Color;
import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import java.nio.ByteBuffer;

import static me.hannsi.lfjg.frame.frame.LFJGContext.*;
import static org.lwjgl.opengl.GL11.*;

public class NanoVGUtil {
    public static void nvgFramePush() {
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        nvgBeginFrame(frameBufferSize.x(), frameBufferSize.y(), devicePixelRatio);

        nvgSave();
    }

    public static void nvgFramePop() {
        nvgRestore();
        nvgEndFrame();
        glPopAttrib();
    }

    public static Vector2f conversionCoordinate(Vector2f openGLCoordinate) {
        float ox = openGLCoordinate.x();
        float oy = openGLCoordinate.y();
        float nx = ox;
        float ny = frameBufferSize.y() - oy;
        return new Vector2f(nx, ny);
    }

    public static void nvgCurrentTransform(float[] xform) {
        NanoVG.nvgCurrentTransform(nanoVGContext, xform);
    }

    public static void nvgRestore() {
        NanoVG.nvgRestore(nanoVGContext);
    }

    public static void nvgTranslate(float x, float y) {
        Vector2f nvgCoordinate = conversionCoordinate(new Vector2f(x, y));
        NanoVG.nvgTranslate(nanoVGContext, nvgCoordinate.x(), nvgCoordinate.y());
    }

    public static void nvgScale(float x, float y) {
        NanoVG.nvgScale(nanoVGContext, x, y);
    }

    public static void nvgSave() {
        NanoVG.nvgSave(nanoVGContext);
    }

    public static void nvgBeginFrame(float windowWidth, float windowHeight, float devicePixelRatio) {
        NanoVG.nvgBeginFrame(nanoVGContext, windowWidth, windowHeight, devicePixelRatio);
    }

    public static void nvgEndFrame() {
        NanoVG.nvgEndFrame(nanoVGContext);
    }

    public static void nvgBeginPath() {
        NanoVG.nvgBeginPath(nanoVGContext);
    }

    public static void nvgClosePath() {
        NanoVG.nvgClosePath(nanoVGContext);
    }

    public static void nvgRotate(float angle) {
        NanoVG.nvgRotate(nanoVGContext, angle);
    }

    public static void nvgFillColor(NVGColor color) {
        NanoVG.nvgFillColor(nanoVGContext, color);
    }

    public static void nvgFontSize(float size) {
        NanoVG.nvgFontSize(nanoVGContext, size);
    }

    public static void nvgTextAlign(int align) {
        NanoVG.nvgTextAlign(nanoVGContext, align);
    }

    public static void nvgFontFace(CharSequence font) {
        NanoVG.nvgFontFace(nanoVGContext, font);
    }

    public static void nvgTransform(float a, float b, float c, float d, float e, float f) {
        NanoVG.nvgTransform(nanoVGContext, a, b, c, d, e, f);
    }

    public static void nvgResetTransform() {
        NanoVG.nvgResetTransform(nanoVGContext);
    }

    public static void nvgTransform(float[] matrix) {
        NanoVG.nvgTransform(nanoVGContext, matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]);
    }

    public static float nvgTextBounds(float x, float y, CharSequence string, float[] bounds) {
        Vector2f nvgCoordinate = conversionCoordinate(new Vector2f(x, y));
        return NanoVG.nvgTextBounds(nanoVGContext, nvgCoordinate.x(), nvgCoordinate.y(), string, bounds);
    }

    public static void nvgTextMetrics(float[] ascender, float[] descender, float[] lineh) {
        NanoVG.nvgTextMetrics(nanoVGContext, ascender, descender, lineh);
    }

    public static void nvgText(float x, float y, String text) {
        Vector2f nvgCoordinate = conversionCoordinate(new Vector2f(x, y));
        NanoVG.nvgText(nanoVGContext, nvgCoordinate.x(), nvgCoordinate.y(), text);
    }

    public static void nvgFill() {
        NanoVG.nvgFill(nanoVGContext);
    }

    public static int nvgCreateFontMem(CharSequence name, ByteBuffer data, boolean freeData) {
        return NanoVG.nvgCreateFontMem(nanoVGContext, name, data, freeData);
    }

    public static void nvgMoveTo(float x, float y) {
        Vector2f nvgCoordinate = conversionCoordinate(new Vector2f(x, y));
        NanoVG.nvgMoveTo(nanoVGContext, nvgCoordinate.x(), nvgCoordinate.y());
    }

    public static void nvgLineTo(float x, float y) {
        Vector2f nvgCoordinate = conversionCoordinate(new Vector2f(x, y));
        NanoVG.nvgLineTo(nanoVGContext, nvgCoordinate.x(), nvgCoordinate.y());
    }

    public static void nvgStrokeWidth(float size) {
        NanoVG.nvgStrokeWidth(nanoVGContext, size);
    }

    public static void nvgStrokeColor(NVGColor color) {
        NanoVG.nvgStrokeColor(nanoVGContext, color);
    }

    public static void nvgStroke() {
        NanoVG.nvgStroke(nanoVGContext);
    }

    public static void nvgFontBlur(float blur) {
        NanoVG.nvgFontBlur(nanoVGContext, blur);
    }

    public static NVGColor colorToNVG(Color color) {
        NVGColor nvgColor = NVGColor.calloc();
        nvgColor.r(color.getRedF());
        nvgColor.g(color.getGreenF());
        nvgColor.b(color.getBlueF());
        nvgColor.a(color.getAlphaF());
        return nvgColor;
    }
}
