package me.hannsi.lfjg.utils.math.animation;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.utils.Util;
import me.hannsi.lfjg.utils.math.MathHelper;

/**
 * Utility class for easing functions and animations.
 */
@Getter
@Setter
public class EasingUtil extends Util {
    /**
     * -- SETTER --
     *  Sets the current time.
     *
     *
     * -- GETTER --
     *  Gets the current time.
     *
     @param current the new current time
      * @return the current time
     */
    public long current;
    /**
     * -- SETTER --
     *  Sets the reverse mode of the animation.
     *
     *
     * -- GETTER --
     *  Checks if the animation is in reverse mode.
     *
     @param reverse true to set the animation to reverse mode, false otherwise
      * @return true if the animation is in reverse mode, false otherwise
     */
    public boolean reverse;
    /**
     * -- SETTER --
     *  Sets the easing function.
     *
     *
     * -- GETTER --
     *  Gets the easing function.
     *
     @param easing the new easing function
      * @return the easing function
     */
    public Easing easing;

    /**
     * Constructs an EasingUtil instance with the specified easing function.
     *
     * @param easing the easing function to use
     */
    public EasingUtil(Easing easing) {
        this.easing = easing;
    }

    /**
     * Easing function for a bounce easing out.
     *
     * @param value the input value
     * @return the eased value
     */
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

    /**
     * Resets the current time to the current system time.
     */
    public void reset() {
        current = System.currentTimeMillis();
    }

    /**
     * Gets the eased value based on the elapsed time and the specified duration.
     *
     * @param millis the duration in milliseconds
     * @return the eased value
     */
    public float get(long millis) {
        float clamped = MathHelper.clamp((float) (System.currentTimeMillis() - current) / millis, 0F, 1F);
        return (float) (reverse ? 1 - easing.ease(clamped) : easing.ease(clamped));
    }

    /**
     * Checks if the animation is done based on the current value.
     *
     * @param value the current value
     * @return true if the animation is done, false otherwise
     */
    public boolean done(float value) {
        if (reverse) {
            return value <= 0.0001f && value >= -0.0001f;
        } else {
            return value <= 1.0001f && value >= 0.9999f;
        }
    }

}