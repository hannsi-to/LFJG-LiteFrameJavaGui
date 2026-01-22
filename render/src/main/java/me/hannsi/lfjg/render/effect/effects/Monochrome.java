package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.effect.system.EffectBase;

public class Monochrome extends EffectBase {
    private float intensity = 1f;
    private Color color = Color.DARK_GRAY;
    private boolean preserveBrightness = true;

    Monochrome(String name) {
        super(name);
    }

    public static Monochrome createMonochrome(String name) {
        return new Monochrome(name);
    }

    public Monochrome intensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public Monochrome intensity(double intensity) {
        this.intensity = (float) intensity;
        return this;
    }

    public Monochrome color(Color color) {
        this.color = color;
        return this;
    }

    public Monochrome preserveBrightness(boolean preserveBrightness) {
        this.preserveBrightness = preserveBrightness;
        return this;
    }
}
