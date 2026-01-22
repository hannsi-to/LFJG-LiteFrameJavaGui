package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;

public class LensBlur extends EffectBase {
    private float range = 20f;
    private float intensity = 1.5f;
    private float sigma = 10f;
    private int radialSteps = 8;
    private int angularSamples = 64;

    LensBlur(String name) {
        super(name);
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
}
