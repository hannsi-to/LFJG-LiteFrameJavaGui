package me.hannsi.lfjg.event.events;

import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.event.system.Event;

public class LoggingEvent extends Event {
    private DebugLog debugLog;

    public LoggingEvent(DebugLog debugLog) {
        this.debugLog = debugLog;
    }

    public DebugLog getDebugLog() {
        return debugLog;
    }

    public void setDebugLog(DebugLog debugLog) {
        this.debugLog = debugLog;
    }
}
