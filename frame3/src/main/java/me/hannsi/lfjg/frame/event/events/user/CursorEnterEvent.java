package me.hannsi.lfjg.frame.event.events.user;

import me.hannsi.lfjg.core.event.Event;

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
