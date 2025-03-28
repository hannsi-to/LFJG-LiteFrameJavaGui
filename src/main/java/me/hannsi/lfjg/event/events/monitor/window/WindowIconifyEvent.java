package me.hannsi.lfjg.event.events.monitor.window;

import me.hannsi.lfjg.event.system.Event;

public class WindowIconifyEvent extends Event {
    private final long window;
    private final boolean iconified;

    public WindowIconifyEvent(long window, boolean iconified) {
        this.window = window;
        this.iconified = iconified;
    }

    public long getWindow() {
        return window;
    }

    public boolean isIconified() {
        return iconified;
    }
}
