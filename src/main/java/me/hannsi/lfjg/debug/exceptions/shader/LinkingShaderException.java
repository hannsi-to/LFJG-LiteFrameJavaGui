package me.hannsi.lfjg.debug.exceptions.shader;

/**
 * Exception thrown when there is an issue linking the shader.
 */
public class LinkingShaderException extends RuntimeException {

    /**
     * Constructs a new LinkingShaderException with the specified detail message.
     *
     * @param message the detail message
     */
    public LinkingShaderException(String message) {
        super(message);
        System.err.println(message);
        System.exit(1);
    }
}