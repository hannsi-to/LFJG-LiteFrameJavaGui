package me.hannsi.lfjg.utils.graphics.image;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.utils.reflection.Location;
import me.hannsi.lfjg.utils.type.types.ImageLoaderType;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for managing a cache of textures.
 */
public class TextureCache {
    private Map<Location, TextureLoader> textureMap;

    /**
     * Constructs a TextureCache instance and initializes the cache with the default texture.
     */
    public TextureCache() {
        this.textureMap = new HashMap<>();
    }

    /**
     * Cleans up all textures in the cache.
     */
    public void cleanup() {
        textureMap.values().forEach(TextureLoader::cleanup);
        textureMap.clear();
    }

    /**
     * Creates a cache entry for the specified texture path.
     *
     * @param path the path to the texture resource
     */
    public void createCache(Location path) {
        textureMap.put(path, new TextureLoader(path, ImageLoaderType.STBImage));

        LogGenerator logGenerator = new LogGenerator("TextureCache Debug Message", "Source: TextureCache", "Type: Cache Creation", "ID: " + path.hashCode(), "Severity: Info", "Message: Create texture cache: " + path.getPath());
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Retrieves the texture loader for the specified texture path.
     * If the texture path is null or not found, the default texture loader is returned.
     *
     * @param path the path to the texture resource
     * @return the texture loader for the specified path, or the default texture loader if not found
     */
    public TextureLoader getTexture(Location path) {
        TextureLoader texture = null;
        if (path != null) {
            texture = textureMap.get(path);
        }

        return texture;
    }

    /**
     * Gets the texture map.
     *
     * @return the texture map
     */
    public Map<Location, TextureLoader> getTextureMap() {
        return textureMap;
    }

    /**
     * Sets the texture map.
     *
     * @param textureMap the new texture map
     */
    public void setTextureMap(Map<Location, TextureLoader> textureMap) {
        this.textureMap = textureMap;
    }
}