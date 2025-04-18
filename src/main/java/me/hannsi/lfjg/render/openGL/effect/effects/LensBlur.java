package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;

/**
 * Class representing a Lens Blur effect in OpenGL.
 */
public class LensBlur extends EffectBase {
    private float range;
    private float intensity;

    /**
     * Constructs a new LensBlur effect with the specified parameters.
     *
     * @param range     the range of the blur
     * @param intensity the intensity of the blur
     */
    public LensBlur(float range, float intensity) {
        super(new ResourcesLocation("shader/frameBuffer/filter/LensBlur.fsh"), true, 20, "LensBlur");

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
        getFrameBuffer().getShaderProgramFBO().setUniform("range", range);
        getFrameBuffer().getShaderProgramFBO().setUniform("intensity", intensity);

        super.setUniform(baseGLObject);
    }

    /**
     * Gets the range of the blur.
     *
     * @return the range of the blur
     */
    public float getRange() {
        return range;
    }

    /**
     * Sets the range of the blur.
     *
     * @param range the range of the blur
     */
    public void setRange(float range) {
        this.range = range;
    }

    /**
     * Gets the intensity of the blur.
     *
     * @return the intensity of the blur
     */
    public float getIntensity() {
        return intensity;
    }

    /**
     * Sets the intensity of the blur.
     *
     * @param intensity the intensity of the blur
     */
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
}