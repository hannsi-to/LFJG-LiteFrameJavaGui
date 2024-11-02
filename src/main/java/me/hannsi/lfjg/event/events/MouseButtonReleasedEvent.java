package me.hannsi.lfjg.event.events;

import me.hannsi.lfjg.event.system.Event;

public class MouseButtonReleasedEvent extends Event {
    private final int button;
    private final int mods;
    private final long window;

    public MouseButtonReleasedEvent(int button, int mods, long window) {
        this.button = button;
        this.mods = mods;
        this.window = window;
    }

    public int getButton() {
        return button;
    }

    public int getMods() {
        return mods;
    }

    public long getWindow() {
        return window;
    }
}
