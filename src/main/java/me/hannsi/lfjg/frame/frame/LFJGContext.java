package me.hannsi.lfjg.frame.frame;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.system.font.FontCache;
import me.hannsi.lfjg.render.system.svg.SVGCache;
import me.hannsi.lfjg.utils.math.Projection;
import me.hannsi.lfjg.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.utils.toolkit.MouseInfo;
import org.joml.Vector2i;

public class LFJGContext {
    public static String[] args;

    public static Frame frame;
    public static Vector2i windowSize;
    public static Vector2i frameBufferSize;
    public static float devicePixelRatio;
    public static Projection projection;
    public static long nanoVGContext;

    public static MouseInfo mouseInfo;
    public static KeyboardInfo keyboardInfo;

    public static FontCache fontCache;
    public static SVGCache svgCache;
}
