package me.hannsi.lfjg.core.manager.asset;

import me.hannsi.lfjg.core.utils.reflection.location.Location;

public interface AssetLoader<T> {
    T load(Location location) throws AssetLoadException;

    Class<T> getAssetType();

    default void dispose(T asset) {
    }
}
