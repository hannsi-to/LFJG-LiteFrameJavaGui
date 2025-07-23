package me.hannsi.lfjg.core.utils.toolkit;

import com.sun.management.OperatingSystemMXBean;
import me.hannsi.lfjg.core.utils.Util;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.util.List;

public class RuntimeUtil extends Util {
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