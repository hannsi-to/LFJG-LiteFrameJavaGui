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

    public TextureCache createCache(String name, Location path) {
        TextureLoader textureLoader = new TextureLoader(path, ImageLoaderType.STB_IMAGE);
        textureMap.put(name, textureLoader);

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                name,
                path.path()
        ).logging(DebugLevel.DEBUG);

        return this;
    }

    public TextureLoader getTexture(String name) {
        TextureLoader textureLoader = textureMap.get(name);
        if (textureLoader == null) {
            throw new RuntimeException("Texture path not found: " + name);
        }

        return textureLoader;
    }
}