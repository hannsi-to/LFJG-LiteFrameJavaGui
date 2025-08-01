package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

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
        getFrameBuffer().getShaderProgramFBO().setUniform("range", UploadUniformType.ON_CHANGE, range);
        getFrameBuffer().getShaderProgramFBO().setUniform("intensity", UploadUniformType.ON_CHANGE, intensity);
        getFrameBuffer().getShaderProgramFBO().setUniform("sigma", UploadUniformType.ON_CHANGE, sigma);
        getFrameBuffer().getShaderProgramFBO().setUniform("radialSteps", UploadUniformType.ON_CHANGE, radialSteps);
        getFrameBuffer().getShaderProgramFBO().setUniform("angularSamples", UploadUniformType.ON_CHANGE, angularSamples);

        super.setUniform(baseGLObject);
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public float getSigma() {
        return sigma;
    }

    public void setSigma(float sigma) {
        this.sigma = sigma;
    }

    public int getRadialSteps() {
        return radialSteps;
    }

    public void setRadialSteps(int radialSteps) {
        this.radialSteps = radialSteps;
    }

    public int getAngularSamples() {
        return angularSamples;
    }

    public void setAngularSamples(int angularSamples) {
        this.angularSamples = angularSamples;
    }
}