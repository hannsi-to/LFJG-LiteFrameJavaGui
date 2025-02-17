package me.hannsi.lfjg.debug.exceptions.shader;

/**
 * Exception thrown when there is an issue creating the shader.
 */
public class CreatingShaderException extends RuntimeException {

    /**
     * Constructs a new CreatingShaderException with the specified detail message.
     *
     * @param message the detail message
     */
    public CreatingShaderException(String message) {
        super(message);
        System.err.println(message);
        System.exit(1);
    }
}