package me.hannsi.lfjg.debug.exceptions.render;

/**
 * Exception thrown when there is an issue creating the render buffer.
 */
public class CreatingRenderBufferException extends RuntimeException {

    /**
     * Constructs a new CreatingRenderBufferException with the specified detail message.
     *
     * @param message the detail message
     */
    public CreatingRenderBufferException(String message) {
        super(message);
    }
}