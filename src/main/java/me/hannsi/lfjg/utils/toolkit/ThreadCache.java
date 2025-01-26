package me.hannsi.lfjg.utils.toolkit;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.debug.LogGenerator;

import java.util.HashMap;

public class ThreadCache {
    private HashMap<Long, Thread> threadCache;

    public ThreadCache() {
        threadCache = new HashMap<>();
    }

    public void createCache(Thread thread) {
        threadCache.put(thread.threadId(), thread);

        LogGenerator logGenerator = new LogGenerator("ThreadCache Debug Message", "Source: ThreadCache", "Type: Cache Creation", "ID: " + thread.threadId(), "Severity: Info", "Message: Create thread cache: " + thread.getName());

        DebugLog.debug(getClass(), logGenerator.createLog());
    }

    public void run() {
        threadCache.forEach((key, value) -> {
            value.start();
        });
    }

    public void stop() {
        threadCache.forEach((key, value) -> {
            value.interrupt();
        });
    }

    public void run(long... threadId) {
        threadCache.forEach((key, value) -> {
            for (long l : threadId) {
                if (key.equals(l)) {
                    value.start();
                }
            }
        });
    }

    public void stop(long... threadId) {
        threadCache.forEach((key, value) -> {
            for (long l : threadId) {
                if (key.equals(l)) {
                    value.interrupt();
                }
            }
        });
    }

    public void cleanup() {
        threadCache.forEach((key, value) -> {
            if (!value.isInterrupted()) {
                value.interrupt();
            }
        });

        threadCache.clear();
    }

    public HashMap<Long, Thread> getThreadCache() {
        return threadCache;
    }

    public void setThreadCache(HashMap<Long, Thread> threadCache) {
        this.threadCache = threadCache;
    }
}
