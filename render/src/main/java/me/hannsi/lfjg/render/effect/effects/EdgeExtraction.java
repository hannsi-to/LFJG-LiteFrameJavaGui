package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.effect.system.EffectBase;

public class EdgeExtraction extends EffectBase {
    private float edgeStrength = 0.5f;
    private float threshold = 0.1f;
    private boolean enableLuminanceEdge = false;
    private boolean enableAlphaEdge = true;
    private Color edgeColor = Color.BLUE;

    EdgeExtraction(String name) {
        super(name);
    }

    public static EdgeExtraction createEdgeExtraction(String name) {
        return new EdgeExtraction(name);
    }

    public EdgeExtraction edgeStrength(float edgeStrength) {
        this.edgeStrength = edgeStrength;
        return this;
    }

    public EdgeExtraction edgeStrength(double edgeStrength) {
        this.edgeStrength = (float) edgeStrength;
        return this;
    }

    public EdgeExtraction threshold(float threshold) {
        this.threshold = threshold;
        return this;
    }

    public EdgeExtraction threshold(double threshold) {
        this.threshold = (float) threshold;
        return this;
    }

    public EdgeExtraction enableLuminanceEdge(boolean enableLuminanceEdge) {
        this.enableLuminanceEdge = enableLuminanceEdge;
        return this;
    }

    public EdgeExtraction enableAlphaEdge(boolean enableAlphaEdge) {
        this.enableAlphaEdge = enableAlphaEdge;
        return this;
    }

    public EdgeExtraction edgeColor(Color edgeColor) {
        this.edgeColor = edgeColor;
        return this;
    }
}
