package me.hannsi.lfjg.debug.exceptions.texture;

public class CreatingTextureException extends RuntimeException {
    public CreatingTextureException(String message) {
        super(message);
        System.err.println(message);
        System.exit(1);
    }
}
