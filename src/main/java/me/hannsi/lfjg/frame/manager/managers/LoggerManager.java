package me.hannsi.lfjg.frame.manager.managers;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.debug.DebugType;
import me.hannsi.lfjg.event.events.system.LoggingEvent;
import me.hannsi.lfjg.event.system.EventHandler;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.IFrame;
import me.hannsi.lfjg.utils.math.ANSIFormat;
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
                System.out.print(ANSIFormat.RESET);
                logger.debug(ANSIFormat.RESET + description + ANSIFormat.RESET);
                break;
            case INFO:
                System.out.print(ANSIFormat.BLUE);
                logger.info(ANSIFormat.BLUE + description + ANSIFormat.RESET);
                break;
            case ERROR:
                System.out.print(ANSIFormat.RED);
                logger.error(ANSIFormat.RED + description + ANSIFormat.RESET);
                break;
            case WARNING:
                System.out.print(ANSIFormat.YELLOW);
                logger.warn(ANSIFormat.YELLOW + description + ANSIFormat.RESET);
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
