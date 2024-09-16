package me.hannsi.lfjg.frame.manager.managers;

import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.debug.DebugType;
import me.hannsi.lfjg.frame.Frame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerManager {
    private Logger logger;

    public LoggerManager(Frame frame) {
        this.logger = LogManager.getLogger(frame);
    }

    public void logEvent(DebugLog debugLog) {
        DebugLevel debugLevel = debugLog.getDebugLevel();
        String level = debugLevel.getDisplay();
        String description = "";
        DebugType debugType = debugLog.getDebugType();
        Exception exception = debugLog.getException();
        String debugText = debugLog.getDebugText();

        if (debugType == DebugType.EXCEPTION) {
            description = exception.getClass().getName();
        } else if (debugType == DebugType.TEXT) {
            description = debugText;
        }

        switch (debugLevel) {
            case DEBUG:
                logger.debug(description);
                break;
            case INFO:
                logger.info(description);
                break;
            case ERROR:
                logger.error(description);
                break;
            case WARNING:
                logger.warn(description);
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
