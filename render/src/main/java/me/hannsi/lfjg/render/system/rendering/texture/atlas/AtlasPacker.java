package me.hannsi.lfjg.render.system.rendering.texture.atlas;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.math.map.string2objectMap.LinkedString2ObjectMap;
import me.hannsi.lfjg.render.debug.exceptions.texture.AtlasPackerException;
import me.hannsi.lfjg.render.uitl.id.Id;

import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static me.hannsi.lfjg.core.utils.math.MathHelper.min;
import static me.hannsi.lfjg.render.LFJGRenderContext.*;

public class AtlasPacker {
    public static final int PADDING = 1;
    private final int atlasWidth;
    private final int atlasHeight;
    private final int atlasLayer;
    private final LinkedString2ObjectMap<Sprite> sprites;
    private int currentSpriteIndex;
    private int currentX;
    private int currentY;
    private int currentLayer;
    private int rowHeight;

    public AtlasPacker(int atlasWidth, int atlasHeight, int atlasLayer, int currentX, int currentY, int rowHeight) {
        this.sprites = new LinkedString2ObjectMap<>();

        int oldW = atlasWidth;
        int oldH = atlasHeight;
        atlasWidth = min(atlasWidth, MAX_TEXTURE_SIZE);
        atlasHeight = min(atlasHeight, MAX_TEXTURE_SIZE);
        if (oldW != atlasWidth || oldH != atlasHeight) {
            DebugLog.warning(getClass(), "Atlas resized due to GPU limit. " + oldW + "x" + oldH + " -> " + atlasWidth + "x" + atlasHeight);
        }
        oldW = atlasWidth;
        oldH = atlasHeight;
        atlasWidth = ((atlasWidth + VIRTUAL_PAGE_SIZE_X - 1) / VIRTUAL_PAGE_SIZE_X) * VIRTUAL_PAGE_SIZE_X;
        atlasHeight = ((atlasHeight + VIRTUAL_PAGE_SIZE_Y - 1) / VIRTUAL_PAGE_SIZE_Y) * VIRTUAL_PAGE_SIZE_Y;
        if (oldW != atlasWidth || oldH != atlasHeight) {
            DebugLog.info(getClass(), "Atlas size aligned for Sparse Texture: " + oldW + "x" + oldH + " -> " + atlasWidth + "x" + atlasHeight);
        }

        this.atlasWidth = atlasWidth;
        this.atlasHeight = atlasHeight;

        if (atlasLayer > MAX_ARRAY_TEXTURE_LAYERS) {
            DebugLog.warning(getClass(), "Requested layers: " + atlasLayer + " exceeds GPU limit: " + MAX_ARRAY_TEXTURE_LAYERS + " Setting layer: " + MAX_ARRAY_TEXTURE_LAYERS);
            this.atlasLayer = MAX_ARRAY_TEXTURE_LAYERS;
        } else {
            this.atlasLayer = max(1, atlasLayer);
        }

        this.currentSpriteIndex = Id.initialSpriteIndexId;
        this.currentX = currentX;
        this.currentY = currentY;
        this.rowHeight = rowHeight;
    }

    public AtlasPacker addSprite(String name, Sprite sprite) {
        int textureWidth = sprite.width;
        int textureHeight = sprite.height;
        sprite.setSpriteIndex(currentSpriteIndex);
        currentSpriteIndex++;
        if (textureWidth + PADDING <= atlasWidth && textureHeight + PADDING <= atlasHeight) {
            sprites.put(name, sprite);
        } else {
            throw new AtlasPackerException("Sprite (" + name + ") is too large for the atlas! " + "[Sprite Size: " + textureWidth + "x" + textureHeight + "] > [Atlas Layer Size: " + atlasWidth + "x" + atlasHeight + "]. " + "Please increase atlasWidth/Height or downscale the sprite.");
        }

        return this;
    }

    public void generate() {
        this.currentX = 0;
        this.currentY = 0;
        this.currentLayer = 0;
        this.rowHeight = 0;

        persistentMappedSSBO.resetBindingPoint(1);

        final float INSET = 0.5f;
        final int BYTES_PER_PIXEL = 4;

        float invW = 1.0f / atlasWidth;
        float invH = 1.0f / atlasHeight;

        sprites.forEach((key, sprite) -> {
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
            }

            sprite.offsetX = currentX;
            sprite.offsetY = currentY;
            sprite.offsetZ = currentLayer;

            int rowSize = sprite.width * BYTES_PER_PIXEL;
            for (int y = 0; y < sprite.height; y++) {
                int spriteRowOffset = y * rowSize;

                sprite.data.position(spriteRowOffset);
                sprite.data.limit(spriteRowOffset + rowSize);

                sprite.data.clear();
            }

            float uvX = (sprite.offsetX + INSET) * invW;
            float uvY = (sprite.offsetY + INSET) * invH;
            float uvW = (sprite.width - (2 * INSET)) * invW;
            float uvH = (sprite.height - (2 * INSET)) * invH;

            persistentMappedSSBO.addVec4(1, uvX, uvY, uvW, uvH);
            persistentMappedSSBO.addFloat(1, currentLayer);
            persistentMappedSSBO.addFloat(1, 0);
            persistentMappedSSBO.addFloat(1, 0);
            persistentMappedSSBO.addFloat(1, 0);

            currentX += sprite.width + PADDING;
            rowHeight = Math.max(rowHeight, sprite.height + PADDING);
        });
    }

    public int getAtlasWidth() {
        return atlasWidth;
    }

    public int getAtlasHeight() {
        return atlasHeight;
    }

    public int getAtlasLayer() {
        return atlasLayer;
    }

    public LinkedString2ObjectMap<Sprite> getSprites() {
        return sprites;
    }
}
