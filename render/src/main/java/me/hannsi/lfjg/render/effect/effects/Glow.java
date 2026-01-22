package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.effect.system.EffectBase;

public class Glow extends EffectBase {
    private float intensity = 1.0f;
    private float threshold = 0.7f;
    private float spread = 3.0f;
    private boolean useOriginalColor = true;
    private Color glowColor = Color.GOLD;
    private boolean glowOnly = false;

    Glow(String name) {
        super(name);
    }

    public static Glow createGlow(String name) {
        return new Glow(name);
    }

    public Glow intensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public Glow intensity(double intensity) {
        this.intensity = (float) intensity;
        return this;
    }

    public Glow threshold(float threshold) {
        this.threshold = threshold;
        return this;
    }

    public Glow threshold(double threshold) {
        this.threshold = (float) threshold;
        return this;
    }

    public Glow spread(float spread) {
        this.spread = spread;
        return this;
    }

    public Glow spread(double spread) {
        this.spread = (float) spread;
        return this;
    }

    public Glow useOriginalColor(boolean useOriginalColor) {
        this.useOriginalColor = useOriginalColor;
        return this;
    }

    public Glow glowColor(Color glowColor) {
        this.glowColor = glowColor;
        return this;
    }

    public Glow glowOnly(boolean glowOnly) {
        this.glowOnly = glowOnly;
        return this;
    }
}
