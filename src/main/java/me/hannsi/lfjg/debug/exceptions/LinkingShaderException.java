package me.hannsi.lfjg.debug.exceptions;

public class LinkingShaderException extends RuntimeException {
    public LinkingShaderException(String message) {
        super(message);
        System.err.println(message);
        System.exit(1);
    }
}
