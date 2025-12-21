package me.hannsi.lfjg.render.system.rendering.texture.atlas;

import me.hannsi.lfjg.core.debug.DebugLog;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.PERSISTENT_MAPPED_SSBO;
import static me.hannsi.lfjg.render.system.mesh.InstanceData.NO_ATTACH_TEXTURE;
import static org.lwjgl.opengl.GL11.GL_MAX_TEXTURE_SIZE;
import static org.lwjgl.opengl.GL11.glGetInteger;

public class AtlasPacker {
    private final int atlasWidth;
    private final int atlasHeight;
    private final ByteBuffer atlasBuffer;
    private final Map<String, Sprite> sprites;
    private int currentX;
    private int currentY;
    private int rowHeight;

    public AtlasPacker(int atlasWidth, int atlasHeight, int currentX, int currentY, int rowHeight) {
        this.sprites = new HashMap<>();

        int maxAtlasSize = glGetInteger(GL_MAX_TEXTURE_SIZE);
        int oldW = atlasWidth;
        int oldH = atlasHeight;
        atlasWidth = Math.min(atlasWidth, maxAtlasSize);
        atlasHeight = Math.min(atlasHeight, maxAtlasSize);
        if (oldW != atlasWidth || oldH != atlasHeight) {
            DebugLog.warning(getClass(),
                    "Atlas resized due to GPU limit. " +
                            "W: " + oldW + " -> " + atlasWidth + ", " +
                            "H: " + oldH + " -> " + atlasHeight
            );
        }
        this.atlasWidth = atlasWidth;
        this.atlasHeight = atlasHeight;

        this.currentX = currentX;
        this.currentY = currentY;
        this.rowHeight = rowHeight;
        this.atlasBuffer = BufferUtils.createByteBuffer(atlasWidth * atlasHeight * 4);
    }

    public AtlasPacker addSprite(String name, Sprite sprite) {
        sprites.put(name, sprite);

        return this;
    }

    public ByteBuffer generate() {
        final int PADDING = 1;

        for (Map.Entry<String, Sprite> spriteEntry : sprites.entrySet()) {
            Sprite sprite = spriteEntry.getValue();
            if (currentX + sprite.width + PADDING > atlasWidth) {
                currentX = 0;
                currentY += rowHeight;
                rowHeight = 0;
            }
            if (currentY + sprite.height + PADDING > atlasHeight) {
                throw new RuntimeException("Atlas size shortage");
            }

            sprite.offsetX = currentX;
            sprite.offsetY = currentY;

            currentX += sprite.width + PADDING;
            rowHeight = Math.max(rowHeight, sprite.height + PADDING);
        }

        for (Map.Entry<String, Sprite> spriteEntry : sprites.entrySet()) {
            Sprite sprite = spriteEntry.getValue();
            for (int y = 0; y < sprite.height; y++) {
                for (int x = 0; x < sprite.width; x++) {
                    int spriteIndex = (y * sprite.width + x) * 4;
                    int atlasIndex = ((sprite.offsetY + y) * atlasWidth + (sprite.offsetX + x)) * 4;
                    atlasBuffer.put(atlasIndex, sprite.data.get(spriteIndex));
                    atlasBuffer.put(atlasIndex + 1, sprite.data.get(spriteIndex + 1));
                    atlasBuffer.put(atlasIndex + 2, sprite.data.get(spriteIndex + 2));
                    atlasBuffer.put(atlasIndex + 3, sprite.data.get(spriteIndex + 3));
                }
            }
        }

        PERSISTENT_MAPPED_SSBO.addInt(1, sprites.size()).addInt(1, 0).addInt(1, 0).addInt(1, 0);
        final float INSET = 0.5f;
        for (Map.Entry<String, Sprite> spriteEntry : sprites.entrySet()) {
            Sprite sprite = spriteEntry.getValue();
            float pixel_width = 1.0f / atlasWidth;
            float pixel_height = 1.0f / atlasHeight;

            float uvX = (float) sprite.offsetX / atlasWidth + (INSET * pixel_width);
            float uvY = (float) sprite.offsetY / atlasHeight + (INSET * pixel_height);

            float uv_width_inset = (sprite.width - (2 * INSET)) / atlasWidth;
            float uv_height_inset = (sprite.height - (2 * INSET)) / atlasHeight;

            PERSISTENT_MAPPED_SSBO.addVec4(1, uvX, uvY, uv_width_inset, uv_height_inset);
        }

        PERSISTENT_MAPPED_SSBO.syncToGPU();

        return atlasBuffer;
    }

    public int getLayer(String name) {
        if (!sprites.containsKey(name)) {
            DebugLog.error(getClass(), "The sprite registered under " + name + " does not exist.");

            return NO_ATTACH_TEXTURE;
        }

        return sprites.get(name).instanceLayer;
    }

    public int getAtlasWidth() {
        return atlasWidth;
    }

    public int getAtlasHeight() {
        return atlasHeight;
    }

    public ByteBuffer getAtlasBuffer() {
        return atlasBuffer;
    }

    public Map<String, Sprite> getSprites() {
        return sprites;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public int getRowHeight() {
        return rowHeight;
    }
}
