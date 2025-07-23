package me.hannsi.lfjg.frame.event.events.monitor.window;

import lombok.Getter;
import me.hannsi.lfjg.core.event.Event;

@Getter
public class DropEvent extends Event {
    private final long window;
    private final int count;
    private final long name;

    public DropEvent(long window, int count, long name) {
        this.window = window;
        this.count = count;
        this.name = name;
    }

}
