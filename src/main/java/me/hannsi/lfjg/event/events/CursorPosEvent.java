package me.hannsi.lfjg.event.events;

import me.hannsi.lfjg.event.system.Event;

public class CursorPosEvent extends Event {
    private final double xPos;
    private final double yPos;
    private final long window;

    public CursorPosEvent(double xPos, double yPos, long window) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.window = window;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public long getWindow() {
        return window;
    }
}
