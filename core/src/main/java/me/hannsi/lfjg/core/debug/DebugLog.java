package me.hannsi.lfjg.core.debug;

import me.hannsi.lfjg.core.utils.toolkit.ANSIFormat;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DebugLog {
    private static final Logger LOGGER = LogManager.getLogger();
    public static boolean fabricDebug = false;

    static {
        ((org.apache.logging.log4j.core.Logger) LOGGER).setLevel(Level.DEBUG);
    }

    private Class<?> clazz;
    private DebugType debugType;
    private Exception exception;
    private Error error;
    private String debugText;
    private DebugLevel debugLevel;

    public DebugLog(Class<?> clazz, DebugType debugType, Exception exception, DebugLevel debugLevel) {
        this.clazz = clazz;
        this.debugType = debugType;
        this.exception = exception;
        this.debugText = null;
        this.error = null;
        this.debugLevel = debugLevel;

        logging();
    }

    public DebugLog(Class<?> clazz, DebugType debugType, Error error, DebugLevel debugLevel) {
        this.clazz = clazz;
        this.debugType = debugType;
        this.error = error;
        this.exception = null;
        this.debugText = null;
        this.debugLevel = debugLevel;

        logging();
    }

    public DebugLog(Class<?> clazz, DebugType debugType, String debugText, DebugLevel debugLevel) {
        this.clazz = clazz;
        this.debugType = debugType;
        this.exception = null;
        this.error = null;
        this.debugText = debugText;
        this.debugLevel = debugLevel;

        logging();
    }

    public static void debug(Class<?> clazz, String text) {
        new DebugLog(clazz, DebugType.TEXT, text, DebugLevel.DEBUG);
    }

    public static void debug(Class<?> clazz, Exception exception) {
        new DebugLog(clazz, DebugType.EXCEPTION, exception, DebugLevel.DEBUG);
    }

    public static void debug(Class<?> clazz, Error error) {
        new DebugLog(clazz, DebugType.ERROR, error, DebugLevel.DEBUG);
    }

    public static void info(Class<?> clazz, String text) {
        new DebugLog(clazz, DebugType.TEXT, text, DebugLevel.INFO);
    }

    public static void info(Class<?> clazz, Exception exception) {
        new DebugLog(clazz, DebugType.EXCEPTION, exception, DebugLevel.INFO);
    }

    public static void info(Class<?> clazz, Error error) {
        new DebugLog(clazz, DebugType.ERROR, error, DebugLevel.INFO);
    }

    public static void error(Class<?> clazz, String text) {
        new DebugLog(clazz, DebugType.TEXT, text, DebugLevel.ERROR);
    }

    public static void error(Class<?> clazz, Exception exception) {
        new DebugLog(clazz, DebugType.EXCEPTION, exception, DebugLevel.ERROR);
    }

    public static void error(Class<?> clazz, Error error) {
        new DebugLog(clazz, DebugType.ERROR, error, DebugLevel.ERROR);
    }

    public static void warning(Class<?> clazz, String text) {
        new DebugLog(clazz, DebugType.TEXT, text, DebugLevel.WARNING);
    }

    public static void warning(Class<?> clazz, Exception exception) {
        new DebugLog(clazz, DebugType.EXCEPTION, exception, DebugLevel.WARNING);
    }

    public static void warning(Class<?> clazz, Error error) {
        new DebugLog(clazz, DebugType.ERROR, error, DebugLevel.WARNING);
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    private String getDescription() {
        String description = "[" + clazz.getSimpleName() + "] ";

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        switch (debugType) {
            case EXCEPTION:
                exception.printStackTrace(pw);
                description += "\n" + sw;
                break;
            case ERROR:
                error.printStackTrace(pw);
                description += "\n" + sw;
                break;
            case TEXT:
                description += debugText;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + debugType);
        }
        return description;
    }

    public void logging() {
        String level = debugLevel.getDisplay();
        String description = getDescription();

        switch (debugLevel) {
            case DEBUG:
                System.out.print(ANSIFormat.RESET);
                if (fabricDebug) {
                    LOGGER.info("{}{}{}", ANSIFormat.RESET, description, ANSIFormat.RESET);
                } else {
                    LOGGER.debug("{}{}{}", ANSIFormat.RESET, description, ANSIFormat.RESET);
                }
                break;
            case INFO:
                System.out.print(ANSIFormat.BLUE);
                LOGGER.info("{}{}{}", ANSIFormat.BLUE, description, ANSIFormat.RESET);
                break;
            case ERROR:
                System.out.print(ANSIFormat.RED);
                LOGGER.error("{}{}{}", ANSIFormat.RED, description, ANSIFormat.RESET);
                break;
            case WARNING:
                System.out.print(ANSIFormat.YELLOW);
                LOGGER.warn("{}{}{}", ANSIFormat.YELLOW, description, ANSIFormat.RESET);
                break;
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public DebugType getDebugType() {
        return debugType;
    }

    public void setDebugType(DebugType debugType) {
        this.debugType = debugType;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getDebugText() {
        return debugText;
    }

    public void setDebugText(String debugText) {
        this.debugText = debugText;
    }

    public DebugLevel getDebugLevel() {
        return debugLevel;
    }

    public void setDebugLevel(DebugLevel debugLevel) {
        this.debugLevel = debugLevel;
    }
}
