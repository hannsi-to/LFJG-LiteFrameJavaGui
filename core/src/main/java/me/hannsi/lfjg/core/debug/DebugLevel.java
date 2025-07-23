package me.hannsi.lfjg.core.debug;

import java.awt.*;

public enum DebugLevel {
    DEBUG(0, "Debug", new Color(255, 255, 255, 255)),
    INFO(1, "Info", new Color(0, 128, 255, 255)),
    WARNING(2, "Warning", new Color(255, 255, 0, 255)),
    ERROR(3, "Error", new Color(255, 0, 0, 255));

    private final int code;
    private final String display;
    private final Color jglColor;

    DebugLevel(int code, String display, Color jglColor) {
        this.code = code;
        this.display = display;
        this.jglColor = jglColor;
    }

    public int getCode() {
        return code;
    }

    public String getDisplay() {
        return display;
    }

    public Color getJglColor() {
        return jglColor;
    }
}