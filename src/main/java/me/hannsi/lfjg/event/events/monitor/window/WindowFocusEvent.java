package me.hannsi.lfjg.event.events.monitor.window;

import me.hannsi.lfjg.event.system.Event;

public class WindowFocusEvent extends Event {
    private final long window;
    private final boolean focused;

    public WindowFocusEvent(long window, boolean focused) {
        this.window = window;
        this.focused = focused;
    }

    public long getWindow() {
        return window;
    }

    public boolean isFocused() {
        return focused;
    }
}
