package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

import static me.hannsi.lfjg.render.LFJGRenderContext.SHADER_PROGRAM;

public class LensBlur extends EffectBase {
    private float range = 20f;
    private float intensity = 1.5f;
    private float sigma = 10f;
    private int radialSteps = 8;
    private int angularSamples = 64;

    LensBlur(String name) {
        super(name, false);
    }

    public static LensBlur createLensBlur(String name) {
        return new LensBlur(name);
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
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        SHADER_PROGRAM.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.LENS_BLUR.getId());
        SHADER_PROGRAM.setUniform("lensBlurRange", UploadUniformType.ON_CHANGE, range);
        SHADER_PROGRAM.setUniform("lensBlurIntensity", UploadUniformType.ON_CHANGE, intensity);
        SHADER_PROGRAM.setUniform("lensBlurSigma", UploadUniformType.ON_CHANGE, sigma);
        SHADER_PROGRAM.setUniform("lensBlurRadialSteps", UploadUniformType.ON_CHANGE, radialSteps);
        SHADER_PROGRAM.setUniform("lensBlurAngularSamples", UploadUniformType.ON_CHANGE, angularSamples);

        super.drawFrameBuffer(latestFrameBuffer);
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
