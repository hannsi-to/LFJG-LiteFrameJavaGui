package me.hannsi.lfjg.render.openGL.system;

import me.hannsi.lfjg.debug.exceptions.frameBuffer.CompleteFrameBufferException;
import me.hannsi.lfjg.debug.exceptions.frameBuffer.CreatingFrameBufferException;
import me.hannsi.lfjg.debug.exceptions.texture.CreatingTextureException;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;

public class FrameBuffer {
    private final int frameBufferId;
    private final int textureId;

    public FrameBuffer() {
        frameBufferId = GL30.glGenFramebuffers();
        if (frameBufferId == 0) {
            throw new CreatingFrameBufferException("Failed to create frame buffer object");
        }

        textureId = GL30.glGenTextures();
        if (textureId == 0) {
            throw new CreatingTextureException("Failed to create texture");
        }
    }

    public void createFrameBuffer(int width, int height) {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferId);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);

        GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_RGBA, width, height, 0, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, (ByteBuffer) null);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);

        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_TEXTURE_2D, textureId, 0);

        if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE) {
            throw new CompleteFrameBufferException("Frame Buffer not complete");
        }

        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    public void bind() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferId);
    }

    public void unbind() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    public void draw() {

    }
}
