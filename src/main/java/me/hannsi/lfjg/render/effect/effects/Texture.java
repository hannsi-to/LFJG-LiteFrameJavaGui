package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.utils.graphics.image.TextureLoader;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.type.types.BlendType;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * Class representing a Texture effect in OpenGL.
 */
@Setter
@Getter
public class Texture extends EffectBase {
    /**
     * -- GETTER --
     * Gets the texture cache.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the texture cache.
     *
     * @return the texture cache
     * @param textureCache the texture cache
     */
    private TextureCache textureCache;
    private Location path;
    private BlendType blendType;
    /**
     * -- GETTER --
     * Gets the texture loader.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the texture loader.
     *
     * @return the texture loader
     * @param textureLoader the texture loader
     */
    private TextureLoader textureLoader;
    private int textureId;

    /**
     * Constructs a new Texture effect with the specified parameters.
     *
     * @param textureCache the texture cache to be used
     * @param path         the location of the resources
     */
    public Texture(TextureCache textureCache, Location path, BlendType blendType) {
        super(3, "Texture");

        this.textureCache = textureCache;
        this.path = path;
        this.blendType = blendType;
        this.textureLoader = textureCache.getTexture(path);
    }

    /**
     * Constructs a new Texture effect with the specified parameters.
     *
     * @param textureId the ID of the texture
     */
    public Texture(int textureId) {
        super(3, "Texture", (Class<GLObject>) null);

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
            glBindTexture(GL_TEXTURE_2D, 0);
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
        GLStateCache.enable(GL_TEXTURE_2D);
        glActiveTexture(GL_TEXTURE0);
        if (textureLoader != null) {
            textureLoader.bind();
        } else {
            glBindTexture(GL_TEXTURE_2D, textureId);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("blendMode", blendType.getId());

        super.setUniform(baseGLObject);
    }

    public Location getFileLocation() {
        return path;
    }

}