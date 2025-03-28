package me.hannsi.lfjg.event.events.monitor.window;

import me.hannsi.lfjg.event.system.Event;

public class FramebufferSizeEvent extends Event {
    private final long window;
    private final int width;
    private final int height;

    public FramebufferSizeEvent(long window, int width, int height) {
        this.window = window;
        this.width = width;
        this.height = height;
    }

    public long getWindow() {
        return window;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
