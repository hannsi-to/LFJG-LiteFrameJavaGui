package me.hannsi.lfjg.core.manager.asset;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AssetHandle<T> {
    private final CompletableFuture<T> future;

    public AssetHandle(CompletableFuture<T> future) {
        this.future = future;
    }

    public T get() {
        try {
            return future.join();
        } catch (Exception e) {
            throw new AssetLoadException("Asset retrieval failed: " + e.getMessage());
        }
    }

    public boolean isDone() {
        return future.isDone();
    }

    public AssetHandle<T> onLoad(Consumer<T> callback) {
        future.thenAccept(callback);

        return this;
    }
}
