package me.hannsi.lfjg.frame.manager.managers;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.debug.DebugType;
import me.hannsi.lfjg.event.events.system.LoggingEvent;
import me.hannsi.lfjg.event.system.EventHandler;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.IFrame;
import me.hannsi.lfjg.utils.math.ANSIColors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerManager {
    private Logger logger;

    public LoggerManager() {
        IFrame.eventManager.register(this);
        this.logger = LogManager.getLogger(Frame.class);
    }

    @EventHandler
    public void loggingEvent(LoggingEvent loggingEvent) {
        DebugLog debugLog = loggingEvent.getDebugLog();
        DebugLevel debugLevel = debugLog.getDebugLevel();
        String level = debugLevel.getDisplay();
        String description = "";
        DebugType debugType = debugLog.getDebugType();
        Exception exception = debugLog.getException();
        String debugText = debugLog.getDebugText();

        if (debugType == DebugType.EXCEPTION) {
            description = exception.getMessage();
        } else if (debugType == DebugType.TEXT) {
            description = debugText;
        }

        switch (debugLevel) {
            case DEBUG:
                System.out.print(ANSIColors.RESET);
                logger.debug(ANSIColors.RESET + description + ANSIColors.RESET);
                break;
            case INFO:
                System.out.print(ANSIColors.BLUE);
                logger.info(ANSIColors.BLUE + description + ANSIColors.RESET);
                break;
            case ERROR:
                System.out.print(ANSIColors.RED);
                logger.error(ANSIColors.RED + description + ANSIColors.RESET);
                break;
            case WARNING:
                System.out.print(ANSIColors.YELLOW);
                logger.warn(ANSIColors.YELLOW + description + ANSIColors.RESET);
                break;
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
