package me.hannsi.lfjg.render.system.rendering.texture;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.render.system.rendering.texture.atlas.AtlasPacker;
import org.lwjgl.opengl.GL;

import java.nio.ByteBuffer;

import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static org.lwjgl.opengl.ARBSparseTexture.GL_TEXTURE_SPARSE_ARB;
import static org.lwjgl.opengl.ARBSparseTexture.glTexPageCommitmentARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.glTexSubImage3D;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL42.glTexStorage3D;

public class SparseTexture2DArray {
    public static final int DEFAULT_MIP_LEVELS = 1;

    private final AtlasPacker atlasPacker;
    private final int width;
    private final int height;
    private final int maxLayers;
    private final int textureId;
    private final boolean sparseSupported;
    private int committedLayers = 0;

    public SparseTexture2DArray(AtlasPacker atlasPacker) {
        this.atlasPacker = atlasPacker;
        this.width = Math.max(1, ((atlasPacker.getAtlasWidth() + PAGE_SIZE_X - 1) / PAGE_SIZE_X) * PAGE_SIZE_X);
        this.height = Math.max(1, ((atlasPacker.getAtlasHeight() + PAGE_SIZE_Y - 1) / PAGE_SIZE_Y) * PAGE_SIZE_Y);
        this.maxLayers = Math.max(1, atlasPacker.getAtlasLayers().length);

        this.textureId = glGenTextures();
        GL_STATE_CACHE.bindTexture(GL_TEXTURE_2D_ARRAY, textureId);

        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        sparseSupported = GL.getCapabilities().GL_ARB_sparse_texture;
        if (sparseSupported) {
            glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_SPARSE_ARB, GL_TRUE);
        } else {
            DebugLog.error(getClass(), "Sparse Texture is not supported on this GPU.");
        }

        glTexStorage3D(GL_TEXTURE_2D_ARRAY, DEFAULT_MIP_LEVELS, GL_RGBA8, width, height, maxLayers);
    }

    public SparseTexture2DArray updateFromAtlas(ByteBuffer[] layers) {
        if (layers == null || layers.length == 0) {
            return this;
        }

        GL_STATE_CACHE.bindTexture(GL_TEXTURE_2D_ARRAY, textureId);

        glPixelStorei(GL_UNPACK_ROW_LENGTH, atlasPacker.getAtlasWidth());

        for (int z = 0; z < layers.length; z++) {
            if (z >= maxLayers) {
                DebugLog.warning(getClass(), "Reached maxLayers limit: " + maxLayers);
                break;
            }

            if (z >= committedLayers) {
                if (sparseSupported) {
                    glTexPageCommitmentARB(GL_TEXTURE_2D_ARRAY, 0, 0, 0, z, width, height, 1, true);
                    committedLayers++;
                } else {
                    committedLayers = max(committedLayers, z + 1);
                }
            }

            ByteBuffer buffer = layers[z];
            if (buffer != null) {
                glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, z, width, height, 1, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            }
        }

        glPixelStorei(GL_UNPACK_ROW_LENGTH, 0);

        return this;
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

    public int getCommittedLayers() {
        return committedLayers;
    }
}