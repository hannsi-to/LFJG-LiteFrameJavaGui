package me.hannsi.lfjg.core.utils.graphics.image;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.ImageLoaderType;

import java.util.HashMap;
import java.util.Map;

public class TextureCache {
    private final Map<String, TextureLoader> textureCaches;

    TextureCache() {
        this.textureCaches = new HashMap<>();
    }

    public static TextureCache createTextureCache() {
        return new TextureCache();
    }

    public void cleanup(String... names) {
        StringBuilder ids = new StringBuilder();
        int index = 0;
        for (String name : names) {
            TextureLoader textureLoader = textureCaches.get(name);
            if (textureLoader == null) {
                DebugLog.warning(getClass(), "The texture cache named " + name + " does not exist.");
                continue;
            }

            if (index == 0) {
                ids.append(textureLoader.getTextureId());
            } else {
                ids.append(", ").append(textureLoader.getTextureId());
            }
            textureLoader.cleanup();
            index++;

            textureCaches.remove(name);
        }

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.toString(),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);
    }

    public void cleanup() {
        StringBuilder ids = new StringBuilder();
        int index = 0;
        for (TextureLoader textureLoader : textureCaches.values()) {
            if (index == 0) {
                ids.append(textureLoader.getTextureId());
            } else {
                ids.append(", ").append(textureLoader.getTextureId());
            }
            textureLoader.cleanup();
            index++;
        }
        textureCaches.clear();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.toString(),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);
    }

    public TextureCache createCache(String name, Location path) {
        TextureLoader textureLoader = new TextureLoader(path, ImageLoaderType.STB_IMAGE);
        textureCaches.put(name, textureLoader);

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                name,
                path.path()
        ).logging(getClass(), DebugLevel.DEBUG);

        return this;
    }

    public TextureLoader getTexture(String name) {
        TextureLoader textureLoader = textureCaches.get(name);
        if (textureLoader == null) {
            throw new RuntimeException("Texture path not found: " + name);
        }

        return textureLoader;
    }

    public Map<String, TextureLoader> getTextureCaches() {
        return textureCaches;
    }
}