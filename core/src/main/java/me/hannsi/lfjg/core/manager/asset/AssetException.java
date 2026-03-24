package me.hannsi.lfjg.core.manager.asset;

public class AssetException extends RuntimeException {
    public AssetException(String message) {
        super(message);
    }

    public AssetException(String message, Throwable cause) {
        super(message, cause);
    }
}
