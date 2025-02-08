package me.hannsi.lfjg.utils.toolkit;

/**
 * Utility class for retrieving runtime memory information.
 */
public class RuntimeUtil {

    /**
     * Gets the runtime instance associated with the current Java application.
     *
     * @return the runtime instance
     */
    public static Runtime getRuntime() {
        return Runtime.getRuntime();
    }

    /**
     * Gets the maximum amount of memory that the Java virtual machine will attempt to use.
     *
     * @return the maximum memory in bytes
     */
    public static long getMaxMemoryByte() {
        return getRuntime().maxMemory();
    }

    /**
     * Gets the total amount of memory currently allocated to the Java virtual machine.
     *
     * @return the allocated memory in bytes
     */
    public static long getAllocatedMemoryByte() {
        return getRuntime().totalMemory();
    }

    /**
     * Gets the amount of free memory in the Java virtual machine.
     *
     * @return the free memory in bytes
     */
    public static long getFreeMemoryByte() {
        return getRuntime().freeMemory();
    }

    /**
     * Gets the amount of memory currently used by the Java virtual machine.
     *
     * @return the used memory in bytes
     */
    public static long getUseMemoryByte() {
        return getAllocatedMemoryByte() - getFreeMemoryByte();
    }

    /**
     * Gets the maximum amount of memory that the Java virtual machine will attempt to use, in kilobytes.
     *
     * @return the maximum memory in kilobytes
     */
    public static double getMaxMemoryKB() {
        return getMaxMemoryByte() / 1024d;
    }

    /**
     * Gets the total amount of memory currently allocated to the Java virtual machine, in kilobytes.
     *
     * @return the allocated memory in kilobytes
     */
    public static double getAllocatedMemoryKB() {
        return getAllocatedMemoryByte() / 1024d;
    }

    /**
     * Gets the amount of free memory in the Java virtual machine, in kilobytes.
     *
     * @return the free memory in kilobytes
     */
    public static double getFreeMemoryKB() {
        return getFreeMemoryByte() / 1024d;
    }

    /**
     * Gets the amount of memory currently used by the Java virtual machine, in kilobytes.
     *
     * @return the used memory in kilobytes
     */
    public static double getUseMemoryKB() {
        return getUseMemoryByte() / 1024d;
    }

    /**
     * Gets the maximum amount of memory that the Java virtual machine will attempt to use, in megabytes.
     *
     * @return the maximum memory in megabytes
     */
    public static double getMaxMemoryMB() {
        return getMaxMemoryKB() / 1024d;
    }

    /**
     * Gets the total amount of memory currently allocated to the Java virtual machine, in megabytes.
     *
     * @return the allocated memory in megabytes
     */
    public static double getAllocatedMemoryMB() {
        return getAllocatedMemoryKB() / 1024d;
    }

    /**
     * Gets the amount of free memory in the Java virtual machine, in megabytes.
     *
     * @return the free memory in megabytes
     */
    public static double getFreeMemoryMB() {
        return getFreeMemoryKB() / 1024d;
    }

    /**
     * Gets the amount of memory currently used by the Java virtual machine, in megabytes.
     *
     * @return the used memory in megabytes
     */
    public static double getUseMemoryMB() {
        return getUseMemoryKB() / 1024d;
    }

    /**
     * Gets the maximum amount of memory that the Java virtual machine will attempt to use, in gigabytes.
     *
     * @return the maximum memory in gigabytes
     */
    public static double getMaxMemoryGB() {
        return getMaxMemoryMB() / 1024d;
    }

    /**
     * Gets the total amount of memory currently allocated to the Java virtual machine, in gigabytes.
     *
     * @return the allocated memory in gigabytes
     */
    public static double getAllocatedMemoryGB() {
        return getAllocatedMemoryMB() / 1024d;
    }

    /**
     * Gets the amount of free memory in the Java virtual machine, in gigabytes.
     *
     * @return the free memory in gigabytes
     */
    public static double getFreeMemoryGB() {
        return getFreeMemoryMB() / 1024d;
    }

    /**
     * Gets the amount of memory currently used by the Java virtual machine, in gigabytes.
     *
     * @return the used memory in gigabytes
     */
    public static double getUseMemoryGB() {
        return getUseMemoryMB() / 1024d;
    }
}