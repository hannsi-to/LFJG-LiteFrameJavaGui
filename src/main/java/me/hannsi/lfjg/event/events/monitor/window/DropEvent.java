package me.hannsi.lfjg.event.events.monitor.window;

import me.hannsi.lfjg.event.system.Event;

public class DropEvent extends Event {
    private final long window;
    private final int count;
    private final long name;

    public DropEvent(long window, int count, long name) {
        this.window = window;
        this.count = count;
        this.name = name;
    }

    public long getWindow() {
        return window;
    }

    public int getCount() {
        return count;
    }

    public long getName() {
        return name;
    }
}
