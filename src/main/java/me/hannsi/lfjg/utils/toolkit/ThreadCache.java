package me.hannsi.lfjg.utils.toolkit;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.LogGenerator;

import java.util.HashMap;

/**
 * Utility class for managing a cache of threads.
 */
public class ThreadCache {
    private HashMap<Long, Thread> threadCache;

    /**
     * Constructs a new ThreadCache instance.
     */
    public ThreadCache() {
        threadCache = new HashMap<>();
    }

    /**
     * Creates a cache entry for the specified thread.
     *
     * @param thread the thread to cache
     */
    public void createCache(Thread thread) {
        threadCache.put(thread.threadId(), thread);

        LogGenerator logGenerator = new LogGenerator("ThreadCache Debug Message", "Source: ThreadCache", "Type: Cache Creation", "ID: " + thread.threadId(), "Severity: Info", "Message: Create thread cache: " + thread.getName());
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Starts all threads in the cache.
     */
    public void run() {
        threadCache.forEach((key, value) -> {
            threadRun(value);
        });
    }

    /**
     * Interrupts all threads in the cache.
     */
    public void stop() {
        threadCache.forEach((key, value) -> {
            threadStop(value);
        });
    }

    /**
     * Starts the threads with the specified IDs.
     *
     * @param threadId the IDs of the threads to start
     */
    public void run(long... threadId) {
        threadCache.forEach((key, value) -> {
            for (long l : threadId) {
                if (key.equals(l)) {
                    threadRun(value);
                }
            }
        });
    }

    /**
     * Interrupts the threads with the specified IDs.
     *
     * @param threadId the IDs of the threads to interrupt
     */
    public void stop(long... threadId) {
        threadCache.forEach((key, value) -> {
            for (long l : threadId) {
                if (key.equals(l)) {
                    threadStop(value);
                }
            }
        });
    }

    private void threadStop(Thread thread) {
        thread.interrupt();

        LogGenerator logGenerator = new LogGenerator("Thread Stop", "Thread Name: " + thread.getName(), "Thread ID: " + thread.threadId(), "State Before: " + thread.getState(), "Action: Interrupting thread");
        logGenerator.logging(DebugLevel.DEBUG);
    }

    private void threadRun(Thread thread) {
        thread.start();

        LogGenerator logGenerator = new LogGenerator("Thread Start", "Thread Name: " + thread.getName(), "Thread ID: " + thread.threadId(), "State Before: " + thread.getState(), "Action: Starting thread");
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Interrupts all threads in the cache and clears the cache.
     */
    public void cleanup() {
        stop();

        threadCache.clear();
    }

    /**
     * Gets the current thread cache.
     *
     * @return the current thread cache
     */
    public HashMap<Long, Thread> getThreadCache() {
        return threadCache;
    }

    /**
     * Sets the thread cache.
     *
     * @param threadCache the new thread cache
     */
    public void setThreadCache(HashMap<Long, Thread> threadCache) {
        this.threadCache = threadCache;
    }
}