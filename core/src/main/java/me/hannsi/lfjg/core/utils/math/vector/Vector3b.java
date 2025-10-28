package me.hannsi.lfjg.core.utils.math.vector;

import java.io.Serializable;
import java.util.Objects;

public class Vector3b implements Serializable, Cloneable {
    public static final Vector3b ALL_FALSE = new Vector3b(false, false, false);
    public static final Vector3b ALL_TRUE = new Vector3b(true, true, true);
    private static final long serialVersionUID = 1L;
    public boolean b1;
    public boolean b2;
    public boolean b3;

    public Vector3b(boolean b1, boolean b2, boolean b3) {
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
    }

    public Vector3b() {
        this(false, false, false);
    }

    public Vector3b(Vector3b other) {
        this(other.b1, other.b2, other.b3);
    }

    public static Vector3b of(boolean b1, boolean b2, boolean b3) {
        return new Vector3b(b1, b2, b3);
    }

    public static Vector3b copyOf(Vector3b other) {
        return new Vector3b(other);
    }

    public Vector3b set(boolean b1, boolean b2, boolean b3) {
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        return this;
    }

    public Vector3b set(Vector3b other) {
        return set(other.b1, other.b2, other.b3);
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

    public Vector3b and(Vector3b other) {
        return new Vector3b(this.b1 & other.b1, this.b2 & other.b2, this.b3 & other.b3);
    }

    public Vector3b or(Vector3b other) {
        return new Vector3b(this.b1 | other.b1, this.b2 | other.b2, this.b3 | other.b3);
    }

    public Vector3b xor(Vector3b other) {
        return new Vector3b(this.b1 ^ other.b1, this.b2 ^ other.b2, this.b3 ^ other.b3);
    }

    public Vector3b not() {
        return new Vector3b(!this.b1, !this.b2, !this.b3);
    }

    public Vector3b andLocal(Vector3b other) {
        this.b1 &= other.b1;
        this.b2 &= other.b2;
        this.b3 &= other.b3;
        return this;
    }

    public Vector3b orLocal(Vector3b other) {
        this.b1 |= other.b1;
        this.b2 |= other.b2;
        this.b3 |= other.b3;
        return this;
    }

    public Vector3b xorLocal(Vector3b other) {
        this.b1 ^= other.b1;
        this.b2 ^= other.b2;
        this.b3 ^= other.b3;
        return this;
    }

    public Vector3b notLocal() {
        this.b1 = !this.b1;
        this.b2 = !this.b2;
        this.b3 = !this.b3;
        return this;
    }

    public boolean allTrue() {
        return b1 && b2 && b3;
    }

    public boolean anyTrue() {
        return b1 || b2 || b3;
    }

    public boolean allFalse() {
        return !b1 && !b2 && !b3;
    }

    public boolean anyFalse() {
        return !b1 || !b2 || !b3;
    }

    @Override
    public Vector3b clone() {
        return new Vector3b(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vector3b)) {
            return false;
        }
        
        Vector3b v = (Vector3b) o;
        return b1 == v.b1 && b2 == v.b2 && b3 == v.b3;
    }

    @Override
    public int hashCode() {
        return Objects.hash(b1, b2, b3);
    }

    @Override
    public String toString() {
        return "Vector3b{" + b1 + ", " + b2 + ", " + b3 + '}';
    }
}

