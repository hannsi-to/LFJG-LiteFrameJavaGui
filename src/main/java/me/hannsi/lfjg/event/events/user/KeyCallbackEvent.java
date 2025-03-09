package me.hannsi.lfjg.event.events.user;

import me.hannsi.lfjg.event.system.Event;

public class KeyCallbackEvent extends Event {
    private final long window;
    private final int key;
    private final int scancode;
    private final int action;
    private final int mods;

    public KeyCallbackEvent(long window, int key, int scancode, int action, int mods) {
        this.window = window;
        this.key = key;
        this.scancode = scancode;
        this.action = action;
        this.mods = mods;
    }

    public long getWindow() {
        return window;
    }

    public int getKey() {
        return key;
    }

    public int getScancode() {
        return scancode;
    }

    public int getAction() {
        return action;
    }

    public int getMods() {
        return mods;
    }
}
