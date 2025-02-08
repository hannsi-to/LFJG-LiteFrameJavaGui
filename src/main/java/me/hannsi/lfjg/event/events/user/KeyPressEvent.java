package me.hannsi.lfjg.event.events.user;

import me.hannsi.lfjg.event.system.Event;

/**
 * The KeyPressEvent class represents an event that occurs when a key is pressed.
 */
public class KeyPressEvent extends Event {
    private final int key;
    private final int scancode;
    private final int mods;
    private final long window;

    /**
     * Constructs a KeyPressEvent with the specified key, scancode, mods, and window.
     *
     * @param key      The key code of the pressed key.
     * @param scancode The scancode of the pressed key.
     * @param mods     The modifier keys pressed along with the key.
     * @param window   The window associated with the event.
     */
    public KeyPressEvent(int key, int scancode, int mods, long window) {
        this.key = key;
        this.scancode = scancode;
        this.mods = mods;
        this.window = window;
    }

    /**
     * Retrieves the key code of the pressed key.
     *
     * @return The key code of the pressed key.
     */
    public int getKey() {
        return key;
    }

    /**
     * Retrieves the scancode of the pressed key.
     *
     * @return The scancode of the pressed key.
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
