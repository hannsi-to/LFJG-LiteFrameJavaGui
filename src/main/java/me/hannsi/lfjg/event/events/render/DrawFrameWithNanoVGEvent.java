package me.hannsi.lfjg.event.events.render;

import me.hannsi.lfjg.event.system.Event;

public class DrawFrameWithNanoVGEvent extends Event {
    private final long nvg;

    public DrawFrameWithNanoVGEvent(long nvg) {
        this.nvg = nvg;
    }

    public long getNvg() {
        return nvg;
    }
}
