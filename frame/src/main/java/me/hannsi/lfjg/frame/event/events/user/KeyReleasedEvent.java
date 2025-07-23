package me.hannsi.lfjg.frame.event.events.user;

import lombok.Getter;
import me.hannsi.lfjg.core.event.Event;

/**
 * The KeyReleasedEvent class represents an event that occurs when a key is released.
 */
@Getter
public class KeyReleasedEvent extends Event {
    /**
     * -- GETTER --
     * Retrieves the key code of the released key.
     *
     * @return The key code of the released key.
     */
    private final int key;
    /**
     * -- GETTER --
     * Retrieves the scancode of the released key.
     *
     * @return The scancode of the released key.
     */
    private final int scancode;
    /**
     * -- GETTER --
     * Retrieves the modifier keys pressed along with the key.
     *
     * @return The modifier keys pressed along with the key.
     */
    private final int mods;
    /**
     * -- GETTER --
     * Retrieves the window associated with the event.
     *
     * @return The window associated with the event.
     */
    private final long window;

    /**
     * Constructs a KeyReleasedEvent with the specified key, scancode, mods, and window.
     *
     * @param key      The key code of the released key.
     * @param scancode The scancode of the released key.
     * @param mods     The modifier keys pressed along with the key.
     * @param window   The window associated with the event.
     */
    public KeyReleasedEvent(int key, int scancode, int mods, long window) {
        this.key = key;
        this.scancode = scancode;
        this.mods = mods;
        this.window = window;
    }

}
