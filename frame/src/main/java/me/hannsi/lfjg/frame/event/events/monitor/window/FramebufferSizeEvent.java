package me.hannsi.lfjg.frame.event.events.monitor.window;

import lombok.Getter;
import me.hannsi.lfjg.core.event.Event;

@Getter
public class FramebufferSizeEvent extends Event {
    private final long window;
    private final int width;
    private final int height;

    public FramebufferSizeEvent(long window, int width, int height) {
        this.window = window;
        this.width = width;
        this.height = height;
    }

}
