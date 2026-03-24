package me.hannsi.lfjg.core.manager.asset;

import me.hannsi.lfjg.core.event.events.CleanupEvent;
import me.hannsi.lfjg.core.utils.Cleanup;
import me.hannsi.lfjg.core.utils.reflection.location.Location;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AssetManager implements Cleanup {
    private final Map<String, Object> cache = new ConcurrentHashMap<>();
    private final Map<Class<?>, AssetLoader<?>> loaders = new ConcurrentHashMap<>();
    private final ExecutorService executor;

    public AssetManager() {
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public <T> void registerLoader(AssetLoader<T> loader) {
        loaders.put(loader.getAssetType(), loader);
    }

    public <T> void registerAsset(String assetName, Location location, Class<T> assetType) {
        String cacheName = getCacheName(assetName, assetType);
        if (cache.containsKey(cacheName)) {
            throw new AssetException("The asset name " + assetName + " is already registered in the " + assetType.getSimpleName() + " category. Please use a different asset name.");
        }

        AssetLoader<T> loader = getLoader(assetType);
        T asset = loader.load(location);
        cache.put(cacheName, asset);
    }

    @SuppressWarnings("unchecked")
    public <T> T load(String assetName, Class<T> assetType) {
        String cacheName = getCacheName(assetName, assetType);
        if (!cache.containsKey(cacheName)) {
            throw new AssetLoadException("The asset " + assetName + " is not registered in the " + assetType.getSimpleName() + " category.");
        }

        return (T) cache.get(cacheName);
    }

    public <T> AssetHandle<T> loadAsync(String assetName, Class<T> assetType) {
        CompletableFuture<T> future = CompletableFuture.supplyAsync(() -> load(assetName, assetType), executor);

        return new AssetHandle<>(future);
    }

    public <T> boolean isLoaded(String assetName, Class<T> assetType) {
        String cacheName = getCacheName(assetName, assetType);
        return cache.containsKey(cacheName);
    }

    public <T> void unload(String assetName, Class<T> assetType) {
        String cacheName = getCacheName(assetName, assetType);
        Object asset = cache.remove(cacheName);
        if (asset != null) {
            AssetLoader<T> loader = getLoader(assetType);
            loader.dispose(assetType.cast(asset));
        }
    }

    public void unloadAll() {
        cache.clear();
    }

    public <T> String getCacheName(String assetName, Class<T> assetType) {
        return assetName + "#" + assetType.getSimpleName();
    }

    @SuppressWarnings("unchecked")
    private <T> AssetLoader<T> getLoader(Class<T> assetType) {
        AssetLoader<T> loader = (AssetLoader<T>) loaders.get(assetType);
        if (loader == null) {
            throw new AssetLoadException("No loader registered for type: " + assetType.getName());
        }

        return loader;
    }

    @Override
    public boolean cleanup(CleanupEvent event) {
        CleanupEvent.CleanupData datum = new CleanupEvent.CleanupData(this.getClass());
        for (Map.Entry<String, Object> entry : cache.entrySet()) {

        }

        executor.shutdown();
        unloadAll();

        return false;
    }
}
