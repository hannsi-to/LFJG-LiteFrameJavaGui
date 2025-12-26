package me.hannsi.lfjg.core.utils.reflection;

import java.util.Arrays;

public final class MethodSignature {
    final String className;
    final String methodName;
    final Class<?>[] paramTypes;
    private final int hash;

    MethodSignature(String className, String methodName, Class<?>[] paramTypes) {
        this.className = className;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.hash = computeHash();
    }

    private int computeHash() {
        int result = className.hashCode();
        result = 31 * result + methodName.hashCode();
        result = 31 * result + Arrays.hashCode(paramTypes);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MethodSignature methodSignature)) {
            return false;
        }

        return className.equals(methodSignature.className) && methodName.equals(methodSignature.methodName) && Arrays.equals(paramTypes, methodSignature.paramTypes);
    }

    public long computeLongHash() {
        long h = (long) className.hashCode() << 32 | (methodName.hashCode() & 0xFFFFFFFFL);
        for (Class<?> pt : paramTypes) {
            h = h * 31 + pt.hashCode();
        }
        return h;
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
