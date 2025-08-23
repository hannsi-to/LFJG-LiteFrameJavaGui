package me.hannsi.lfjg.core.utils.math.animation;

import me.hannsi.lfjg.core.utils.Util;
import me.hannsi.lfjg.core.utils.math.MathHelper;

public class EasingUtil extends Util {
    public long current;
    public boolean reverse;
    public Easing easing;

    public EasingUtil(Easing easing) {
        this.easing = easing;
    }

    public static double easeOutBounce(float value) {
        double n1 = 7.5625;
        double d1 = 2.75;

        if (value < 1 / d1) {
            return n1 * value * value;
        } else if (value < 2 / d1) {
            return n1 * (value -= (float) (1.5 / d1)) * value + 0.75;
        } else if (value < 2.5 / d1) {
            return n1 * (value -= (float) (2.25 / d1)) * value + 0.9375;
        } else {
            return n1 * (value -= (float) (2.625 / d1)) * value + 0.984375;
        }
    }

    public void reset() {
        current = System.currentTimeMillis();
    }

    public float get(long millis) {
        float clamped = MathHelper.clamp((float) (System.currentTimeMillis() - current) / millis, 0F, 1F);
        return (float) (reverse ? 1 - easing.ease(clamped) : easing.ease(clamped));
    }

    public boolean done(float value) {
        if (reverse) {
            return value <= 0.0001f && value >= -0.0001f;
        } else {
            return value <= 1.0001f && value >= 0.9999f;
        }
    }
}