package me.hannsi.lfjg.frame.event.events.monitor.window;

import me.hannsi.lfjg.frame.event.system.Event;

public class WindowPosEvent extends Event {
    private final long window;
    private final int xpos;
    private final int ypos;

    public WindowPosEvent(long window, int xpos, int ypos) {
        this.window = window;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public long getWindow() {
        return window;
    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }
}
