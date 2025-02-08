package me.hannsi.lfjg.event.events.user;

import me.hannsi.lfjg.event.system.Event;

/**
 * The MouseButtonCallbackEvent class represents an event that occurs when a mouse button is pressed or released.
 */
public class MouseButtonCallbackEvent extends Event {
    private final long window;
    private final int button;
    private final int action;
    private final int mods;

    /**
     * Constructs a MouseButtonCallbackEvent with the specified window, button, action, and mods.
     *
     * @param window The window associated with the event.
     * @param button The mouse button that was pressed or released.
     * @param action The action performed (press or release).
     * @param mods   The modifier keys pressed along with the mouse button.
     */
    public MouseButtonCallbackEvent(long window, int button, int action, int mods) {
        this.window = window;
        this.button = button;
        this.action = action;
        this.mods = mods;
    }

    /**
     * Retrieves the window associated with this event.
     *
     * @return The window associated with this event.
     */
    public long getWindow() {
        return window;
    }

    /**
     * Retrieves the mouse button that was pressed or released.
     *
     * @return The mouse button that was pressed or released.
     */
    public int getButton() {
        return button;
    }

    /**
     * Retrieves the action performed (press or release).
     *
     * @return The action performed (press or release).
     */
    public int getAction() {
        return action;
    }

    /**
     * Retrieves the modifier keys pressed along with the mouse button.
     *
     * @return The modifier keys pressed along with the mouse button.
     */
    public int getMods() {
        return mods;
    }
}
