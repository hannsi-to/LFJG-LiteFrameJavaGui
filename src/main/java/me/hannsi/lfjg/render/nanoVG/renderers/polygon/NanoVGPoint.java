package me.hannsi.lfjg.render.nanoVG.renderers.polygon;

import me.hannsi.lfjg.render.nanoVG.system.NanoVGUtil;
import me.hannsi.lfjg.render.nanoVG.system.polygon.NanoVGRendererBase;
import org.lwjgl.nanovg.NanoVG;

/**
 * Represents a renderer for drawing points using NanoVG.
 */
public class NanoVGPoint extends NanoVGRendererBase {

    /**
     * Constructs a new NanoVGPoint with the specified NanoVG context.
     *
     * @param nvg the NanoVG context
     */
    public NanoVGPoint(long nvg) {
        super(nvg);
    }

    /**
     * Draws the point with the current settings.
     */
    @Override
    public void draw() {
        NanoVG.nvgBeginPath(nvg);

        NanoVG.nvgCircle(nvg, x, y, pointSize);

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

        NanoVG.nvgClosePath(nvg);

        super.draw();
    }
}