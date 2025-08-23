package me.hannsi.lfjg.frame.event.events.user;

import me.hannsi.lfjg.core.event.Event;

public class KeyPressEvent extends Event {
    private final int key;
    private final int scancode;
    private final int mods;
    private final long window;

    public KeyPressEvent(int key, int scancode, int mods, long window) {
        this.key = key;
        this.scancode = scancode;
        this.mods = mods;
        this.window = window;
    }

    public int getKey() {
        return key;
    }

    public int getScancode() {
        return scancode;
    }

    public int getMods() {
        return mods;
    }

    public long getWindow() {
        return window;
    }
}
