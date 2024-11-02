package me.hannsi.lfjg.utils.toolkit;

public class RuntimeUtil {
    public static Runtime getRuntime() {
        return Runtime.getRuntime();
    }

    public static long getMaxMemoryByte() {
        return getRuntime().maxMemory();
    }

    public static long getAllocatedMemoryByte() {
        return getRuntime().totalMemory();
    }

    public static long getFreeMemoryByte() {
        return getRuntime().freeMemory();
    }

    public static long getUseMemoryByte() {
        return getAllocatedMemoryByte() - getFreeMemoryByte();
    }

    public static double getMaxMemoryKB() {
        return getMaxMemoryByte() / 1024d;
    }

    public static double getAllocatedMemoryKB() {
        return getAllocatedMemoryByte() / 1024d;
    }

    public static double getFreeMemoryKB() {
        return getFreeMemoryByte() / 1024d;
    }

    public static double getUseMemoryKB() {
        return getUseMemoryByte() / 1024d;
    }

    public static double getMaxMemoryMB() {
        return getMaxMemoryKB() / 1024d;
    }

    public static double getAllocatedMemoryMB() {
        return getAllocatedMemoryKB() / 1024d;
    }

    public static double getFreeMemoryMB() {
        return getFreeMemoryKB() / 1024d;
    }

    public static double getUseMemoryMB() {
        return getUseMemoryKB() / 1024d;
    }

    public static double getMaxMemoryGB() {
        return getMaxMemoryMB() / 1024d;
    }

    public static double getAllocatedMemoryGB() {
        return getAllocatedMemoryMB() / 1024d;
    }

    public static double getFreeMemoryGB() {
        return getFreeMemoryMB() / 1024d;
    }

    public static double getUseMemoryGB() {
        return getUseMemoryMB() / 1024d;
    }
}
