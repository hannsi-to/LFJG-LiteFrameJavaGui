package me.hannsi.lfjg.utils.reflection;

import me.hannsi.lfjg.utils.Util;

import java.util.Map;

public class StackTraceUtil extends Util {
    public static String getStackTrace(String mainThreadName, String... ignoreMethod) {
        return getStackTraceWithInsert(mainThreadName, "", ignoreMethod);
    }

    public static String getStackTraceWithInsert(String mainThreadName, String insert, String... ignoreMethod) {
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        StringBuilder stackTraceStr = new StringBuilder();

        for (Map.Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()) {
            Thread thread = entry.getKey();

            if (!thread.getName().equals(mainThreadName)) {
                continue;
            }

            StackTraceElement[] stackTrace = entry.getValue();

            int index = 0;
            for (StackTraceElement element : stackTrace) {
                boolean ignore = false;

                for (String method : ignoreMethod) {
                    if (element.getMethodName().equals(method)) {
                        ignore = true;
                        break;
                    }
                }

                if (ignore) {
                    continue;
                }

                if (isLibraryClass(element.getClassName())) {
                    continue;
                }

                if (element.isNativeMethod()) {
                    continue;
                }

                if (index == 0) {
                    stackTraceStr.append(insert);
                } else {
                    stackTraceStr.append(insert).append("\t");
                }
                stackTraceStr.append(element).append("\n");

                index++;
            }
        }
        stackTraceStr.delete(stackTraceStr.length() - 1, stackTraceStr.length());

        return stackTraceStr.toString();
    }

    private static boolean isLibraryClass(String className) {
        return className.startsWith("java.") || className.startsWith("sun.") || className.startsWith("com.sun.") || className.startsWith("jdk.");
    }

    private static boolean isUserClass(String className) {
        return !isLibraryClass(className);
    }

    public static String getCurrentThreadStackTrace(String... ignoreMethod) {
        return getStackTrace(Thread.currentThread().getName(), ignoreMethod);
    }

    public static String getStackTraceForThread(Thread thread, String... ignoreMethod) {
        return getStackTrace(thread.getName(), ignoreMethod);
    }

    public static String getAllThreadStackTraces(String... ignoreMethod) {
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        StringBuilder stackTraceStr = new StringBuilder();

        for (Map.Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()) {
            stackTraceStr.append("Thread: ").append(entry.getKey().getName()).append("\n");
            stackTraceStr.append(getStackTrace(entry.getKey().getName(), ignoreMethod)).append("\n");
        }

        return stackTraceStr.toString();
    }
}
