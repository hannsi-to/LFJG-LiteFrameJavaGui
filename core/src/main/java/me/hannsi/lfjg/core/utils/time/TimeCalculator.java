package me.hannsi.lfjg.core.utils.time;

import java.util.concurrent.Callable;

public class TimeCalculator {
    public static long calculateMillis(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - start;
    }

    public static long calculateNano(Runnable runnable) {
        long start = System.nanoTime();
        runnable.run();
        return System.nanoTime() - start;
    }

    public static String formatNanoTime(long nanos) {
        if (nanos >= 1_000_000_000) {
            return String.format("%.3f sec", nanos / 1_000_000_000.0);
        } else if (nanos >= 1_000_000) {
            return String.format("%.3f ms", nanos / 1_000_000.0);
        } else {
            return nanos + " ns";
        }
    }

    public static double calculateAverage(Runnable runnable, int iterations) {
        if (iterations <= 0) {
            throw new IllegalArgumentException("Iterations must be greater than 0.");
        }

        long total = 0;
        for (int i = 0; i < iterations; i++) {
            total += calculateNano(runnable);
        }

        return (total / (double) iterations) / 1_000_000.0;
    }

    public static <T> ResultWithTime<T> calculateCallable(Callable<T> callable) throws Exception {
        long start = System.nanoTime();
        T result = callable.call();
        long time = System.nanoTime() - start;
        return new ResultWithTime<>(result, time);
    }

    public record ResultWithTime<T>(T result, long timeTakenNano) {
        public String getFormattedTime() {
            return formatNanoTime(timeTakenNano);
        }
    }
}