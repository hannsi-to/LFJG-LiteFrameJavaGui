package me.hannsi.lfjg.core.manager.asset;

import me.hannsi.lfjg.core.utils.reflection.location.Location;

public class AssetLoadException extends AssetException {
    public AssetLoadException(Location location, Throwable cause) {
        super("Failed to load asset: " + location.path(), cause);
    }

    public AssetLoadException(String message) {
        super(message);
    }
}
