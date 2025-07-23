package me.hannsi.lfjg.frame.event.events.monitor.window;

import lombok.Getter;
import me.hannsi.lfjg.core.event.Event;

@Getter
public class WindowMaximizeEvent extends Event {
    private final long window;
    private final boolean maximized;

    public WindowMaximizeEvent(long window, boolean maximized) {
        this.window = window;
        this.maximized = maximized;
    }

}
