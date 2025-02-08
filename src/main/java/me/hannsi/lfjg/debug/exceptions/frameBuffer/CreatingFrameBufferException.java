package me.hannsi.lfjg.debug.exceptions.frameBuffer;

/**
 * Exception thrown when there is an issue creating the frame buffer.
 */
public class CreatingFrameBufferException extends RuntimeException {

    /**
     * Constructs a new CreatingFrameBufferException with the specified detail message.
     *
     * @param message the detail message
     */
    public CreatingFrameBufferException(String message) {
        super(message);
        System.err.println(message);
        System.exit(1);
    }
}