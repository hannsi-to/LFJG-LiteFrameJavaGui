package me.hannsi.lfjg.debug.exceptions.texture;

/**
 * Exception thrown when there is an issue creating the texture.
 */
public class CreatingTextureException extends RuntimeException {

    /**
     * Constructs a new CreatingTextureException with the specified detail message.
     *
     * @param message the detail message
     */
    public CreatingTextureException(String message) {
        super(message);
        System.err.println(message);
        System.exit(1);
    }
}