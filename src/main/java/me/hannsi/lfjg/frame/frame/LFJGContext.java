package me.hannsi.lfjg.frame.frame;

import me.hannsi.lfjg.render.openGL.system.font.FontCache;
import me.hannsi.lfjg.render.openGL.system.svg.SVGCache;
import me.hannsi.lfjg.utils.math.Projection;
import org.joml.Vector2f;

public class LFJGContext {
    public static Frame frame;
    public static Vector2f windowSize;
    public static Vector2f frameBufferSize;
    public static float devicePixelRatio;
    public static Projection projection;
    public static long nanoVGContext;

    public static FontCache fontCache;
    public static SVGCache svgCache;
}
