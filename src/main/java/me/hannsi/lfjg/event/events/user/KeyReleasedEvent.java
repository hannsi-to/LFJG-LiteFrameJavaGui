package me.hannsi.lfjg.event.events.user;

import me.hannsi.lfjg.event.system.Event;

/**
 * The KeyReleasedEvent class represents an event that occurs when a key is released.
 */
public class KeyReleasedEvent extends Event {
    private final int key;
    private final int scancode;
    private final int mods;
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

    /**
     * Retrieves the key code of the released key.
     *
     * @return The key code of the released key.
     */
    public int getKey() {
        return key;
    }

    /**
     * Retrieves the scancode of the released key.
     *
     * @return The scancode of the released key.
     */
    public int getScancode() {
        return scancode;
    }

    /**
     * Retrieves the modifier keys pressed along with the key.
     *
     * @return The modifier keys pressed along with the key.
     */
    public int getMods() {
        return mods;
    }

    /**
     * Retrieves the window associated with the event.
     *
     * @return The window associated with the event.
     */
    public long getWindow() {
        return window;
    }
}
