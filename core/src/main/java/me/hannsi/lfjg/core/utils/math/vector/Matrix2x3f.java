package me.hannsi.lfjg.core.utils.math.vector;

import java.nio.FloatBuffer;

public class Matrix2x3f implements Cloneable {
    public float m00;
    public float m01;
    public float m02;
    public float m10;
    public float m11;
    public float m12;

    public Matrix2x3f() {
        m00 = m11 = 1.0f;
        m01 = m02 = m10 = m12 = 0.0f;
    }

    public Matrix2x3f(Matrix2x3f other) {
        set(other);
    }

    public Matrix2x3f(float m00, float m01, float m02, float m10, float m11, float m12) {
        set(m00, m01, m02, m10, m11, m12);
    }

    public Matrix2x3f set(float m00, float m01, float m02, float m10, float m11, float m12) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        return this;
    }

    public Matrix2x3f set(Matrix2x3f m) {
        return set(m.m00, m.m01, m.m02, m.m10, m.m11, m.m12);
    }

    public Matrix2x3f identity() {
        m00 = m11 = 1.0f;
        m01 = m02 = m10 = m12 = 0.0f;
        return this;
    }

    public Matrix2x3f mul(Matrix2x3f other) {
        float nm00 = m00 * other.m00 + m01 * other.m10;
        float nm01 = m00 * other.m01 + m01 * other.m11;
        float nm02 = m00 * other.m02 + m01 * other.m12;
        float nm10 = m10 * other.m00 + m11 * other.m10;
        float nm11 = m10 * other.m01 + m11 * other.m11;
        float nm12 = m10 * other.m02 + m11 * other.m12;
        return set(nm00, nm01, nm02, nm10, nm11, nm12);
    }

    public Matrix2x3f translate(float tx, float ty) {
        m02 += tx;
        m12 += ty;
        return this;
    }

    public Matrix2x3f scale(float sx, float sy) {
        m00 *= sx;
        m01 *= sx;
        m02 *= sx;
        m10 *= sy;
        m11 *= sy;
        m12 *= sy;
        return this;
    }

    public FloatBuffer get(FloatBuffer buffer) {
        buffer.put(m00).put(m10)
                .put(m01).put(m11)
                .put(m02).put(m12);
        return buffer;
    }

    public Matrix2x3f set(FloatBuffer buffer) {
        m00 = buffer.get();
        m10 = buffer.get();
        m01 = buffer.get();
        m11 = buffer.get();
        m02 = buffer.get();
        m12 = buffer.get();
        return this;
    }

    @Override
    public Matrix2x3f clone() {
        return new Matrix2x3f(this);
    }

    @Override
    public String toString() {
        return String.format(
                "[[%f, %f, %f], [%f, %f, %f]]",
                m00, m01, m02, m10, m11, m12
        );
    }
}
