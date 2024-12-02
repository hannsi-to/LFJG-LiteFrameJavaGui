package me.hannsi.lfjg.utils.graphics;

import me.hannsi.lfjg.utils.toolkit.ToolkitUtil;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.awt.*;

public class DisplayUtil {
    public static Dimension getDisplaySizeDimension() {
        return ToolkitUtil.getDefaultToolkit().getScreenSize();
    }

    public static Vector2i getDisplaySizeVector2i() {
        return new Vector2i(getDisplayWidthI(), getDisplayHeightI());
    }

    public static Vector2f getDisplaySizeVector2f() {
        return new Vector2f(getDisplayWidthF(), getDisplayHeightF());
    }

    public static Vector2d getDisplaySizeVector2d() {
        return new Vector2d(getDisplayWidthD(), getDisplayHeightD());
    }

    public static double getDisplayWidthD() {
        return getDisplaySizeDimension().getWidth();
    }

    public static double getDisplayHeightD() {
        return getDisplaySizeDimension().getHeight();
    }

    public static float getDisplayWidthF() {
        return (float) getDisplayWidthD();
    }

    public static float getDisplayHeightF() {
        return (float) getDisplayHeightD();
    }

    public static int getDisplayWidthI() {
        return (int) getDisplayWidthD();
    }

    public static int getDisplayHeightI() {
        return (int) getDisplayHeightD();
    }
}
