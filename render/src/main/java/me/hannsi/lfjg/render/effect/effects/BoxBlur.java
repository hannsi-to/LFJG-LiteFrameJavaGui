package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;

public class BoxBlur extends EffectBase {
    private int kernelX = 10;
    private int kernelY = 10;

    BoxBlur(String name) {
        super(name);
    }

    public static BoxBlur createBoxBlur(String name) {
        return new BoxBlur(name);
    }

    public BoxBlur kernelX(int kernelX) {
        this.kernelX = kernelX;
        return this;
    }

    public BoxBlur kernelY(int kernelY) {
        this.kernelY = kernelY;
        return this;
    }
}
