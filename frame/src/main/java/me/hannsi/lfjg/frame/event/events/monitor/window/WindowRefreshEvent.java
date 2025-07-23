package me.hannsi.lfjg.frame.event.events.monitor.window;

import lombok.Getter;
import me.hannsi.lfjg.core.event.Event;

@Getter
public class WindowRefreshEvent extends Event {
    private final long window;

    public WindowRefreshEvent(long window) {
        this.window = window;
    }

}
