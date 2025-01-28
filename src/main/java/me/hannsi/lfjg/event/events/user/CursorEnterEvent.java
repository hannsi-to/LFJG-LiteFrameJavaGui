package me.hannsi.lfjg.event.events.user;

import me.hannsi.lfjg.event.system.Event;

public class CursorEnterEvent extends Event {
    private final long window;
    private final boolean entered;

    public CursorEnterEvent(long window, boolean entered) {
        this.window = window;
        this.entered = entered;
    }

    public long getWindow() {
        return window;
    }

    public boolean isEntered() {
        return entered;
    }
}
