package me.hannsi.lfjg.utils.graphics;

import me.hannsi.lfjg.utils.toolkit.ToolkitUtil;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.awt.*;

/**
 * Utility class for retrieving display size information in various formats.
 */
public class DisplayUtil {

    /**
     * Gets the display size as a Dimension object.
     *
     * @return the display size as a Dimension
     */
    public static Dimension getDisplaySizeDimension() {
        return ToolkitUtil.getDefaultToolkit().getScreenSize();
    }

    /**
     * Gets the display size as a Vector2i object.
     *
     * @return the display size as a Vector2i
     */
    public static Vector2i getDisplaySizeVector2i() {
        return new Vector2i(getDisplayWidthI(), getDisplayHeightI());
    }

    /**
     * Gets the display size as a Vector2f object.
     *
     * @return the display size as a Vector2f
     */
    public static Vector2f getDisplaySizeVector2f() {
        return new Vector2f(getDisplayWidthF(), getDisplayHeightF());
    }

    /**
     * Gets the display size as a Vector2d object.
     *
     * @return the display size as a Vector2d
     */
    public static Vector2d getDisplaySizeVector2d() {
        return new Vector2d(getDisplayWidthD(), getDisplayHeightD());
    }

    /**
     * Gets the display width as a double.
     *
     * @return the display width as a double
     */
    public static double getDisplayWidthD() {
        return getDisplaySizeDimension().getWidth();
    }

    /**
     * Gets the display height as a double.
     *
     * @return the display height as a double
     */
    public static double getDisplayHeightD() {
        return getDisplaySizeDimension().getHeight();
    }

    /**
     * Gets the display width as a float.
     *
     * @return the display width as a float
     */
    public static float getDisplayWidthF() {
        return (float) getDisplayWidthD();
    }

    /**
     * Gets the display height as a float.
     *
     * @return the display height as a float
     */
    public static float getDisplayHeightF() {
        return (float) getDisplayHeightD();
    }

    /**
     * Gets the display width as an int.
     *
     * @return the display width as an int
     */
    public static int getDisplayWidthI() {
        return (int) getDisplayWidthD();
    }

    /**
     * Gets the display height as an int.
     *
     * @return the display height as an int
     */
    public static int getDisplayHeightI() {
        return (int) getDisplayHeightD();
    }
}