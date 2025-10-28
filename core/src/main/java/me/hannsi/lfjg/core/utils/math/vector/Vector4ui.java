package me.hannsi.lfjg.core.utils.math.vector;

import java.io.Serializable;
import java.util.Objects;

public class Vector4ui implements Serializable, Cloneable {
    public static final Vector4ui ZERO = new Vector4ui(0, 0, 0, 0);
    public static final Vector4ui ONE = new Vector4ui(1, 1, 1, 1);
    public static final Vector4ui MAX = new Vector4ui(0xFFFFFFFFL, 0xFFFFFFFFL, 0xFFFFFFFFL, 0xFFFFFFFFL);
    private static final long serialVersionUID = 1L;
    private static final long UINT_MASK = 0xFFFFFFFFL;

    public long x;
    public long y;
    public long z;
    public long w;

    public Vector4ui() {
        this(0, 0, 0, 0);
    }

    public Vector4ui(long x, long y, long z, long w) {
        this.x = x & UINT_MASK;
        this.y = y & UINT_MASK;
        this.z = z & UINT_MASK;
        this.w = w & UINT_MASK;
    }

    public Vector4ui(Vector4ui o) {
        this(o.x, o.y, o.z, o.w);
    }

    public static Vector4ui of(long x, long y, long z, long w) {
        return new Vector4ui(x, y, z, w);
    }

    public static Vector4ui copyOf(Vector4ui o) {
        return new Vector4ui(o);
    }

    public Vector4ui set(long x, long y, long z, long w) {
        this.x = x & UINT_MASK;
        this.y = y & UINT_MASK;
        this.z = z & UINT_MASK;
        this.w = w & UINT_MASK;
        return this;
    }

    public Vector4ui set(Vector4ui o) {
        return set(o.x, o.y, o.z, o.w);
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

    public long getW() {
        return w & UINT_MASK;
    }

    public void setW(long w) {
        this.w = w & UINT_MASK;
    }

    public Vector4ui add(Vector4ui o) {
        return new Vector4ui((x + o.x) & UINT_MASK, (y + o.y) & UINT_MASK, (z + o.z) & UINT_MASK, (w + o.w) & UINT_MASK);
    }

    public Vector4ui sub(Vector4ui o) {
        return new Vector4ui((x - o.x) & UINT_MASK, (y - o.y) & UINT_MASK, (z - o.z) & UINT_MASK, (w - o.w) & UINT_MASK);
    }

    public Vector4ui mul(Vector4ui o) {
        return new Vector4ui((x * o.x) & UINT_MASK, (y * o.y) & UINT_MASK, (z * o.z) & UINT_MASK, (w * o.w) & UINT_MASK);
    }

    public Vector4ui div(Vector4ui o) {
        return new Vector4ui(
                o.x == 0 ? 0 : (x / o.x) & UINT_MASK,
                o.y == 0 ? 0 : (y / o.y) & UINT_MASK,
                o.z == 0 ? 0 : (z / o.z) & UINT_MASK,
                o.w == 0 ? 0 : (w / o.w) & UINT_MASK
        );
    }

    public Vector4ui mod(Vector4ui o) {
        return new Vector4ui(
                o.x == 0 ? 0 : (x % o.x) & UINT_MASK,
                o.y == 0 ? 0 : (y % o.y) & UINT_MASK,
                o.z == 0 ? 0 : (z % o.z) & UINT_MASK,
                o.w == 0 ? 0 : (w % o.w) & UINT_MASK
        );
    }

    public Vector4ui addLocal(Vector4ui o) {
        x = (x + o.x) & UINT_MASK;
        y = (y + o.y) & UINT_MASK;
        z = (z + o.z) & UINT_MASK;
        w = (w + o.w) & UINT_MASK;
        return this;
    }

    public Vector4ui subLocal(Vector4ui o) {
        x = (x - o.x) & UINT_MASK;
        y = (y - o.y) & UINT_MASK;
        z = (z - o.z) & UINT_MASK;
        w = (w - o.w) & UINT_MASK;
        return this;
    }

    public Vector4ui mulLocal(Vector4ui o) {
        x = (x * o.x) & UINT_MASK;
        y = (y * o.y) & UINT_MASK;
        z = (z * o.z) & UINT_MASK;
        w = (w * o.w) & UINT_MASK;
        return this;
    }

    public Vector4ui divLocal(Vector4ui o) {
        x = (o.x == 0 ? 0 : (x / o.x)) & UINT_MASK;
        y = (o.y == 0 ? 0 : (y / o.y)) & UINT_MASK;
        z = (o.z == 0 ? 0 : (z / o.z)) & UINT_MASK;
        w = (o.w == 0 ? 0 : (w / o.w)) & UINT_MASK;
        return this;
    }

    public Vector4ui modLocal(Vector4ui o) {
        x = (o.x == 0 ? 0 : (x % o.x)) & UINT_MASK;
        y = (o.y == 0 ? 0 : (y % o.y)) & UINT_MASK;
        z = (o.z == 0 ? 0 : (z % o.z)) & UINT_MASK;
        w = (o.w == 0 ? 0 : (w % o.w)) & UINT_MASK;
        return this;
    }

    public Vector4ui and(Vector4ui o) {
        return new Vector4ui(x & o.x, y & o.y, z & o.z, w & o.w);
    }

    public Vector4ui or(Vector4ui o) {
        return new Vector4ui(x | o.x, y | o.y, z | o.z, w | o.w);
    }

    public Vector4ui xor(Vector4ui o) {
        return new Vector4ui(x ^ o.x, y ^ o.y, z ^ o.z, w ^ o.w);
    }

    public Vector4ui not() {
        return new Vector4ui(~x & UINT_MASK, ~y & UINT_MASK, ~z & UINT_MASK, ~w & UINT_MASK);
    }

    public Vector4ui andLocal(Vector4ui o) {
        x &= o.x;
        y &= o.y;
        z &= o.z;
        w &= o.w;
        return this;
    }

    public Vector4ui orLocal(Vector4ui o) {
        x |= o.x;
        y |= o.y;
        z |= o.z;
        w |= o.w;
        return this;
    }

    public Vector4ui xorLocal(Vector4ui o) {
        x ^= o.x;
        y ^= o.y;
        z ^= o.z;
        w ^= o.w;
        return this;
    }

    public Vector4ui notLocal() {
        x = ~x & UINT_MASK;
        y = ~y & UINT_MASK;
        z = ~z & UINT_MASK;
        w = ~w & UINT_MASK;
        return this;
    }

    public boolean equalsUnsigned(Vector4ui o) {
        return Long.compareUnsigned(x, o.x) == 0 &&
                Long.compareUnsigned(y, o.y) == 0 &&
                Long.compareUnsigned(z, o.z) == 0 &&
                Long.compareUnsigned(w, o.w) == 0;
    }

    public boolean allZero() {
        return x == 0 && y == 0 && z == 0 && w == 0;
    }

    public boolean anyZero() {
        return x == 0 || y == 0 || z == 0 || w == 0;
    }

    @Override
    public Vector4ui clone() {
        return new Vector4ui(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vector4ui)) {
            return false;
        }
        Vector4ui v = (Vector4ui) o;
        return x == v.x && y == v.y && z == v.z && w == v.w;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

    @Override
    public String toString() {
        return "Vector4ui{" + (x & UINT_MASK) + ", " + (y & UINT_MASK) + ", " + (z & UINT_MASK) + ", " + (w & UINT_MASK) + '}';
    }
}