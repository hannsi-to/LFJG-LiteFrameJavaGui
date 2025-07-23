package me.hannsi.lfjg.core.utils.graphics;

import me.hannsi.lfjg.core.utils.Util;
import me.hannsi.lfjg.core.utils.toolkit.ToolkitUtil;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for retrieving display size information in various formats.
 */
public class DisplayUtil extends Util {

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

    public static Dimension getTotalDisplaySizeDimension() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();

        int width = 0;
        int height = 0;
        for (GraphicsDevice screen : screens) {
            Rectangle bounds = screen.getDefaultConfiguration().getBounds();
            width += bounds.width;
            height = Math.max(height, bounds.height);
        }

        return new Dimension(width, height);
    }

    public static List<Vector2i> getAllMonitorSizes() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();
        List<Vector2i> resolutions = new ArrayList<>();

        for (GraphicsDevice screen : screens) {
            Rectangle bounds = screen.getDefaultConfiguration().getBounds();
            resolutions.add(new Vector2i(bounds.width, bounds.height));
        }

        return resolutions;
    }

    public static float getDpiScale() {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        GraphicsConfiguration config = device.getDefaultConfiguration();
        AffineTransform transform = config.getDefaultTransform();
        return (float) transform.getScaleX();
    }

    public static int getPrimaryMonitorRefreshRate() {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode displayMode = device.getDisplayMode();
        return displayMode.getRefreshRate();
    }

    public static Vector2i getFullScreenResolution() {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode displayMode = device.getDisplayMode();
        return new Vector2i(displayMode.getWidth(), displayMode.getHeight());
    }

    public static double getAspectRatio() {
        Dimension screenSize = getDisplaySizeDimension();
        return (double) screenSize.width / screenSize.height;
    }

    public static String getScreenOrientation() {
        Dimension screenSize = getDisplaySizeDimension();
        return screenSize.width > screenSize.height ? "Landscape" : "Portrait";
    }

    public static Vector2i getMonitorResolution(int monitorIndex) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();

        if (monitorIndex < 0 || monitorIndex >= screens.length) {
            return null;
        }

        Rectangle bounds = screens[monitorIndex].getDefaultConfiguration().getBounds();
        return new Vector2i(bounds.width, bounds.height);
    }

    public static int getMonitorIndexAt(int x, int y) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();

        for (int i = 0; i < screens.length; i++) {
            Rectangle screenBounds = screens[i].getDefaultConfiguration().getBounds();
            if (screenBounds.contains(x, y)) {
                return i;
            }
        }

        return -1;
    }
}