package me.hannsi.lfjg.utils.graphics.image;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.TextureLoaderType;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for managing a cache of textures.
 */
public class TextureCache {
    public static final ResourcesLocation DEFAULT_TEXTURE = new ResourcesLocation("texture/default.png");

    private Map<ResourcesLocation, TextureLoader> textureMap;

    /**
     * Constructs a TextureCache instance and initializes the cache with the default texture.
     */
    public TextureCache() {
        this.textureMap = new HashMap<>();
        createCache(DEFAULT_TEXTURE);
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
     * @param texturePath the path to the texture resource
     */
    public void createCache(ResourcesLocation texturePath) {
        textureMap.put(texturePath, new TextureLoader(texturePath, TextureLoaderType.STBImage));

        LogGenerator logGenerator = new LogGenerator("TextureCache Debug Message", "Source: TextureCache", "Type: Cache Creation", "ID: " + texturePath.hashCode(), "Severity: Info", "Message: Create texture cache: " + texturePath.getPath());

        DebugLog.debug(getClass(), logGenerator.createLog());
    }

    /**
     * Retrieves the texture loader for the specified texture path.
     * If the texture path is null or not found, the default texture loader is returned.
     *
     * @param texturePath the path to the texture resource
     * @return the texture loader for the specified path, or the default texture loader if not found
     */
    public TextureLoader getTexture(ResourcesLocation texturePath) {
        TextureLoader texture = null;
        if (texturePath != null) {
            texture = textureMap.get(texturePath);
        }
        if (texture == null) {
            texture = textureMap.get(DEFAULT_TEXTURE);
        }
        return texture;
    }

    /**
     * Gets the texture map.
     *
     * @return the texture map
     */
    public Map<ResourcesLocation, TextureLoader> getTextureMap() {
        return textureMap;
    }

    /**
     * Sets the texture map.
     *
     * @param textureMap the new texture map
     */
    public void setTextureMap(Map<ResourcesLocation, TextureLoader> textureMap) {
        this.textureMap = textureMap;
    }
}