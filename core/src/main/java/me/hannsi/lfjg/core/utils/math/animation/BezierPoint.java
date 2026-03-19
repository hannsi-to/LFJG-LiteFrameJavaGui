package me.hannsi.lfjg.core.utils.math.animation;

import java.util.Arrays;

import static me.hannsi.lfjg.core.utils.math.MathHelper.sqrt;

public class BezierPoint {
    public final float[] coords;
    public final int dimensions;

    public BezierPoint(float... coords) {
        this.coords = coords.clone();
        this.dimensions = coords.length;
    }

    public static BezierPoint of1D(float x) {
        return new BezierPoint(x);
    }

    public static BezierPoint of2D(float x, float y) {
        return new BezierPoint(x, y);
    }

    public static BezierPoint of3D(float x, float y, float z) {
        return new BezierPoint(x, y, z);
    }

    public BezierPoint add(BezierPoint other) {
        assertSameDimension(other);

        float[] result = new float[dimensions];
        for (int i = 0; i < dimensions; i++) {
            result[i] = coords[i] + other.coords[i];
        }
        return new BezierPoint(result);
    }

    public BezierPoint sub(BezierPoint other) {
        assertSameDimension(other);

        float[] result = new float[dimensions];
        for (int i = 0; i < dimensions; i++) {
            result[i] = coords[i] - other.coords[i];
        }
        return new BezierPoint(result);
    }

    public BezierPoint scale(float scalar) {
        float[] result = new float[dimensions];
        for (int i = 0; i < dimensions; i++) {
            result[i] = coords[i] * scalar;
        }
        return new BezierPoint(result);
    }

    public BezierPoint lerp(BezierPoint other, float t) {
        return this.scale(1 - t).add(other.scale(t));
    }

    public BezierPoint normalize() {
        float len = 0;
        for (float c : coords) {
            len += c * c;
        }
        len = sqrt(len);
        if (len < 1e-10) {
            return new BezierPoint(new float[dimensions]);
        }
        return scale(1.0f / len);
    }

    public BezierPoint reverse() {
        float[] result = new float[dimensions];
        for (int i = 0; i < dimensions; i++) {
            result[i] = 1 - coords[i];
        }
        return new BezierPoint(result);
    }

    public float distanceTo(BezierPoint other) {
        assertSameDimension(other);
        float sum = 0;
        for (int i = 0; i < dimensions; i++) {
            float d = coords[i] - other.coords[i];
            sum += d * d;
        }
        return sqrt(sum);
    }

    public float get(int index) {
        return coords[index];
    }

    public float x() {
        return coords[0];
    }

    public float y() {
        return coords.length > 1 ? coords[1] : 0;
    }

    public float z() {
        return coords.length > 2 ? coords[2] : 0;
    }

    private void assertSameDimension(BezierPoint other) {
        if (dimensions != other.dimensions) {
            throw new IllegalArgumentException("Dimension mismatch: " + dimensions + " : " + other.dimensions);
        }
    }

    @Override
    public String toString() {
        return "BezierPoint" + Arrays.toString(coords);
    }
}
