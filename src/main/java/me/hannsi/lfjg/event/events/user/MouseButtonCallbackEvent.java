package me.hannsi.lfjg.event.events.user;

import me.hannsi.lfjg.event.system.Event;

public class MouseButtonCallbackEvent extends Event {
    private final long window;
    private final int button;
    private final int action;
    private final int mods;

    public MouseButtonCallbackEvent(long window, int button, int action, int mods) {
        this.window = window;
        this.button = button;
        this.action = action;
        this.mods = mods;
    }

    public long getWindow() {
        return window;
    }

    public int getButton() {
        return button;
    }

    public int getAction() {
        return action;
    }

    public int getMods() {
        return mods;
    }
}
