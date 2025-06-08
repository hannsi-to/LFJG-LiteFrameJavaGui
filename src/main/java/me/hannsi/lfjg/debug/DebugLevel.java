package me.hannsi.lfjg.debug;

import java.awt.*;

/**
 * Enum representing different levels of debugging information.
 */
public enum DebugLevel {
    DEBUG(0, "Debug", new Color(255, 255, 255, 255)),
    INFO(1, "Info", new Color(0, 128, 255, 255)),
    WARNING(2, "Warning", new Color(255, 255, 0, 255)),
    ERROR(3, "Error", new Color(255, 0, 0, 255));

    private final int code;
    private final String display;
    private final Color jglColor;

    /**
     * Constructor for DebugLevel enum.
     *
     * @param code the code representing the debug level
     * @param display the display name of the debug level
     * @param jglColor the color associated with the debug level
     */
    DebugLevel(int code, String display, Color jglColor) {
        this.code = code;
        this.display = display;
        this.jglColor = jglColor;
    }

    /**
     * Gets the code representing the debug level.
     *
     * @return the code of the debug level
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets the display name of the debug level.
     *
     * @return the display name of the debug level
     */
    public String getDisplay() {
        return display;
    }

    /**
     * Gets the color associated with the debug level.
     *
     * @return the color of the debug level
     */
    public Color getJglColor() {
        return jglColor;
    }
}