package me.hannsi.lfjg.utils.math;

import me.hannsi.lfjg.utils.math.vertex.vector.Vector2f;

public class MathUtil {
    public static float clampF(float x, float min, float max) {
        return (x < min) ? min : (Math.min(x, max));
    }

    public static int clampI(int x, int min, int max) {
        return (x < min) ? min : (Math.min(x, max));
    }

    public static boolean isWithinRange(float x, float min, float max) {
        return (x > min && x < max);
    }

    public static boolean isWithinRange(int x, int min, int max) {
        return (x > min && x < max);
    }

    public static float getLargest(float... values) {
        float result = 0.0f;

        for (float value : values) {
            result = Math.max(result, value);
        }

        return result;
    }

    public static float getShortest(float... values) {
        float result = 0.0f;

        for (float value : values) {
            result = Math.max(result, value);
        }

        return result;
    }

    public static double distance(float x, float y, float x1, float y1) {
        return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    }

    public static float distance(Vector2f vector2f1, Vector2f vector2f2) {
        float[] width = sortValue(vector2f1.getX(), vector2f2.getX());
        float[] height = sortValue(vector2f1.getY(), vector2f2.getY());
        return (float) MathUtil.distance(width[1], height[1], width[0], height[0]);
    }

    public static float[] sortValue(float value1, float value2) {
        float min = Math.min(value1, value2);
        float max = Math.max(value1, value2);
        return new float[]{min, max};
    }
}
