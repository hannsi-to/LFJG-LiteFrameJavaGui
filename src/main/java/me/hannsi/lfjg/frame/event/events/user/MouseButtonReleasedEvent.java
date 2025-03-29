package me.hannsi.lfjg.frame.event.events.user;

import me.hannsi.lfjg.frame.event.system.Event;

/**
 * The MouseButtonReleasedEvent class represents an event that occurs when a mouse button is released.
 */
public class MouseButtonReleasedEvent extends Event {
    private final int button;
    private final int mods;
    private final long window;

    /**
     * Constructs a MouseButtonReleasedEvent with the specified button, mods, and window.
     *
     * @param button The mouse button that was released.
     * @param mods   The modifier keys pressed along with the mouse button.
     * @param window The window associated with the event.
     */
    public MouseButtonReleasedEvent(int button, int mods, long window) {
        this.button = button;
        this.mods = mods;
        this.window = window;
    }

    /**
     * Retrieves the mouse button that was released.
     *
     * @return The mouse button that was released.
     */
    public int getButton() {
        return button;
    }

    /**
     * Retrieves the modifier keys pressed along with the mouse button.
     *
     * @return The modifier keys pressed along with the mouse button.
     */
    public int getMods() {
        return mods;
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
