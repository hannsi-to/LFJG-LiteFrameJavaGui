package me.hannsi.lfjg.frame.event.events.user;

import lombok.Getter;
import me.hannsi.lfjg.frame.event.system.Event;

/**
 * The CursorEnterEvent class represents an event that occurs when the cursor enters or leaves a window.
 */
@Getter
public class CursorEnterEvent extends Event {
    /**
     * -- GETTER --
     *  Retrieves the window associated with this event.
     *
     * @return The window associated with this event.
     */
    private final long window;
    /**
     * -- GETTER --
     *  Checks if the cursor entered the window.
     *
     * @return True if the cursor entered the window, false if it left.
     */
    private final boolean entered;

    /**
     * Constructs a CursorEnterEvent with the specified window and entered state.
     *
     * @param window  The window associated with the event.
     * @param entered True if the cursor entered the window, false if it left.
     */
    public CursorEnterEvent(long window, boolean entered) {
        this.window = window;
        this.entered = entered;
    }

}
