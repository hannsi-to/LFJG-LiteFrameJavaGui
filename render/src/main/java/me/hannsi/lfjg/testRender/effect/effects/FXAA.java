package me.hannsi.lfjg.testRender.effect.effects;

import me.hannsi.lfjg.testRender.effect.system.EffectBase;

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
