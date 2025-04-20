package me.hannsi.lfjg.utils.graphics;

import me.hannsi.lfjg.utils.graphics.color.Color;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import java.nio.ByteBuffer;

import static me.hannsi.lfjg.frame.frame.LFJGContext.nanoVGContext;
import static me.hannsi.lfjg.frame.frame.LFJGContext.projection;
import static org.lwjgl.opengl.GL11.*;

public class NanoVGUtil {
    public static void nvgFramePush() {
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        nvgBeginFrame(projection.getWindowWidth(), projection.getWindowHeight(), 1);

        nvgSave();

        nvgTranslate(0, projection.getWindowHeight());
    }

    public static void nvgFramePop() {
        nvgRestore();
        nvgEndFrame();
        glPopAttrib();
    }

    public static void nvgRestore() {
        NanoVG.nvgRestore(nanoVGContext);
    }

    public static void nvgTranslate(float x, float y) {
        NanoVG.nvgTranslate(nanoVGContext, x, y);
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

    public static void nvgText(float x, float y, String text) {
        NanoVG.nvgText(nanoVGContext, x, y, text);
    }

    public static void nvgFill() {
        NanoVG.nvgFill(nanoVGContext);
    }

    public static int nvgCreateFontMem(CharSequence name, ByteBuffer data, boolean freeData) {
        return NanoVG.nvgCreateFontMem(nanoVGContext, name, data, freeData);
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
