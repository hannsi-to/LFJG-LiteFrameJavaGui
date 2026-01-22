package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.render.effect.system.EffectBase;

public class LuminanceKey extends EffectBase {
    private float threshold = 0.6f;
    private float blurAmount = 0.1f;
    private LuminanceMode luminanceMode = LuminanceMode.BOTH;

    LuminanceKey(String name) {
        super(name);
    }

    public static LuminanceKey createLuminanceKey(String name) {
        return new LuminanceKey(name);
    }

    public LuminanceKey threshold(float threshold) {
        this.threshold = threshold;
        return this;
    }

    public LuminanceKey threshold(double threshold) {
        this.threshold = (float) threshold;
        return this;
    }

    public LuminanceKey blurAmount(float blurAmount) {
        this.blurAmount = blurAmount;
        return this;
    }

    public LuminanceKey blurAmount(double blurAmount) {
        this.blurAmount = (float) blurAmount;
        return this;
    }

    public LuminanceKey luminanceMode(LuminanceMode luminanceMode) {
        this.luminanceMode = luminanceMode;
        return this;
    }

    public enum LuminanceMode implements IEnumTypeBase {
        ONLY_DARK("OnlyDark", 0),
        ONLY_Light("OnlyLight", 1),
        BOTH("Both", 2);

        final String name;
        final int id;

        LuminanceMode(String name, int id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
