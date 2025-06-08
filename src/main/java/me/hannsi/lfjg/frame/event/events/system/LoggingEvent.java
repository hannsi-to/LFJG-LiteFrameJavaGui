package me.hannsi.lfjg.frame.event.events.system;

import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.frame.event.system.Event;

/**
 * The LoggingEvent class represents an event that occurs when a debug log is generated.
 */
public class LoggingEvent extends Event {
    private final DebugLog debugLog;

    /**
     * Constructs a LoggingEvent with the specified DebugLog.
     *
     * @param debugLog The DebugLog associated with this event.
     */
    public LoggingEvent(DebugLog debugLog) {
        this.debugLog = debugLog;
    }

    /**
     * Retrieves the DebugLog associated with this event.
     *
     * @return The DebugLog.
     */
    public DebugLog getDebugLog() {
        return debugLog;
    }
}
