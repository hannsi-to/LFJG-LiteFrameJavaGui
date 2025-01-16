package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.utils.graphics.image.TextureLoader;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL30;

public class Texture extends EffectBase {
    private TextureCache textureCache;
    private ResourcesLocation resourcesLocation;
    private TextureLoader textureLoader;
    private int textureId;

    public Texture(Vector2f resolution, TextureCache textureCache, ResourcesLocation resourcesLocation) {
        super(resolution, 3, "Texture", (Class<GLObject>) null);

        this.textureCache = textureCache;
        this.resourcesLocation = resourcesLocation;
        this.textureLoader = textureCache.getTexture(resourcesLocation);
    }

    public Texture(Vector2f resolution, int textureId) {
        super(resolution, 3, "Texture", (Class<GLObject>) null);

        this.textureId = textureId;
    }

    @Override
    public void pop(GLObject baseGLObject) {
        if (textureLoader != null) {
            textureLoader.unbind();
        } else {
            GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
        }

        super.pop(baseGLObject);
    }

    @Override
    public void push(GLObject baseGLObject) {
        baseGLObject.getGlUtil().addGLTarget(GL30.GL_TEXTURE_2D);
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        if (textureLoader != null) {
            textureLoader.bind();
        } else {
            GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
        }

        super.push(baseGLObject);
    }

    public TextureCache getTextureCache() {
        return textureCache;
    }

    public void setTextureCache(TextureCache textureCache) {
        this.textureCache = textureCache;
    }

    public ResourcesLocation getResourcesLocation() {
        return resourcesLocation;
    }

    public void setResourcesLocation(ResourcesLocation resourcesLocation) {
        this.resourcesLocation = resourcesLocation;
    }

    public TextureLoader getTextureLoader() {
        return textureLoader;
    }

    public void setTextureLoader(TextureLoader textureLoader) {
        this.textureLoader = textureLoader;
    }
}
