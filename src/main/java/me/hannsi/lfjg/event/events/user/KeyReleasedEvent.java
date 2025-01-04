package me.hannsi.lfjg.event.events.user;

import me.hannsi.lfjg.event.system.Event;

public class KeyReleasedEvent extends Event {
    private final int key;
    private final int scancode;
    private final int mods;
    private final long window;

    public KeyReleasedEvent(int key, int scancode, int mods, long window) {
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
