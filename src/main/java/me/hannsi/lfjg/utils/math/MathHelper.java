package me.hannsi.lfjg.utils.math;

import org.joml.Math;
import org.joml.Vector2f;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class providing various mathematical functions and constants.
 */
public class MathHelper {
    /**
     * Value of PI as double.
     */
    public static double PI_d = (float) java.lang.Math.PI;
    /**
     * Value of PI as float.
     */
    public static float PI = (float) java.lang.Math.PI;
    /**
     * Value of 2*PI as double.
     */
    public static double PI_TIMES_2_d = PI_d * 2.0;
    /**
     * Value of 2*PI as float.
     */
    public static float PI_TIMES_2 = (float) (PI_d * 2.0f);
    /**
     * Value of PI/2 as double.
     */
    public static double PI_OVER_2_d = PI_d * 0.5;
    /**
     * Value of PI/2 as float.
     */
    public static float PI_OVER_2 = (float) (PI_d * 0.5);
    /**
     * Value of PI/4 as double.
     */
    public static double PI_OVER_4_d = PI_d * 0.25;
    /**
     * Value of PI/4 as float.
     */
    public static float PI_OVER_4 = (float) (PI_d * 0.25);
    /**
     * Value of 1/PI as double.
     */
    public static double ONE_OVER_PI_d = 1.0 / PI_d;
    /**
     * Value of 1/PI as float.
     */
    public static float ONE_OVER_PI = (float) (1.0 / PI_d);
    /**
     * Value of E as double.
     */
    public static double E_d = java.lang.Math.E;
    /**
     * Value of E as float.
     */
    public static float E = (float) java.lang.Math.E;
    /**
     * Value of TAU as double.
     */
    public static double TAU_d = java.lang.Math.TAU;
    /**
     * Value of TAU as float.
     */
    public static float TAU = (float) java.lang.Math.TAU;

    /**
     * Clamps the given value between a and b.
     *
     * @param a   the lower bound
     * @param b   the upper bound
     * @param val the value to clamp
     * @return the clamped value
     */
    public static int clamp(int a, int b, int val) {
        return max(a, min(b, val));
    }

    /**
     * Clamps the given value between a and b.
     *
     * @param a   the lower bound
     * @param b   the upper bound
     * @param val the value to clamp
     * @return the clamped value
     */
    public static long clamp(long a, long b, long val) {
        return max(a, min(b, val));
    }

    /**
     * Returns the sine of the given angle.
     *
     * @param x the angle in radians
     * @return the sine of the angle
     */
    public static float sin(float x) {
        return Math.sin(x);
    }

    /**
     * Returns the sine of the given angle.
     *
     * @param x the angle in radians
     * @return the sine of the angle
     */
    public static double sin(double x) {
        return Math.sin(x);
    }

    /**
     * Returns the cosine of the given angle.
     *
     * @param x the angle in radians
     * @return the cosine of the angle
     */
    public static float cos(float x) {
        return Math.cos(x);
    }

    /**
     * Returns the cosine of the given angle.
     *
     * @param x the angle in radians
     * @return the cosine of the angle
     */
    public static double cos(double x) {
        return Math.cos(x);
    }

    /**
     * Returns the tangent of the given angle.
     *
     * @param x the angle in radians
     * @return the tangent of the angle
     */
    public static float tan(float x) {
        return Math.tan(x);
    }

    /**
     * Returns the tangent of the given angle.
     *
     * @param x the angle in radians
     * @return the tangent of the angle
     */
    public static double tan(double x) {
        return Math.tan(x);
    }

    /**
     * Returns the hyperbolic sine of the given value.
     *
     * @param x the value
     * @return the hyperbolic sine of the value
     */
    public static float sinh(float x) {
        return (float) java.lang.Math.sinh(x);
    }

    /**
     * Returns the hyperbolic sine of the given value.
     *
     * @param x the value
     * @return the hyperbolic sine of the value
     */
    public static double sinh(double x) {
        return java.lang.Math.sinh(x);
    }

    /**
     * Returns the hyperbolic cosine of the given value.
     *
     * @param x the value
     * @return the hyperbolic cosine of the value
     */
    public static float cosh(float x) {
        return (float) java.lang.Math.cosh(x);
    }

    /**
     * Returns the hyperbolic cosine of the given value.
     *
     * @param x the value
     * @return the hyperbolic cosine of the value
     */
    public static double cosh(double x) {
        return java.lang.Math.cosh(x);
    }

    /**
     * Returns the hyperbolic tangent of the given value.
     *
     * @param x the value
     * @return the hyperbolic tangent of the value
     */
    public static float tanh(float x) {
        return (float) java.lang.Math.tanh(x);
    }

    /**
     * Returns the hyperbolic tangent of the given value.
     *
     * @param x the value
     * @return the hyperbolic tangent of the value
     */
    public static double tanh(double x) {
        return java.lang.Math.tanh(x);
    }

    /**
     * Returns the cosine of the given angle using the sine.
     *
     * @param sin   the sine of the angle
     * @param angle the angle in radians
     * @return the cosine of the angle
     */
    public static float cosFromSin(float sin, float angle) {
        return Math.cosFromSin(sin, angle);
    }

    /**
     * Returns the cosine of the given angle using the sine.
     *
     * @param sin   the sine of the angle
     * @param angle the angle in radians
     * @return the cosine of the angle
     */
    public static double cosFromSin(double sin, double angle) {
        return Math.cosFromSin(sin, angle);
    }

    /**
     * Returns the square root of the given value.
     *
     * @param r the value
     * @return the square root of the value
     */
    public static float sqrt(float r) {
        return (float) java.lang.Math.sqrt(r);
    }

    /**
     * Returns the square root of the given value.
     *
     * @param r the value
     * @return the square root of the value
     */
    public static double sqrt(double r) {
        return java.lang.Math.sqrt(r);
    }

    /**
     * Returns the inverse square root of the given value.
     *
     * @param r the value
     * @return the inverse square root of the value
     */
    public static float invsqrt(float r) {
        return 1.0f / (float) java.lang.Math.sqrt(r);
    }

    /**
     * Returns the inverse square root of the given value.
     *
     * @param r the value
     * @return the inverse square root of the value
     */
    public static double invsqrt(double r) {
        return 1.0 / java.lang.Math.sqrt(r);
    }

    /**
     * Returns the arc cosine of the given value.
     *
     * @param r the value
     * @return the arc cosine of the value
     */
    public static float acos(float r) {
        return (float) java.lang.Math.acos(r);
    }

    /**
     * Returns the arc cosine of the given value.
     *
     * @param r the value
     * @return the arc cosine of the value
     */
    public static double acos(double r) {
        return java.lang.Math.acos(r);
    }

    /**
     * Returns the arc cosine of the given value, clamped to the range [-1, 1].
     *
     * @param v the value
     * @return the arc cosine of the value
     */
    public static float safeAcos(float v) {
        return Math.safeAcos(v);
    }

    /**
     * Returns the arc cosine of the given value, clamped to the range [-1, 1].
     *
     * @param v the value
     * @return the arc cosine of the value
     */
    public static double safeAcos(double v) {
        return Math.safeAcos(v);
    }

    /**
     * Returns the arc sine of the given value.
     *
     * @param r the value
     * @return the arc sine of the value
     */
    public static float asin(float r) {
        return (float) java.lang.Math.asin(r);
    }

    /**
     * Returns the arc sine of the given value.
     *
     * @param r the value
     * @return the arc sine of the value
     */
    public static double asin(double r) {
        return java.lang.Math.asin(r);
    }

    /**
     * Returns the arc sine of the given value, clamped to the range [-1, 1].
     *
     * @param v the value
     * @return the arc sine of the value
     */
    public static float safeAsin(float v) {
        return Math.safeAsin(v);
    }

    /**
     * Returns the arc sine of the given value, clamped to the range [-1, 1].
     *
     * @param v the value
     * @return the arc sine of the value
     */
    public static double safeAsin(double v) {
        return Math.safeAsin(v);
    }

    /**
     * Returns the arc tangent of the given value.
     *
     * @param r the value
     * @return the arc tangent of the value
     */
    public static float atan(float r) {
        return (float) java.lang.Math.atan(r);
    }

    /**
     * Returns the arc tangent of the given value.
     *
     * @param r the value
     * @return the arc tangent of the value
     */
    public static double atan(double r) {
        return java.lang.Math.atan(r);
    }

    /**
     * Returns the arc tangent of the quotient of given values.
     *
     * @param y the numerator
     * @param x the denominator
     * @return the arc tangent of the quotient
     */
    public static float atan2(float y, float x) {
        return Math.atan2(y, x);
    }

    /**
     * Returns the arc tangent of the quotient of given values.
     *
     * @param y the numerator
     * @param x the denominator
     * @return the arc tangent of the quotient
     */
    public static double atan2(double y, double x) {
        return Math.atan2(y, x);
    }

    /**
     * Returns the absolute value of the given value.
     *
     * @param r the value
     * @return the absolute value of the value
     */
    public static float abs(float r) {
        return java.lang.Math.abs(r);
    }

    /**
     * Returns the absolute value of the given value.
     *
     * @param r the value
     * @return the absolute value of the value
     */
    public static double abs(double r) {
        return java.lang.Math.abs(r);
    }

    /**
     * Returns the absolute value of the given value.
     *
     * @param r the value
     * @return the absolute value of the value
     */
    public static int abs(int r) {
        return java.lang.Math.abs(r);
    }

    /**
     * Returns the absolute value of the given value.
     *
     * @param r the value
     * @return the absolute value of the value
     */
    public static long abs(long r) {
        return java.lang.Math.abs(r);
    }

    /**
     * Returns the maximum of the two given values.
     *
     * @param x the first value
     * @param y the second value
     * @return the maximum of the two values
     */
    public static int max(int x, int y) {
        return java.lang.Math.max(x, y);
    }

    /**
     * Returns the minimum of the two given values.
     *
     * @param x the first value
     * @param y the second value
     * @return the minimum of the two values
     */
    public static int min(int x, int y) {
        return java.lang.Math.min(x, y);
    }

    /**
     * Returns the maximum of the two given values.
     *
     * @param x the first value
     * @param y the second value
     * @return the maximum of the two values
     */
    public static long max(long x, long y) {
        return java.lang.Math.max(x, y);
    }

    /**
     * Returns the minimum of the two given values.
     *
     * @param x the first value
     * @param y the second value
     * @return the minimum of the two values
     */
    public static long min(long x, long y) {
        return java.lang.Math.min(x, y);
    }

    /**
     * Returns the minimum of the two given values.
     *
     * @param a the first value
     * @param b the second value
     * @return the minimum of the two values
     */
    public static double min(double a, double b) {
        return java.lang.Math.min(a, b);
    }

    /**
     * Returns the minimum of the two given values.
     *
     * @param a the first value
     * @param b the second value
     * @return the minimum of the two values
     */
    public static float min(float a, float b) {
        return java.lang.Math.min(a, b);
    }

    /**
     * Returns the maximum of the two given values.
     *
     * @param a the first value
     * @param b the second value
     * @return the maximum of the two values
     */
    public static float max(float a, float b) {
        return java.lang.Math.max(a, b);
    }

    /**
     * Returns the maximum of the two given values.
     *
     * @param a the first value
     * @param b the second value
     * @return the maximum of the two values
     */
    public static double max(double a, double b) {
        return java.lang.Math.max(a, b);
    }

    /**
     * Clamps the given value between a and b.
     *
     * @param a   the lower bound
     * @param b   the upper bound
     * @param val the value to clamp
     * @return the clamped value
     */
    public static float clamp(float a, float b, float val) {
        return max(a, min(b, val));
    }

    /**
     * Clamps the given value between a and b.
     *
     * @param a   the lower bound
     * @param b   the upper bound
     * @param val the value to clamp
     * @return the clamped value
     */
    public static double clamp(double a, double b, double val) {
        return max(a, min(b, val));
    }

    /**
     * Converts the given angle from degrees to radians.
     *
     * @param angles the angle in degrees
     * @return the angle in radians
     */
    public static float toRadians(float angles) {
        return (float) java.lang.Math.toRadians(angles);
    }

    /**
     * Converts the given angle from degrees to radians.
     *
     * @param angles the angle in degrees
     * @return the angle in radians
     */
    public static double toRadians(double angles) {
        return java.lang.Math.toRadians(angles);
    }

    /**
     * Converts the given angle from radians to degrees.
     *
     * @param angles the angle in radians
     * @return the angle in degrees
     */
    public static float toDegrees(float angles) {
        return (float) java.lang.Math.toDegrees(angles);
    }

    /**
     * Converts the given angle from radians to degrees.
     *
     * @param angles the angle in radians
     * @return the angle in degrees
     */
    public static double toDegrees(double angles) {
        return java.lang.Math.toDegrees(angles);
    }

    /**
     * Returns the largest (closest to positive infinity) double value that is less than or equal to the argument and is equal to a mathematical integer.
     *
     * @param v the value
     * @return the largest double value that is less than or equal to the argument and is equal to a mathematical integer
     */
    public static double floor(double v) {
        return java.lang.Math.floor(v);
    }

    /**
     * Returns the largest (closest to positive infinity) float value that is less than or equal to the argument and is equal to a mathematical integer.
     *
     * @param v the value
     * @return the largest float value that is less than or equal to the argument and is equal to a mathematical integer
     */
    public static float floor(float v) {
        return (float) java.lang.Math.floor(v);
    }

    /**
     * Returns the smallest (closest to negative infinity) double value that is greater than or equal to the argument and is equal to a mathematical integer.
     *
     * @param v the value
     * @return the smallest double value that is greater than or equal to the argument and is equal to a mathematical integer
     */
    public static double ceil(double v) {
        return java.lang.Math.ceil(v);
    }

    /**
     * Returns the smallest (closest to negative infinity) float value that is greater than or equal to the argument and is equal to a mathematical integer.
     *
     * @param v the value
     * @return the smallest float value that is greater than or equal to the argument and is equal to a mathematical integer
     */
    public static float ceil(float v) {
        return (float) java.lang.Math.ceil(v);
    }

    /**
     * Returns the closest long to the argument.
     *
     * @param v the value
     * @return the closest long to the argument
     */
    public static long round(double v) {
        return java.lang.Math.round(v);
    }

    /**
     * Returns the closest int to the argument.
     *
     * @param v the value
     * @return the closest int to the argument
     */
    public static int round(float v) {
        return java.lang.Math.round(v);
    }

    /**
     * Returns Euler's number e raised to the power of a double value.
     *
     * @param a the exponent
     * @return the value e^a, where e is the base of the natural logarithms
     */
    public static double exp(double a) {
        return java.lang.Math.exp(a);
    }

    /**
     * Returns Euler's number e raised to the power of a double value.
     *
     * @param a the exponent
     * @return the value e^a, where e is the base of the natural logarithms
     */
    public static float exp(float a) {
        return (float) java.lang.Math.exp(a);
    }

    /**
     * Checks if the given double value is finite.
     *
     * @param d the double value to check
     * @return true if the value is finite, false otherwise
     */
    public static boolean isFinite(double d) {
        return abs(d) <= Double.MAX_VALUE;
    }

    /**
     * Checks if the given float value is finite.
     *
     * @param f the float value to check
     * @return true if the value is finite, false otherwise
     */
    public static boolean isFinite(float f) {
        return abs(f) <= Float.MAX_VALUE;
    }

    /**
     * Performs a fused multiply-add operation on the given float values.
     *
     * @param a the first operand
     * @param b the second operand
     * @param c the third operand
     * @return the result of (a * b) + c
     */
    public static float fma(float a, float b, float c) {
        return Math.fma(a, b, c);
    }

    /**
     * Performs a fused multiply-add operation on the given double values.
     *
     * @param a the first operand
     * @param b the second operand
     * @param c the third operand
     * @return the result of (a * b) + c
     */
    public static double fma(double a, double b, double c) {
        return Math.fma(a, b, c);
    }

    /**
     * Rounds the given float value using the specified rounding mode.
     *
     * @param v    the value to round
     * @param mode the rounding mode to use
     * @return the rounded value
     */
    public static int roundUsing(float v, int mode) {
        return Math.roundUsing(v, mode);
    }

    /**
     * Rounds the given double value using the specified rounding mode.
     *
     * @param v    the value to round
     * @param mode the rounding mode to use
     * @return the rounded value
     */
    public static int roundUsing(double v, int mode) {
        return Math.roundUsing(v, mode);
    }

    /**
     * Rounds the given double value to a long using the specified rounding mode.
     *
     * @param v    the value to round
     * @param mode the rounding mode to use
     * @return the rounded long value
     */
    public static long roundLongUsing(double v, int mode) {
        return Math.roundLongUsing(v, mode);
    }

    /**
     * Performs a linear interpolation between two float values.
     *
     * @param a the starting value
     * @param b the ending value
     * @param t the interpolation factor (between 0 and 1)
     * @return the interpolated value
     */
    public static float lerp(float a, float b, float t) {
        return Math.fma(b - a, t, a);
    }

    /**
     * Performs a linear interpolation between two double values.
     *
     * @param a the starting value
     * @param b the ending value
     * @param t the interpolation factor (between 0 and 1)
     * @return the interpolated value
     */
    public static double lerp(double a, double b, double t) {
        return Math.fma(b - a, t, a);
    }

    /**
     * Performs a bilinear interpolation on four float values.
     *
     * @param q00 the top-left value
     * @param q10 the top-right value
     * @param q01 the bottom-left value
     * @param q11 the bottom-right value
     * @param tx  the horizontal interpolation factor (between 0 and 1)
     * @param ty  the vertical interpolation factor (between 0 and 1)
     * @return the interpolated value
     */
    public static float biLerp(float q00, float q10, float q01, float q11, float tx, float ty) {
        return Math.biLerp(q00, q10, q01, q11, tx, ty);
    }

    /**
     * Performs a bilinear interpolation on four double values.
     *
     * @param q00 the top-left value
     * @param q10 the top-right value
     * @param q01 the bottom-left value
     * @param q11 the bottom-right value
     * @param tx  the horizontal interpolation factor (between 0 and 1)
     * @param ty  the vertical interpolation factor (between 0 and 1)
     * @return the interpolated value
     */
    public static double biLerp(double q00, double q10, double q01, double q11, double tx, double ty) {
        return Math.biLerp(q00, q10, q01, q11, tx, ty);
    }

    /**
     * Performs a trilinear interpolation on eight float values.
     *
     * @param q000 the front-top-left value
     * @param q100 the front-top-right value
     * @param q010 the front-bottom-left value
     * @param q110 the front-bottom-right value
     * @param q001 the back-top-left value
     * @param q101 the back-top-right value
     * @param q011 the back-bottom-left value
     * @param q111 the back-bottom-right value
     * @param tx   the horizontal interpolation factor (between 0 and 1)
     * @param ty   the vertical interpolation factor (between 0 and 1)
     * @param tz   the depth interpolation factor (between 0 and 1)
     * @return the interpolated value
     */
    public static float triLerp(float q000, float q100, float q010, float q110, float q001, float q101, float q011, float q111, float tx, float ty, float tz) {
        return Math.triLerp(q000, q100, q010, q110, q001, q101, q011, q111, tx, ty, tz);
    }

    /**
     * Performs a trilinear interpolation on eight double values.
     *
     * @param q000 the front-top-left value
     * @param q100 the front-top-right value
     * @param q010 the front-bottom-left value
     * @param q110 the front-bottom-right value
     * @param q001 the back-top-left value
     * @param q101 the back-top-right value
     * @param q011 the back-bottom-left value
     * @param q111 the back-bottom-right value
     * @param tx   the horizontal interpolation factor (between 0 and 1)
     * @param ty   the vertical interpolation factor (between 0 and 1)
     * @param tz   the depth interpolation factor (between 0 and 1)
     * @return the interpolated value
     */
    public static double triLerp(double q000, double q100, double q010, double q110, double q001, double q101, double q011, double q111, double tx, double ty, double tz) {
        return Math.triLerp(q000, q100, q010, q110, q001, q101, q011, q111, tx, ty, tz);
    }

    /**
     * Rounds the given float value to the nearest integer using the "half-even" rounding mode.
     *
     * @param v the value to round
     * @return the rounded integer value
     */
    public static int roundHalfEven(float v) {
        return (int) java.lang.Math.rint(v);
    }

    /**
     * Rounds the given float value to the nearest integer using the "half-down" rounding mode.
     *
     * @param v the value to round
     * @return the rounded integer value
     */
    public static int roundHalfDown(float v) {
        return Math.roundHalfDown(v);
    }

    /**
     * Rounds the given float value to the nearest integer using the "half-up" rounding mode.
     *
     * @param v the value to round
     * @return the rounded integer value
     */
    public static int roundHalfUp(float v) {
        return Math.roundHalfUp(v);
    }

    /**
     * Rounds the given double value to the nearest integer using the "half-even" rounding mode.
     *
     * @param v the value to round
     * @return the rounded integer value
     */
    public static int roundHalfEven(double v) {
        return (int) java.lang.Math.rint(v);
    }

    /**
     * Rounds the given double value to the nearest integer using the "half-down" rounding mode.
     *
     * @param v the value to round
     * @return the rounded integer value
     */
    public static int roundHalfDown(double v) {
        return Math.roundHalfDown(v);
    }

    /**
     * Rounds the given double value to the nearest integer using the "half-up" rounding mode.
     *
     * @param v the value to round
     * @return the rounded integer value
     */
    public static int roundHalfUp(double v) {
        return Math.roundHalfUp(v);
    }

    /**
     * Rounds the given double value to the nearest long integer using the "half-even" rounding mode.
     *
     * @param v the value to round
     * @return the rounded long value
     */
    public static long roundLongHalfEven(double v) {
        return (long) java.lang.Math.rint(v);
    }

    /**
     * Rounds the given double value to the nearest long integer using the "half-down" rounding mode.
     *
     * @param v the value to round
     * @return the rounded long value
     */
    public static long roundLongHalfDown(double v) {
        return Math.roundLongHalfDown(v);
    }

    /**
     * Rounds the given double value to the nearest long integer using the "half-up" rounding mode.
     *
     * @param v the value to round
     * @return the rounded long value
     */
    public static long roundLongHalfUp(double v) {
        return Math.roundLongHalfUp(v);
    }

    /**
     * Returns a random double value between 0.0 and 1.0.
     *
     * @return a random double value
     */
    public static double random() {
        return java.lang.Math.random();
    }

    /**
     * Returns the sign of the given double value.
     *
     * @param v the value to evaluate
     * @return 1.0 if v is positive, -1.0 if v is negative, or 0.0 if v is zero
     */
    public static double signum(double v) {
        return java.lang.Math.signum(v);
    }

    /**
     * Returns the sign of the given float value.
     *
     * @param v the value to evaluate
     * @return 1.0f if v is positive, -1.0f if v is negative, or 0.0f if v is zero
     */
    public static float signum(float v) {
        return java.lang.Math.signum(v);
    }

    /**
     * Returns the sign of the given integer value.
     *
     * @param v the value to evaluate
     * @return 1 if v is positive, -1 if v is negative, or 0 if v is zero
     */
    public static int signum(int v) {
        return Math.signum(v);
    }

    /**
     * Returns the sign of the given long value.
     *
     * @param v the value to evaluate
     * @return 1 if v is positive, -1 if v is negative, or 0 if v is zero
     */
    public static int signum(long v) {
        return Math.signum(v);
    }

    /**
     * Checks if the given float value is within the specified range.
     *
     * @param x   the value to check
     * @param min the minimum value of the range
     * @param max the maximum value of the range
     * @return true if x is within the range, false otherwise
     */
    public static boolean isWithinRange(float x, float min, float max) {
        return (x > min && x < max);
    }

    /**
     * Checks if the given double value is within the specified range.
     *
     * @param x   the value to check
     * @param min the minimum value of the range
     * @param max the maximum value of the range
     * @return true if x is within the range, false otherwise
     */
    public static boolean isWithinRange(double x, double min, double max) {
        return (x > min && x < max);
    }

    /**
     * Checks if the given integer value is within the specified range.
     *
     * @param x   the value to check
     * @param min the minimum value of the range
     * @param max the maximum value of the range
     * @return true if x is within the range, false otherwise
     */
    public static boolean isWithinRange(int x, int min, int max) {
        return (x > min && x < max);
    }

    /**
     * Returns the largest float value from the given values.
     *
     * @param values the values to compare
     * @return the largest value
     */
    public static float getLargest(float... values) {
        float result = 0.0f;

        for (float value : values) {
            result = java.lang.Math.max(result, value);
        }

        return result;
    }

    /**
     * Returns the smallest (shortest) float value from the given values.
     *
     * @param values the values to compare
     * @return the smallest value
     */
    public static float getShortest(float... values) {
        float result = 0.0f;

        for (float value : values) {
            result = Math.max(result, value);
        }

        return result;
    }

    /**
     * Returns the largest double value from the given values.
     *
     * @param values the values to compare
     * @return the largest value
     */
    public static double getLargest(double... values) {
        double result = 0.0f;

        for (double value : values) {
            result = java.lang.Math.max(result, value);
        }

        return result;
    }

    /**
     * Returns the smallest (shortest) double value from the given values.
     *
     * @param values the values to compare
     * @return the smallest value
     */
    public static double getShortest(double... values) {
        double result = 0.0f;

        for (double value : values) {
            result = Math.max(result, value);
        }

        return result;
    }

    /**
     * Calculates the distance between two points in 2D space (float).
     *
     * @param x  The x-coordinate of the first point.
     * @param y  The y-coordinate of the first point.
     * @param x1 The x-coordinate of the second point.
     * @param y1 The y-coordinate of the second point.
     * @return The Euclidean distance between the two points.
     */
    public static float distance(float x, float y, float x1, float y1) {
        return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    }

    /**
     * Calculates the distance between two points in 2D space (double).
     *
     * @param x  The x-coordinate of the first point.
     * @param y  The y-coordinate of the first point.
     * @param x1 The x-coordinate of the second point.
     * @param y1 The y-coordinate of the second point.
     * @return The Euclidean distance between the two points.
     */
    public static double distance(double x, double y, double x1, double y1) {
        return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    }

    /**
     * Calculates the distance between two 2D vectors.
     *
     * @param vector2f1 The first vector.
     * @param vector2f2 The second vector.
     * @return The Euclidean distance between the two vectors.
     */
    public static float distance(Vector2f vector2f1, Vector2f vector2f2) {
        float[] width = sortValue(vector2f1.x(), vector2f2.x());
        float[] height = sortValue(vector2f1.y(), vector2f2.y());
        return MathHelper.distance(width[1], height[1], width[0], height[0]);
    }

    /**
     * Sorts two float values in ascending order.
     *
     * @param value1 The first value.
     * @param value2 The second value.
     * @return A sorted array where the first element is the minimum and the second is the maximum value.
     */
    public static float[] sortValue(float value1, float value2) {
        float min = Math.min(value1, value2);
        float max = Math.max(value1, value2);
        return new float[]{min, max};
    }

    /**
     * Sorts two double values in ascending order.
     *
     * @param value1 The first value.
     * @param value2 The second value.
     * @return A sorted array where the first element is the minimum and the second is the maximum value.
     */
    public static double[] sortValue(double value1, double value2) {
        double min = Math.min(value1, value2);
        double max = Math.max(value1, value2);
        return new double[]{min, max};
    }

    /**
     * Calculates the Gaussian value of x for a given sigma.
     *
     * @param x     The input value.
     * @param sigma The standard deviation.
     * @return The calculated Gaussian value.
     */
    public static float calculateGaussianValue(float x, float sigma) {
        double output = 1.0 / Math.sqrt(2.0 * Math.PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

    /**
     * Calculates the Gaussian value of x for a given sigma.
     *
     * @param x     The input value.
     * @param sigma The standard deviation.
     * @return The calculated Gaussian value.
     */
    public static double calculateGaussianValue(double x, double sigma) {
        double output = 1.0 / Math.sqrt(2.0 * Math.PI * (sigma * sigma));
        return (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

    /**
     * Returns the natural logarithm (base e) of a float value.
     *
     * @param a The value.
     * @return The natural logarithm of the value.
     */
    public static float log(float a) {
        return (float) java.lang.Math.log(a);
    }

    /**
     * Returns the natural logarithm (base e) of a double value.
     *
     * @param a The value.
     * @return The natural logarithm of the value.
     */
    public static double log(double a) {
        return java.lang.Math.log(a);
    }

    /**
     * Returns the base 10 logarithm of a float value.
     *
     * @param a The value.
     * @return The base 10 logarithm of the value.
     */
    public static float log10(float a) {
        return (float) java.lang.Math.log10(a);
    }

    /**
     * Returns the base 10 logarithm of a double value.
     *
     * @param a The value.
     * @return The base 10 logarithm of the value.
     */
    public static double log10(double a) {
        return java.lang.Math.log10(a);
    }

    /**
     * Returns the cube root of a float value.
     *
     * @param a The value.
     * @return The cube root of the value.
     */
    public static float cbrt(float a) {
        return (float) java.lang.Math.cbrt(a);
    }

    /**
     * Returns the cube root of a double value.
     *
     * @param a The value.
     * @return The cube root of the value.
     */
    public static double cbrt(double a) {
        return java.lang.Math.cbrt(a);
    }

    /**
     * Returns the remainder of dividing two float values, following the IEEE 754 standard.
     *
     * @param f1 The dividend.
     * @param f2 The divisor.
     * @return The remainder.
     */
    public static float IEEEremainder(float f1, float f2) {
        return (float) java.lang.Math.IEEEremainder(f1, f2);
    }

    /**
     * Returns the remainder of dividing two double values, following the IEEE 754 standard.
     *
     * @param f1 The dividend.
     * @param f2 The divisor.
     * @return The remainder.
     */
    public static double IEEEremainder(double f1, double f2) {
        return java.lang.Math.IEEEremainder(f1, f2);
    }

    /**
     * Returns the integer closest to the float argument, rounding up or down as necessary.
     *
     * @param a The value.
     * @return The rounded value.
     */
    public static float rint(float a) {
        return (float) java.lang.Math.rint(a);
    }

    /**
     * Returns the integer closest to the double argument, rounding up or down as necessary.
     *
     * @param a The value.
     * @return The rounded value.
     */
    public static double rint(double a) {
        return java.lang.Math.rint(a);
    }

    /**
     * Returns the value of the first argument raised to the power of the second argument (float).
     *
     * @param a The base.
     * @param b The exponent.
     * @return The result of a raised to the power of b.
     */
    public static float pow(float a, float b) {
        return (float) java.lang.Math.pow(a, b);
    }

    /**
     * Returns the value of the first argument raised to the power of the second argument (double).
     *
     * @param a The base.
     * @param b The exponent.
     * @return The result of a raised to the power of b.
     */
    public static double pow(double a, double b) {
        return java.lang.Math.pow(a, b);
    }

    /**
     * Returns the square of the given float value.
     *
     * @param a The value.
     * @return The square of the value.
     */
    public static float pow2(float a) {
        return (float) java.lang.Math.pow(a, 2);
    }

    /**
     * Returns the square of the given double value.
     *
     * @param a The value.
     * @return The square of the value.
     */
    public static double pow2(double a) {
        return java.lang.Math.pow(a, 2);
    }

    /**
     * Returns the cube of the given float value.
     *
     * @param a The value.
     * @return The cube of the value.
     */
    public static float pow3(float a) {
        return (float) java.lang.Math.pow(a, 3);
    }

    /**
     * Returns the cube of the given double value.
     *
     * @param a The value.
     * @return The cube of the value.
     */
    public static double pow3(double a) {
        return java.lang.Math.pow(a, 3);
    }

    /**
     * Returns the fourth power of the given float value.
     *
     * @param a The value.
     * @return The fourth power of the value.
     */
    public static float pow4(float a) {
        return (float) java.lang.Math.pow(a, 4);
    }

    /**
     * Returns the fourth power of the given double value.
     *
     * @param a The value.
     * @return The fourth power of the value.
     */
    public static double pow4(double a) {
        return java.lang.Math.pow(a, 4);
    }

    /**
     * Adds two integers, throwing an exception if an overflow occurs.
     *
     * @param x The first integer.
     * @param y The second integer.
     * @return The sum of the two integers.
     * @throws ArithmeticException If overflow occurs.
     */
    public static int addExact(int x, int y) {
        return java.lang.Math.addExact(x, y);
    }

    /**
     * Adds two long integers, throwing an exception if an overflow occurs.
     *
     * @param x The first long.
     * @param y The second long.
     * @return The sum of the two longs.
     * @throws ArithmeticException If overflow occurs.
     */
    public static long addExact(long x, long y) {
        return java.lang.Math.addExact(x, y);
    }

    /**
     * Subtracts the second integer from the first, throwing an exception if an overflow occurs.
     *
     * @param x The first integer.
     * @param y The second integer.
     * @return The difference of the two integers.
     * @throws ArithmeticException If overflow occurs.
     */
    public static int subtractExact(int x, int y) {
        return java.lang.Math.subtractExact(x, y);
    }

    /**
     * Subtracts the second long from the first, throwing an exception if an overflow occurs.
     *
     * @param x The first long.
     * @param y The second long.
     * @return The difference of the two longs.
     * @throws ArithmeticException If overflow occurs.
     */
    public static long subtractExact(long x, long y) {
        return java.lang.Math.subtractExact(x, y);
    }

    /**
     * Multiplies two integers, throwing an exception if an overflow occurs.
     *
     * @param x The first integer.
     * @param y The second integer.
     * @return The product of the two integers.
     * @throws ArithmeticException If overflow occurs.
     */
    public static int multiplyExact(int x, int y) {
        return java.lang.Math.multiplyExact(x, y);
    }

    /**
     * Multiplies two long integers, throwing an exception if an overflow occurs.
     *
     * @param x The first long.
     * @param y The second long.
     * @return The product of the two longs.
     * @throws ArithmeticException If overflow occurs.
     */
    public static long multiplyExact(long x, long y) {
        return java.lang.Math.multiplyExact(x, y);
    }

    /**
     * Divides the first integer by the second, throwing an exception if an overflow occurs.
     *
     * @param x The numerator.
     * @param y The denominator.
     * @return The quotient of the two integers.
     * @throws ArithmeticException If overflow occurs.
     */
    public static int divideExact(int x, int y) {
        return java.lang.Math.divideExact(x, y);
    }

    /**
     * Divides the first long by the second, throwing an exception if an overflow occurs.
     *
     * @param x The numerator.
     * @param y The denominator.
     * @return The quotient of the two longs.
     * @throws ArithmeticException If overflow occurs.
     */
    public static long divideExact(long x, long y) {
        return java.lang.Math.divideExact(x, y);
    }

    /**
     * Divides the first integer by the second and returns the floor value, throwing an exception if an overflow occurs.
     *
     * @param x The numerator.
     * @param y The denominator.
     * @return The floor value of the division.
     * @throws ArithmeticException If overflow occurs.
     */
    public static int floorDivExact(int x, int y) {
        return java.lang.Math.floorDivExact(x, y);
    }

    /**
     * Divides the first long by the second and returns the floor value, throwing an exception if an overflow occurs.
     *
     * @param x The numerator.
     * @param y The denominator.
     * @return The floor value of the division.
     * @throws ArithmeticException If overflow occurs.
     */
    public static long floorDivExact(long x, long y) {
        return java.lang.Math.floorDivExact(x, y);
    }

    /**
     * Computes the modulus of two integers, throwing an exception if an overflow occurs.
     *
     * @param x The numerator.
     * @param y The denominator.
     * @return The modulus of the two integers.
     * @throws ArithmeticException If overflow occurs.
     */
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

    /**
     * Computes the modulus of two long integers, throwing an exception if an overflow occurs.
     *
     * @param x The numerator.
     * @param y The denominator.
     * @return The modulus of the two longs.
     * @throws ArithmeticException If overflow occurs.
     */
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

    /**
     * Returns the result of dividing the first argument by the second, rounding up, and returning the exact result.
     *
     * @param x The dividend.
     * @param y The divisor.
     * @return The result of dividing x by y, rounded up to the nearest integer.
     * @throws ArithmeticException If y is zero.
     */
    public static int ceilDivExact(int x, int y) {
        return java.lang.Math.ceilDivExact(x, y);
    }

    /**
     * Returns the result of dividing the first argument by the second, rounding up, and returning the exact result.
     *
     * @param x The dividend.
     * @param y The divisor.
     * @return The result of dividing x by y, rounded up to the nearest integer.
     * @throws ArithmeticException If y is zero.
     */
    public static long ceilDivExact(long x, long y) {
        return java.lang.Math.ceilDivExact(x, y);
    }

    /**
     * Returns the exact remainder of the division of x by y, rounding up if necessary.
     *
     * @param x The dividend.
     * @param y The divisor.
     * @return The remainder of the division of x by y, rounded up.
     * @throws ArithmeticException If y is zero or if overflow occurs.
     */
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

    /**
     * Returns the exact remainder of the division of x by y, rounding up if necessary.
     *
     * @param x The dividend.
     * @param y The divisor.
     * @return The remainder of the division of x by y, rounded up.
     * @throws ArithmeticException If y is zero or if overflow occurs.
     */
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

    /**
     * Increments the given integer value by 1 and returns the result.
     *
     * @param a The value to increment.
     * @return The value of a incremented by 1.
     * @throws ArithmeticException If the result overflows.
     */
    public static int incrementExact(int a) {
        return java.lang.Math.incrementExact(a);
    }

    /**
     * Increments the given long value by 1 and returns the result.
     *
     * @param a The value to increment.
     * @return The value of a incremented by 1.
     * @throws ArithmeticException If the result overflows.
     */
    public static long incrementExact(long a) {
        return java.lang.Math.incrementExact(a);
    }

    /**
     * Decrements the given integer value by 1 and returns the result.
     *
     * @param a The value to decrement.
     * @return The value of a decremented by 1.
     * @throws ArithmeticException If the result overflows.
     */
    public static int decrementExact(int a) {
        return java.lang.Math.decrementExact(a);
    }

    /**
     * Decrements the given long value by 1 and returns the result.
     *
     * @param a The value to decrement.
     * @return The value of a decremented by 1.
     * @throws ArithmeticException If the result overflows.
     */
    public static long decrementExact(long a) {
        return java.lang.Math.decrementExact(a);
    }

    /**
     * Returns the negation of the given integer value.
     *
     * @param a The value to negate.
     * @return The negation of a.
     * @throws ArithmeticException If the result overflows.
     */
    public static int negateExact(int a) {
        return java.lang.Math.negateExact(a);
    }

    /**
     * Returns the negation of the given long value.
     *
     * @param a The value to negate.
     * @return The negation of a.
     * @throws ArithmeticException If the result overflows.
     */
    public static long negateExact(long a) {
        return java.lang.Math.negateExact(a);
    }

    /**
     * Converts the given long value to an int and returns the result.
     *
     * @param value The long value to convert.
     * @return The int value equivalent to the long value.
     * @throws ArithmeticException If the long value cannot be converted to an int without overflow.
     */
    public static int toIntExact(long value) {
        return java.lang.Math.toIntExact(value);
    }

    /**
     * Multiplies two integers and returns the result as a long.
     *
     * @param x The first value.
     * @param y The second value.
     * @return The result of multiplying x and y.
     */
    public static long multiplyFull(int x, int y) {
        return (long) x * (long) y;
    }

    /**
     * Multiplies two long values and returns the high part of the result.
     *
     * @param x The first value.
     * @param y The second value.
     * @return The high part of the product of x and y.
     */
    public static long multiplyHigh(long x, long y) {
        return java.lang.Math.multiplyHigh(x, y);
    }

    /**
     * Multiplies two unsigned long values and returns the high part of the result.
     *
     * @param x The first value.
     * @param y The second value.
     * @return The high part of the product of x and y.
     */
    public static long unsignedMultiplyHigh(long x, long y) {
        return java.lang.Math.unsignedMultiplyHigh(x, y);
    }

    /**
     * Performs integer division, rounding down to the nearest integer.
     *
     * @param x The dividend.
     * @param y The divisor.
     * @return The result of dividing x by y, rounded down.
     * @throws ArithmeticException If y is zero.
     */
    public static int floorDiv(int x, int y) {
        return java.lang.Math.floorDiv(x, y);
    }

    /**
     * Performs long division, rounding down to the nearest integer.
     *
     * @param x The dividend.
     * @param y The divisor.
     * @return The result of dividing x by y, rounded down.
     * @throws ArithmeticException If y is zero.
     */
    public static long floorDiv(long x, long y) {
        return java.lang.Math.floorDiv(x, y);
    }

    /**
     * Returns the remainder of the division of x by y, rounding down.
     *
     * @param x The dividend.
     * @param y The divisor.
     * @return The remainder of the division of x by y, rounded down.
     * @throws ArithmeticException If y is zero.
     */
    public static int floorMod(int x, int y) {
        return java.lang.Math.floorMod(x, y);
    }

    /**
     * Returns the remainder of the division of x by y, rounding down.
     *
     * @param x The dividend.
     * @param y The divisor.
     * @return The remainder of the division of x by y, rounded down.
     * @throws ArithmeticException If y is zero.
     */
    public static long floorMod(long x, long y) {
        return java.lang.Math.floorMod(x, y);
    }

    /**
     * Returns the result of dividing x by y, rounding up to the nearest integer.
     *
     * @param x The dividend.
     * @param y The divisor.
     * @return The result of dividing x by y, rounded up.
     * @throws ArithmeticException If y is zero.
     */
    public static int ceilDiv(int x, int y) {
        return java.lang.Math.ceilDiv(x, y);
    }

    /**
     * Returns the result of dividing x by y, rounding up to the nearest integer.
     *
     * @param x The dividend.
     * @param y The divisor.
     * @return The result of dividing x by y, rounded up.
     * @throws ArithmeticException If y is zero.
     */
    public static long ceilDiv(long x, long y) {
        return java.lang.Math.ceilDiv(x, y);
    }

    /**
     * Returns the remainder of the division of x by y, rounded up.
     *
     * @param x The dividend.
     * @param y The divisor.
     * @return The remainder of the division of x by y, rounded up.
     * @throws ArithmeticException If y is zero.
     */
    public static int ceilMod(int x, int y) {
        return java.lang.Math.ceilMod(x, y);
    }

    /**
     * Returns the remainder of the division of x by y, rounded up.
     *
     * @param x The dividend.
     * @param y The divisor.
     * @return The remainder of the division of x by y, rounded up.
     * @throws ArithmeticException If y is zero.
     */
    public static long ceilMod(long x, long y) {
        return java.lang.Math.ceilMod(x, y);
    }

    /**
     * Returns the exponent of the given double value, as a base 2 exponent.
     *
     * @param d The double value.
     * @return The exponent of the given value.
     */
    public static int getExponent(double d) {
        return java.lang.Math.getExponent(d);
    }

    /**
     * Returns the exponent of the given float value, as a base 2 exponent.
     *
     * @param f The float value.
     * @return The exponent of the given value.
     */
    public static int getExponent(float f) {
        return java.lang.Math.getExponent(f);
    }

    /**
     * Returns the ulp (Unit in the Last Place) of the given float value.
     *
     * @param a The float value.
     * @return The ulp of the given value.
     */
    public static float ulp(float a) {
        return java.lang.Math.ulp(a);
    }

    /**
     * Returns the ulp (Unit in the Last Place) of the given double value.
     *
     * @param a The double value.
     * @return The ulp of the given value.
     */
    public static double ulp(double a) {
        return java.lang.Math.ulp(a);
    }

    /**
     * Returns the exact absolute value of the given integer.
     *
     * @param a The integer value.
     * @return The absolute value of the given integer.
     * @throws ArithmeticException If the result overflows.
     */
    public static int absExact(int a) {
        return java.lang.Math.absExact(a);
    }

    /**
     * Returns the absolute value of the given long, throwing an exception if the value is Long.MIN_VALUE.
     *
     * @param a the value whose absolute value is to be returned
     * @return the absolute value of the given value
     * @throws ArithmeticException if the absolute value of the given value overflows
     */
    public static long absExact(long a) {
        return java.lang.Math.absExact(a);
    }

    /**
     * Returns the hypotenuse of the two given doubles, i.e., √(x^2 + y^2).
     *
     * @param x the first value
     * @param y the second value
     * @return the hypotenuse of the two values
     */
    public static double hypot(double x, double y) {
        return java.lang.Math.hypot(x, y);
    }

    /**
     * Returns the hypotenuse of the two given floats, i.e., √(x^2 + y^2).
     *
     * @param x the first value
     * @param y the second value
     * @return the hypotenuse of the two values
     */
    public static float hypot(float x, float y) {
        return (float) java.lang.Math.hypot(x, y);
    }

    /**
     * Returns the value of e raised to the power of the given double, minus 1 (e^x - 1).
     *
     * @param x the exponent value
     * @return the result of e^x - 1
     */
    public static double expm1(double x) {
        return java.lang.Math.expm1(x);
    }

    /**
     * Returns the value of e raised to the power of the given float, minus 1 (e^x - 1).
     *
     * @param x the exponent value
     * @return the result of e^x - 1
     */
    public static float expm1(float x) {
        return (float) java.lang.Math.expm1(x);
    }

    /**
     * Returns the value of the natural logarithm of (1 + x).
     *
     * @param x the value
     * @return the natural logarithm of (1 + x)
     */
    public static double log1p(double x) {
        return java.lang.Math.log1p(x);
    }

    /**
     * Returns the value of the natural logarithm of (1 + x).
     *
     * @param x the value
     * @return the natural logarithm of (1 + x)
     */
    public static float log1p(float x) {
        return (float) java.lang.Math.log1p(x);
    }

    /**
     * Returns a value with the same magnitude as the first argument and the sign of the second argument.
     *
     * @param magnitude the value whose magnitude is used
     * @param sign      the value whose sign is used
     * @return a value with the same magnitude as the first argument and the sign of the second argument
     */
    public static double copySign(double magnitude, double sign) {
        return java.lang.Math.copySign(magnitude, sign);
    }

    /**
     * Returns a value with the same magnitude as the first argument and the sign of the second argument.
     *
     * @param magnitude the value whose magnitude is used
     * @param sign      the value whose sign is used
     * @return a value with the same magnitude as the first argument and the sign of the second argument
     */
    public static float copySign(float magnitude, float sign) {
        return java.lang.Math.copySign(magnitude, sign);
    }

    /**
     * Returns the next floating-point value after the given double in the specified direction.
     *
     * @param start     the starting value
     * @param direction the direction to move towards
     * @return the next value after the given value in the specified direction
     */
    public static double nextAfter(double start, double direction) {
        return java.lang.Math.nextAfter(start, direction);
    }

    /**
     * Returns the next floating-point value after the given float in the specified direction.
     *
     * @param start     the starting value
     * @param direction the direction to move towards
     * @return the next value after the given value in the specified direction
     */
    public static float nextAfter(float start, float direction) {
        return java.lang.Math.nextAfter(start, direction);
    }

    /**
     * Returns the next higher floating-point value after the given double.
     *
     * @param d the starting value
     * @return the next higher value after the given value
     */
    public static double nextUp(double d) {
        return java.lang.Math.nextUp(d);
    }

    /**
     * Returns the next higher floating-point value after the given float.
     *
     * @param f the starting value
     * @return the next higher value after the given value
     */
    public static float nextUp(float f) {
        return java.lang.Math.nextUp(f);
    }

    /**
     * Returns the next lower floating-point value after the given double.
     *
     * @param d the starting value
     * @return the next lower value after the given value
     */
    public static double nextDown(double d) {
        return java.lang.Math.nextDown(d);
    }

    /**
     * Returns the next lower floating-point value after the given float.
     *
     * @param f the starting value
     * @return the next lower value after the given value
     */
    public static float nextDown(float f) {
        return java.lang.Math.nextDown(f);
    }

    /**
     * Returns the next floating-point value from the start towards the target value.
     * If start equals target, returns the start value.
     *
     * @param start  the starting value
     * @param target the target value
     * @return the next value from start towards target
     */
    public static double nextTowards(double start, double target) {
        if (Double.isNaN(start) || Double.isNaN(target)) {
            return Double.NaN;
        }

        if (start == target) {
            return start;
        }

        return (start < target) ? nextUp(start) : nextDown(start);
    }

    /**
     * Returns the next floating-point value from the start towards the target value.
     * If start equals target, returns the start value.
     *
     * @param start  the starting value
     * @param target the target value
     * @return the next value from start towards target
     */
    public static float nextTowards(float start, float target) {
        if (Float.isNaN(start) || Float.isNaN(target)) {
            return Float.NaN;
        }

        if (start == target) {
            return start;
        }

        return (start < target) ? nextUp(start) : nextDown(start);
    }

    /**
     * Returns the next floating-point value after the given double in the specified direction as an integer.
     *
     * @param start     the starting value
     * @param direction the direction to move towards
     * @return the next value after the given value in the specified direction
     */
    public static double nextAfter(double start, long direction) {
        return java.lang.Math.nextAfter(start, direction);
    }

    /**
     * Returns the next floating-point value after the given float in the specified direction as an integer.
     *
     * @param start     the starting value
     * @param direction the direction to move towards
     * @return the next value after the given value in the specified direction
     */
    public static float nextAfter(float start, int direction) {
        return java.lang.Math.nextAfter(start, direction);
    }

    /**
     * Returns the value of d scaled by 2^scaleFactor.
     *
     * @param d           the value to scale
     * @param scaleFactor the scaling factor
     * @return the scaled value
     */
    public static double scalb(double d, int scaleFactor) {
        return java.lang.Math.scalb(d, scaleFactor);
    }

    /**
     * Returns the value of f scaled by 2^scaleFactor.
     *
     * @param f           the value to scale
     * @param scaleFactor the scaling factor
     * @return the scaled value
     */
    public static float scalb(float f, int scaleFactor) {
        return java.lang.Math.scalb(f, scaleFactor);
    }

    /**
     * Returns the result of raising a to the power of b using an efficient algorithm (exponentiation by squaring).
     *
     * @param a the base value
     * @param b the exponent value
     * @return the result of a raised to the power of b
     */
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

    /**
     * Returns the result of raising a to the power of b using an efficient algorithm (exponentiation by squaring).
     *
     * @param a the base value
     * @param b the exponent value
     * @return the result of a raised to the power of b
     */
    public static float fastPow(float a, int b) {
        return (float) fastPow((double) a, b);
    }

    /**
     * Returns the result of raising a to the power of b using an efficient algorithm (exponentiation by squaring).
     *
     * @param a the base value
     * @param b the exponent value
     * @return the result of a raised to the power of b
     */
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

    /**
     * Returns the result of raising a to the power of b using an efficient algorithm (exponentiation by squaring).
     *
     * @param a the base value
     * @param b the exponent value
     * @return the result of a raised to the power of b
     */
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

    /**
     * Returns true if the given integer is a power of two.
     *
     * @param value the value to check
     * @return true if the value is a power of two, false otherwise
     */
    public static boolean mathIsPoT(int value) {
        return org.lwjgl.system.MathUtil.mathIsPoT(value);
    }

    /**
     * Rounds the given integer to the next power of two.
     *
     * @param value the value to round
     * @return the next power of two greater than or equal to the value
     */
    public static int mathRoundPoT(int value) {
        return org.lwjgl.system.MathUtil.mathRoundPoT(value);
    }

    /**
     * Returns true if the given integer contains at least one byte that is zero.
     *
     * @param value the value to check
     * @return true if the value has a zero byte, false otherwise
     */
    public static boolean mathHasZeroByte(int value) {
        return org.lwjgl.system.MathUtil.mathHasZeroByte(value);
    }

    /**
     * Returns true if the given long contains at least one byte that is zero.
     *
     * @param value the value to check
     * @return true if the value has a zero byte, false otherwise
     */
    public static boolean mathHasZeroByte(long value) {
        return org.lwjgl.system.MathUtil.mathHasZeroByte(value);
    }

    /**
     * Returns true if the given integer contains at least one short that is zero.
     *
     * @param value the value to check
     * @return true if the value has a zero short, false otherwise
     */
    public static boolean mathHasZeroShort(int value) {
        return org.lwjgl.system.MathUtil.mathHasZeroShort(value);
    }

    /**
     * Returns true if the given long contains at least one short that is zero.
     *
     * @param value the value to check
     * @return true if the value has a zero short, false otherwise
     */
    public static boolean mathHasZeroShort(long value) {
        return org.lwjgl.system.MathUtil.mathHasZeroShort(value);
    }

    /**
     * Returns the high part of the product of two unsigned 64-bit integers.
     *
     * @param x the first operand
     * @param y the second operand
     * @return the high part of the product
     */
    public static long mathMultiplyHighU64(long x, long y) {
        return org.lwjgl.system.MathUtil.mathMultiplyHighU64(x, y);
    }

    /**
     * Returns the high part of the product of two signed 64-bit integers.
     *
     * @param x the first operand
     * @param y the second operand
     * @return the high part of the product
     */
    public static long mathMultiplyHighS64(long x, long y) {
        return org.lwjgl.system.MathUtil.mathMultiplyHighS64(x, y);
    }

    /**
     * Returns the result of dividing the given unsigned long by another unsigned long.
     *
     * @param dividend the dividend
     * @param divisor  the divisor
     * @return the quotient
     */
    public static long mathDivideUnsigned(long dividend, long divisor) {
        return org.lwjgl.system.MathUtil.mathDivideUnsigned(dividend, divisor);
    }

    /**
     * Returns the remainder when dividing the given unsigned long by another unsigned long.
     *
     * @param dividend the dividend
     * @param divisor  the divisor
     * @return the remainder
     */
    public static long mathRemainderUnsigned(long dividend, long divisor) {
        return org.lwjgl.system.MathUtil.mathRemainderUnsigned(dividend, divisor);
    }

    /**
     * Calculates the mean (average) of the given array of values.
     *
     * @param values an array of double values
     * @return the mean of the values
     */
    public static double mean(double[] values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    /**
     * Calculates the median of the given array of values.
     * If the array has an odd number of elements, returns the middle element.
     * If the array has an even number of elements, returns the average of the two middle elements.
     *
     * @param values an array of double values
     * @return the median of the values
     */
    public static double median(double[] values) {
        double[] sortedValues = Arrays.copyOf(values, values.length);
        Arrays.sort(sortedValues);
        int middle = sortedValues.length / 2;
        if (sortedValues.length % 2 == 0) {
            return (sortedValues[middle - 1] + sortedValues[middle]) / 2.0;
        } else {
            return sortedValues[middle];
        }
    }

    /**
     * Calculates the mode of the given array of values.
     * The mode is the value that appears most frequently in the array.
     * If there are multiple values with the same frequency, it returns the first one encountered.
     *
     * @param values an array of double values
     * @return the mode of the values
     */
    public static double mode(double[] values) {
        Map<Double, Integer> frequencyMap = new HashMap<>();
        for (double value : values) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }
        double mode = values[0];
        int maxCount = 0;
        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mode = entry.getKey();
            }
        }
        return mode;
    }

    /**
     * Calculates the standard deviation of the given array of values.
     * Standard deviation measures the amount of variation or dispersion of a set of values.
     *
     * @param values an array of double values
     * @return the standard deviation of the values
     */
    public static double standardDeviation(double[] values) {
        double mean = mean(values);
        double sumSquaredDifferences = 0.0;
        for (double value : values) {
            sumSquaredDifferences += pow(value - mean, 2);
        }
        return Math.sqrt(sumSquaredDifferences / values.length);
    }

    /**
     * Performs linear interpolation between two values, a and b, based on the given parameter t.
     * The parameter t should be between 0 and 1, where 0 returns a and 1 returns b.
     *
     * @param a the first value
     * @param b the second value
     * @param t the interpolation parameter
     * @return the interpolated value
     */
    public static double linearInterpolate(double a, double b, double t) {
        return a + t * (b - a);
    }

    /**
     * Performs linear interpolation between two values, a and b, based on the given parameter t.
     * The parameter t should be between 0 and 1, where 0 returns a and 1 returns b.
     *
     * @param a the first value
     * @param b the second value
     * @param t the interpolation parameter
     * @return the interpolated value
     */
    public static float linearInterpolate(float a, float b, float t) {
        return a + t * (b - a);
    }
}