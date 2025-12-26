package me.hannsi.lfjg.render.system.rendering.texture;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.reflection.reference.Ref;
import me.hannsi.lfjg.render.system.rendering.texture.atlas.AtlasPacker;
import me.hannsi.lfjg.render.system.rendering.texture.atlas.Sprite;
import me.hannsi.lfjg.render.system.rendering.texture.atlas.SpriteMemoryPolicy;
import org.lwjgl.opengl.GL;

import java.nio.ByteBuffer;
import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static org.lwjgl.opengl.ARBSparseTexture.GL_TEXTURE_SPARSE_ARB;
import static org.lwjgl.opengl.ARBSparseTexture.glTexPageCommitmentARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.glTexSubImage3D;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL42.glTexStorage3D;

public class SparseTexture2DArray {
    public static final int DEFAULT_MIP_LEVELS = 1;
    public static final int PBO_SEGMENT_ID = 1;

    private final AtlasPacker atlasPacker;
    private final int width;
    private final int height;
    private final int maxLayers;
    private final int textureId;

    public SparseTexture2DArray(AtlasPacker atlasPacker) {
        this.atlasPacker = atlasPacker;
        this.width = Math.max(1, ((atlasPacker.getAtlasWidth() + PAGE_SIZE_X - 1) / PAGE_SIZE_X) * PAGE_SIZE_X);
        this.height = Math.max(1, ((atlasPacker.getAtlasHeight() + PAGE_SIZE_Y - 1) / PAGE_SIZE_Y) * PAGE_SIZE_Y);
        this.maxLayers = Math.max(1, atlasPacker.getAtlasLayer());

        this.textureId = glGenTextures();
        GL_STATE_CACHE.bindTexture(GL_TEXTURE_2D_ARRAY, textureId);

        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        boolean sparseSupported = GL.getCapabilities().GL_ARB_sparse_texture;
        if (sparseSupported) {
            glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_SPARSE_ARB, GL_TRUE);
        } else {
            DebugLog.error(getClass(), "Sparse Texture is not supported on this GPU.");
        }

        glTexStorage3D(GL_TEXTURE_2D_ARRAY, DEFAULT_MIP_LEVELS, GL_RGBA8, width, height, maxLayers);
    }

    public SparseTexture2DArray updateSprite(String name, ByteBuffer byteBuffer) {
        Sprite sprite = getSpriteFormName(name);

        GL_STATE_CACHE.bindTexture(GL_TEXTURE_2D_ARRAY, textureId);
        GL_STATE_CACHE.bindPixelUnpackBuffer(PERSISTENT_MAPPED_PBO.getBufferId());

        glPixelStorei(GL_UNPACK_ALIGNMENT, 4);
        glPixelStorei(GL_UNPACK_ROW_LENGTH, 0);

        if (sprite.memoryPolicy == SpriteMemoryPolicy.STREAMING) {
            PERSISTENT_MAPPED_PBO.updateSegment(PBO_SEGMENT_ID, sprite.address, byteBuffer);

            long absoluteOffset = PERSISTENT_MAPPED_PBO.getSegmentOffset(PBO_SEGMENT_ID) + sprite.address;
            glTexSubImage3D(
                    GL_TEXTURE_2D_ARRAY,
                    0,
                    sprite.offsetX,
                    sprite.offsetY,
                    sprite.offsetZ,
                    sprite.width,
                    sprite.height,
                    1,
                    GL_RGBA,
                    GL_UNSIGNED_BYTE,
                    absoluteOffset
            );
        } else {
            DebugLog.warning(getClass(), "When updating information using the updateSprite method, the " + SpriteMemoryPolicy.class.getSimpleName() + " within the Sprite(" + name + ") must be set to " + SpriteMemoryPolicy.STREAMING.getName() + ".");
        }

        GL_STATE_CACHE.bindPixelUnpackBuffer(0);

        return this;
    }

    public SparseTexture2DArray updateFromAtlas() {
        GL_STATE_CACHE.bindTexture(GL_TEXTURE_2D_ARRAY, textureId);

        PERSISTENT_MAPPED_PBO.resetSegment(PBO_SEGMENT_ID);
        GL_STATE_CACHE.bindPixelUnpackBuffer(PERSISTENT_MAPPED_PBO.getBufferId());

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glPixelStorei(GL_UNPACK_ROW_LENGTH, 0);

        atlasPacker.getSprites().forEach((name, sprite) -> {
            if (sprite.commited && sprite.data != null) {
                sprite.data.rewind();

                long offset = PERSISTENT_MAPPED_PBO.uploadToSegment(PBO_SEGMENT_ID, sprite.data);

                glTexSubImage3D(
                        GL_TEXTURE_2D_ARRAY,
                        0,
                        sprite.offsetX,
                        sprite.offsetY,
                        sprite.offsetZ,
                        sprite.width,
                        sprite.height,
                        1,
                        GL_RGBA,
                        GL_UNSIGNED_BYTE,
                        offset
                );

                switch (sprite.memoryPolicy) {
                    case KEEP -> {
                    }
                    case RELEASE ->
                            sprite.data = null;
                    case STREAMING -> {
                        sprite.data = null;
                        sprite.address = offset;
                    }
                    default ->
                            throw new IllegalStateException("Unexpected value: " + sprite.memoryPolicy);
                }
            }
        });

        GL_STATE_CACHE.bindPixelUnpackBuffer(0);

        return this;
    }

    private void commitTexture(Sprite sprite, boolean state) {
        GL_STATE_CACHE.bindTexture(GL_TEXTURE_2D_ARRAY, textureId);

        int alignedX = (sprite.offsetX / PAGE_SIZE_X) * PAGE_SIZE_X;
        int alignedY = (sprite.offsetY / PAGE_SIZE_Y) * PAGE_SIZE_Y;

        int endX = ((sprite.offsetX + sprite.width + PAGE_SIZE_X - 1) / PAGE_SIZE_X) * PAGE_SIZE_X;
        int endY = ((sprite.offsetY + sprite.height + PAGE_SIZE_Y - 1) / PAGE_SIZE_Y) * PAGE_SIZE_Y;

        int alignedWidth = endX - alignedX;
        int alignedHeight = endY - alignedY;

        if (!state) {
            for (Map.Entry<String, Sprite> entry : atlasPacker.getSprites().entrySet()) {
                String otherName = entry.getKey();
                Sprite other = entry.getValue();
                if (other == sprite) {
                    return;
                }

                if (other.commited && other.offsetZ == sprite.offsetZ) {
                    int oAlignedX = (other.offsetX / PAGE_SIZE_X) * PAGE_SIZE_X;
                    int oAlignedY = (other.offsetY / PAGE_SIZE_Y) * PAGE_SIZE_Y;
                    int oEndX = ((other.offsetX + other.width + PAGE_SIZE_X - 1) / PAGE_SIZE_X) * PAGE_SIZE_X;
                    int oEndY = ((other.offsetY + other.height + PAGE_SIZE_Y - 1) / PAGE_SIZE_Y) * PAGE_SIZE_Y;

                    boolean overlap = (alignedX < oEndX && endX > oAlignedX) && (alignedY < oEndY && endY > oAlignedY);

                    if (overlap) {
                        DebugLog.warning(getClass(), String.format("Decommit skipped: Sprite (" + getSpriteName(sprite) + ") shares the same Virtual Pages with already committed Sprite (" + otherName + ") (Layer: " + sprite.offsetZ + "). " + "Physical memory will remain allocated."));

                        sprite.commited = false;
                    }
                }
            }
        }

        glTexPageCommitmentARB(
                GL_TEXTURE_2D_ARRAY,
                0,
                alignedX,
                alignedY,
                sprite.offsetZ,
                alignedWidth,
                alignedHeight,
                1,
                state
        );

        sprite.commited = state;
    }

    public SparseTexture2DArray commitTexture(String name, boolean state) {
        Sprite sprite = getSpriteFormName(name);
        commitTexture(sprite, state);

        return this;
    }

    public boolean commitedTexture(String name) {
        return commitedTexture(name, null);
    }

    public boolean commitedTexture(String name, Ref<Sprite> pointerSprite) {
        Sprite sprite = getSpriteFormName(name);

        if (pointerSprite != null) {
            pointerSprite.setValue(sprite);
        }

        return sprite.commited;
    }

    public Sprite getSpriteFormName(String name) {
        Sprite sprite = atlasPacker.getSprites().get(name);
        if (sprite == null) {
            DebugLog.error(getClass(), "The sprite registered under " + name + " does not exist.");
            return null;
        }

        return sprite;
    }

    private String getSpriteName(Sprite sprite) {
        return atlasPacker.getSprites().getKeyByValue(sprite);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxLayers() {
        return maxLayers;
    }

    public int getTextureId() {
        return textureId;
    }
}