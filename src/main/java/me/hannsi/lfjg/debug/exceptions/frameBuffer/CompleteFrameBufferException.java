package me.hannsi.lfjg.debug.exceptions.frameBuffer;

/**
 * Exception thrown when there is an issue with the complete frame buffer.
 */
public class CompleteFrameBufferException extends RuntimeException {

    /**
     * Constructs a new CompleteFrameBufferException with the specified detail message.
     *
     * @param message the detail message
     */
    public CompleteFrameBufferException(String message) {
        super(message);
    }
}