package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.image.TextureLoader;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

import static me.hannsi.lfjg.render.LFJGRenderContext.textureCache;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Texture extends EffectBase {
    private TextureLoader textureLoader;
    private BlendType blendType = BlendType.NORMAL;
    private int textureId;

    Texture(String name) {
        super(name, true);
    }

    public static Texture createTexture(String name) {
        return new Texture(name);
    }

    public Texture textureName(String textureName) {
        textureLoader = textureCache.getTexture(textureName);
        return this;
    }

    public Texture textureId(int textureId) {
        this.textureId = textureId;
        return this;
    }

    public Texture blendType(BlendType blendType) {
        this.blendType = blendType;
        return this;
    }

    @Override
    public void push(GLObject baseGLObject) {
        GLStateCache.enable(GL_TEXTURE_2D);
        glActiveTexture(GL_TEXTURE0);
        if (textureLoader != null) {
            textureLoader.bind();
        } else {
            GLStateCache.bindTexture(GL_TEXTURE_2D, textureId);
        }

        baseGLObject.getShaderProgram().setUniform("objectReplaceColor", UploadUniformType.ON_CHANGE, false);
        baseGLObject.getShaderProgram().setUniform("objectBlendMode", UploadUniformType.ON_CHANGE, blendType.getId());

        super.push(baseGLObject);
    }

    @Override
    public void pop(GLObject baseGLObject) {
        super.pop(baseGLObject);
    }

    public TextureLoader getTextureLoader() {
        return textureLoader;
    }

    public void setTextureLoader(TextureLoader textureLoader) {
        this.textureLoader = textureLoader;
    }

    public BlendType getBlendType() {
        return blendType;
    }

    public void setBlendType(BlendType blendType) {
        this.blendType = blendType;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }
}
