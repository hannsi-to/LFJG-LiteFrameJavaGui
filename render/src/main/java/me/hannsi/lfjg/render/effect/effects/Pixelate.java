package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;

public class Pixelate extends EffectBase {
    private float mosaicSize = 10f;

    Pixelate(String name) {
        super(name);
    }

    public static Pixelate createPixelate(String name) {
        return new Pixelate(name);
    }

    public Pixelate mosaicSize(float mosaicSize) {
        this.mosaicSize = mosaicSize;
        return this;
    }

    public Pixelate mosaicSize(double mosaicSize) {
        this.mosaicSize = (float) mosaicSize;
        return this;
    }
}
