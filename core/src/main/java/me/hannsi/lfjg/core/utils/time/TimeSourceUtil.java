package me.hannsi.lfjg.core.utils.time;

import me.hannsi.lfjg.core.utils.Util;
import me.hannsi.lfjg.core.utils.type.types.TimeSourceType;

import static me.hannsi.lfjg.core.Core.GLFW.glfwGetTime;

public class TimeSourceUtil extends Util {
    public static double getGLFWTime() {
        return glfwGetTime();
    }

    public static long getGLFWTimeMills() {
        return (long) (getGLFWTime() * 1000);
    }

    public static long getGLFWTimeNano() {
        return getGLFWTimeMills() * 1_000_000;
    }

    public static double getSystemTime() {
        return getSystemTimeMills() / 1000d;
    }

    public static long getSystemTimeMills() {
        return System.currentTimeMillis();
    }

    public static long getSystemTimeNano() {
        return getSystemTimeMills() * 1_000_000;
    }

    public static double getNanoTime() {
        return (double) getNanoTimeNano() / 1_000_000_000;
    }

    public static long getNanoTimeMills() {
        return getNanoTimeNano() / 1_000_000;
    }

    public static long getNanoTimeNano() {
        return System.nanoTime();
    }

    public static long getTimeMills(TimeSourceType timeSourceType) {
        switch (timeSourceType) {
            case GLFW_TIME:
                return getGLFWTimeMills();
            case SYSTEM_TIME:
                return getSystemTimeMills();
            case NANO_TIME:
                return getNanoTimeMills();
            default:
                throw new IllegalStateException("Unexpected value: " + timeSourceType);
        }
    }

    public static double getTime(TimeSourceType timeSourceType) {
        switch (timeSourceType) {
            case GLFW_TIME:
                return getGLFWTime();
            case SYSTEM_TIME:
                return getSystemTime();
            case NANO_TIME:
                return getNanoTime();
            default:
                throw new IllegalStateException("Unexpected value: " + timeSourceType);
        }
    }

    public static long getNanoTime(TimeSourceType timeSourceType) {
        switch (timeSourceType) {
            case GLFW_TIME:
                return getGLFWTimeNano();
            case SYSTEM_TIME:
                return getSystemTimeNano();
            case NANO_TIME:
                return getNanoTimeNano();
            default:
                throw new IllegalStateException("Unexpected value: " + timeSourceType);
        }
    }
}