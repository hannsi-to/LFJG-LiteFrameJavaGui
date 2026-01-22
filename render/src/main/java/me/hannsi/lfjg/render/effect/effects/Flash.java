package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.render.effect.system.EffectBase;

public class Flash extends EffectBase {
    private float intensity = 0.08f;
    private float x = 1920 / 2f;
    private float y = 1080 / 2f;
    private FlashBlendMode flashBlendMode = FlashBlendMode.BACKWARD_SYNTHESIS;
    private Color lightColor = Color.FOREST_GREEN;

    Flash(String name) {
        super(name);
    }

    public static Flash createFlash(String name) {
        return new Flash(name);
    }

    public Flash intensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public Flash intensity(double intensity) {
        this.intensity = (float) intensity;
        return this;
    }

    public Flash x(float x) {
        this.x = x;
        return this;
    }

    public Flash x(double x) {
        this.x = (float) x;
        return this;
    }

    public Flash y(float y) {
        this.y = y;
        return this;
    }

    public Flash y(double y) {
        this.y = (float) y;
        return this;
    }

    public Flash flashBlendMode(FlashBlendMode flashBlendMode) {
        this.flashBlendMode = flashBlendMode;
        return this;
    }

    public Flash lightColor(Color lightColor) {
        this.lightColor = lightColor;
        return this;
    }

    public enum FlashBlendMode implements IEnumTypeBase {
        FORWARD_SYNTHESIS("ForwardSynthesis", 0),
        BACKWARD_SYNTHESIS("BackwardSynthesis", 1),
        LIGHT_COMPONENT_ONLY("LightComponentOnly", 2),
        ORIGINAL_COLOR("OriginalColor", 3);

        final String name;
        final int id;

        FlashBlendMode(String name, int id) {
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
