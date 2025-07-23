package me.hannsi.lfjg.frame.event.events.monitor.window;

import me.hannsi.lfjg.core.event.Event;

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
