package me.hannsi.lfjg.event.events.user;

import me.hannsi.lfjg.event.system.Event;

/**
 * The CursorPosEvent class represents an event that occurs when the cursor position changes.
 */
public class CursorPosEvent extends Event {
    private final double xPos;
    private final double yPos;
    private final long window;

    /**
     * Constructs a CursorPosEvent with the specified cursor position and window.
     *
     * @param xPos   The x-coordinate of the cursor position.
     * @param yPos   The y-coordinate of the cursor position.
     * @param window The window associated with the event.
     */
    public CursorPosEvent(double xPos, double yPos, long window) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.window = window;
    }

    /**
     * Retrieves the x-coordinate of the cursor position.
     *
     * @return The x-coordinate of the cursor position.
     */
    public double getXPos() {
        return xPos;
    }

    /**
     * Retrieves the y-coordinate of the cursor position.
     *
     * @return The y-coordinate of the cursor position.
     */
    public double getYPos() {
        return yPos;
    }

    /**
     * Retrieves the window associated with this event.
     *
     * @return The window associated with this event.
     */
    public long getWindow() {
        return window;
    }
}
