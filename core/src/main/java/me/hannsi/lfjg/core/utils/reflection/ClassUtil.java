package me.hannsi.lfjg.core.utils.reflection;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.Util;
import org.reflections.Reflections;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ClassUtil extends Util {
    private static final Map<String, MethodHandle> METHOD_HANDLE_CACHE = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Class<?>> PRIMITIVE_MAP;

    static {
        Map<Class<?>, Class<?>> map = new HashMap<>();
        map.put(Boolean.class, boolean.class);
        map.put(Byte.class, byte.class);
        map.put(Character.class, char.class);
        map.put(Double.class, double.class);
        map.put(Float.class, float.class);
        map.put(Integer.class, int.class);
        map.put(Long.class, long.class);
        map.put(Short.class, short.class);

        PRIMITIVE_MAP = Collections.unmodifiableMap(map);
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

    public static Set<Class<?>> getAllClassesFromPackage(String packagePath) {
        return new HashSet<>(new Reflections(packagePath).getSubTypesOf(Object.class));
    }

    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        return clazz.getDeclaredConstructor(parameterTypes);
    }

    public static <T> T createInstanceWithoutArgs(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException("Could not instantiate class: " + clazz.getName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T createInstanceWithoutArgs(String className) {
        Class<T> clazz = null;
        try {
            clazz = (Class<T>) Class.forName(className);
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException("Could not instantiate class: " + clazz.getName(), e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    public static Object invokeMethodFlexible(Object instance, String methodName, Object... args) {
        try {
            Class<?> clazz = instance.getClass();
            Method method = findMethod(clazz, methodName, args);
            if (method == null) {
                throw new NoSuchMethodException("No suitable method named " + methodName + " found in " + clazz.getName());
            }
            method.setAccessible(true);

            return method.invoke(instance, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Could not flexibly invoke method: " + methodName + " on class: " + instance.getClass().getName(), e);
        }
    }

    public static Object invokeStaticMethod(String className, String methodName, Object... args) {
        try {
            StringBuilder cacheKey = new StringBuilder().append(className).append("#").append(methodName).append("#").append(getParamTypesKey(args));
            MethodHandle handle = METHOD_HANDLE_CACHE.get(cacheKey.toString());

            if (handle == null) {
                Class<?> clazz = Class.forName(className);
                Method method = findMethod(clazz, methodName, args);
                if (method == null) {
                    throw new NoSuchMethodException("No static method named " + methodName + " found in " + className);
                }
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                handle = lookup.unreflect(method);
                METHOD_HANDLE_CACHE.put(cacheKey.toString(), handle);
            }

            return handle.invokeWithArguments(args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<Object> invokeMethodIfExists(Object instance, String methodName, Object... args) {
        try {
            Method method = findMethod(instance.getClass(), methodName, args);
            if (method != null) {
                method.setAccessible(true);
                return Optional.ofNullable(method.invoke(instance, args));
            }
        } catch (Exception e) {
            DebugLog.warning(ClassUtil.class, "Optional method call failed: " + e.getMessage());
        }
        return Optional.empty();
    }

    public static Object getFieldValue(Object instance, String fieldName) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Could not access field: " + fieldName + " on class: " + instance.getClass().getName(), e);
        }
    }

    public static void setFieldValue(Object instance, String fieldName, Object value) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Could not set field: " + fieldName + " on class: " + instance.getClass().getName(), e);
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

    private static String getParamTypesKey(Object... args) {
        if (args == null || args.length == 0) {
            return "";
        }
        StringBuilder key = new StringBuilder();
        for (Object arg : args) {
            key.append(arg == null ? "null" : arg.getClass().getName()).append(",");
        }
        return key.toString();
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

    public static void setStaticFieldValue(String className, String fieldName, Object value) {
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(null, value);
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
            throw new RuntimeException("Could not set static field: " + fieldName + " on class: " + className, e);
        }
    }
}