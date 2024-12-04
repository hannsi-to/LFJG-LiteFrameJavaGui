package me.hannsi.lfjg.debug.exceptions;

public class CreatingShaderProgramException extends RuntimeException {
    public CreatingShaderProgramException(String message) {
        super(message);
        System.err.println(message);
        System.exit(1);
    }
}
