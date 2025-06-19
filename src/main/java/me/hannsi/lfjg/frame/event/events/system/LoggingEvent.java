package me.hannsi.lfjg.frame.event.events.system;

import lombok.Getter;
import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.frame.event.system.Event;

/**
 * The LoggingEvent class represents an event that occurs when a debug log is generated.
 */
@Getter
public class LoggingEvent extends Event {
    /**
     * -- GETTER --
     *  Retrieves the DebugLog associated with this event.
     *
     * @return The DebugLog.
     */
    private final DebugLog debugLog;

    /**
     * Constructs a LoggingEvent with the specified DebugLog.
     *
     * @param debugLog The DebugLog associated with this event.
     */
    public LoggingEvent(DebugLog debugLog) {
        this.debugLog = debugLog;
    }

}
