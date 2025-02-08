package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.utils.graphics.image.TextureLoader;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL30;

/**
 * Class representing a Texture effect in OpenGL.
 */
public class Texture extends EffectBase {
    private TextureCache textureCache;
    private ResourcesLocation resourcesLocation;
    private TextureLoader textureLoader;
    private int textureId;

    /**
     * Constructs a new Texture effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param textureCache the texture cache to be used
     * @param resourcesLocation the location of the resources
     */
    public Texture(Vector2f resolution, TextureCache textureCache, ResourcesLocation resourcesLocation) {
        super(resolution, 3, "Texture", (Class<GLObject>) null);

        this.textureCache = textureCache;
        this.resourcesLocation = resourcesLocation;
        this.textureLoader = textureCache.getTexture(resourcesLocation);
    }

    /**
     * Constructs a new Texture effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param textureId the ID of the texture
     */
    public Texture(Vector2f resolution, int textureId) {
        super(resolution, 3, "Texture", (Class<GLObject>) null);

        this.textureId = textureId;
    }

    /**
     * Pops the transformation from the stack and unbinds the texture.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void pop(GLObject baseGLObject) {
        if (textureLoader != null) {
            textureLoader.unbind();
        } else {
            GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
        }

        super.pop(baseGLObject);
    }

    /**
     * Pushes the transformation onto the stack and binds the texture.
     *
     * @param baseGLObject the base GL object
     */
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

    /**
     * Pushes the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBufferPush(GLObject baseGLObject) {
        getFrameBuffer().bindFrameBuffer();

        super.frameBufferPush(baseGLObject);
    }

    /**
     * Pops the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBufferPop(GLObject baseGLObject) {
        getFrameBuffer().unbindFrameBuffer();

        super.frameBufferPop(baseGLObject);
    }

    /**
     * Draws the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBuffer(GLObject baseGLObject) {
        getFrameBuffer().drawFrameBuffer();

        super.frameBuffer(baseGLObject);
    }

    /**
     * Sets the uniform variables for the shader program.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void setUniform(GLObject baseGLObject) {
        super.setUniform(baseGLObject);
    }

    /**
     * Gets the texture cache.
     *
     * @return the texture cache
     */
    public TextureCache getTextureCache() {
        return textureCache;
    }

    /**
     * Sets the texture cache.
     *
     * @param textureCache the texture cache
     */
    public void setTextureCache(TextureCache textureCache) {
        this.textureCache = textureCache;
    }

    /**
     * Gets the resources location.
     *
     * @return the resources location
     */
    public ResourcesLocation getResourcesLocation() {
        return resourcesLocation;
    }

    /**
     * Sets the resources location.
     *
     * @param resourcesLocation the resources location
     */
    public void setResourcesLocation(ResourcesLocation resourcesLocation) {
        this.resourcesLocation = resourcesLocation;
    }

    /**
     * Gets the texture loader.
     *
     * @return the texture loader
     */
    public TextureLoader getTextureLoader() {
        return textureLoader;
    }

    /**
     * Sets the texture loader.
     *
     * @param textureLoader the texture loader
     */
    public void setTextureLoader(TextureLoader textureLoader) {
        this.textureLoader = textureLoader;
    }
}