package me.hannsi.lfjg.core.utils.math;

public final class UInt32 implements Comparable<UInt32> {
    public static final long MAX_VALUE_LONG = 0xffff_ffffL;
    public static final long MIN_VALUE_LONG = 0L;
    public static final UInt32 MAX_VALUE = fromLong(MAX_VALUE_LONG);
    public static final UInt32 MIN_VALUE = fromLong(MIN_VALUE_LONG);

    private final int value;

    public UInt32(int value) {
        this.value = value;
    }

    public static UInt32 fromInt(int value) {
        return new UInt32(value);
    }

    public static UInt32 fromLong(long value) {
        if (value < MIN_VALUE_LONG || value > MAX_VALUE_LONG) {
            throw new IllegalArgumentException("Out of uint range: " + value);
        }

        return new UInt32((int) value);
    }

    public long toLong() {
        return Integer.toUnsignedLong(value);
    }

    public int toInt() {
        return value;
    }

    public UInt32 add(UInt32 other) {
        return new UInt32(this.value + other.value);
    }

    public UInt32 subtract(UInt32 other) {
        return new UInt32(this.value - other.value);
    }

    public UInt32 multiply(UInt32 other) {
        return new UInt32(this.value * other.value);
    }

    public UInt32 divide(UInt32 other) {
        return new UInt32(Integer.divideUnsigned(this.value, other.value));
    }

    public UInt32 mod(UInt32 other) {
        return new UInt32(Integer.remainderUnsigned(this.value, other.value));
    }

    public UInt32 increment() {
        return new UInt32(this.value + 1);
    }

    public UInt32 decrement() {
        return new UInt32(this.value - 1);
    }

    public UInt32 and(UInt32 other) {
        return new UInt32(this.value & other.value);
    }

    public UInt32 or(UInt32 other) {
        return new UInt32(this.value | other.value);
    }

    public UInt32 xor(UInt32 other) {
        return new UInt32(this.value ^ other.value);
    }

    public UInt32 not() {
        return new UInt32(~this.value);
    }

    @Override
    public int compareTo(UInt32 o) {
        return Integer.compareUnsigned(this.value, o.value);
    }

    public boolean equals(UInt32 o) {
        return this.value == o.value;
    }

    public boolean greater(UInt32 o) {
        return Integer.compareUnsigned(this.value, o.value) > 0;
    }

    public boolean less(UInt32 o) {
        return Integer.compareUnsigned(this.value, o.value) < 0;
    }

    @Override
    public String toString() {
        return Long.toString(toLong());
    }
}
