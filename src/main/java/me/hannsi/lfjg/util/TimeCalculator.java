package me.hannsi.lfjg.util;

public class TimeCalculator {
    public static long calculate(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - start;
    }
}
