package me.hannsi.lfjg.debug.debug;

import me.hannsi.lfjg.event.events.system.LoggingEvent;
import me.hannsi.lfjg.frame.IFrame;

public class DebugLog {
    private Class<?> clazz;
    private DebugType debugType;
    private Exception exception;
    private String debugText;
    private DebugLevel debugLevel;

    public DebugLog(Class<?> clazz, DebugType debugType, Exception exception, DebugLevel debugLevel) {
        this.clazz = clazz;
        this.debugType = debugType;
        this.exception = exception;
        this.debugText = null;
        this.debugLevel = debugLevel;

        IFrame.eventManager.call(new LoggingEvent(this));
    }

    public DebugLog(Class<?> clazz, DebugType debugType, String debugText, DebugLevel debugLevel) {
        this.clazz = clazz;
        this.debugType = debugType;
        this.exception = null;
        this.debugText = debugText;
        this.debugLevel = debugLevel;

        IFrame.eventManager.call(new LoggingEvent(this));
    }

    public static void debug(Class<?> clazz, String text) {
        new DebugLog(clazz, DebugType.TEXT, text, DebugLevel.DEBUG);
    }

    public static void debug(Class<?> clazz, Exception exception) {
        new DebugLog(clazz, DebugType.EXCEPTION, exception, DebugLevel.DEBUG);
    }

    public static void info(Class<?> clazz, String text) {
        new DebugLog(clazz, DebugType.TEXT, text, DebugLevel.INFO);
    }

    public static void info(Class<?> clazz, Exception exception) {
        new DebugLog(clazz, DebugType.EXCEPTION, exception, DebugLevel.INFO);
    }

    public static void error(Class<?> clazz, String text) {
        new DebugLog(clazz, DebugType.TEXT, text, DebugLevel.ERROR);
    }

    public static void error(Class<?> clazz, Exception exception) {
        new DebugLog(clazz, DebugType.EXCEPTION, exception, DebugLevel.ERROR);
    }

    public static void warning(Class<?> clazz, String text) {
        new DebugLog(clazz, DebugType.TEXT, text, DebugLevel.WARNING);
    }

    public static void warning(Class<?> clazz, Exception exception) {
        new DebugLog(clazz, DebugType.EXCEPTION, exception, DebugLevel.WARNING);
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
