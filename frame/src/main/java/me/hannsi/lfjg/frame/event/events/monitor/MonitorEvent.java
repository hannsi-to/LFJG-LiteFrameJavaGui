package me.hannsi.lfjg.frame.event.events.monitor;

import lombok.Getter;
import me.hannsi.lfjg.core.event.Event;

@Getter
public class MonitorEvent extends Event {
    private final long monitor;
    private final int event;

    public MonitorEvent(long monitor, int event) {
        this.monitor = monitor;
        this.event = event;
    }

}
