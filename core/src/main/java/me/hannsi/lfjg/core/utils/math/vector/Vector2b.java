package me.hannsi.lfjg.core.utils.math.vector;

import java.io.Serializable;
import java.util.Objects;

public class Vector2b implements Serializable, Cloneable {
    public static final Vector2b ALL_FALSE = new Vector2b(false, false);
    public static final Vector2b ALL_TRUE = new Vector2b(true, true);
    private static final long serialVersionUID = 1L;
    public boolean b1;
    public boolean b2;

    public Vector2b(boolean b1, boolean b2) {
        this.b1 = b1;
        this.b2 = b2;
    }

    public Vector2b() {
        this(false, false);
    }

    public Vector2b(Vector2b other) {
        this(other.b1, other.b2);
    }

    public static Vector2b of(boolean x, boolean y) {
        return new Vector2b(x, y);
    }

    public static Vector2b copyOf(Vector2b other) {
        return new Vector2b(other);
    }

    public Vector2b set(boolean b1, boolean b2) {
        this.b1 = b1;
        this.b2 = b2;
        return this;
    }

    public Vector2b set(Vector2b other) {
        return set(other.b1, other.b2);
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

    public Vector2b and(Vector2b other) {
        return new Vector2b(this.b1 & other.b1, this.b2 & other.b2);
    }

    public Vector2b or(Vector2b other) {
        return new Vector2b(this.b1 | other.b1, this.b2 | other.b2);
    }

    public Vector2b xor(Vector2b other) {
        return new Vector2b(this.b1 ^ other.b1, this.b2 ^ other.b2);
    }

    public Vector2b not() {
        return new Vector2b(!this.b1, !this.b2);
    }

    public Vector2b andLocal(Vector2b other) {
        this.b1 &= other.b1;
        this.b2 &= other.b2;
        return this;
    }

    public Vector2b orLocal(Vector2b other) {
        this.b1 |= other.b1;
        this.b2 |= other.b2;
        return this;
    }

    public Vector2b xorLocal(Vector2b other) {
        this.b1 ^= other.b1;
        this.b2 ^= other.b2;
        return this;
    }

    public Vector2b notLocal() {
        this.b1 = !this.b1;
        this.b2 = !this.b2;
        return this;
    }

    public boolean allTrue() {
        return b1 && b2;
    }

    public boolean anyTrue() {
        return b1 || b2;
    }

    public boolean allFalse() {
        return !b1 && !b2;
    }

    public boolean anyFalse() {
        return !b1 || !b2;
    }

    public Vector2b clone() {
        return new Vector2b(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vector2b)) {
            return false;
        }

        Vector2b v = (Vector2b) o;
        return this.b1 == v.b1 && this.b2 == v.b2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(b1, b2);
    }

    @Override
    public String toString() {
        return "Vector2b{" + b1 + ", " + b2 + '}';
    }
}
