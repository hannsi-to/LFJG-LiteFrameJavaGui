package me.hannsi.lfjg.utils.reflection;

import java.util.Map;

public class StackTraceUtil {
    public static String getStackTrace(String mainThreadName, String... ignoreMethod) {
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
                    stackTraceStr.append("\t\t");
                } else {
                    stackTraceStr.append("\t\t\t");
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
}
