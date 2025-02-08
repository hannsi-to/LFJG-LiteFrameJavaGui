package me.hannsi.lfjg.utils.math;

import org.joml.Vector2f;

public class MathUtil {

    /**
     * Clamps a float value between a minimum and maximum value.
     *
     * @param x the value to clamp
     * @param min the minimum value
     * @param max the maximum value
     * @return the clamped value
     */
    public static float clampF(float x, float min, float max) {
        return (x < min) ? min : (Math.min(x, max));
    }

    /**
     * Clamps an integer value between a minimum and maximum value.
     *
     * @param x the value to clamp
     * @param min the minimum value
     * @param max the maximum value
     * @return the clamped value
     */
    public static int clampI(int x, int min, int max) {
        return (x < min) ? min : (Math.min(x, max));
    }

    /**
     * Checks if a float value is within a specified range.
     *
     * @param x the value to check
     * @param min the minimum value of the range
     * @param max the maximum value of the range
     * @return true if the value is within the range, false otherwise
     */
    public static boolean isWithinRange(float x, float min, float max) {
        return (x > min && x < max);
    }

    /**
     * Checks if an integer value is within a specified range.
     *
     * @param x the value to check
     * @param min the minimum value of the range
     * @param max the maximum value of the range
     * @return true if the value is within the range, false otherwise
     */
    public static boolean isWithinRange(int x, int min, int max) {
        return (x > min && x < max);
    }

    /**
     * Gets the largest value from an array of float values.
     *
     * @param values the array of values
     * @return the largest value
     */
    public static float getLargest(float... values) {
        float result = 0.0f;

        for (float value : values) {
            result = Math.max(result, value);
        }

        return result;
    }

    /**
     * Gets the shortest value from an array of float values.
     *
     * @param values the array of values
     * @return the shortest value
     */
    public static float getShortest(float... values) {
        float result = 0.0f;

        for (float value : values) {
            result = Math.max(result, value);
        }

        return result;
    }

    /**
     * Calculates the distance between two points in 2D space.
     *
     * @param x the x-coordinate of the first point
     * @param y the y-coordinate of the first point
     * @param x1 the x-coordinate of the second point
     * @param y1 the y-coordinate of the second point
     * @return the distance between the two points
     */
    public static double distance(float x, float y, float x1, float y1) {
        return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    }

    /**
     * Calculates the distance between two vectors in 2D space.
     *
     * @param vector2f1 the first vector
     * @param vector2f2 the second vector
     * @return the distance between the two vectors
     */
    public static float distance(Vector2f vector2f1, Vector2f vector2f2) {
        float[] width = sortValue(vector2f1.x(), vector2f2.x());
        float[] height = sortValue(vector2f1.y(), vector2f2.y());
        return (float) MathUtil.distance(width[1], height[1], width[0], height[0]);
    }

    /**
     * Sorts two float values in ascending order.
     *
     * @param value1 the first value
     * @param value2 the second value
     * @return an array containing the sorted values
     */
    public static float[] sortValue(float value1, float value2) {
        float min = Math.min(value1, value2);
        float max = Math.max(value1, value2);
        return new float[]{min, max};
    }

    /**
     * Calculates the Gaussian value for a given x and sigma.
     *
     * @param x the input value
     * @param sigma the standard deviation
     * @return the Gaussian value
     */
    public static float calculateGaussianValue(float x, float sigma) {
        double PI = 3.141592653;
        double output = 1.0 / Math.sqrt(2.0 * PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }
}