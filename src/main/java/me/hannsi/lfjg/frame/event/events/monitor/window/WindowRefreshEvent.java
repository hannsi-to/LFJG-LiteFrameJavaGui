package me.hannsi.lfjg.frame.event.events.monitor.window;

import me.hannsi.lfjg.frame.event.system.Event;

public class WindowRefreshEvent extends Event {
    private final long window;

    public WindowRefreshEvent(long window) {
        this.window = window;
    }

    public long getWindow() {
        return window;
    }
}
