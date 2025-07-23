package me.hannsi.lfjg.frame.event.events.user;

import lombok.Getter;
import me.hannsi.lfjg.core.event.Event;

/**
 * The MouseButtonCallbackEvent class represents an event that occurs when a mouse button is pressed or released.
 */
@Getter
public class MouseButtonEvent extends Event {
    /**
     * -- GETTER --
     * Retrieves the window associated with this event.
     *
     * @return The window associated with this event.
     */
    private final long window;
    /**
     * -- GETTER --
     * Retrieves the mouse button that was pressed or released.
     *
     * @return The mouse button that was pressed or released.
     */
    private final int button;
    /**
     * -- GETTER --
     * Retrieves the action performed (press or release).
     *
     * @return The action performed (press or release).
     */
    private final int action;
    /**
     * -- GETTER --
     * Retrieves the modifier keys pressed along with the mouse button.
     *
     * @return The modifier keys pressed along with the mouse button.
     */
    private final int mods;

    /**
     * Constructs a MouseButtonCallbackEvent with the specified window, button, action, and mods.
     *
     * @param window The window associated with the event.
     * @param button The mouse button that was pressed or released.
     * @param action The action performed (press or release).
     * @param mods   The modifier keys pressed along with the mouse button.
     */
    public MouseButtonEvent(long window, int button, int action, int mods) {
        this.window = window;
        this.button = button;
        this.action = action;
        this.mods = mods;
    }

}
