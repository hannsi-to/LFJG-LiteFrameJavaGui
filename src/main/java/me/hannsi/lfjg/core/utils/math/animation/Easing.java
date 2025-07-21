package me.hannsi.lfjg.core.utils.math.animation;

import static me.hannsi.lfjg.core.utils.math.MathHelper.*;

/**
 * Interface representing various easing functions for animations.
 */
public interface Easing {
    /**
     * Linear easing function.
     */
    Easing easeLinear = value -> value;

    /**
     * Easing function for a sine wave easing in.
     */
    Easing easeInSine = value -> 1 - cos((value * PI) / 2);

    /**
     * Easing function for a sine wave easing out.
     */
    Easing easeOutSine = value -> sin((value * PI) / 2);

    /**
     * Easing function for a sine wave easing in and out.
     */
    Easing easeInOutSine = value -> -(cos(PI * value) - 1) / 2;

    /**
     * Easing function for a quadratic easing in.
     */
    Easing easeInQuad = value -> value * value;

    /**
     * Easing function for a quadratic easing out.
     */
    Easing easeOutQuad = value -> 1 - (1 - value) * (1 - value);

    /**
     * Easing function for a quadratic easing in and out.
     */
    Easing easeInOutQuad = value -> value < 0.5 ? 2 * value * value : 1 - pow(-2 * value + 2, 2) / 2;

    /**
     * Easing function for a cubic easing in.
     */
    Easing easeInCubic = value -> value * value * value;

    /**
     * Easing function for a cubic easing out.
     */
    Easing easeOutCubic = value -> 1 - pow(1 - value, 3);

    /**
     * Easing function for a cubic easing in and out.
     */
    Easing easeInOutCubic = value -> value < 0.5 ? 4 * value * value * value : 1 - pow(-2 * value + 2, 3) / 2;

    /**
     * Easing function for a quartic easing in.
     */
    Easing easeInQuart = value -> value * value * value * value;

    /**
     * Easing function for a quartic easing out.
     */
    Easing easeOutQuart = value -> 1 - pow(1 - value, 4);

    /**
     * Easing function for a quartic easing in and out.
     */
    Easing easeInOutQuart = value -> value < 0.5 ? 8 * value * value * value * value : 1 - pow(-2 * value + 2, 4) / 2;

    /**
     * Easing function for a quintic easing in.
     */
    Easing easeInQuint = value -> value * value * value * value * value;

    /**
     * Easing function for a quintic easing out.
     */
    Easing easeOutQuint = value -> 1 - pow(1 - value, 5);

    /**
     * Easing function for a quintic easing in and out.
     */
    Easing easeInOutQuint = value -> value < 0.5 ? 16 * value * value * value * value * value : 1 - pow(-2 * value + 2, 5) / 2;

    /**
     * Easing function for an exponential easing in.
     */
    Easing easeInExpo = value -> value == 0 ? 0 : pow(2, 10 * value - 10);

    /**
     * Easing function for an exponential easing out.
     */
    Easing easeOutExpo = value -> value == 1 ? 1 : 1 - pow(2, -10 * value);

    /**
     * Easing function for an exponential easing in and out.
     */
    Easing easeInOutExpo = value -> value == 0 ? 0 : value == 1 ? 1 : value < 0.5 ? pow(2, 20 * value - 10) / 2 : (2 - pow(2, -20 * value + 10)) / 2;

    /**
     * Easing function for a circular easing in.
     */
    Easing easeInCirc = value -> 1 - sqrt(1 - pow(value, 2));

    /**
     * Easing function for a circular easing out.
     */
    Easing easeOutCirc = value -> sqrt(1 - pow(value - 1, 2));

    /**
     * Easing function for a circular easing in and out.
     */
    Easing easeInOutCirc = value -> value < 0.5 ? (1 - sqrt(1 - pow(2 * value, 2))) / 2 : (sqrt(1 - pow(-2 * value + 2, 2)) + 1) / 2;

    /**
     * Easing function for a back easing in.
     */
    Easing easeInBack = value -> (1.70158 + 1) * value * value * value - 1.70158 * value * value;

    /**
     * Easing function for a back easing out.
     */
    Easing easeOutBack = value -> 1 + (1.70158 + 1) * pow(value - 1, 3) + 1.70158 * pow(value - 1, 2);

    /**
     * Easing function for a back easing in and out.
     */
    Easing easeInOutBack = value -> value < 0.5 ? (pow(2 * value, 2) * (((1.70158 * 1.525) + 1) * 2 * value - (1.70158 * 1.525))) / 2 : (pow(2 * value - 2, 2) * (((1.70158 * 1.525) + 1) * (value * 2 - 2) + (1.70158 * 1.525)) + 2) / 2;

    /**
     * Easing function for an elastic easing in.
     */
    Easing easeInElastic = value -> value == 0 ? 0 : value == 1 ? 1 : -pow(2, 10 * value - 10) * sin((value * 10 - 10.75) * (2 * PI) / 3);

    /**
     * Easing function for an elastic easing out.
     */
    Easing easeOutElastic = value -> value == 0 ? 0 : value == 1 ? 1 : pow(2, -10 * value) * sin((value * 10 - 0.75) * (2 * PI) / 3) + 1;

    /**
     * Easing function for an elastic easing in and out.
     */
    Easing easeInOutElastic = value -> value == 0 ? 0 : value == 1 ? 1 : value < 0.5 ? -(pow(2, 20 * value - 10) * sin((20 * value - 11.125) * (2 * PI) / 4.5)) / 2 : (pow(2, -20 * value + 10) * sin((20 * value - 11.125) * (2 * PI) / 4.5)) / 2 + 1;

    /**
     * Easing function for a bounce easing in.
     */
    Easing easeInBounce = value -> {
        float value2 = 1 - value;

        if (value2 < 1 / 2.75) {
            return 7.5625 * value2 * value2;
        } else if (value2 < 2 / 2.75) {
            return 7.5625 * (value2 -= (float) (1.5 / 2.75)) * value2 + 0.75;
        } else if (value2 < 2.5 / 2.75) {
            return 7.5625 * (value2 -= (float) (2.25 / 2.75)) * value2 + 0.9375;
        } else {
            return 7.5625 * (value2 -= (float) (2.625 / 2.75)) * value2 + 0.984375;
        }
    };

    /**
     * Easing function for a bounce easing out.
     */
    Easing easeOutBounce = value -> {
        if (value < 1 / 2.75) {
            return 7.5625 * value * value;
        } else if (value < 2 / 2.75) {
            return 7.5625 * (value -= (float) (1.5 / 2.75)) * value + 0.75;
        } else if (value < 2.5 / 2.75) {
            return 7.5625 * (value -= (float) (2.25 / 2.75)) * value + 0.9375;
        } else {
            return 7.5625 * (value -= (float) (2.625 / 2.75)) * value + 0.984375;
        }
    };

    /**
     * Easing function for a bounce easing in and out.
     */
    Easing easeInOutBounce = value -> value < 0.5 ? (1 - EasingUtil.easeOutBounce(1 - 2 * value)) / 2 : (1 + EasingUtil.easeOutBounce(2 * value - 1)) / 2;

    /**
     * Applies the easing function to the given value.
     *
     * @param value the input value
     * @return the eased value
     */
    double ease(float value);
}