package me.hannsi.lfjg.core.utils.math.vector;

import java.io.Serializable;
import java.util.Objects;

public class Vector4b implements Serializable, Cloneable {
    public static final Vector4b ALL_FALSE = new Vector4b(false, false, false, false);
    public static final Vector4b ALL_TRUE = new Vector4b(true, true, true, true);
    private static final long serialVersionUID = 1L;
    public boolean b1;
    public boolean b2;
    public boolean b3;
    public boolean b4;

    public Vector4b(boolean b1, boolean b2, boolean b3, boolean b4) {
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.b4 = b4;
    }

    public Vector4b() {
        this(false, false, false, false);
    }

    public Vector4b(Vector4b other) {
        this(other.b1, other.b2, other.b3, other.b4);
    }

    public static Vector4b of(boolean b1, boolean b2, boolean b3, boolean b4) {
        return new Vector4b(b1, b2, b3, b4);
    }

    public static Vector4b copyOf(Vector4b other) {
        return new Vector4b(other);
    }

    public Vector4b set(boolean b1, boolean b2, boolean b3, boolean b4) {
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.b4 = b4;
        return this;
    }

    public Vector4b set(Vector4b other) {
        return set(other.b1, other.b2, other.b3, other.b4);
    }

    public boolean isB1() {
        return b1;
    }

    public void setB1(boolean b1) {
        this.b1 = b1;
    }

    public boolean isB2() {
        return b2;
    }

    public void setB2(boolean b2) {
        this.b2 = b2;
    }

    public boolean isB3() {
        return b3;
    }

    public void setB3(boolean b3) {
        this.b3 = b3;
    }

    public boolean isB4() {
        return b4;
    }

    public void setB4(boolean b4) {
        this.b4 = b4;
    }

    public Vector4b and(Vector4b other) {
        return new Vector4b(
                this.b1 & other.b1,
                this.b2 & other.b2,
                this.b3 & other.b3,
                this.b4 & other.b4
        );
    }

    public Vector4b or(Vector4b other) {
        return new Vector4b(
                this.b1 | other.b1,
                this.b2 | other.b2,
                this.b3 | other.b3,
                this.b4 | other.b4
        );
    }

    public Vector4b xor(Vector4b other) {
        return new Vector4b(
                this.b1 ^ other.b1,
                this.b2 ^ other.b2,
                this.b3 ^ other.b3,
                this.b4 ^ other.b4
        );
    }

    public Vector4b not() {
        return new Vector4b(!this.b1, !this.b2, !this.b3, !this.b4);
    }

    public Vector4b andLocal(Vector4b other) {
        this.b1 &= other.b1;
        this.b2 &= other.b2;
        this.b3 &= other.b3;
        this.b4 &= other.b4;
        return this;
    }

    public Vector4b orLocal(Vector4b other) {
        this.b1 |= other.b1;
        this.b2 |= other.b2;
        this.b3 |= other.b3;
        this.b4 |= other.b4;
        return this;
    }

    public Vector4b xorLocal(Vector4b other) {
        this.b1 ^= other.b1;
        this.b2 ^= other.b2;
        this.b3 ^= other.b3;
        this.b4 ^= other.b4;
        return this;
    }

    public Vector4b notLocal() {
        this.b1 = !this.b1;
        this.b2 = !this.b2;
        this.b3 = !this.b3;
        this.b4 = !this.b4;
        return this;
    }

    public boolean allTrue() {
        return b1 && b2 && b3 && b4;
    }

    public boolean anyTrue() {
        return b1 || b2 || b3 || b4;
    }

    public boolean allFalse() {
        return !b1 && !b2 && !b3 && !b4;
    }

    public boolean anyFalse() {
        return !b1 || !b2 || !b3 || !b4;
    }

    @Override
    public Vector4b clone() {
        return new Vector4b(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vector4b)) {
            return false;
        }

        Vector4b v = (Vector4b) o;
        return b1 == v.b1 && b2 == v.b2 && b3 == v.b3 && b4 == v.b4;
    }

    @Override
    public int hashCode() {
        return Objects.hash(b1, b2, b3, b4);
    }

    @Override
    public String toString() {
        return "Vector4b{" + b1 + ", " + b2 + ", " + b3 + ", " + b4 + '}';
    }
}
