package me.hannsi.lfjg.utils.image;

import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.TextureLoaderType;

import java.util.HashMap;
import java.util.Map;

public class TextureCache {
    public static final ResourcesLocation DEFAULT_TEXTURE = new ResourcesLocation("texture/default.png");

    private Map<ResourcesLocation, TextureLoader> textureMap;

    public TextureCache() {
        this.textureMap = new HashMap<>();
        createTexture(DEFAULT_TEXTURE);
    }

    public void cleanup() {
        textureMap.values().forEach(TextureLoader::cleanup);
    }

    public void createTexture(ResourcesLocation texturePath) {
        textureMap.put(texturePath, new TextureLoader(texturePath, TextureLoaderType.STBImage));
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
