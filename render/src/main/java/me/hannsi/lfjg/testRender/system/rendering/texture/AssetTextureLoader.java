package me.hannsi.lfjg.testRender.system.rendering.texture;

import me.hannsi.lfjg.core.manager.asset.AssetLoadException;
import me.hannsi.lfjg.core.manager.asset.AssetLoader;
import me.hannsi.lfjg.core.utils.math.io.IOUtil;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.testRender.system.rendering.texture.atlas.Sprite;
import me.hannsi.lfjg.testRender.system.rendering.texture.atlas.SpriteMemoryPolicy;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;

public class AssetTextureLoader implements AssetLoader<Sprite> {
    @Override
    public Sprite load(Location location) throws AssetLoadException {
        ByteBuffer buffer = location.getByteBuffer();
        ByteBuffer image = null;
        Sprite asset;

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

        return asset;
    }

    @Override
    public Class<Sprite> getAssetType() {
        return Sprite.class;
    }
}
