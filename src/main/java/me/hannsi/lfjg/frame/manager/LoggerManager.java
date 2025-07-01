package me.hannsi.lfjg.frame.manager;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.debug.DebugType;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.event.events.system.LoggingEvent;
import me.hannsi.lfjg.frame.event.system.EventHandler;
import me.hannsi.lfjg.frame.frame.IFrame;
import me.hannsi.lfjg.utils.math.ANSIFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Manages logging for the application.
 */
@Setter
@Getter
public class LoggerManager {
    /**
     * -- GETTER --
     * Retrieves the logger.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the logger.
     *
     * @return the logger
     * @param logger the logger to set
     */
    private Logger logger;

    /**
     * Constructs a new LoggerManager and registers it with the event manager.
     */
    public LoggerManager() {
        IFrame.eventManager.register(this);
        this.logger = LogManager.getLogger(Frame.class);
    }

    private static String getDescription(DebugLog debugLog) {
        String description = "";
        DebugType debugType = debugLog.getDebugType();
        Exception exception = debugLog.getException();
        String debugText = debugLog.getDebugText();

        if (debugType == DebugType.EXCEPTION) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            description = "\n" + sw;
        } else if (debugType == DebugType.TEXT) {
            description = debugText;
        }
        return description;
    }

    /**
     * Handles logging events.
     *
     * @param loggingEvent the logging event to handle
     */
    @EventHandler
    public void loggingEvent(LoggingEvent loggingEvent) {
        DebugLog debugLog = loggingEvent.getDebugLog();
        DebugLevel debugLevel = debugLog.getDebugLevel();
        String level = debugLevel.getDisplay();
        String description = getDescription(debugLog);

        switch (debugLevel) {
            case DEBUG:
                System.out.print(ANSIFormat.RESET);
                logger.debug("{}{}{}", ANSIFormat.RESET, description, ANSIFormat.RESET);
                break;
            case INFO:
                System.out.print(ANSIFormat.BLUE);
                logger.info("{}{}{}", ANSIFormat.BLUE, description, ANSIFormat.RESET);
                break;
            case ERROR:
                System.out.print(ANSIFormat.RED);
                logger.error("{}{}{}", ANSIFormat.RED, description, ANSIFormat.RESET);
                break;
            case WARNING:
                System.out.print(ANSIFormat.YELLOW);
                logger.warn("{}{}{}", ANSIFormat.YELLOW, description, ANSIFormat.RESET);
                break;
        }
    }

}