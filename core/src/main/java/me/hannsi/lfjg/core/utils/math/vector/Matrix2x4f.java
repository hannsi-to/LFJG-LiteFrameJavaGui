package me.hannsi.lfjg.core.utils.math.vector;

import java.nio.FloatBuffer;

public class Matrix2x4f implements Cloneable {
    public float m00;
    public float m01;
    public float m02;
    public float m03;
    public float m10;
    public float m11;
    public float m12;
    public float m13;

    public Matrix2x4f() {
        m00 = m11 = 1.0f;
        m01 = m02 = m03 = m10 = m12 = m13 = 0.0f;
    }

    public Matrix2x4f(Matrix2x4f other) {
        set(other);
    }

    public Matrix2x4f(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13) {
        set(m00, m01, m02, m03, m10, m11, m12, m13);
    }

    public Matrix2x4f set(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        return this;
    }

    public Matrix2x4f set(Matrix2x4f m) {
        return set(m.m00, m.m01, m.m02, m.m03, m.m10, m.m11, m.m12, m.m13);
    }

    public Matrix2x4f identity() {
        m00 = m11 = 1.0f;
        m01 = m02 = m03 = m10 = m12 = m13 = 0.0f;
        return this;
    }

    public Matrix2x4f translate(float tx, float ty) {
        m03 += tx;
        m13 += ty;
        return this;
    }

    public Matrix2x4f scale(float sx, float sy) {
        m00 *= sx;
        m01 *= sx;
        m02 *= sx;
        m03 *= sx;
        m10 *= sy;
        m11 *= sy;
        m12 *= sy;
        m13 *= sy;
        return this;
    }

    public FloatBuffer get(FloatBuffer buffer) {
        buffer.put(m00).put(m10)
                .put(m01).put(m11)
                .put(m02).put(m12)
                .put(m03).put(m13);
        return buffer;
    }

    public Matrix2x4f set(FloatBuffer buffer) {
        m00 = buffer.get();
        m10 = buffer.get();
        m01 = buffer.get();
        m11 = buffer.get();
        m02 = buffer.get();
        m12 = buffer.get();
        m03 = buffer.get();
        m13 = buffer.get();
        return this;
    }

    @Override
    public Matrix2x4f clone() {
        return new Matrix2x4f(this);
    }

    @Override
    public String toString() {
        return String.format(
                "[[%f, %f, %f, %f], [%f, %f, %f, %f]]",
                m00, m01, m02, m03,
                m10, m11, m12, m13
        );
    }
}
