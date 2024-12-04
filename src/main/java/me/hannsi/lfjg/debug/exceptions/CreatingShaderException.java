package me.hannsi.lfjg.debug.exceptions;

public class CreatingShaderException extends RuntimeException {
    public CreatingShaderException(String message) {
        super(message);
        System.err.println(message);
        System.exit(1);
    }
}
