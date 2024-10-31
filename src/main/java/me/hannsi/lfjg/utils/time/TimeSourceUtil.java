package me.hannsi.lfjg.utils.time;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.TimeSourceSetting;
import me.hannsi.lfjg.utils.type.types.TimeSourceType;
import org.lwjgl.glfw.GLFW;

public class TimeSourceUtil {
    public static double getGLFWTime() {
        return GLFW.glfwGetTime();
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

    public static long getTimeMills(Frame frame) {
        switch ((TimeSourceType) frame.getFrameSettingBase(TimeSourceSetting.class).getValue()) {
            case GLFWTime -> {
                return getGLFWTimeMills();
            }
            case SystemTime -> {
                return getSystemTimeMills();
            }
            case NanoTime -> {
                return getNanoTimeMills();
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + frame.getFrameSettingBase(TimeSourceSetting.class).getValue());
        }
    }

    public static double getTime(Frame frame) {
        switch ((TimeSourceType) frame.getFrameSettingBase(TimeSourceSetting.class).getValue()) {
            case GLFWTime -> {
                return getGLFWTime();
            }
            case SystemTime -> {
                return getSystemTime();
            }
            case NanoTime -> {
                return getNanoTime();
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + frame.getFrameSettingBase(TimeSourceSetting.class).getValue());
        }
    }

    public static long getNanoTime(Frame frame) {
        switch ((TimeSourceType) frame.getFrameSettingBase(TimeSourceSetting.class).getValue()) {
            case GLFWTime -> {
                return getGLFWTimeNano();
            }
            case SystemTime -> {
                return getSystemTimeNano();
            }
            case NanoTime -> {
                return getNanoTimeNano();
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + frame.getFrameSettingBase(TimeSourceSetting.class).getValue());
        }
    }
}
