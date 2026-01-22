package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;

public class Inversion extends EffectBase {
    private boolean flipVertical = true;
    private boolean flipHorizontal = true;
    private boolean invertBrightness = true;
    private boolean invertHue = true;
    private boolean invertAlpha = false;

    Inversion(String name) {
        super(name);
    }

    public static Inversion createInversion(String name) {
        return new Inversion(name);
    }

    public Inversion flipVertical(boolean flipVertical) {
        this.flipVertical = flipVertical;
        return this;
    }

    public Inversion flipHorizontal(boolean flipHorizontal) {
        this.flipHorizontal = flipHorizontal;
        return this;
    }

    public Inversion invertBrightness(boolean invertBrightness) {
        this.invertBrightness = invertBrightness;
        return this;
    }

    public Inversion invertHue(boolean invertHue) {
        this.invertHue = invertHue;
        return this;
    }

    public Inversion invertAlpha(boolean invertAlpha) {
        this.invertAlpha = invertAlpha;
        return this;
    }
}
