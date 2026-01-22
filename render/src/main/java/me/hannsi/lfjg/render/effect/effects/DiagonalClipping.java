package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.render.effect.system.EffectBase;

public class DiagonalClipping extends EffectBase {
    private float centerX = 500;
    private float centerY = 500;
    private float clipAngle = MathHelper.toRadians(45);
    private float blurWidth = 0f;
    private boolean invertClip = false;

    DiagonalClipping(String name) {
        super(name);
    }

    public static DiagonalClipping createDiagonalClipping(String name) {
        return new DiagonalClipping(name);
    }

    public DiagonalClipping centerX(float centerX) {
        this.centerX = centerX;
        return this;
    }

    public DiagonalClipping centerX(double centerX) {
        this.centerX = (float) centerX;
        return this;
    }

    public DiagonalClipping centerY(float centerY) {
        this.centerY = centerY;
        return this;
    }

    public DiagonalClipping centerY(double centerY) {
        this.centerY = (float) centerY;
        return this;
    }

    public DiagonalClipping clipAngleRadian(float clipAngleRadian) {
        this.clipAngle = clipAngleRadian;
        return this;
    }

    public DiagonalClipping clipAngleRadian(double clipAngleRadian) {
        this.clipAngle = (float) clipAngleRadian;
        return this;
    }

    public DiagonalClipping clipAngleDegree(float clipAngleDegree) {
        this.clipAngle = MathHelper.toRadians(clipAngleDegree);
        return this;
    }

    public DiagonalClipping blurWidth(float blurWidth) {
        this.blurWidth = blurWidth;
        return this;
    }

    public DiagonalClipping blurWidth(double blurWidth) {
        this.blurWidth = (float) blurWidth;
        return this;
    }

    public DiagonalClipping invertClip(boolean invertClip) {
        this.invertClip = invertClip;
        return this;
    }
}
