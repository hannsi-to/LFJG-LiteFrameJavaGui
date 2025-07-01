package me.hannsi.lfjg.utils.graphics.image;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.type.types.ImageLoaderType;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for managing a cache of textures.
 */
@Getter
@Setter
public class TextureCache {
    /**
     * -- SETTER --
     * Sets the texture map.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the texture map.
     *
     * @param textureMap the new texture map
     * @return the texture map
     */
    private Map<String, TextureLoader> textureMap;

    /**
     * Constructs a TextureCache instance and initializes the cache with the default texture.
     */
    TextureCache() {
        this.textureMap = new HashMap<>();
    }

    public static TextureCache createTextureCache() {
        return new TextureCache();
    }

    /**
     * Cleans up all textures in the cache.
     */
    public void cleanup() {
        StringBuilder ids = new StringBuilder();
        int index = 0;
        for (TextureLoader textureLoader : textureMap.values()) {
            if (index == 0) {
                ids.append(textureLoader.getTextureId());
            } else {
                ids.append(", ").append(textureLoader.getTextureId());
            }
            textureLoader.cleanup();
            index++;
        }
        textureMap.clear();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.toString(),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    /**
     * Creates a cache entry for the specified texture path.
     *
     * @param path the path to the texture resource
     */
    public TextureCache createCache(Location path) {
        TextureLoader textureLoader = new TextureLoader(path, ImageLoaderType.STB_IMAGE);
        textureMap.put(path.path(), textureLoader);

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                textureLoader.getTextureId(),
                path.path()
        ).logging(DebugLevel.DEBUG);

        return this;
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
            texture = textureMap.get(path.path());
        }

        return texture;
    }

}