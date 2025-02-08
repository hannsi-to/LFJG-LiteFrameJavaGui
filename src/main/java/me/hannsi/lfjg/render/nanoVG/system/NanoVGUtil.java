package me.hannsi.lfjg.render.nanoVG.system;

import me.hannsi.lfjg.utils.graphics.color.Color;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;

/**
 * Utility class for NanoVG rendering operations.
 */
public class NanoVGUtil {

    /**
     * Begins a new frame for NanoVG rendering.
     *
     * @param nvg the NanoVG context
     * @param windowWidth the width of the window
     * @param windowHeight the height of the window
     * @param devicePixelRatio the device pixel ratio
     */
    public static void framePush(long nvg, float windowWidth, float windowHeight, float devicePixelRatio) {
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        NanoVG.nvgBeginFrame(nvg, windowWidth, windowHeight, devicePixelRatio);
    }

    /**
     * Ends the current frame for NanoVG rendering.
     *
     * @param nvg the NanoVG context
     */
    public static void framePop(long nvg) {
        NanoVG.nvgEndFrame(nvg);
        GL11.glPopAttrib();
    }

    /**
     * Fills a box gradient with the specified colors.
     *
     * @param nvg the NanoVG context
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param width the width of the box
     * @param height the height of the box
     * @param round the corner radius
     * @param feather the feather value
     * @param color1 the first color
     * @param color2 the second color
     */
    public static void fillBoxGradientColor(long nvg, float x, float y, float width, float height, float round, float feather, Color color1, Color color2) {
        NVGColor nvgColor1 = NVGColor.calloc();
        NVGColor nvgColor2 = NVGColor.calloc();
        color(color1, nvgColor1);
        color(color2, nvgColor2);
        NanoVG.nvgFillPaint(nvg, NanoVG.nvgBoxGradient(nvg, x, y, width, height, round, feather, nvgColor1, nvgColor2, NVGPaint.calloc()));
        NanoVG.nvgFill(nvg);
    }

    /**
     * Fills a linear gradient with the specified colors.
     *
     * @param nvg the NanoVG context
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param width the width of the gradient
     * @param height the height of the gradient
     * @param color1 the first color
     * @param color2 the second color
     */
    public static void fillLinearGradientColor(long nvg, float x, float y, float width, float height, Color color1, Color color2) {
        NVGColor nvgColor1 = NVGColor.calloc();
        NVGColor nvgColor2 = NVGColor.calloc();
        color(color1, nvgColor1);
        color(color2, nvgColor2);
        NanoVG.nvgFillPaint(nvg, NanoVG.nvgLinearGradient(nvg, x, y, x + width, y + height, nvgColor1, nvgColor2, NVGPaint.calloc()));
        NanoVG.nvgFill(nvg);
    }

    /**
     * Fills a radial gradient with the specified colors.
     *
     * @param nvg the NanoVG context
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param inRange the inner range of the gradient
     * @param outRange the outer range of the gradient
     * @param color1 the first color
     * @param color2 the second color
     */
    public static void fillRadialGradientColor(long nvg, float x, float y, float inRange, float outRange, Color color1, Color color2) {
        NVGColor nvgColor1 = NVGColor.calloc();
        NVGColor nvgColor2 = NVGColor.calloc();
        color(color1, nvgColor1);
        color(color2, nvgColor2);
        NanoVG.nvgFillPaint(nvg, NanoVG.nvgRadialGradient(nvg, x, y, inRange, outRange, nvgColor1, nvgColor2, NVGPaint.calloc()));
        NanoVG.nvgFill(nvg);
    }

    /**
     * Fills the specified color.
     *
     * @param nvg the NanoVG context
     * @param color the color to fill
     */
    public static void fillColor(long nvg, Color color) {
        NVGColor nvgColor = NVGColor.calloc();
        color(color, nvgColor);
        NanoVG.nvgFillColor(nvg, nvgColor);
        NanoVG.nvgFill(nvg);
    }

    /**
     * Strokes the specified color.
     *
     * @param nvg the NanoVG context
     * @param color the color to stroke
     */
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

    /**
     * Strokes a box gradient with the specified colors.
     *
     * @param nvg the NanoVG context
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param width the width of the box
     * @param height the height of the box
     * @param round the corner radius
     * @param feather the feather value
     * @param color1 the first color
     * @param color2 the second color
     */
    public static void strokeFillBoxGradientColor(long nvg, float x, float y, float width, float height, float round, float feather, Color color1, Color color2) {
        NVGColor nvgColor1 = NVGColor.calloc();
        NVGColor nvgColor2 = NVGColor.calloc();
        color(color1, nvgColor1);
        color(color2, nvgColor2);
        NanoVG.nvgStrokePaint(nvg, NanoVG.nvgBoxGradient(nvg, x, y, width, height, round, feather, nvgColor1, nvgColor2, NVGPaint.calloc()));
        NanoVG.nvgStroke(nvg);
    }

    /**
     * Strokes a linear gradient with the specified colors.
     *
     * @param nvg the NanoVG context
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param width the width of the gradient
     * @param height the height of the gradient
     * @param color1 the first color
     * @param color2 the second color
     */
    public static void strokeFillLinearGradientColor(long nvg, float x, float y, float width, float height, Color color1, Color color2) {
        NVGColor nvgColor1 = NVGColor.calloc();
        NVGColor nvgColor2 = NVGColor.calloc();
        color(color1, nvgColor1);
        color(color2, nvgColor2);
        NanoVG.nvgStrokePaint(nvg, NanoVG.nvgLinearGradient(nvg, x, y, x + width, y + height, nvgColor1, nvgColor2, NVGPaint.calloc()));
        NanoVG.nvgStroke(nvg);
    }

    /**
     * Strokes a radial gradient with the specified colors.
     *
     * @param nvg the NanoVG context
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param inRange the inner range of the gradient
     * @param outRange the outer range of the gradient
     * @param color1 the first color
     * @param color2 the second color
     */
    public static void strokeRadialGradientColor(long nvg, float x, float y, float inRange, float outRange, Color color1, Color color2) {
        NVGColor nvgColor1 = NVGColor.calloc();
        NVGColor nvgColor2 = NVGColor.calloc();
        color(color1, nvgColor1);
        color(color2, nvgColor2);
        NanoVG.nvgStrokePaint(nvg, NanoVG.nvgRadialGradient(nvg, x, y, inRange, outRange, nvgColor1, nvgColor2, NVGPaint.calloc()));
        NanoVG.nvgStroke(nvg);
    }

    /**
     * Converts a Color object to an NVGColor object.
     *
     * @param color the Color object
     * @param nvgColor the NVGColor object
     */
    public static void color(Color color, NVGColor nvgColor) {
        NanoVG.nvgRGBAf(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f, nvgColor);
    }
}