package me.hannsi.lfjg.debug.exceptions;

public class CompilingShaderException extends RuntimeException {
    public CompilingShaderException(String message) {
        super(message);
        System.err.println(message);
        System.exit(1);
    }
}
