package me.hannsi.lfjg.core.utils.reflection;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.Util;
import me.hannsi.lfjg.core.utils.math.map.long2Object.ConcurrentLong2ObjectMap;
import org.reflections.Reflections;

import java.lang.invoke.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ClassUtil extends Util {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_MAP;
    private static final ConcurrentLong2ObjectMap<MethodHandle> METHOD_HANDLE_CACHE = new ConcurrentLong2ObjectMap<>();

    static {
        PRIMITIVE_MAP = Map.of(Boolean.class, boolean.class, Byte.class, byte.class, Character.class, char.class, Double.class, double.class, Float.class, float.class, Integer.class, int.class, Long.class, long.class, Short.class, short.class);
    }

    public static <T> T bindInstance(String className, String methodName, Class<T> functionalInterface, MethodType methodType) {
        try {
            Class<?> owner = Class.forName(className);
            return bindInstance(owner, methodName, functionalInterface, methodType);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T bindStatic(String className, String methodName, Class<T> functionalInterface, MethodType methodType) {
        try {
            Class<?> owner = Class.forName(className);
            return bindStatic(owner, methodName, functionalInterface, methodType);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T bindInstance(Class<?> owner, String methodName, Class<T> functionalInterface, MethodType methodType) {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();

            MethodHandle target = lookup.findVirtual(
                    owner,
                    methodName,
                    methodType
            );

            CallSite site = LambdaMetafactory.metafactory(
                    lookup,
                    "call",
                    MethodType.methodType(functionalInterface),
                    methodType.insertParameterTypes(0, Object.class).erase(),
                    target,
                    target.type()
            );

            MethodHandle factory = site.getTarget().asType(MethodType.methodType(Object.class));

            @SuppressWarnings("unchecked")
            T lambda = (T) factory.invokeExact();

            return lambda;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static <T> T bindStatic(Class<?> owner, String methodName, Class<T> functionalInterface, MethodType methodType) {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();

            MethodHandle target = lookup.findStatic(
                    owner,
                    methodName,
                    methodType
            );

            Method samMethod = Arrays.stream(functionalInterface.getMethods())
                    .filter(m -> m.getName().equals("call"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Method 'call' not found in " + functionalInterface.getName()));

            MethodType samMethodType = MethodType.methodType(samMethod.getReturnType(), samMethod.getParameterTypes());

            CallSite site = LambdaMetafactory.metafactory(
                    lookup,
                    "call",
                    MethodType.methodType(functionalInterface),
                    samMethodType,
                    target,
                    methodType
            );

            MethodHandle factory = site.getTarget().asType(MethodType.methodType(Object.class));

            @SuppressWarnings("unchecked")
            T lambda = (T) factory.invokeExact();

            return lambda;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static Class<?> wrapPrimitive(Class<?> clazz) {
        return PRIMITIVE_MAP.getOrDefault(clazz, clazz);
    }

    private static Class<?>[] getParameterTypes(Object... args) {
        return Arrays.stream(args)
                .map(arg -> arg != null ? wrapPrimitive(arg.getClass()) : Object.class)
                .toArray(Class<?>[]::new);
    }

    public static <T> Set<Class<? extends T>> getClassesFromPackage(String packagePath, Class<T> clazz) {
        return new HashSet<>(new Reflections(packagePath).getSubTypesOf(clazz));
    }

    public static <T> T createInstance(Class<T> clazz, Object... args) {
        T instance;
        List<Class<?>> parameterTypes = new ArrayList<>();

        for (Object arg : args) {
            parameterTypes.add(arg != null ? arg.getClass() : Object.class);
        }

        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(parameterTypes.toArray(new Class<?>[0]));
            instance = constructor.newInstance(args);
        } catch (InstantiationException e) {
            throw new RuntimeException("Could not instantiate class: " + clazz.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Illegal access to constructor: " + clazz.getName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Constructor threw an exception: " + clazz.getName(), e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No matching constructor found: " + clazz.getName(), e);
        }

        return instance;
    }

    @SuppressWarnings("unchecked")
    public static <T> T createInstanceWithoutArgs(String className) {
        Class<T> clazz = null;
        try {
            clazz = (Class<T>) Class.forName(className);
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Could not instantiate class: " + clazz.getName(), e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invokeStaticMethod(String className, String methodName, Object... args) {
        try {
            Class<?>[] paramTypes = getParameterTypes(args);
            MethodSignature signature = new MethodSignature(className, methodName, paramTypes);

            long key = signature.computeLongHash();
            MethodHandle handle = METHOD_HANDLE_CACHE.get(key);
            if (handle == null) {
                Class<?> clazz = Class.forName(className);
                Method method = findMethod(clazz, methodName, args);
                if (method == null) {
                    throw new NoSuchMethodException("No static method " + methodName + " found in " + className);
                }

                MethodHandles.Lookup lookup = MethodHandles.lookup();
                handle = lookup.unreflect(method);

                METHOD_HANDLE_CACHE.put(key, handle);
            }

            return handle.invokeWithArguments(args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static Method findMethod(Class<?> clazz, String methodName, Object... args) {
        Method[] methods = clazz.getDeclaredMethods();
        outer:
        for (Method method : methods) {
            if (!method.getName().equals(methodName)) {
                continue;
            }
            if (method.getParameterCount() != args.length) {
                continue;
            }

            Class<?>[] paramTypes = method.getParameterTypes();
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg == null) {
                    continue;
                }

                Class<?> expected = wrapPrimitive(paramTypes[i]);
                Class<?> actual = wrapPrimitive(arg.getClass());
                if (!expected.isAssignableFrom(actual)) {
                    continue outer;
                }
            }
            return method;
        }
        return null;
    }

    public static Object invokeMethodExact(Object instance, String methodName, Object... args) {
        try {
            Class<?> clazz = instance.getClass();
            Method method = clazz.getDeclaredMethod(methodName, getParameterTypes(args));
            method.setAccessible(true);
            return method.invoke(instance, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Could not invoke exact method: " + methodName + " on class: " + instance.getClass().getName(), e);
        }
    }

    public static boolean isClassAvailable(String className) {
        try {
            Class.forName(className, false, Thread.currentThread().getContextClassLoader());
            return true;
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            return false;
        } catch (LinkageError e) {
            DebugLog.error(Core.class, e);
            return false;
        } catch (Throwable t) {
            DebugLog.error(Core.class, "Unexpected error while checking class: " + t.getMessage());
            return false;
        }
    }

    public static Object getStaticFieldValue(String className, String fieldName) {
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(null);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Could not access static field: " + fieldName + " on class: " + className, e);
        }
    }
}