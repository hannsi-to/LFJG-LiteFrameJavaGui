package me.hannsi.lfjg.render.openGL.exceptions.shader;

/**
 * Exception thrown when there is an issue creating the shader program.
 */
public class CreatingShaderProgramException extends RuntimeException {

    /**
     * Constructs a new CreatingShaderProgramException with the specified detail message.
     *
     * @param message the detail message
     */
    public CreatingShaderProgramException(String message) {
        super(message);
    }
}