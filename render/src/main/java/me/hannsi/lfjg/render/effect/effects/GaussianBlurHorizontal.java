package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;

public class GaussianBlurHorizontal extends EffectBase {
    private float radiusX = 30f;

    GaussianBlurHorizontal(String name) {
        super(name);
    }

    public static GaussianBlurHorizontal createGaussianBlurHorizontal(String name) {
        return new GaussianBlurHorizontal(name);
    }

    public GaussianBlurHorizontal radiusX(float radiusX) {
        this.radiusX = radiusX;
        return this;
    }

    public GaussianBlurHorizontal radiusX(double radiusX) {
        this.radiusX = (float) radiusX;
        return this;
    }
}
