package me.hannsi.lfjg.render.system.rendering.frameBuffer;

import me.hannsi.lfjg.render.debug.exceptions.frameBuffer.CreatingFrameBufferException;
import me.hannsi.lfjg.render.debug.exceptions.texture.CreatingTextureException;

import static me.hannsi.lfjg.render.LFJGRenderContext.GL_STATE_CACHE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;

public class TestFrameBuffer {
    private final int frameBufferId;
    private final int colorId;
    private final int depthId;
    private final int stencilId;


    public TestFrameBuffer(float x1, float y1, float x2, float y2, boolean color, boolean depth, boolean stencil) {
        frameBufferId = glGenFramebuffers();
        if (frameBufferId == 0) {
            throw new CreatingFrameBufferException("Failed to create frame buffer");
        }
        GL_STATE_CACHE.bindFrameBuffer(frameBufferId);

        if (color) {
            colorId = glGenTextures();
            if (colorId == 0) {
                throw new CreatingTextureException("Failed to create color texture");
            }

            GL_STATE_CACHE.bindTexture(GL_TEXTURE_2D, colorId);
        } else {
            colorId = -1;
        }

        if (depth) {
            depthId = glGenTextures();
            if (depthId == 0) {
                throw new CreatingTextureException("Failed to create depth texture");
            }

            GL_STATE_CACHE.bindTexture(GL_TEXTURE_2D, depthId);
        } else {
            depthId = -1;
        }

        if (stencil) {
            stencilId = glGenTextures();
            if (stencilId == 0) {
                throw new CreatingTextureException("Failed to create stencil texture");
            }

            GL_STATE_CACHE.bindTexture(GL_TEXTURE_2D, stencilId);
        } else {
            stencilId = -1;
        }


    }
}
