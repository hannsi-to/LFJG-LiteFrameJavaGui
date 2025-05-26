package me.hannsi.lfjg.frame.frame;

import me.hannsi.lfjg.render.openGL.system.font.FontCache;
import me.hannsi.lfjg.render.openGL.system.svg.SVGCache;
import me.hannsi.lfjg.utils.math.Projection;
import org.joml.Vector2i;

public class LFJGContext {
    public static String[] args;

    public static Frame frame;
    public static Vector2i windowSize;
    public static Vector2i frameBufferSize;
    public static float devicePixelRatio;
    public static Projection projection;
    public static long nanoVGContext;

    public static FontCache fontCache;
    public static SVGCache svgCache;
}
