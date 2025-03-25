package me.hannsi.lfjg.utils.time;

import me.hannsi.lfjg.utils.type.types.TimeSourceType;
import org.lwjgl.glfw.GLFW;

/**
 * Utility class for retrieving time from different sources.
 */
public class TimeSourceUtil {

    /**
     * Gets the current time from GLFW in seconds.
     *
     * @return the current GLFW time in seconds
     */
    public static double getGLFWTime() {
        return GLFW.glfwGetTime();
    }

    /**
     * Gets the current time from GLFW in milliseconds.
     *
     * @return the current GLFW time in milliseconds
     */
    public static long getGLFWTimeMills() {
        return (long) (getGLFWTime() * 1000);
    }

    /**
     * Gets the current time from GLFW in nanoseconds.
     *
     * @return the current GLFW time in nanoseconds
     */
    public static long getGLFWTimeNano() {
        return getGLFWTimeMills() * 1_000_000;
    }

    /**
     * Gets the current system time in seconds.
     *
     * @return the current system time in seconds
     */
    public static double getSystemTime() {
        return getSystemTimeMills() / 1000d;
    }

    /**
     * Gets the current system time in milliseconds.
     *
     * @return the current system time in milliseconds
     */
    public static long getSystemTimeMills() {
        return System.currentTimeMillis();
    }

    /**
     * Gets the current system time in nanoseconds.
     *
     * @return the current system time in nanoseconds
     */
    public static long getSystemTimeNano() {
        return getSystemTimeMills() * 1_000_000;
    }

    /**
     * Gets the current time from the system's high-resolution time source in seconds.
     *
     * @return the current high-resolution time in seconds
     */
    public static double getNanoTime() {
        return (double) getNanoTimeNano() / 1_000_000_000;
    }

    /**
     * Gets the current time from the system's high-resolution time source in milliseconds.
     *
     * @return the current high-resolution time in milliseconds
     */
    public static long getNanoTimeMills() {
        return getNanoTimeNano() / 1_000_000;
    }

    /**
     * Gets the current time from the system's high-resolution time source in nanoseconds.
     *
     * @return the current high-resolution time in nanoseconds
     */
    public static long getNanoTimeNano() {
        return System.nanoTime();
    }

    /**
     * Gets the current time in milliseconds based on the specified frame's time source setting.
     *
     * @param frame the frame containing the time source setting
     * @return the current time in milliseconds
     * @throws IllegalStateException if the time source setting is unexpected
     */
    public static long getTimeMills(TimeSourceType timeSourceType) {
        switch (timeSourceType) {
            case GLFWTime -> {
                return getGLFWTimeMills();
            }
            case SystemTime -> {
                return getSystemTimeMills();
            }
            case NanoTime -> {
                return getNanoTimeMills();
            }
            default -> throw new IllegalStateException("Unexpected value: " + timeSourceType);
        }
    }

    /**
     * Gets the current time in seconds based on the specified frame's time source setting.
     *
     * @param frame the frame containing the time source setting
     * @return the current time in seconds
     * @throws IllegalStateException if the time source setting is unexpected
     */
    public static double getTime(TimeSourceType timeSourceType) {
        switch (timeSourceType) {
            case GLFWTime -> {
                return getGLFWTime();
            }
            case SystemTime -> {
                return getSystemTime();
            }
            case NanoTime -> {
                return getNanoTime();
            }
            default -> throw new IllegalStateException("Unexpected value: " + timeSourceType);
        }
    }

    /**
     * Gets the current time in nanoseconds based on the specified frame's time source setting.
     *
     * @param frame the frame containing the time source setting
     * @return the current time in nanoseconds
     * @throws IllegalStateException if the time source setting is unexpected
     */
    public static long getNanoTime(TimeSourceType timeSourceType) {
        switch (timeSourceType) {
            case GLFWTime -> {
                return getGLFWTimeNano();
            }
            case SystemTime -> {
                return getSystemTimeNano();
            }
            case NanoTime -> {
                return getNanoTimeNano();
            }
            default -> throw new IllegalStateException("Unexpected value: " + timeSourceType);
        }
    }
}