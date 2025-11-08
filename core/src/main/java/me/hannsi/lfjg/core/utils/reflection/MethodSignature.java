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
        if (!(o instanceof MethodSignature)) {
            return false;
        }

        MethodSignature methodSignature = (MethodSignature) o;
        return className.equals(methodSignature.className) && methodName.equals(methodSignature.methodName) && Arrays.equals(paramTypes, methodSignature.paramTypes);
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
