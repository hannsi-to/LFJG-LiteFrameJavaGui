package me.hannsi.lfjg.frame.event.events.monitor.window;

import lombok.Getter;
import me.hannsi.lfjg.frame.event.system.Event;

@Getter
public class WindowSizeEvent extends Event {
    private final long window;
    private final int width;
    private final int height;

    public WindowSizeEvent(long window, int width, int height) {
        this.window = window;
        this.width = width;
        this.height = height;
    }

}
