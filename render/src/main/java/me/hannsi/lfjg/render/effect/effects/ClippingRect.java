package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;

public class ClippingRect extends EffectBase {
    private float x1 = 0f;
    private float y1 = 0f;
    private float x2 = 0f;
    private float y2 = 0f;
    private boolean invert = false;

    ClippingRect(String name) {
        super(name);
    }

    public static ClippingRect createClippingRect(String name) {
        return new ClippingRect(name);
    }

    public ClippingRect x1(float x1) {
        this.x1 = x1;
        return this;
    }

    public ClippingRect x1(double x1) {
        this.x1 = (float) x1;
        return this;
    }

    public ClippingRect x1(int x1) {
        this.x1 = x1;
        return this;
    }

    public ClippingRect y1(float y1) {
        this.y1 = y1;
        return this;
    }

    public ClippingRect y1(double y1) {
        this.y1 = (float) y1;
        return this;
    }

    public ClippingRect y1(int y1) {
        this.y1 = y1;
        return this;
    }

    public ClippingRect x2(float x2) {
        this.x2 = x2;
        return this;
    }

    public ClippingRect x2(double x2) {
        this.x2 = (float) x2;
        return this;
    }

    public ClippingRect x2(int x2) {
        this.x2 = x2;
        return this;
    }

    public ClippingRect y2(float y2) {
        this.y2 = y2;
        return this;
    }

    public ClippingRect y2(double y2) {
        this.y2 = (float) y2;
        return this;
    }

    public ClippingRect y2(int y2) {
        this.y2 = y2;
        return this;
    }

    public ClippingRect invert(boolean invert) {
        this.invert = invert;
        return this;
    }
}
