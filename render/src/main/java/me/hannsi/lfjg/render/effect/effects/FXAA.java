package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;

public class FXAA extends EffectBase {
    private boolean useAlpha = true;

    FXAA(String name) {
        super(name);
    }

    public static FXAA createFXAA(String name) {
        return new FXAA(name);
    }

    public FXAA useAlpha(boolean useAlpha) {
        this.useAlpha = useAlpha;
        return this;
    }
}
