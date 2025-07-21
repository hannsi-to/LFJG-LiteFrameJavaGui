package me.hannsi.lfjg.frame;

import me.hannsi.lfjg.core.utils.math.Projection;
import me.hannsi.lfjg.core.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.core.utils.toolkit.MouseInfo;
import org.joml.Vector2i;

public class LFJGContext {
    public static String[] args;

    public static Frame frame;
    public static Vector2i windowSize;
    public static Vector2i frameBufferSize;
    public static float devicePixelRatio;
    public static Projection projection2D;
    public static Projection projection3D;
    public static long nanoVGContext;

    public static MouseInfo mouseInfo;
    public static KeyboardInfo keyboardInfo;
}
