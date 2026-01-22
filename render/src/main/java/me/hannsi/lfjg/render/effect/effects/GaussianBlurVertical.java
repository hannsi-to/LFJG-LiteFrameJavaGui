package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;

public class GaussianBlurVertical extends EffectBase {
    private float radiusX = 30f;

    GaussianBlurVertical(String name) {
        super(name);
    }

    public static GaussianBlurVertical createGaussianBlurVertical(String name) {
        return new GaussianBlurVertical(name);
    }

    public GaussianBlurVertical radiusX(float radiusX) {
        this.radiusX = radiusX;
        return this;
    }

    public GaussianBlurVertical radiusX(double radiusX) {
        this.radiusX = (float) radiusX;
        return this;
    }
}
