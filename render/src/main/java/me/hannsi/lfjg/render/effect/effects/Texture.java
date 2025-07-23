package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.core.utils.graphics.image.TextureLoader;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Texture extends EffectBase {
    private TextureCache textureCache;
    private TextureLoader textureLoader;
    private int textureId;

    private BlendType blendType = BlendType.NORMAL;

    Texture(TextureCache textureCache, String name) {
        super(3, "Texture");

        this.textureCache = textureCache;
        this.textureLoader = textureCache.getTexture(name);
    }

    public Texture(int textureId) {
        super(3, "Texture", (Class<GLObject>) null);

        this.textureId = textureId;
    }

    public static Texture createTexture(TextureCache textureCache, String name) {
        return new Texture(textureCache, name);
    }

    public Texture blendType(BlendType blendType) {
        this.blendType = blendType;
        return this;
    }

    @Override
    public void pop(GLObject baseGLObject) {
        if (textureLoader != null) {
            textureLoader.unbind();
        } else {
            glBindTexture(GL_TEXTURE_2D, 0);
        }

        super.pop(baseGLObject);
    }

    @Override
    public void push(GLObject baseGLObject) {
        GLStateCache.enable(GL_TEXTURE_2D);
        glActiveTexture(GL_TEXTURE0);
        if (textureLoader != null) {
            textureLoader.bind();
        } else {
            glBindTexture(GL_TEXTURE_2D, textureId);
        }

        super.push(baseGLObject);
    }

    @Override
    public void frameBufferPush(GLObject baseGLObject) {
        getFrameBuffer().bindFrameBuffer();

        super.frameBufferPush(baseGLObject);
    }

    @Override
    public void frameBufferPop(GLObject baseGLObject) {
        getFrameBuffer().unbindFrameBuffer();

        super.frameBufferPop(baseGLObject);
    }

    @Override
    public void frameBuffer(GLObject baseGLObject) {
        getFrameBuffer().drawFrameBuffer();

        super.frameBuffer(baseGLObject);
    }

    @Override
    public void setUniform(GLObject baseGLObject) {
        getFrameBuffer().getShaderProgramFBO().setUniform("blendMode", blendType.getId());

        super.setUniform(baseGLObject);
    }

    public TextureCache getTextureCache() {
        return textureCache;
    }

    public void setTextureCache(TextureCache textureCache) {
        this.textureCache = textureCache;
    }

    public TextureLoader getTextureLoader() {
        return textureLoader;
    }

    public void setTextureLoader(TextureLoader textureLoader) {
        this.textureLoader = textureLoader;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public BlendType getBlendType() {
        return blendType;
    }

    public void setBlendType(BlendType blendType) {
        this.blendType = blendType;
    }
}