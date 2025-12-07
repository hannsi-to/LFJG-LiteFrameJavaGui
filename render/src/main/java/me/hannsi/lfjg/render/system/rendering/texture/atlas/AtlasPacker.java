package me.hannsi.lfjg.render.system.rendering.texture.atlas;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.render.system.mesh.MeshConstants;
import me.hannsi.lfjg.render.system.mesh.persistent.TestPersistentMappedSSBO;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_MAX_TEXTURE_SIZE;
import static org.lwjgl.opengl.GL11.glGetInteger;

public class AtlasPacker {
    private final int atlasWidth;
    private final int atlasHeight;
    private final ByteBuffer atlasBuffer;
    private final List<Sprite> sprites;
    protected TestPersistentMappedSSBO persistentMappedSSBO;
    private int currentX;
    private int currentY;
    private int rowHeight;

    public AtlasPacker(int atlasWidth, int atlasHeight, int currentX, int currentY, int rowHeight) {
        this.sprites = new ArrayList<>();

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

        persistentMappedSSBO = new TestPersistentMappedSSBO(MeshConstants.DEFAULT_FLAGS_HINT, 1000);
    }

    public AtlasPacker addSprite(Sprite sprite) {
        sprites.add(sprite);

        return this;
    }

    public ByteBuffer generate() {
        final int PADDING = 1;

        for (Sprite sprite : sprites) {
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

        for (Sprite sprite : sprites) {
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

        persistentMappedSSBO.addInt(1, sprites.size()).addInt(1, 0).addInt(1, 0).addInt(1, 0);
        sprites.sort(Comparator.comparingInt(sprite -> sprite.instanceLayer));
        final float INSET = 0.5f;

        for (Sprite sprite : sprites) {
            float pixel_width = 1.0f / atlasWidth;
            float pixel_height = 1.0f / atlasHeight;

            float uvX = (float) sprite.offsetX / atlasWidth + (INSET * pixel_width);
            float uvY = (float) sprite.offsetY / atlasHeight + (INSET * pixel_height);

            float uv_width_inset = (sprite.width - (2 * INSET)) / atlasWidth;
            float uv_height_inset = (sprite.height - (2 * INSET)) / atlasHeight;


            persistentMappedSSBO.addVec4(1, uvX, uvY, uv_width_inset, uv_height_inset);
        }
        persistentMappedSSBO.addInt(2, 0);
        persistentMappedSSBO.addInt(2, 2);
        persistentMappedSSBO.addInt(2, 2);
        persistentMappedSSBO.addInt(2, 3);

        persistentMappedSSBO.syncToGPU();
        persistentMappedSSBO.bindSSBODataAll();

        return atlasBuffer;
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

    public List<Sprite> getSprites() {
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
