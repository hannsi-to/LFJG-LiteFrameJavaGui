package me.hannsi.lfjg.render.nanoVG.system;

import me.hannsi.lfjg.utils.color.Color;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;

public class NanoVGUtil {
    public static void framePush(long nvg, float windowWidth, float windowHeight, float devicePixelRatio) {
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        NanoVG.nvgBeginFrame(nvg, windowWidth, windowHeight, devicePixelRatio);
    }

    public static void framePop(long nvg) {
        NanoVG.nvgEndFrame(nvg);
        GL11.glPopAttrib();
    }

    public static void fillBoxGradientColor(long nvg, float x, float y, float width, float height, float round, float feather, Color color1, Color color2) {
        NVGColor nvgColor1 = NVGColor.calloc();
        NVGColor nvgColor2 = NVGColor.calloc();
        color(color1, nvgColor1);
        color(color2, nvgColor2);
        NanoVG.nvgFillPaint(nvg, NanoVG.nvgBoxGradient(nvg, x, y, width, height, round, feather, nvgColor1, nvgColor2, NVGPaint.calloc()));
        NanoVG.nvgFill(nvg);
    }

    public static void fillLinearGradientColor(long nvg, float x, float y, float width, float height, Color color1, Color color2) {
        NVGColor nvgColor1 = NVGColor.calloc();
        NVGColor nvgColor2 = NVGColor.calloc();
        color(color1, nvgColor1);
        color(color2, nvgColor2);
        NanoVG.nvgFillPaint(nvg, NanoVG.nvgLinearGradient(nvg, x, y, x + width, y + height, nvgColor1, nvgColor2, NVGPaint.calloc()));
        NanoVG.nvgFill(nvg);
    }

    public static void fillRadialGradientColor(long nvg, float x, float y, float inRange, float outRange, Color color1, Color color2) {
        NVGColor nvgColor1 = NVGColor.calloc();
        NVGColor nvgColor2 = NVGColor.calloc();
        color(color1, nvgColor1);
        color(color2, nvgColor2);
        NanoVG.nvgFillPaint(nvg, NanoVG.nvgRadialGradient(nvg, x, y, inRange, outRange, nvgColor1, nvgColor2, NVGPaint.calloc()));
        NanoVG.nvgFill(nvg);
    }

    public static void fillColor(long nvg, Color color) {
        NVGColor nvgColor = NVGColor.calloc();
        color(color, nvgColor);
        NanoVG.nvgFillColor(nvg, nvgColor);
        NanoVG.nvgFill(nvg);
    }

    public static void strokeColor(long nvg, Color color) {
        float r = color.getRed() / 255.0f;
        float g = color.getGreen() / 255.0f;
        float b = color.getBlue() / 255.0f;
        float a = color.getAlpha() / 255.0f;

        NVGColor nvgColor = NVGColor.calloc();
        NanoVG.nvgRGBAf(r, g, b, a, nvgColor);
        NanoVG.nvgStrokeColor(nvg, nvgColor);
        NanoVG.nvgStroke(nvg);
    }

    public static void strokeFillBoxGradientColor(long nvg, float x, float y, float width, float height, float round, float feather, Color color1, Color color2) {
        NVGColor nvgColor1 = NVGColor.calloc();
        NVGColor nvgColor2 = NVGColor.calloc();
        color(color1, nvgColor1);
        color(color2, nvgColor2);
        NanoVG.nvgStrokePaint(nvg, NanoVG.nvgBoxGradient(nvg, x, y, width, height, round, feather, nvgColor1, nvgColor2, NVGPaint.calloc()));
        NanoVG.nvgStroke(nvg);
    }

    public static void strokeFillLinearGradientColor(long nvg, float x, float y, float width, float height, Color color1, Color color2) {
        NVGColor nvgColor1 = NVGColor.calloc();
        NVGColor nvgColor2 = NVGColor.calloc();
        color(color1, nvgColor1);
        color(color2, nvgColor2);
        NanoVG.nvgStrokePaint(nvg, NanoVG.nvgLinearGradient(nvg, x, y, x + width, y + height, nvgColor1, nvgColor2, NVGPaint.calloc()));
        NanoVG.nvgStroke(nvg);
    }

    public static void strokeRadialGradientColor(long nvg, float x, float y, float inRange, float outRange, Color color1, Color color2) {
        NVGColor nvgColor1 = NVGColor.calloc();
        NVGColor nvgColor2 = NVGColor.calloc();
        color(color1, nvgColor1);
        color(color2, nvgColor2);
        NanoVG.nvgStrokePaint(nvg, NanoVG.nvgRadialGradient(nvg, x, y, inRange, outRange, nvgColor1, nvgColor2, NVGPaint.calloc()));
        NanoVG.nvgStroke(nvg);
    }

    public static void color(Color color, NVGColor nvgColor) {
        NanoVG.nvgRGBAf(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f, nvgColor);
    }
}
