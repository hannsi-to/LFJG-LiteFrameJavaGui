package me.hannsi.lfjg.render.manager;

import me.hannsi.lfjg.core.event.EventHandler;
import me.hannsi.lfjg.core.event.events.CleanupEvent;
import me.hannsi.lfjg.core.utils.math.io.IOUtil;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.event.RenderCleanupEvent;
import me.hannsi.lfjg.render.system.rendering.texture.atlas.Sprite;
import me.hannsi.lfjg.render.system.rendering.texture.atlas.SpriteMemoryPolicy;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;

public class AssetManager {
    public static final Map<String, String> textAssets;
    public static final Map<String, Sprite> textureAssets;

    static {
        textAssets = new ConcurrentHashMap<>();
        textureAssets = new ConcurrentHashMap<>();
    }

    public static String getTextAsset(Location location) {
        String cacheName = getCacheName(AssetType.TEXT, location);
        String asset = textAssets.get(cacheName);
        if (asset == null) {
            try {
                asset = IOUtil.readInputStreamToString(location.openStream());
            } catch (IOException e) {
                throw new RuntimeException(e);

            }
            textAssets.put(cacheName, asset);
        }

        return asset;
    }

    public static Sprite getTextureAsset(Location location) {
        String cacheName = getCacheName(AssetType.TEXTURE, location);
        Sprite asset = textureAssets.get(cacheName);
        if (asset == null) {
            ByteBuffer buffer = location.getByteBuffer();
            ByteBuffer image = null;

            try (MemoryStack stack = MemoryStack.stackPush()) {
                IntBuffer width = stack.mallocInt(1);
                IntBuffer height = stack.mallocInt(1);
                IntBuffer channels = stack.mallocInt(1);

                image = stbi_load_from_memory(buffer, width, height, channels, 4);
                if (image == null) {
                    throw new RuntimeException("Failed to load image: " + location.path());
                }

                asset = new Sprite(width.get(0), height.get(0), IOUtil.copyByteBuffer(image, BufferUtils.createByteBuffer(image.remaining())), SpriteMemoryPolicy.KEEP);
            } finally {
                if (image != null) {
                    stbi_image_free(image);
                }
            }
            textureAssets.put(cacheName, asset);
        }

        return asset;
    }

    public static void removeTextAsset(Location location) {
        textAssets.remove(getCacheName(AssetType.TEXT, location));
    }

    public static void removeTextureAsset(Location location) {
        textureAssets.remove(getCacheName(AssetType.TEXTURE, location));
    }

    private static String getCacheName(AssetType assetType, Location location) {
        return assetType.getName() + ": " + location.path();
    }

    @EventHandler
    public static void renderCleanupEvent(RenderCleanupEvent event) {
        CleanupEvent.CleanupData textDatum = new CleanupEvent.CleanupData("TextAssets");
        for (Map.Entry<String, String> entry : textAssets.entrySet()) {
            textDatum.addData(entry.getKey(), true);
        }
        textAssets.clear();

        CleanupEvent.CleanupData textureDatum = new CleanupEvent.CleanupData("TextureAssets");
        for (Map.Entry<String, Sprite> entry : textureAssets.entrySet()) {
            textureDatum.addData(entry.getKey(), entry.getValue().cleanup(event));
        }
        textureAssets.clear();

        event.debug(AssetManager.class, textDatum, textureDatum);
    }
}