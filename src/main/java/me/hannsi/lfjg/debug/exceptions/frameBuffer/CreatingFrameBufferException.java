package me.hannsi.lfjg.debug.exceptions.frameBuffer;

public class CreatingFrameBufferException extends RuntimeException {
    public CreatingFrameBufferException(String message) {
        super(message);
        System.err.println(message);
        System.exit(1);
    }
}
