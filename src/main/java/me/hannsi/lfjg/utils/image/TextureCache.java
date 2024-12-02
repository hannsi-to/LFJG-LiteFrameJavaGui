package me.hannsi.lfjg.utils.image;

import me.hannsi.lfjg.utils.reflection.ResourcesLocation;

import java.util.HashMap;
import java.util.Map;

public class TextureCache {
    private static Map<ResourcesLocation, Integer> textureCache = new HashMap<>();

    public static Map<ResourcesLocation, Integer> getTextureCache() {
        return textureCache;
    }

    public static void setTextureCache(Map<ResourcesLocation, Integer> textureCache) {
        TextureCache.textureCache = textureCache;
    }

    public int getTextureId(ResourcesLocation texturePath) {
        if (textureCache.containsKey(texturePath)) {
            return textureCache.get(texturePath);
        }

        ImageData imageDat = new ImageData(texturePath);
        int textureId = TextureLoader.createTexture(imageDat.getByteBuffer(), imageDat.getMat().cols(), imageDat.getMat().rows());
        textureCache.put(texturePath, textureId);

        return textureId;
    }
}
