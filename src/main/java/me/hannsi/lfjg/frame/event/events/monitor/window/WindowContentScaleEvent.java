package me.hannsi.lfjg.frame.event.events.monitor.window;

import lombok.Getter;
import me.hannsi.lfjg.frame.event.system.Event;

@Getter
public class WindowContentScaleEvent extends Event {
    private final long window;
    private final float xScale;
    private final float yScale;

    public WindowContentScaleEvent(long window, float xScale, float yScale) {
        this.window = window;
        this.xScale = xScale;
        this.yScale = yScale;
    }

}
