package me.hannsi.lfjg.utils.time;

/**
 * Utility class for calculating the execution time of a Runnable.
 */
public class TimeCalculator {

    /**
     * Calculates the time taken to execute a Runnable.
     *
     * @param runnable the Runnable to execute
     * @return the time taken to execute the Runnable in milliseconds
     */
    public static long calculate(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - start;
    }
}