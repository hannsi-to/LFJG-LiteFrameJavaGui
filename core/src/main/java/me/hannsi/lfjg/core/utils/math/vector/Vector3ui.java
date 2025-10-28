package me.hannsi.lfjg.core.utils.math.vector;

import java.io.Serializable;
import java.util.Objects;

public class Vector3ui implements Serializable, Cloneable {
    public static final Vector3ui ZERO = new Vector3ui(0, 0, 0);
    public static final Vector3ui ONE = new Vector3ui(1, 1, 1);
    public static final Vector3ui MAX = new Vector3ui(0xFFFFFFFFL, 0xFFFFFFFFL, 0xFFFFFFFFL);
    private static final long serialVersionUID = 1L;
    private static final long UINT_MASK = 0xFFFFFFFFL;

    public long x;
    public long y;
    public long z;

    public Vector3ui() {
        this(0, 0, 0);
    }

    public Vector3ui(long x, long y, long z) {
        this.x = x & UINT_MASK;
        this.y = y & UINT_MASK;
        this.z = z & UINT_MASK;
    }

    public Vector3ui(Vector3ui other) {
        this(other.x, other.y, other.z);
    }

    public static Vector3ui of(long x, long y, long z) {
        return new Vector3ui(x, y, z);
    }

    public static Vector3ui copyOf(Vector3ui other) {
        return new Vector3ui(other);
    }

    public Vector3ui set(long x, long y, long z) {
        this.x = x & UINT_MASK;
        this.y = y & UINT_MASK;
        this.z = z & UINT_MASK;
        return this;
    }

    public Vector3ui set(Vector3ui other) {
        return set(other.x, other.y, other.z);
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

    public long getZ() {
        return z & UINT_MASK;
    }

    public void setZ(long z) {
        this.z = z & UINT_MASK;
    }

    public Vector3ui add(Vector3ui o) {
        return new Vector3ui((x + o.x) & UINT_MASK, (y + o.y) & UINT_MASK, (z + o.z) & UINT_MASK);
    }

    public Vector3ui sub(Vector3ui o) {
        return new Vector3ui((x - o.x) & UINT_MASK, (y - o.y) & UINT_MASK, (z - o.z) & UINT_MASK);
    }

    public Vector3ui mul(Vector3ui o) {
        return new Vector3ui((x * o.x) & UINT_MASK, (y * o.y) & UINT_MASK, (z * o.z) & UINT_MASK);
    }

    public Vector3ui div(Vector3ui o) {
        return new Vector3ui(
                o.x == 0 ? 0 : (x / o.x) & UINT_MASK,
                o.y == 0 ? 0 : (y / o.y) & UINT_MASK,
                o.z == 0 ? 0 : (z / o.z) & UINT_MASK
        );
    }

    public Vector3ui mod(Vector3ui o) {
        return new Vector3ui(
                o.x == 0 ? 0 : (x % o.x) & UINT_MASK,
                o.y == 0 ? 0 : (y % o.y) & UINT_MASK,
                o.z == 0 ? 0 : (z % o.z) & UINT_MASK
        );
    }

    public Vector3ui addLocal(Vector3ui o) {
        x = (x + o.x) & UINT_MASK;
        y = (y + o.y) & UINT_MASK;
        z = (z + o.z) & UINT_MASK;
        return this;
    }

    public Vector3ui subLocal(Vector3ui o) {
        x = (x - o.x) & UINT_MASK;
        y = (y - o.y) & UINT_MASK;
        z = (z - o.z) & UINT_MASK;
        return this;
    }

    public Vector3ui mulLocal(Vector3ui o) {
        x = (x * o.x) & UINT_MASK;
        y = (y * o.y) & UINT_MASK;
        z = (z * o.z) & UINT_MASK;
        return this;
    }

    public Vector3ui divLocal(Vector3ui o) {
        x = (o.x == 0 ? 0 : (x / o.x)) & UINT_MASK;
        y = (o.y == 0 ? 0 : (y / o.y)) & UINT_MASK;
        z = (o.z == 0 ? 0 : (z / o.z)) & UINT_MASK;
        return this;
    }

    public Vector3ui modLocal(Vector3ui o) {
        x = (o.x == 0 ? 0 : (x % o.x)) & UINT_MASK;
        y = (o.y == 0 ? 0 : (y % o.y)) & UINT_MASK;
        z = (o.z == 0 ? 0 : (z % o.z)) & UINT_MASK;
        return this;
    }

    public Vector3ui and(Vector3ui o) {
        return new Vector3ui(x & o.x, y & o.y, z & o.z);
    }

    public Vector3ui or(Vector3ui o) {
        return new Vector3ui(x | o.x, y | o.y, z | o.z);
    }

    public Vector3ui xor(Vector3ui o) {
        return new Vector3ui(x ^ o.x, y ^ o.y, z ^ o.z);
    }

    public Vector3ui not() {
        return new Vector3ui(~x & UINT_MASK, ~y & UINT_MASK, ~z & UINT_MASK);
    }

    public Vector3ui andLocal(Vector3ui o) {
        x &= o.x;
        y &= o.y;
        z &= o.z;
        return this;
    }

    public Vector3ui orLocal(Vector3ui o) {
        x |= o.x;
        y |= o.y;
        z |= o.z;
        return this;
    }

    public Vector3ui xorLocal(Vector3ui o) {
        x ^= o.x;
        y ^= o.y;
        z ^= o.z;
        return this;
    }

    public Vector3ui notLocal() {
        x = ~x & UINT_MASK;
        y = ~y & UINT_MASK;
        z = ~z & UINT_MASK;
        return this;
    }

    public boolean equalsUnsigned(Vector3ui o) {
        return Long.compareUnsigned(x, o.x) == 0 &&
                Long.compareUnsigned(y, o.y) == 0 &&
                Long.compareUnsigned(z, o.z) == 0;
    }

    public boolean allZero() {
        return x == 0 && y == 0 && z == 0;
    }

    public boolean anyZero() {
        return x == 0 || y == 0 || z == 0;
    }

    @Override
    public Vector3ui clone() {
        return new Vector3ui(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vector3ui)) {
            return false;
        }
        Vector3ui v = (Vector3ui) o;
        return x == v.x && y == v.y && z == v.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Vector3ui{" + (x & UINT_MASK) + ", " + (y & UINT_MASK) + ", " + (z & UINT_MASK) + '}';
    }
}