package me.hannsi.lfjg.render.nanoVG.renderers.polygon;

import me.hannsi.lfjg.render.nanoVG.system.NanoVGUtil;
import me.hannsi.lfjg.render.nanoVG.system.polygon.NanoVGRendererBase;
import org.lwjgl.nanovg.NanoVG;

/**
 * Represents a renderer for drawing rounded rectangles using NanoVG.
 */
public class NanoVGRoundRect extends NanoVGRendererBase {
    public float round;

    /**
     * Constructs a new NanoVGRoundRect with the specified NanoVG context.
     *
     * @param nvg the NanoVG context
     */
    public NanoVGRoundRect(long nvg) {
        super(nvg);
    }

    /**
     * Sets the size and corner radius of the rounded rectangle.
     *
     * @param x the x-coordinate of the rectangle
     * @param y the y-coordinate of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param round the corner radius of the rectangle
     */
    public void setSize(float x, float y, float width, float height, float round) {
        this.round = round;
        super.setSize(x, y, width, height);
    }

    /**
     * Draws the rounded rectangle with the current settings.
     */
    @Override
    public void draw() {
        NanoVG.nvgBeginPath(nvg);

        NanoVG.nvgRoundedRect(nvg, x, y, width, height, round);

        if (base) {
            switch (gradientMode) {
                case Normal -> {
                    NanoVGUtil.fillColor(nvg, color1);
                }
                case Linear -> {
                    NanoVGUtil.fillLinearGradientColor(nvg, x, y, width, height, color1, color2);
                }
                case Box -> {
                    NanoVGUtil.fillBoxGradientColor(nvg, x, y, width, height, 0, feather, color1, color2);
                }
                case Radial -> {
                    NanoVGUtil.fillRadialGradientColor(nvg, cx, cy, inRange, outRange, color1, color2);
                }
                default -> throw new IllegalStateException("Unexpected value: " + gradientMode);
            }
        }

        if (outLine) {
            NanoVG.nvgStrokeWidth(nvg, lineWidth);

            switch (outLineGradientMode) {
                case Normal -> {
                    NanoVGUtil.strokeColor(nvg, outLineColor1);
                }
                case Linear -> {
                    NanoVGUtil.strokeFillLinearGradientColor(nvg, x, y, width, height, outLineColor1, outLineColor2);
                }
                case Box -> {
                    NanoVGUtil.strokeFillBoxGradientColor(nvg, x, y, width, height, 0, outLineFeather, outLineColor1, outLineColor2);
                }
                case Radial -> {
                    NanoVGUtil.strokeRadialGradientColor(nvg, cx, cy, outLineInRange, outLineOutRange, outLineColor1, outLineColor2);
                }
                default -> throw new IllegalStateException("Unexpected value: " + outLineGradientMode);
            }
        }

        NanoVG.nvgClosePath(nvg);
        super.draw();
    }
}