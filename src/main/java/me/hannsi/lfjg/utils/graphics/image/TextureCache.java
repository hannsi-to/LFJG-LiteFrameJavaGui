package me.hannsi.lfjg.utils.graphics.image;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.TextureLoaderType;

import java.util.HashMap;
import java.util.Map;

public class TextureCache {
    public static final ResourcesLocation DEFAULT_TEXTURE = new ResourcesLocation("texture/default.png");

    private Map<ResourcesLocation, TextureLoader> textureMap;

    public TextureCache() {
        this.textureMap = new HashMap<>();
        createCache(DEFAULT_TEXTURE);
    }

    public void cleanup() {
        textureMap.values().forEach(TextureLoader::cleanup);

        textureMap.clear();
    }

    public void createCache(ResourcesLocation texturePath) {
        textureMap.put(texturePath, new TextureLoader(texturePath, TextureLoaderType.STBImage));

        String logMessage = "\n---------- TextureCache Debug Message ----------" +
                "\n\tSource: TextureCache" +
                "\n\tType: Cache Creation" +
                "\n\tID: " + texturePath.hashCode() +
                "\n\tSeverity: Info" +
                "\n\tMessage: Create texture cache: " + texturePath.getPath() +
                "\n------------------------------------------\n";

        DebugLog.debug(getClass(), logMessage);
    }

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

    public Map<ResourcesLocation, TextureLoader> getTextureMap() {
        return textureMap;
    }

    public void setTextureMap(Map<ResourcesLocation, TextureLoader> textureMap) {
        this.textureMap = textureMap;
    }
}
