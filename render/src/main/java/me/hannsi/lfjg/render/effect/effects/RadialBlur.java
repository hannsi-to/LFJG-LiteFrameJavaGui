package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;

public class RadialBlur extends EffectBase {
    private float range = 1f;
    private float centerX = 500f;
    private float centerY = 500f;

    RadialBlur(String name) {
        super(name);
    }

    public static RadialBlur createRadialBlur(String name) {
        return new RadialBlur(name);
    }

    public RadialBlur range(float range) {
        this.range = range;
        return this;
    }

    public RadialBlur range(double range) {
        this.range = (float) range;
        return this;
    }

    public RadialBlur centerX(float centerX) {
        this.centerX = centerX;
        return this;
    }

    public RadialBlur centerX(double centerX) {
        this.centerX = (float) centerX;
        return this;
    }

    public RadialBlur centerY(float centerY) {
        this.centerY = centerY;
        return this;
    }

    public RadialBlur centerY(double centerY) {
        this.centerY = (float) centerY;
        return this;
    }
}
