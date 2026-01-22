package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.effect.system.EffectBase;

public class ChromaKey extends EffectBase {
    private final Color colorAdjustment = Color.of(0, 0, 0, 0);
    private Color chromaKeyColor = Color.GREEN;
    private float hueRange = 0.1f;
    private float saturationRange = 0.3f;
    private float boundarySmoothness = 0.05f;

    ChromaKey(String name) {
        super(name);
    }

    public static ChromaKey createChromaKey(String name) {
        return new ChromaKey(name);
    }

    public ChromaKey chromaKeyColor(Color chromaKeyColor) {
        this.chromaKeyColor = chromaKeyColor;
        return this;
    }

    public ChromaKey hueRange(float hueRange) {
        this.hueRange = hueRange;
        return this;
    }

    public ChromaKey hueRange(double hueRange) {
        this.hueRange = (float) hueRange;
        return this;
    }

    public ChromaKey hueRange(int hueRange) {
        this.hueRange = hueRange / 255f;
        return this;
    }

    public ChromaKey saturationRange(float saturationRange) {
        this.saturationRange = saturationRange;
        return this;
    }

    public ChromaKey saturationRange(double saturationRange) {
        this.saturationRange = (float) saturationRange;
        return this;
    }

    public ChromaKey saturationRange(int saturationRange) {
        this.saturationRange = saturationRange / 255f;
        return this;
    }

    public ChromaKey boundarySmoothness(float boundarySmoothness) {
        this.boundarySmoothness = boundarySmoothness;
        return this;
    }

    public ChromaKey boundarySmoothness(double boundarySmoothness) {
        this.boundarySmoothness = (float) boundarySmoothness;
        return this;
    }

    public ChromaKey colorAdjustment(Color colorAdjustment) {
        this.chromaKeyColor = colorAdjustment;
        return this;
    }
}
