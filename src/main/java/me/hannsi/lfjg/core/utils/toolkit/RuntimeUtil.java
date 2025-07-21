package me.hannsi.lfjg.core.utils.toolkit;

import com.sun.management.OperatingSystemMXBean;
import me.hannsi.lfjg.core.utils.Util;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.util.List;

/**
 * Utility class for retrieving runtime memory information.
 */
public class RuntimeUtil extends Util {

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

    public static long getHeapMemoryUsed() {
        MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        return heapMemoryUsage.getUsed();
    }

    public static long getNonHeapMemoryUsed() {
        MemoryUsage nonHeapMemoryUsage = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
        return nonHeapMemoryUsage.getUsed();
    }

    public static void runGarbageCollector() {
        System.gc();
    }

    public static boolean isMemoryUsageHigh(double thresholdPercentage) {
        double usedMemory = getUseMemoryMB();
        double maxMemory = getMaxMemoryMB();

        return (usedMemory / maxMemory) * 100 >= thresholdPercentage;
    }

    public static long getSwapUsed() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osBean.getTotalSwapSpaceSize() - osBean.getFreeSwapSpaceSize();
    }

    public static long getJvmUptime() {
        return ManagementFactory.getRuntimeMXBean().getUptime();
    }

    public static String getJvmVersion() {
        return System.getProperty("java.version");
    }

    public static int getThreadCount() {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        return threadBean.getThreadCount();
    }

    public static long getTotalSystemMemory() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osBean.getTotalMemorySize();
    }

    public static long getFreeSystemMemory() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osBean.getFreeMemorySize();
    }

    public static long getSystemMemoryUsed() {
        return getTotalSystemMemory() - getFreeSystemMemory();
    }

    public static double getProcessCpuLoad() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osBean.getProcessCpuLoad() * 100;
    }

    public static long getFreeDiskSpace() {
        return new File("/").getFreeSpace();
    }

    public static long getTotalDiskSpace() {
        return new File("/").getTotalSpace();
    }

    public static long getUsedDiskSpace() {
        return getTotalDiskSpace() - getFreeDiskSpace();
    }

    public static String getProcessId() {
        String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        return jvmName.split("@")[0];
    }

    public static String getEnvironmentVariable(String name) {
        return System.getenv(name);
    }

    public static List<String> getJvmArguments() {
        return ManagementFactory.getRuntimeMXBean().getInputArguments();
    }
}