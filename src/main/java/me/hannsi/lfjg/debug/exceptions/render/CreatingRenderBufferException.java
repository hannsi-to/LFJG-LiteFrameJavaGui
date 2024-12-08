package me.hannsi.lfjg.debug.exceptions.render;

public class CreatingRenderBufferException extends RuntimeException{
    public CreatingRenderBufferException(String message) {
        super(message);
        System.err.println(message);
        System.exit(1);
    }
}
