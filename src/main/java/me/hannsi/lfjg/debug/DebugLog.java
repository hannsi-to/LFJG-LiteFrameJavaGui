package me.hannsi.lfjg.debug;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.frame.event.events.system.LoggingEvent;
import me.hannsi.lfjg.frame.frame.IFrame;

/**
 * The DebugLog class is responsible for logging debug information, including exceptions and text messages, with different debug levels.
 */
@Setter
@Getter
public class DebugLog {
    /**
     * -- GETTER --
     * Retrieves the class where the debug log is generated.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the class where the debug log is generated.
     *
     * @return The class where the debug log is generated.
     * @param clazz The class to set.
     */
    private Class<?> clazz;
    /**
     * -- GETTER --
     * Retrieves the type of debug log (EXCEPTION or TEXT).
     * <p>
     * <p>
     * -- SETTER --
     * Sets the type of debug log (EXCEPTION or TEXT).
     *
     * @return The type of debug log.
     * @param debugType The type of debug log to set.
     */
    private DebugType debugType;
    /**
     * -- GETTER --
     * Retrieves the exception to log (if applicable).
     * <p>
     * <p>
     * -- SETTER --
     * Sets the exception to log.
     *
     * @return The exception to log.
     * @param exception The exception to set.
     */
    private Exception exception;
    /**
     * -- GETTER --
     * Retrieves the text message to log.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the text message to log.
     *
     * @return The text message to log.
     * @param debugText The text message to set.
     */
    private String debugText;
    /**
     * -- GETTER --
     * Retrieves the debug level (DEBUG, INFO, WARNING, ERROR).
     * <p>
     * <p>
     * -- SETTER --
     * Sets the debug level (DEBUG, INFO, WARNING, ERROR).
     *
     * @return The debug level.
     * @param debugLevel The debug level to set.
     */
    private DebugLevel debugLevel;

    /**
     * Constructs a DebugLog object with the specified class, debug type, exception, and debug level.
     *
     * @param clazz      The class where the debug log is generated.
     * @param debugType  The type of debug log (EXCEPTION or TEXT).
     * @param exception  The exception to log (if applicable).
     * @param debugLevel The debug level (DEBUG, INFO, WARNING, ERROR).
     */
    public DebugLog(Class<?> clazz, DebugType debugType, Exception exception, DebugLevel debugLevel) {
        this.clazz = clazz;
        this.debugType = debugType;
        this.exception = exception;
        this.debugText = null;
        this.debugLevel = debugLevel;

        IFrame.eventManager.call(new LoggingEvent(this));
    }

    /**
     * Constructs a DebugLog object with the specified class, debug type, text message, and debug level.
     *
     * @param clazz      The class where the debug log is generated.
     * @param debugType  The type of debug log (EXCEPTION or TEXT).
     * @param debugText  The text message to log.
     * @param debugLevel The debug level (DEBUG, INFO, WARNING, ERROR).
     */
    public DebugLog(Class<?> clazz, DebugType debugType, String debugText, DebugLevel debugLevel) {
        this.clazz = clazz;
        this.debugType = debugType;
        this.exception = null;
        this.debugText = debugText;
        this.debugLevel = debugLevel;

        IFrame.eventManager.call(new LoggingEvent(this));
    }

    /**
     * Logs a debug message with the specified class and text.
     *
     * @param clazz The class where the debug log is generated.
     * @param text  The text message to log.
     */
    public static void debug(Class<?> clazz, String text) {
        new DebugLog(clazz, DebugType.TEXT, text, DebugLevel.DEBUG);
    }

    /**
     * Logs a debug message with the specified class and exception.
     *
     * @param clazz     The class where the debug log is generated.
     * @param exception The exception to log.
     */
    public static void debug(Class<?> clazz, Exception exception) {
        new DebugLog(clazz, DebugType.EXCEPTION, exception, DebugLevel.DEBUG);
    }

    /**
     * Logs an info message with the specified class and text.
     *
     * @param clazz The class where the info log is generated.
     * @param text  The text message to log.
     */
    public static void info(Class<?> clazz, String text) {
        new DebugLog(clazz, DebugType.TEXT, text, DebugLevel.INFO);
    }

    /**
     * Logs an info message with the specified class and exception.
     *
     * @param clazz     The class where the info log is generated.
     * @param exception The exception to log.
     */
    public static void info(Class<?> clazz, Exception exception) {
        new DebugLog(clazz, DebugType.EXCEPTION, exception, DebugLevel.INFO);
    }

    /**
     * Logs an error message with the specified class and text.
     *
     * @param clazz The class where the error log is generated.
     * @param text  The text message to log.
     */
    public static void error(Class<?> clazz, String text) {
        new DebugLog(clazz, DebugType.TEXT, text, DebugLevel.ERROR);
    }

    /**
     * Logs an error message with the specified class and exception.
     *
     * @param clazz     The class where the error log is generated.
     * @param exception The exception to log.
     */
    public static void error(Class<?> clazz, Exception exception) {
        new DebugLog(clazz, DebugType.EXCEPTION, exception, DebugLevel.ERROR);
    }

    /**
     * Logs a warning message with the specified class and text.
     *
     * @param clazz The class where the warning log is generated.
     * @param text  The text message to log.
     */
    public static void warning(Class<?> clazz, String text) {
        new DebugLog(clazz, DebugType.TEXT, text, DebugLevel.WARNING);
    }

    /**
     * Logs a warning message with the specified class and exception.
     *
     * @param clazz     The class where the warning log is generated.
     * @param exception The exception to log.
     */
    public static void warning(Class<?> clazz, Exception exception) {
        new DebugLog(clazz, DebugType.EXCEPTION, exception, DebugLevel.WARNING);
    }
}
