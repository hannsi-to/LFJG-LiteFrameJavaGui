package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.effect.system.EffectBase;

public class ColorChanger extends EffectBase {
    private boolean alpha = false;
    private Color targetColor = Color.YELLOW;
    private Color newColor = Color.RED;

    ColorChanger(String name) {
        super(name);
    }

    public static ColorChanger createColorChanger(String name) {
        return new ColorChanger(name);
    }

    public ColorChanger alpha(boolean alpha) {
        this.alpha = alpha;
        return this;
    }

    public ColorChanger targetColor(Color targetColor) {
        this.targetColor = targetColor;
        return this;
    }

    public ColorChanger newColor(Color newColor) {
        this.newColor = newColor;
        return this;
    }
}
