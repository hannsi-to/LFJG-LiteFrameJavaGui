package me.hannsi.lfjg.utils.math;

import org.joml.Math;
import org.joml.Vector2f;

public class MathHelper {
    public static double PI_d = (float) java.lang.Math.PI;
    public static float PI = (float) java.lang.Math.PI;
    public static double PI_TIMES_2_d = PI_d * 2.0;
    public static float PI_TIMES_2 = (float) (PI_d * 2.0f);
    public static double PI_OVER_2_d = PI_d * 0.5;
    public static float PI_OVER_2 = (float) (PI_d * 0.5);
    public static double PI_OVER_4_d = PI_d * 0.25;
    public static float PI_OVER_4 = (float) (PI_d * 0.25);
    public static double ONE_OVER_PI_d = 1.0 / PI_d;
    public static float ONE_OVER_PI = (float) (1.0 / PI_d);
    public static double E_d = java.lang.Math.E;
    public static float E = (float) java.lang.Math.E;
    public static double TAU_d = java.lang.Math.TAU;
    public static float TAU = (float) java.lang.Math.TAU;

    public static int clamp(int a, int b, int val) {
        return max(a, min(b, val));
    }

    public static long clamp(long a, long b, long val) {
        return max(a, min(b, val));
    }

    public static float sin(float x) {
        return Math.sin(x);
    }

    public static double sin(double x) {
        return Math.sin(x);
    }

    public static float cos(float x) {
        return Math.cos(x);
    }

    public static double cos(double x) {
        return Math.cos(x);
    }

    public static float tan(float x) {
        return Math.tan(x);
    }

    public static double tan(double x) {
        return Math.tan(x);
    }

    public static float sinh(float x) {
        return (float) java.lang.Math.sinh(x);
    }

    public static double sinh(double x) {
        return java.lang.Math.sinh(x);
    }

    public static float cosh(float x) {
        return (float) java.lang.Math.cosh(x);
    }

    public static double cosh(double x) {
        return java.lang.Math.cosh(x);
    }

    public static float tanh(float x) {
        return (float) java.lang.Math.tanh(x);
    }

    public static double tanh(double x) {
        return java.lang.Math.tanh(x);
    }

    public static float cosFromSin(float sin, float angle) {
        return Math.cosFromSin(sin, angle);
    }

    public static double cosFromSin(double sin, double angle) {
        return Math.cosFromSin(sin, angle);
    }

    public static float sqrt(float r) {
        return (float) java.lang.Math.sqrt(r);
    }

    public static double sqrt(double r) {
        return java.lang.Math.sqrt(r);
    }

    public static float invsqrt(float r) {
        return 1.0f / (float) java.lang.Math.sqrt(r);
    }

    public static double invsqrt(double r) {
        return 1.0 / java.lang.Math.sqrt(r);
    }

    public static float acos(float r) {
        return (float) java.lang.Math.acos(r);
    }

    public static double acos(double r) {
        return java.lang.Math.acos(r);
    }

    public static float safeAcos(float v) {
        return Math.safeAcos(v);
    }

    public static double safeAcos(double v) {
        return Math.safeAcos(v);
    }

    public static float asin(float r) {
        return (float) java.lang.Math.asin(r);
    }

    public static double asin(double r) {
        return java.lang.Math.asin(r);
    }

    public static float safeAsin(float v) {
        return Math.safeAsin(v);
    }

    public static double safeAsin(double v) {
        return Math.safeAsin(v);
    }

    public static float atan(float r) {
        return (float) java.lang.Math.atan(r);
    }

    public static double atan(double r) {
        return java.lang.Math.atan(r);
    }

    public static float atan2(float y, float x) {
        return Math.atan2(y, x);
    }

    public static double atan2(double y, double x) {
        return Math.atan2(y, x);
    }

    public static float abs(float r) {
        return java.lang.Math.abs(r);
    }

    public static double abs(double r) {
        return java.lang.Math.abs(r);
    }

    public static int abs(int r) {
        return java.lang.Math.abs(r);
    }

    public static long abs(long r) {
        return java.lang.Math.abs(r);
    }

    public static int max(int x, int y) {
        return java.lang.Math.max(x, y);
    }

    public static int min(int x, int y) {
        return java.lang.Math.min(x, y);
    }

    public static long max(long x, long y) {
        return java.lang.Math.max(x, y);
    }

    public static long min(long x, long y) {
        return java.lang.Math.min(x, y);
    }

    public static double min(double a, double b) {
        return java.lang.Math.min(a, b);
    }

    public static float min(float a, float b) {
        return java.lang.Math.min(a, b);
    }

    public static float max(float a, float b) {
        return java.lang.Math.max(a, b);
    }

    public static double max(double a, double b) {
        return java.lang.Math.max(a, b);
    }

    public static float clamp(float a, float b, float val) {
        return max(a, min(b, val));
    }

    public static double clamp(double a, double b, double val) {
        return max(a, min(b, val));
    }

    public static float toRadians(float angles) {
        return (float) java.lang.Math.toRadians(angles);
    }

    public static double toRadians(double angles) {
        return java.lang.Math.toRadians(angles);
    }

    public static float toDegrees(float angles) {
        return (float) java.lang.Math.toDegrees(angles);
    }

    public static double toDegrees(double angles) {
        return java.lang.Math.toDegrees(angles);
    }

    public static double floor(double v) {
        return java.lang.Math.floor(v);
    }

    public static float floor(float v) {
        return (float) java.lang.Math.floor(v);
    }

    public static double ceil(double v) {
        return java.lang.Math.ceil(v);
    }

    public static float ceil(float v) {
        return (float) java.lang.Math.ceil(v);
    }

    public static long round(double v) {
        return java.lang.Math.round(v);
    }

    public static int round(float v) {
        return java.lang.Math.round(v);
    }

    public static double exp(double a) {
        return java.lang.Math.exp(a);
    }

    public static boolean isFinite(double d) {
        return abs(d) <= Double.MAX_VALUE;
    }

    public static boolean isFinite(float f) {
        return abs(f) <= Float.MAX_VALUE;
    }

    public static float fma(float a, float b, float c) {
        return Math.fma(a, b, c);
    }

    public static double fma(double a, double b, double c) {
        return Math.fma(a, b, c);
    }

    public static int roundUsing(float v, int mode) {
        return Math.roundUsing(v, mode);
    }

    public static int roundUsing(double v, int mode) {
        return Math.roundUsing(v, mode);
    }

    public static long roundLongUsing(double v, int mode) {
        return Math.roundLongUsing(v, mode);
    }

    public static float lerp(float a, float b, float t) {
        return Math.fma(b - a, t, a);
    }

    public static double lerp(double a, double b, double t) {
        return Math.fma(b - a, t, a);
    }

    public static float biLerp(float q00, float q10, float q01, float q11, float tx, float ty) {
        return Math.biLerp(q00, q10, q01, q11, tx, ty);
    }

    public static double biLerp(double q00, double q10, double q01, double q11, double tx, double ty) {
        return Math.biLerp(q00, q10, q01, q11, tx, ty);
    }

    public static float triLerp(float q000, float q100, float q010, float q110, float q001, float q101, float q011, float q111, float tx, float ty, float tz) {
        return Math.triLerp(q000, q100, q010, q110, q001, q101, q011, q111, tx, ty, tz);
    }

    public static double triLerp(double q000, double q100, double q010, double q110, double q001, double q101, double q011, double q111, double tx, double ty, double tz) {
        return Math.triLerp(q000, q100, q010, q110, q001, q101, q011, q111, tx, ty, tz);
    }

    public static int roundHalfEven(float v) {
        return (int) java.lang.Math.rint(v);
    }

    public static int roundHalfDown(float v) {
        return Math.roundHalfDown(v);
    }

    public static int roundHalfUp(float v) {
        return Math.roundHalfUp(v);
    }

    public static int roundHalfEven(double v) {
        return (int) java.lang.Math.rint(v);
    }

    public static int roundHalfDown(double v) {
        return Math.roundHalfDown(v);
    }

    public static int roundHalfUp(double v) {
        return Math.roundHalfUp(v);
    }

    public static long roundLongHalfEven(double v) {
        return (long) java.lang.Math.rint(v);
    }

    public static long roundLongHalfDown(double v) {
        return Math.roundLongHalfDown(v);
    }

    public static long roundLongHalfUp(double v) {
        return Math.roundLongHalfUp(v);
    }

    public static double random() {
        return java.lang.Math.random();
    }

    public static double signum(double v) {
        return java.lang.Math.signum(v);
    }

    public static float signum(float v) {
        return java.lang.Math.signum(v);
    }

    public static int signum(int v) {
        return Math.signum(v);
    }

    public static int signum(long v) {
        return Math.signum(v);
    }

    public static boolean isWithinRange(float x, float min, float max) {
        return (x > min && x < max);
    }

    public static boolean isWithinRange(double x, double min, double max) {
        return (x > min && x < max);
    }

    public static boolean isWithinRange(int x, int min, int max) {
        return (x > min && x < max);
    }

    public static float getLargest(float... values) {
        float result = 0.0f;

        for (float value : values) {
            result = java.lang.Math.max(result, value);
        }

        return result;
    }

    public static float getShortest(float... values) {
        float result = 0.0f;

        for (float value : values) {
            result = Math.max(result, value);
        }

        return result;
    }

    public static double getLargest(double... values) {
        double result = 0.0f;

        for (double value : values) {
            result = java.lang.Math.max(result, value);
        }

        return result;
    }

    public static double getShortest(double... values) {
        double result = 0.0f;

        for (double value : values) {
            result = Math.max(result, value);
        }

        return result;
    }

    public static float distance(float x, float y, float x1, float y1) {
        return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    }

    public static double distance(double x, double y, double x1, double y1) {
        return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    }

    public static float distance(Vector2f vector2f1, Vector2f vector2f2) {
        float[] width = sortValue(vector2f1.x(), vector2f2.x());
        float[] height = sortValue(vector2f1.y(), vector2f2.y());
        return MathHelper.distance(width[1], height[1], width[0], height[0]);
    }

    public static float[] sortValue(float value1, float value2) {
        float min = Math.min(value1, value2);
        float max = Math.max(value1, value2);
        return new float[]{min, max};
    }

    public static double[] sortValue(double value1, double value2) {
        double min = Math.min(value1, value2);
        double max = Math.max(value1, value2);
        return new double[]{min, max};
    }

    public static float calculateGaussianValue(float x, float sigma) {
        double output = 1.0 / Math.sqrt(2.0 * Math.PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

    public static double calculateGaussianValue(double x, double sigma) {
        double output = 1.0 / Math.sqrt(2.0 * Math.PI * (sigma * sigma));
        return (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

    public static float log(float a) {
        return (float) java.lang.Math.log(a);
    }

    public static double log(double a) {
        return java.lang.Math.log(a);
    }

    public static float log10(float a) {
        return (float) java.lang.Math.log10(a);
    }

    public static double log10(double a) {
        return java.lang.Math.log10(a);
    }

    public static float cbrt(float a) {
        return (float) java.lang.Math.cbrt(a);
    }

    public static double cbrt(double a) {
        return java.lang.Math.cbrt(a);
    }

    public static float IEEEremainder(float f1, float f2) {
        return (float) java.lang.Math.IEEEremainder(f1, f2);
    }

    public static double IEEEremainder(double f1, double f2) {
        return java.lang.Math.IEEEremainder(f1, f2);
    }

    public static float rint(float a) {
        return (float) java.lang.Math.rint(a);
    }

    public static double rint(double a) {
        return java.lang.Math.rint(a);
    }

    public static float pow(float a, float b) {
        return (float) java.lang.Math.pow(a, b);
    }

    public static double pow(double a, double b) {
        return java.lang.Math.pow(a, b);
    }

    public static float pow2(float a) {
        return (float) java.lang.Math.pow(a, 2);
    }

    public static double pow2(double a) {
        return java.lang.Math.pow(a, 2);
    }

    public static float pow3(float a) {
        return (float) java.lang.Math.pow(a, 3);
    }

    public static double pow3(double a) {
        return java.lang.Math.pow(a, 3);
    }

    public static float pow4(float a) {
        return (float) java.lang.Math.pow(a, 4);
    }

    public static double pow4(double a) {
        return java.lang.Math.pow(a, 4);
    }

    public static int addExact(int x, int y) {
        return java.lang.Math.addExact(x, y);
    }

    public static long addExact(long x, long y) {
        return java.lang.Math.addExact(x, y);
    }

    public static int subtractExact(int x, int y) {
        return java.lang.Math.subtractExact(x, y);
    }

    public static long subtractExact(long x, long y) {
        return java.lang.Math.subtractExact(x, y);
    }

    public static int multiplyExact(int x, int y) {
        return java.lang.Math.multiplyExact(x, y);
    }

    public static long multiplyExact(long x, long y) {
        return java.lang.Math.multiplyExact(x, y);
    }

    public static int divideExact(int x, int y) {
        return java.lang.Math.divideExact(x, y);
    }

    public static long divideExact(long x, long y) {
        return java.lang.Math.divideExact(x, y);
    }

    public static int floorDivExact(int x, int y) {
        return java.lang.Math.floorDivExact(x, y);
    }

    public static long floorDivExact(long x, long y) {
        return java.lang.Math.floorDivExact(x, y);
    }

    public static int floorModExact(int x, int y) {
        if (y == 0) {
            throw new ArithmeticException("division by zero");
        }

        int mod = floorMod(x, y);
        if ((x == Integer.MIN_VALUE) && (y == -1)) {
            throw new ArithmeticException("integer overflow");
        }

        return mod;
    }

    public static long floorModExact(long x, long y) {
        if (y == 0) {
            throw new ArithmeticException("division by zero");
        }

        long mod = floorMod(x, y);
        if ((x == Long.MIN_VALUE) && (y == -1)) {
            throw new ArithmeticException("long overflow");
        }

        return mod;
    }

    public static int ceilDivExact(int x, int y) {
        return java.lang.Math.ceilDivExact(x, y);
    }

    public static long ceilDivExact(long x, long y) {
        return java.lang.Math.ceilDivExact(x, y);
    }

    public static int ceilModExact(int x, int y) {
        if (y == 0) {
            throw new ArithmeticException("division by zero");
        }

        int mod = x % y;
        if ((x == Integer.MIN_VALUE) && (y == -1)) {
            throw new ArithmeticException("integer overflow");
        }

        return (mod == 0) ? 0 : (mod > 0 ? mod : mod + Math.abs(y));
    }

    public static long ceilModExact(long x, long y) {
        if (y == 0) {
            throw new ArithmeticException("division by zero");
        }

        long mod = x % y;
        if ((x == Long.MIN_VALUE) && (y == -1)) {
            throw new ArithmeticException("long overflow");
        }

        return (mod == 0) ? 0 : (mod > 0 ? mod : mod + Math.abs(y));
    }

    public static int incrementExact(int a) {
        return java.lang.Math.incrementExact(a);
    }

    public static long incrementExact(long a) {
        return java.lang.Math.incrementExact(a);
    }

    public static int decrementExact(int a) {
        return java.lang.Math.decrementExact(a);
    }

    public static long decrementExact(long a) {
        return java.lang.Math.decrementExact(a);
    }

    public static int negateExact(int a) {
        return java.lang.Math.negateExact(a);
    }

    public static long negateExact(long a) {
        return java.lang.Math.negateExact(a);
    }

    public static int toIntExact(long value) {
        return java.lang.Math.toIntExact(value);
    }

    public static long multiplyFull(int x, int y) {
        return (long) x * (long) y;
    }

    public static long multiplyHigh(long x, long y) {
        return java.lang.Math.multiplyHigh(x, y);
    }

    public static long unsignedMultiplyHigh(long x, long y) {
        return java.lang.Math.unsignedMultiplyHigh(x, y);
    }

    public static int floorDiv(int x, int y) {
        return java.lang.Math.floorDiv(x, y);
    }

    public static long floorDiv(long x, long y) {
        return java.lang.Math.floorDiv(x, y);
    }

    public static int floorMod(int x, int y) {
        return java.lang.Math.floorMod(x, y);
    }

    public static long floorMod(long x, long y) {
        return java.lang.Math.floorMod(x, y);
    }

    public static int ceilDiv(int x, int y) {
        return java.lang.Math.ceilDiv(x, y);
    }

    public static long ceilDiv(long x, long y) {
        return java.lang.Math.ceilDiv(x, y);
    }

    public static int ceilMod(int x, int y) {
        return java.lang.Math.ceilMod(x, y);
    }

    public static long ceilMod(long x, long y) {
        return java.lang.Math.ceilMod(x, y);
    }

    public static int getExponent(double d) {
        return java.lang.Math.getExponent(d);
    }

    public static int getExponent(float f) {
        return java.lang.Math.getExponent(f);
    }

    public static float ulp(float a) {
        return java.lang.Math.ulp(a);
    }

    public static double ulp(double a) {
        return java.lang.Math.ulp(a);
    }

    public static int absExact(int a) {
        return java.lang.Math.absExact(a);
    }

    public static long absExact(long a) {
        return java.lang.Math.absExact(a);
    }

    public static double hypot(double x, double y) {
        return java.lang.Math.hypot(x, y);
    }

    public static float hypot(float x, float y) {
        return (float) java.lang.Math.hypot(x, y);
    }

    public static double expm1(double x) {
        return java.lang.Math.expm1(x);
    }

    public static float expm1(float x) {
        return (float) java.lang.Math.expm1(x);
    }

    public static double log1p(double x) {
        return java.lang.Math.log1p(x);
    }

    public static float log1p(float x) {
        return (float) java.lang.Math.log1p(x);
    }

    public static double copySign(double magnitude, double sign) {
        return java.lang.Math.copySign(magnitude, sign);
    }

    public static float copySign(float magnitude, float sign) {
        return java.lang.Math.copySign(magnitude, sign);
    }

    public static double nextAfter(double start, double direction) {
        return java.lang.Math.nextAfter(start, direction);
    }

    public static float nextAfter(float start, float direction) {
        return java.lang.Math.nextAfter(start, direction);
    }

    public static double nextUp(double d) {
        return java.lang.Math.nextUp(d);
    }

    public static float nextUp(float f) {
        return java.lang.Math.nextUp(f);
    }

    public static double nextDown(double d) {
        return java.lang.Math.nextDown(d);
    }

    public static float nextDown(float f) {
        return java.lang.Math.nextDown(f);
    }

    public static double nextTowards(double start, double target) {
        if (Double.isNaN(start) || Double.isNaN(target)) {
            return Double.NaN;
        }

        if (start == target) {
            return start;
        }

        return (start < target) ? nextUp(start) : nextDown(start);
    }

    public static float nextTowards(float start, float target) {
        if (Float.isNaN(start) || Float.isNaN(target)) {
            return Float.NaN;
        }

        if (start == target) {
            return start;
        }

        return (start < target) ? nextUp(start) : nextDown(start);
    }

    public static double nextAfter(double start, long direction) {
        return java.lang.Math.nextAfter(start, direction);
    }

    public static float nextAfter(float start, int direction) {
        return java.lang.Math.nextAfter(start, direction);
    }

    public static double scalb(double d, int scaleFactor) {
        return java.lang.Math.scalb(d, scaleFactor);
    }

    public static float scalb(float f, int scaleFactor) {
        return java.lang.Math.scalb(f, scaleFactor);
    }

    public static double fastPow(double a, int b) {
        if (b == 0) {
            return 1.0;
        }

        if (b < 0) {
            a = 1.0 / a;
            b = -b;
        }

        double result = 1.0;
        double base = a;
        while (b > 0) {
            if ((b & 1) == 1) {
                result *= base;
            }
            base *= base;
            b >>= 1;
        }
        return result;
    }

    public static float fastPow(float a, int b) {
        return (float) fastPow((double) a, b);
    }

    public static long fastPow(long a, long b) {
        if (b < 0) {
            throw new IllegalArgumentException("Exponent must be non-negative");
        }

        long result = 1;
        long base = a;
        while (b > 0) {
            if ((b & 1) == 1) {
                result *= base;
            }
            base *= base;
            b >>= 1;
        }

        return result;
    }

    public static int fastPow(int a, int b) {
        if (b == 0) {
            return 1;
        }

        if (b < 0) {
            a = 1 / a;
            b = -b;
        }

        int result = 1;
        int base = a;
        while (b > 0) {
            if ((b & 1) == 1) {
                result *= base;
            }
            base *= base;
            b >>= 1;
        }
        return result;
    }

    public static boolean mathIsPoT(int value) {
        return org.lwjgl.system.MathUtil.mathIsPoT(value);
    }

    public static int mathRoundPoT(int value) {
        return org.lwjgl.system.MathUtil.mathRoundPoT(value);
    }

    public static boolean mathHasZeroByte(int value) {
        return org.lwjgl.system.MathUtil.mathHasZeroByte(value);
    }

    public static boolean mathHasZeroByte(long value) {
        return org.lwjgl.system.MathUtil.mathHasZeroByte(value);
    }

    public static boolean mathHasZeroShort(int value) {
        return org.lwjgl.system.MathUtil.mathHasZeroShort(value);
    }

    public static boolean mathHasZeroShort(long value) {
        return org.lwjgl.system.MathUtil.mathHasZeroShort(value);
    }

    public static long mathMultiplyHighU64(long x, long y) {
        return org.lwjgl.system.MathUtil.mathMultiplyHighU64(x, y);
    }

    public static long mathMultiplyHighS64(long x, long y) {
        return org.lwjgl.system.MathUtil.mathMultiplyHighS64(x, y);
    }

    public static long mathDivideUnsigned(long dividend, long divisor) {
        return org.lwjgl.system.MathUtil.mathDivideUnsigned(dividend, divisor);
    }

    public static long mathRemainderUnsigned(long dividend, long divisor) {
        return org.lwjgl.system.MathUtil.mathRemainderUnsigned(dividend, divisor);
    }
}