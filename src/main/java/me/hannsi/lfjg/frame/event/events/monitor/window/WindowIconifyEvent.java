package me.hannsi.lfjg.frame.event.events.monitor.window;

import lombok.Getter;
import me.hannsi.lfjg.frame.event.system.Event;

@Getter
public class WindowIconifyEvent extends Event {
    private final long window;
    private final boolean iconified;

    public WindowIconifyEvent(long window, boolean iconified) {
        this.window = window;
        this.iconified = iconified;
    }

}
