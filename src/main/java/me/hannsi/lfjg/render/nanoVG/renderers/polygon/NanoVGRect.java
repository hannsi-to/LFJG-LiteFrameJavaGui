package me.hannsi.lfjg.render.nanoVG.renderers.polygon;

import me.hannsi.lfjg.render.nanoVG.system.NanoVGUtil;
import me.hannsi.lfjg.render.nanoVG.system.polygon.NanoVGRendererBase;
import org.lwjgl.nanovg.NanoVG;

/**
 * Represents a renderer for drawing rectangles using NanoVG.
 */
public class NanoVGRect extends NanoVGRendererBase {

    /**
     * Constructs a new NanoVGRect with the specified NanoVG context.
     *
     * @param nvg the NanoVG context
     */
    public NanoVGRect(long nvg) {
        super(nvg);
    }

    /**
     * Draws the rectangle with the current settings.
     */
    @Override
    public void draw() {
        NanoVG.nvgBeginPath(nvg);

        NanoVG.nvgRect(nvg, x, y, width, height);

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