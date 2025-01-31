package me.hannsi.lfjg.render.openGL.system.model.texture;

import me.hannsi.lfjg.utils.reflection.FileLocation;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;

import java.util.HashMap;
import java.util.Map;

public class TextureModelCache {
    public static final FileLocation DEFAULT_TEXTURE = new ResourcesLocation("texture/default.png");

    private final Map<FileLocation, TextureModel> textureMap;

    public TextureModelCache() {
        textureMap = new HashMap<>();
        textureMap.put(DEFAULT_TEXTURE, new TextureModel(DEFAULT_TEXTURE));
    }

    public void cleanup() {
        textureMap.values().forEach(TextureModel::cleanup);
    }

    public TextureModel createTexture(FileLocation texturePath) {
        return textureMap.computeIfAbsent(texturePath, TextureModel::new);
    }

    public TextureModel getTexture(FileLocation texturePath) {
        TextureModel texture = null;
        if (texturePath != null) {
            texture = textureMap.get(texturePath);
        }
        if (texture == null) {
            texture = textureMap.get(DEFAULT_TEXTURE);
        }
        return texture;
    }
}
