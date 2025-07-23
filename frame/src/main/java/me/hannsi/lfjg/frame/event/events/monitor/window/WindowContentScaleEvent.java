package me.hannsi.lfjg.frame.event.events.monitor.window;

import me.hannsi.lfjg.core.event.Event;

public class WindowContentScaleEvent extends Event {
    private final long window;
    private final float xScale;
    private final float yScale;

    public WindowContentScaleEvent(long window, float xScale, float yScale) {
        this.window = window;
        this.xScale = xScale;
        this.yScale = yScale;
    }

    public long getWindow() {
        return window;
    }

    public float getxScale() {
        return xScale;
    }

    public float getyScale() {
        return yScale;
    }
}
