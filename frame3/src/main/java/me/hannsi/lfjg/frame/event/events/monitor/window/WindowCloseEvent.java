package me.hannsi.lfjg.frame.event.events.monitor.window;

import me.hannsi.lfjg.core.event.Event;

public class WindowCloseEvent extends Event {
    private final long window;

    public WindowCloseEvent(long window) {
        this.window = window;
    }

    public long getWindow() {
        return window;
    }
}
