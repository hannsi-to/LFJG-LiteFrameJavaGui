package me.hannsi.lfjg.debug;

import me.hannsi.lfjg.frame.Frame;

public class DebugLog {
    private Frame frame;
    private DebugType debugType;
    private Exception exception;
    private String debugText;
    private DebugLevel debugLevel;

    public DebugLog(Frame frame, DebugType debugType, Exception exception, DebugLevel debugLevel) {
        this.frame = frame;
        this.debugType = debugType;
        this.exception = exception;
        this.debugText = null;
        this.debugLevel = debugLevel;

        frame.getLoggerManager().logEvent(this);
    }

    public DebugLog(Frame frame, DebugType debugType, String debugText, DebugLevel debugLevel) {
        this.frame = frame;
        this.debugType = debugType;
        this.exception = null;
        this.debugText = debugText;
        this.debugLevel = debugLevel;

        frame.getLoggerManager().logEvent(this);
    }

    public static void debug(Frame frame,String text){
        new DebugLog(frame,DebugType.TEXT,text,DebugLevel.DEBUG);
    }

    public static void debug(Frame frame,Exception exception){
        new DebugLog(frame,DebugType.EXCEPTION,exception,DebugLevel.DEBUG);
    }

    public static void info(Frame frame,String text){
        new DebugLog(frame,DebugType.TEXT,text,DebugLevel.INFO);
    }

    public static void info(Frame frame,Exception exception){
        new DebugLog(frame,DebugType.EXCEPTION,exception,DebugLevel.INFO);
    }

    public static void error(Frame frame,String text){
        new DebugLog(frame,DebugType.TEXT,text,DebugLevel.ERROR);
    }

    public static void error(Frame frame,Exception exception){
        new DebugLog(frame,DebugType.EXCEPTION,exception,DebugLevel.ERROR);
    }

    public static void warning(Frame frame,String text){
        new DebugLog(frame,DebugType.TEXT,text,DebugLevel.WARNING);
    }

    public static void warning(Frame frame,Exception exception){
        new DebugLog(frame,DebugType.EXCEPTION,exception,DebugLevel.WARNING);
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
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
