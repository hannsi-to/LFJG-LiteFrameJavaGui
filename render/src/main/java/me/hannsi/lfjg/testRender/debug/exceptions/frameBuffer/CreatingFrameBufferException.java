package me.hannsi.lfjg.testRender.debug.exceptions.frameBuffer;

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
    }
}