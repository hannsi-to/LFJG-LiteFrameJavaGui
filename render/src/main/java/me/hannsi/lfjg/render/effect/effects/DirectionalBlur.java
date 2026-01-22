package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.render.effect.system.EffectBase;

public class DirectionalBlur extends EffectBase {
    private float radius = 10f;
    private float angle = MathHelper.toRadians(45);

    DirectionalBlur(String name) {
        super(name);
    }

    public static DirectionalBlur createDirectionBlur(String name) {
        return new DirectionalBlur(name);
    }

    public DirectionalBlur radius(float radius) {
        this.radius = radius;
        return this;
    }

    public DirectionalBlur radius(double radius) {
        this.radius = (float) radius;
        return this;
    }

    public DirectionalBlur angleRadian(float angleRadian) {
        this.angle = angleRadian;
        return this;
    }

    public DirectionalBlur angleRadian(double angleRadian) {
        this.angle = (float) angleRadian;
        return this;
    }

    public DirectionalBlur angleDegree(float angleDegree) {
        this.angle = MathHelper.toRadians(angleDegree);
        return this;
    }
}
