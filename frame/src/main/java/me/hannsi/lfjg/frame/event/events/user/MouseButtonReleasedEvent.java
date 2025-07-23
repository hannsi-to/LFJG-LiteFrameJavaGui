package me.hannsi.lfjg.frame.event.events.user;

import lombok.Getter;
import me.hannsi.lfjg.core.event.Event;

/**
 * The MouseButtonReleasedEvent class represents an event that occurs when a mouse button is released.
 */
@Getter
public class MouseButtonReleasedEvent extends Event {
    /**
     * -- GETTER --
     * Retrieves the mouse button that was released.
     *
     * @return The mouse button that was released.
     */
    private final int button;
    /**
     * -- GETTER --
     * Retrieves the modifier keys pressed along with the mouse button.
     *
     * @return The modifier keys pressed along with the mouse button.
     */
    private final int mods;
    /**
     * -- GETTER --
     * Retrieves the window associated with this event.
     *
     * @return The window associated with this event.
     */
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

}
