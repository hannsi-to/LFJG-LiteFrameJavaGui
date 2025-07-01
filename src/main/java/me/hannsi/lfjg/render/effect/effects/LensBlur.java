package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.location.Location;

/**
 * Class representing a Lens Blur effect in OpenGL.
 */
@Getter
@Setter
public class LensBlur extends EffectBase {
    /**
     * -- SETTER --
     * Sets the range of the blur.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the range of the blur.
     *
     * @param range the range of the blur
     * @return the range of the blur
     */
    private float range;
    /**
     * -- SETTER --
     * Sets the intensity of the blur.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the intensity of the blur.
     *
     * @param intensity the intensity of the blur
     * @return the intensity of the blur
     */
    private float intensity;

    /**
     * Constructs a new LensBlur effect with the specified parameters.
     *
     * @param range     the range of the blur
     * @param intensity the intensity of the blur
     */
    public LensBlur(float range, float intensity) {
        super(Location.fromResource("shader/frameBuffer/filter/LensBlur.fsh"), true, 20, "LensBlur");

        this.range = range;
        this.intensity = intensity;
    }

    /**
     * Constructs a new LensBlur effect with the specified parameters.
     *
     * @param range     the range of the blur
     * @param intensity the intensity of the blur
     */
    public LensBlur(double range, double intensity) {
        this((float) range, (float) intensity);
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
        getFrameBuffer().getShaderProgramFBO().setUniform1f("range", range);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("intensity", intensity);

        super.setUniform(baseGLObject);
    }

}