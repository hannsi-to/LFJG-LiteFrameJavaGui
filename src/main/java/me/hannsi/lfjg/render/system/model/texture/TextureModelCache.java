package me.hannsi.lfjg.render.system.model.texture;

import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.utils.reflection.location.FileLocation;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * Caches texture models in the OpenGL rendering system.
 */
public class TextureModelCache {
    public static final FileLocation DEFAULT_TEXTURE = new ResourcesLocation("texture/default.png");

    private final Map<FileLocation, TextureModel> textureMap;

    /**
     * Constructs a new TextureModelCache and initializes it with the default texture.
     */
    public TextureModelCache() {
        textureMap = new HashMap<>();
        textureMap.put(DEFAULT_TEXTURE, new TextureModel(DEFAULT_TEXTURE));
    }

    /**
     * Cleans up all texture models in the cache.
     */
    public void cleanup() {
        textureMap.values().forEach(TextureModel::cleanup);
        textureMap.clear();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                hashCode(),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    /**
     * Creates a texture model for the specified texture path if it does not already exist in the cache.
     *
     * @param texturePath the file location of the texture
     * @return the texture model associated with the specified texture path
     */
    public TextureModel createTexture(FileLocation texturePath) {
        return textureMap.computeIfAbsent(texturePath, TextureModel::new);
    }

    /**
     * Gets the texture model associated with the specified texture path.
     * If the texture path is null or not found, returns the default texture model.
     *
     * @param texturePath the file location of the texture
     * @return the texture model associated with the specified texture path, or the default texture model if not found
     */
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