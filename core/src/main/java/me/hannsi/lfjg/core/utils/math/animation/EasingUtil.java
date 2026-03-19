package me.hannsi.lfjg.core.utils.math.animation;

import me.hannsi.lfjg.core.utils.Util;
import me.hannsi.lfjg.core.utils.math.MathHelper;

public class EasingUtil extends Util {
    public long current;
    public float currentValue;
    public boolean reverse;
    public Easing easing;

    public EasingUtil(Easing easing) {
        this.easing = easing;
    }

    public static float easeOutBounce(float value) {
        float n1 = 7.5625f;
        float d1 = 2.75f;

        if (value < 1f / d1) {
            return n1 * value * value;
        } else if (value < 2f / d1) {
            return n1 * (value -= 1.5f / d1) * value + 0.75f;
        } else if (value < 2.5f / d1) {
            return n1 * (value -= 2.25f / d1) * value + 0.9375f;
        } else {
            return n1 * (value -= 2.625f / d1) * value + 0.984375f;
        }
    }

    public void reset() {
        current = System.currentTimeMillis();
    }

    public float get(long millis) {
        if (easing == null) {
            throw new IllegalStateException("Easing not set");
        }

        float clamped = MathHelper.clamp((float) (System.currentTimeMillis() - current) / millis, 0F, 1F);
        return currentValue = reverse ? 1 - easing.ease(clamped) : easing.ease(clamped);
    }

    public BezierPoint getMultiDimensions(long millis) {
        if (easing == null) {
            throw new IllegalStateException("Easing not set");
        }

        MultiBezierEasing multiBezier;
        if (easing instanceof MultiBezierEasing) {
            multiBezier = (MultiBezierEasing) easing;
        } else {
            throw new IllegalStateException("MultiBezierEasing not set");
        }

        float clamped = MathHelper.clamp((float) (System.currentTimeMillis() - current) / millis, 0F, 1F);
        return reverse ? multiBezier.evaluateDeCasteljau(clamped).reverse() : multiBezier.evaluateDeCasteljau(clamped);
    }

    public boolean done(float value) {
        if (reverse) {
            return value <= 0.0001f && value >= -0.0001f;
        } else {
            return value <= 1.0001f && value >= 0.9999f;
        }
    }
}