package me.hannsi.lfjg.frame.event.events.user;

import lombok.Getter;
import me.hannsi.lfjg.core.event.Event;

/**
 * The MouseButtonPressEvent class represents an event that occurs when a mouse button is pressed.
 */
@Getter
public class MouseButtonPressEvent extends Event {
    /**
     * -- GETTER --
     * Retrieves the mouse button that was pressed.
     *
     * @return The mouse button that was pressed.
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
     * Constructs a MouseButtonPressEvent with the specified button, mods, and window.
     *
     * @param button The mouse button that was pressed.
     * @param mods   The modifier keys pressed along with the mouse button.
     * @param window The window associated with the event.
     */
    public MouseButtonPressEvent(int button, int mods, long window) {
        this.button = button;
        this.mods = mods;
        this.window = window;
    }

}
