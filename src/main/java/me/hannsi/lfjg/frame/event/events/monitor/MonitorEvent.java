package me.hannsi.lfjg.frame.event.events.monitor;

import me.hannsi.lfjg.frame.event.system.Event;

public class MonitorEvent extends Event {
    private final long monitor;
    private final int event;

    public MonitorEvent(long monitor, int event) {
        this.monitor = monitor;
        this.event = event;
    }

    public long getMonitor() {
        return monitor;
    }

    public int getEvent() {
        return event;
    }
}
