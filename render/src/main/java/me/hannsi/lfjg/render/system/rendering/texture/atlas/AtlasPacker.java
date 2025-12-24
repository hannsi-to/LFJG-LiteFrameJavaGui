package me.hannsi.lfjg.render.system.rendering.texture.atlas;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.render.debug.exceptions.texture.AtlasPackerException;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static me.hannsi.lfjg.core.utils.math.MathHelper.min;
import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static org.lwjgl.opengl.GL11.GL_MAX_TEXTURE_SIZE;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL30.GL_MAX_ARRAY_TEXTURE_LAYERS;

public class AtlasPacker {
    public static final int USE_MAX_TEXTURE_SIZE = -1;
    public static final int PADDING = 1;
    private final int atlasWidth;
    private final int atlasHeight;
    private final ByteBuffer[] atlasLayers;
    private final Map<String, Sprite> sprites;
    private int currentX;
    private int currentY;
    private int currentLayer;
    private int rowHeight;

    public AtlasPacker(int atlasWidth, int atlasHeight, int atlasLayer, int currentX, int currentY, int rowHeight) {
        this.sprites = new LinkedHashMap<>();

        int maxAtlasSize = glGetInteger(GL_MAX_TEXTURE_SIZE);
        atlasWidth = (atlasWidth == USE_MAX_TEXTURE_SIZE) ? maxAtlasSize : atlasWidth;
        atlasHeight = (atlasHeight == USE_MAX_TEXTURE_SIZE) ? maxAtlasSize : atlasHeight;
        int oldW = atlasWidth;
        int oldH = atlasHeight;
        atlasWidth = min(atlasWidth, maxAtlasSize);
        atlasHeight = min(atlasHeight, maxAtlasSize);
        if (oldW != atlasWidth || oldH != atlasHeight) {
            DebugLog.warning(getClass(), "Atlas resized due to GPU limit. " + oldW + "x" + oldH + " -> " + atlasWidth + "x" + atlasHeight);
        }
        oldW = atlasWidth;
        oldH = atlasHeight;
        atlasWidth = ((atlasWidth + PAGE_SIZE_X - 1) / PAGE_SIZE_X) * PAGE_SIZE_X;
        atlasHeight = ((atlasHeight + PAGE_SIZE_Y - 1) / PAGE_SIZE_Y) * PAGE_SIZE_Y;
        if (oldW != atlasWidth || oldH != atlasHeight) {
            DebugLog.info(getClass(), "Atlas size aligned for Sparse Texture: " + oldW + "x" + oldH + " -> " + atlasWidth + "x" + atlasHeight);
        }

        this.atlasWidth = atlasWidth;
        this.atlasHeight = atlasHeight;

        long totalBytes = (long) atlasWidth * atlasHeight * 4;
        if (totalBytes > Integer.MAX_VALUE) {
            throw new AtlasPackerException("The requested atlas size is too large for Java ByteBuffer! " + "Requested: " + totalBytes + " bytes, Max: " + Integer.MAX_VALUE);
        }

        int maxTextureLayers = glGetInteger(GL_MAX_ARRAY_TEXTURE_LAYERS);
        if (atlasLayer > maxTextureLayers) {
            DebugLog.warning(getClass(), "Requested layers: " + atlasLayer + " exceeds GPU limit: " + maxTextureLayers + " Setting layer: " + maxTextureLayers);
            atlasLayer = maxTextureLayers;
        } else {
            atlasLayer = max(1, atlasLayer);
        }

        this.currentX = currentX;
        this.currentY = currentY;
        this.rowHeight = rowHeight;
        this.atlasLayers = new ByteBuffer[atlasLayer];
        for (int i = 0; i < atlasLayers.length; i++) {
            try {
                this.atlasLayers[i] = BufferUtils.createByteBuffer((int) totalBytes);
            } catch (
                    OutOfMemoryError e) {
                throw new AtlasPackerException("Out of RAM! Failed to allocate " + (totalBytes / 1024 / 1024) + " MB for layer " + i);
            }
        }
    }

    public AtlasPacker addSprite(String name, Sprite sprite) {
        int textureWidth = sprite.width;
        int textureHeight = sprite.height;
        if (textureWidth + PADDING <= atlasWidth && textureHeight + PADDING <= atlasHeight) {
            sprites.put(name, sprite);
        } else {
            throw new AtlasPackerException("Sprite (" + name + ") is too large for the atlas! " + "[Sprite Size: " + textureWidth + "x" + textureHeight + "] > [Atlas Layer Size: " + atlasWidth + "x" + atlasHeight + "]. " + "Please increase atlasWidth/Height or downscale the sprite.");
        }

        return this;
    }

    public ByteBuffer[] generate() {
        this.currentX = 0;
        this.currentY = 0;
        this.currentLayer = 0;
        this.rowHeight = 0;

        for (ByteBuffer buffer : atlasLayers) {
            buffer.clear();
        }

        PERSISTENT_MAPPED_SSBO.resetBindingPoint(1);

        final float INSET = 0.5f;
        final int BYTES_PER_PIXEL = 4;

        float invW = 1.0f / atlasWidth;
        float invH = 1.0f / atlasHeight;

        for (Sprite sprite : sprites.values()) {
            if (currentX + sprite.width + PADDING > atlasWidth) {
                currentX = 0;
                currentY += rowHeight;
                rowHeight = 0;
            }

            if (currentY + sprite.height + PADDING > atlasHeight) {
                currentX = 0;
                currentY = 0;
                rowHeight = 0;
                currentLayer++;

                if (currentLayer >= atlasLayers.length) {
                    throw new RuntimeException("Atlas layer overflow! Pre-allocated " + atlasLayers.length + " layers are not enough for the added sprites.");
                }
            }

            sprite.offsetX = currentX;
            sprite.offsetY = currentY;
            sprite.offsetZ = currentLayer;

            ByteBuffer targetBuffer = atlasLayers[currentLayer];
            int rowSize = sprite.width * BYTES_PER_PIXEL;
            for (int y = 0; y < sprite.height; y++) {
                int spriteRowOffset = y * rowSize;
                int atlasRowOffset = ((sprite.offsetY + y) * atlasWidth + sprite.offsetX) * BYTES_PER_PIXEL;

                sprite.data.position(spriteRowOffset);
                sprite.data.limit(spriteRowOffset + rowSize);

                targetBuffer.position(atlasRowOffset);
                targetBuffer.put(sprite.data);

                sprite.data.clear();
            }

            float uvX = (sprite.offsetX + INSET) * invW;
            float uvY = (sprite.offsetY + INSET) * invH;
            float uvW = (sprite.width - (2 * INSET)) * invW;
            float uvH = (sprite.height - (2 * INSET)) * invH;

            PERSISTENT_MAPPED_SSBO.addVec4(1, uvX, uvY, uvW, uvH);
            PERSISTENT_MAPPED_SSBO.addFloat(1, currentLayer);
            PERSISTENT_MAPPED_SSBO.addFloat(1, 0);
            PERSISTENT_MAPPED_SSBO.addFloat(1, 0);
            PERSISTENT_MAPPED_SSBO.addFloat(1, 0);

            currentX += sprite.width + PADDING;
            rowHeight = Math.max(rowHeight, sprite.height + PADDING);
        }

        for (ByteBuffer buffer : atlasLayers) {
            buffer.flip();
        }

        return atlasLayers;
    }

    public int getAtlasWidth() {
        return atlasWidth;
    }

    public int getAtlasHeight() {
        return atlasHeight;
    }

    public ByteBuffer[] getAtlasLayers() {
        return atlasLayers;
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

    public int getCurrentLayer() {
        return currentLayer;
    }

    public int getRowHeight() {
        return rowHeight;
    }
}
