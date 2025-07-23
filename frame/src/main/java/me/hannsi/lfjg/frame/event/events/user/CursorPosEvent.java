package me.hannsi.lfjg.frame.event.events.user;

import lombok.Getter;
import me.hannsi.lfjg.core.event.Event;

/**
 * The CursorPosEvent class represents an event that occurs when the cursor position changes.
 */
@Getter
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
}
