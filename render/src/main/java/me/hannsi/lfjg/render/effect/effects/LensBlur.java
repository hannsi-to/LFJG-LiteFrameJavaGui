package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.core.utils.reflection.location.Location;

/**
 * Class representing a Lens Blur effect in OpenGL.
 */
@Getter
@Setter
public class LensBlur extends EffectBase {
    private float range = 20f;
    private float intensity = 1.5f;
    private float sigma = 10f;
    private int radialSteps = 8;
    private int angularSamples = 64;

    LensBlur() {
        super(Location.fromResource("shader/frameBuffer/filter/LensBlur.fsh"), true, 20, "LensBlur");
    }

    public static LensBlur createLensBlur() {
        return new LensBlur();
    }

    public LensBlur range(float range) {
        this.range = range;
        return this;
    }

    public LensBlur range(double range) {
        this.range = (float) range;
        return this;
    }

    public LensBlur intensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public LensBlur intensity(double intensity) {
        this.intensity = (float) intensity;
        return this;
    }

    public LensBlur sigma(float sigma) {
        this.sigma = sigma;
        return this;
    }

    public LensBlur sigma(double sigma) {
        this.sigma = (float) sigma;
        return this;
    }

    public LensBlur radialSteps(int radialSteps) {
        this.radialSteps = radialSteps;
        return this;
    }

    public LensBlur angularSamples(int angularSamples) {
        this.angularSamples = angularSamples;
        return this;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("sigma", sigma);
        getFrameBuffer().getShaderProgramFBO().setUniform("radialSteps", radialSteps);
        getFrameBuffer().getShaderProgramFBO().setUniform("angularSamples", angularSamples);

        super.setUniform(baseGLObject);
    }

}