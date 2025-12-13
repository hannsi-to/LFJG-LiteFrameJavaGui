package me.hannsi.lfjg.render.system.rendering.texture;

import me.hannsi.lfjg.core.debug.DebugLog;
import org.lwjgl.opengl.GL;

import java.nio.ByteBuffer;

import static me.hannsi.lfjg.render.LFJGRenderContext.GL_STATE_CACHE;
import static org.lwjgl.opengl.ARBSparseTexture.GL_TEXTURE_SPARSE_ARB;
import static org.lwjgl.opengl.ARBSparseTexture.glTexPageCommitmentARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.glTexSubImage3D;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL42.glTexStorage3D;

public class SparseTexture2DArray {
    public static final int DEFAULT_LAYERS = 4;
    public static final int DEFAULT_MIP_LEVELS = 1;

    private final int textureId;

    public SparseTexture2DArray(int width, int height, ByteBuffer buffer) {
        this(width, height, DEFAULT_LAYERS, DEFAULT_MIP_LEVELS, buffer);
    }

    public SparseTexture2DArray(int width, int height, int layers, int mipLevels, ByteBuffer buffer) {
        textureId = glGenTextures();
        GL_STATE_CACHE.bindTexture(GL_TEXTURE_2D_ARRAY,textureId);

        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexStorage3D(GL_TEXTURE_2D_ARRAY, mipLevels, GL_RGBA8, width, height, layers);
        if (GL.getCapabilities().GL_ARB_sparse_texture) {
            glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_SPARSE_ARB, GL_TRUE);
            glTexPageCommitmentARB(GL_TEXTURE_2D_ARRAY, 0, 0, 0, 0, width, height, 1, true);
            glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, 0, width, height, 1, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        }else{
            DebugLog.warning(getClass(),new RuntimeException("Sparse textures not supported on this GPU"));

            glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, 0, width, height, 1, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        }

    }
}