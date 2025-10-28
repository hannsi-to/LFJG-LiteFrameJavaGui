package me.hannsi.lfjg.core.utils.math.vector;

import java.io.Serializable;
import java.util.Objects;

public class Vector2ui implements Serializable, Cloneable {
    public static final Vector2ui ZERO = new Vector2ui(0, 0);
    public static final Vector2ui ONE = new Vector2ui(1, 1);
    public static final Vector2ui MAX = new Vector2ui(0xFFFFFFFFL, 0xFFFFFFFFL);
    private static final long serialVersionUID = 1L;
    private static final long UINT_MASK = 0xFFFFFFFFL;

    public long x;
    public long y;

    public Vector2ui() {
        this(0, 0);
    }

    public Vector2ui(long x, long y) {
        this.x = x & UINT_MASK;
        this.y = y & UINT_MASK;
    }

    public Vector2ui(Vector2ui other) {
        this(other.x, other.y);
    }

    public static Vector2ui of(long x, long y) {
        return new Vector2ui(x, y);
    }

    public static Vector2ui copyOf(Vector2ui other) {
        return new Vector2ui(other);
    }

    public Vector2ui set(long x, long y) {
        this.x = x & UINT_MASK;
        this.y = y & UINT_MASK;
        return this;
    }

    public Vector2ui set(Vector2ui other) {
        return set(other.x, other.y);
    }

    public long getX() {
        return x & UINT_MASK;
    }

    public void setX(long x) {
        this.x = x & UINT_MASK;
    }

    public long getY() {
        return y & UINT_MASK;
    }

    public void setY(long y) {
        this.y = y & UINT_MASK;
    }

    public Vector2ui add(Vector2ui other) {
        return new Vector2ui((this.x + other.x) & UINT_MASK, (this.y + other.y) & UINT_MASK);
    }

    public Vector2ui sub(Vector2ui other) {
        return new Vector2ui((this.x - other.x) & UINT_MASK, (this.y - other.y) & UINT_MASK);
    }

    public Vector2ui mul(Vector2ui other) {
        return new Vector2ui((this.x * other.x) & UINT_MASK, (this.y * other.y) & UINT_MASK);
    }

    public Vector2ui div(Vector2ui other) {
        return new Vector2ui(
                other.x == 0 ? 0 : (this.x / other.x) & UINT_MASK,
                other.y == 0 ? 0 : (this.y / other.y) & UINT_MASK
        );
    }

    public Vector2ui mod(Vector2ui other) {
        return new Vector2ui(
                other.x == 0 ? 0 : (this.x % other.x) & UINT_MASK,
                other.y == 0 ? 0 : (this.y % other.y) & UINT_MASK
        );
    }

    public Vector2ui addLocal(Vector2ui other) {
        this.x = (this.x + other.x) & UINT_MASK;
        this.y = (this.y + other.y) & UINT_MASK;
        return this;
    }

    public Vector2ui subLocal(Vector2ui other) {
        this.x = (this.x - other.x) & UINT_MASK;
        this.y = (this.y - other.y) & UINT_MASK;
        return this;
    }

    public Vector2ui mulLocal(Vector2ui other) {
        this.x = (this.x * other.x) & UINT_MASK;
        this.y = (this.y * other.y) & UINT_MASK;
        return this;
    }

    public Vector2ui divLocal(Vector2ui other) {
        this.x = (other.x == 0 ? 0 : (this.x / other.x)) & UINT_MASK;
        this.y = (other.y == 0 ? 0 : (this.y / other.y)) & UINT_MASK;
        return this;
    }

    public Vector2ui modLocal(Vector2ui other) {
        this.x = (other.x == 0 ? 0 : (this.x % other.x)) & UINT_MASK;
        this.y = (other.y == 0 ? 0 : (this.y % other.y)) & UINT_MASK;
        return this;
    }

    public Vector2ui and(Vector2ui other) {
        return new Vector2ui(this.x & other.x, this.y & other.y);
    }

    public Vector2ui or(Vector2ui other) {
        return new Vector2ui(this.x | other.x, this.y | other.y);
    }

    public Vector2ui xor(Vector2ui other) {
        return new Vector2ui(this.x ^ other.x, this.y ^ other.y);
    }

    public Vector2ui not() {
        return new Vector2ui(~this.x & UINT_MASK, ~this.y & UINT_MASK);
    }

    public Vector2ui andLocal(Vector2ui other) {
        this.x &= other.x;
        this.y &= other.y;
        return this;
    }

    public Vector2ui orLocal(Vector2ui other) {
        this.x |= other.x;
        this.y |= other.y;
        return this;
    }

    public Vector2ui xorLocal(Vector2ui other) {
        this.x ^= other.x;
        this.y ^= other.y;
        return this;
    }

    public Vector2ui notLocal() {
        this.x = ~this.x & UINT_MASK;
        this.y = ~this.y & UINT_MASK;
        return this;
    }

    public boolean equalsUnsigned(Vector2ui other) {
        return Long.compareUnsigned(this.x, other.x) == 0 &&
                Long.compareUnsigned(this.y, other.y) == 0;
    }

    public boolean allZero() {
        return x == 0 && y == 0;
    }

    public boolean anyZero() {
        return x == 0 || y == 0;
    }

    public Vector2ui clone() {
        return new Vector2ui(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vector2ui)) {
            return false;
        }

        Vector2ui v = (Vector2ui) o;
        return this.x == v.x && this.y == v.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector2ui{" + (x & UINT_MASK) + ", " + (y & UINT_MASK) + '}';
    }
}
