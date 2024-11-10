package me.hannsi.lfjg.utils.graphics;

import me.hannsi.lfjg.utils.toolkit.ToolkitUtil;

import java.awt.*;

public class DisplayUtil {
    public static Dimension getScreenSize() {
        return ToolkitUtil.getDefaultToolkit().getScreenSize();
    }

    public static double getDisplayWidthD() {
        return getScreenSize().getWidth();
    }

    public static double getDisplayHeightD() {
        return getScreenSize().getHeight();
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
