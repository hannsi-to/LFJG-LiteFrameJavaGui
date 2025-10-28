package me.hannsi.lfjg.core.utils.math.vector;

import java.nio.FloatBuffer;

public class Matrix3x4f implements Cloneable {
    public float m00;
    public float m01;
    public float m02;
    public float m03;
    public float m10;
    public float m11;
    public float m12;
    public float m13;
    public float m20;
    public float m21;
    public float m22;
    public float m23;

    public Matrix3x4f() {
        identity();
    }

    public Matrix3x4f(Matrix3x4f other) {
        set(other);
    }

    public Matrix3x4f(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23) {
        set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23);
    }

    public Matrix3x4f set(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        return this;
    }

    public Matrix3x4f set(Matrix3x4f m) {
        return set(m.m00, m.m01, m.m02, m.m03, m.m10, m.m11, m.m12, m.m13, m.m20, m.m21, m.m22, m.m23);
    }

    public Matrix3x4f identity() {
        m00 = m11 = m22 = 1.0f;
        m01 = m02 = m03 = 0.0f;
        m10 = m12 = m13 = 0.0f;
        m20 = m21 = m23 = 0.0f;
        return this;
    }

    public Matrix3x4f translate(float tx, float ty, float tz) {
        m03 += tx;
        m13 += ty;
        m23 += tz;
        return this;
    }

    public Matrix3x4f scale(float sx, float sy, float sz) {
        m00 *= sx;
        m01 *= sx;
        m02 *= sx;
        m03 *= sx;
        m10 *= sy;
        m11 *= sy;
        m12 *= sy;
        m13 *= sy;
        m20 *= sz;
        m21 *= sz;
        m22 *= sz;
        m23 *= sz;
        return this;
    }

    public FloatBuffer get(FloatBuffer buffer) {
        buffer.put(m00).put(m10).put(m20)
                .put(m01).put(m11).put(m21)
                .put(m02).put(m12).put(m22)
                .put(m03).put(m13).put(m23);
        return buffer;
    }

    public Matrix3x4f set(FloatBuffer buffer) {
        m00 = buffer.get();
        m10 = buffer.get();
        m20 = buffer.get();
        m01 = buffer.get();
        m11 = buffer.get();
        m21 = buffer.get();
        m02 = buffer.get();
        m12 = buffer.get();
        m22 = buffer.get();
        m03 = buffer.get();
        m13 = buffer.get();
        m23 = buffer.get();
        return this;
    }

    @Override
    public Matrix3x4f clone() {
        return new Matrix3x4f(this);
    }

    @Override
    public String toString() {
        return String.format(
                "[[%f, %f, %f, %f], [%f, %f, %f, %f], [%f, %f, %f, %f]]",
                m00, m01, m02, m03,
                m10, m11, m12, m13,
                m20, m21, m22, m23
        );
    }
}
