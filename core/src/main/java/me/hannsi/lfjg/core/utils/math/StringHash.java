package me.hannsi.lfjg.core.utils.math;

import static me.hannsi.lfjg.core.Core.UNSAFE;

public class StringHash {

    private static final long PRIME64_1 = 0x9E3779B185EBCA87L;
    private static final long PRIME64_2 = 0xC2B2AE3D27D4EB4FL;
    private static final long PRIME64_3 = 0x165667B19E3779F9L;
    private static final long PRIME64_4 = 0x85EBCA77C2B2AE63L;
    private static final long PRIME64_5 = 0x27D4EB2F165667C5L;

    private static final long BYTE_ARRAY_OFFSET;
    private static final long STRING_VALUE_OFFSET;

    static {
        BYTE_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);
        try {
            STRING_VALUE_OFFSET = UNSAFE.objectFieldOffset(String.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static long hash64(String str) {
        if (str == null) {
            return 0;
        }

        byte[] value = (byte[]) UNSAFE.getObject(str, STRING_VALUE_OFFSET);
        int len = value.length;
        long h64;

        if (len >= 32) {
            h64 = processLarge(value, len);
        } else {
            h64 = seed(len);
        }

        h64 += len;

        int i = (len >= 32) ? (len & ~31) : 0;

        while (i <= len - 8) {
            long k1 = round(0, UNSAFE.getLong(value, BYTE_ARRAY_OFFSET + i));
            h64 ^= k1;
            h64 = Long.rotateLeft(h64, 27) * PRIME64_1 + PRIME64_4;
            i += 8;
        }

        if (i <= len - 4) {
            h64 ^= (UNSAFE.getInt(value, BYTE_ARRAY_OFFSET + i) & 0xFFFFFFFFL) * PRIME64_1;
            h64 = Long.rotateLeft(h64, 23) * PRIME64_2 + PRIME64_3;
            i += 4;
        }

        while (i < len) {
            h64 ^= (UNSAFE.getByte(value, BYTE_ARRAY_OFFSET + i) & 0xFFL) * PRIME64_5;
            h64 = Long.rotateLeft(h64, 11) * PRIME64_1;
            i++;
        }

        h64 ^= h64 >>> 33;
        h64 *= PRIME64_2;
        h64 ^= h64 >>> 29;
        h64 *= PRIME64_3;
        h64 ^= h64 >>> 32;

        return h64;
    }

    private static long processLarge(byte[] value, int len) {
        long v1 = PRIME64_1 + PRIME64_2;
        long v2 = PRIME64_2;
        long v3 = 0;
        long v4 = -PRIME64_1;

        int i = 0;
        while (i + 32 <= len) {
            v1 = round(v1, UNSAFE.getLong(value, BYTE_ARRAY_OFFSET + i));
            v2 = round(v2, UNSAFE.getLong(value, BYTE_ARRAY_OFFSET + i + 8));
            v3 = round(v3, UNSAFE.getLong(value, BYTE_ARRAY_OFFSET + i + 16));
            v4 = round(v4, UNSAFE.getLong(value, BYTE_ARRAY_OFFSET + i + 24));
            i += 32;
        }

        long h64 = Long.rotateLeft(v1, 1) + Long.rotateLeft(v2, 7) + Long.rotateLeft(v3, 12) + Long.rotateLeft(v4, 18);

        h64 = mergeRound(h64, v1);
        h64 = mergeRound(h64, v2);
        h64 = mergeRound(h64, v3);
        h64 = mergeRound(h64, v4);

        return h64;
    }

    private static long seed(int len) {
        return PRIME64_5;
    }

    private static long round(long acc, long input) {
        acc += input * PRIME64_2;
        acc = Long.rotateLeft(acc, 31);
        acc *= PRIME64_1;
        return acc;
    }

    private static long mergeRound(long acc, long val) {
        val = round(0, val);
        acc ^= val;
        acc = acc * PRIME64_1 + PRIME64_4;
        return acc;
    }
}
