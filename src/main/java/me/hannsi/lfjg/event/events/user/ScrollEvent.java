package me.hannsi.lfjg.event.events.user;

import me.hannsi.lfjg.event.system.Event;

public class ScrollEvent extends Event {
    private final long window;
    private final double xoffset;
    private final double yoffset;

    public ScrollEvent(long window, double xoffset, double yoffset) {
        this.window = window;
        this.xoffset = xoffset;
        this.yoffset = yoffset;
    }

    public long getWindow() {
        return window;
    }

    public double getXoffset() {
        return xoffset;
    }

    public double getYoffset() {
        return yoffset;
    }
}
