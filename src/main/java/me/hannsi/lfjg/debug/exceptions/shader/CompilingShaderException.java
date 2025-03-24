package me.hannsi.lfjg.debug.exceptions.shader;

/**
 * Exception thrown when there is an issue compiling the shader.
 */
public class CompilingShaderException extends RuntimeException {

    /**
     * Constructs a new CompilingShaderException with the specified detail message.
     *
     * @param message the detail message
     */
    public CompilingShaderException(String message) {
        super(message);
    }
}