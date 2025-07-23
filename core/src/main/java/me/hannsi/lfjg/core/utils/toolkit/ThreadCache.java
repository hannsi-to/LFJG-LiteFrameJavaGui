package me.hannsi.lfjg.core.utils.toolkit;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerator;

import java.util.HashMap;

public class ThreadCache {
    private HashMap<Long, Thread> threadCache;

    public ThreadCache() {
        threadCache = new HashMap<>();
    }

    public void createCache(Thread thread) {
        threadCache.put(thread.threadId(), thread);

        LogGenerator logGenerator = new LogGenerator("ThreadCache Debug Message", "Source: ThreadCache", "Type: Cache Creation", "ID: " + thread.threadId(), "Severity: Info", "Message: Create thread cache: " + thread.getName());
        logGenerator.logging(DebugLevel.DEBUG);
    }

    public void run() {
        threadCache.forEach((key, value) -> {
            threadRun(value);
        });
    }

    public void stop() {
        threadCache.forEach((key, value) -> {
            threadStop(value);
        });
    }

    public void run(long... threadId) {
        threadCache.forEach((key, value) -> {
            for (long l : threadId) {
                if (key.equals(l)) {
                    threadRun(value);
                }
            }
        });
    }

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

    public void cleanup() {
        stop();

        threadCache.clear();
    }

    public HashMap<Long, Thread> getThreadCache() {
        return threadCache;
    }

    public void setThreadCache(HashMap<Long, Thread> threadCache) {
        this.threadCache = threadCache;
    }
}