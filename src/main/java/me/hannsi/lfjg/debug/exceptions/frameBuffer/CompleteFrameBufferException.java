package me.hannsi.lfjg.debug.exceptions.frameBuffer;

public class CompleteFrameBufferException extends RuntimeException {
    public CompleteFrameBufferException(String message) {
        super(message);
        System.err.println(message);
        System.exit(1);
    }
}
