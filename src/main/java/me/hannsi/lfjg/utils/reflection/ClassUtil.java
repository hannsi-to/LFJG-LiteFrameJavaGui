package me.hannsi.lfjg.utils.reflection;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Utility class for reflection-based operations.
 */
public class ClassUtil {

    /**
     * Retrieves a set of classes from a specified package that are subclasses of a given class.
     *
     * @param packagePath the package path to search for classes
     * @param clazz the superclass to match subclasses against
     * @param <T> the type of the superclass
     * @return a set of classes that are subclasses of the specified class
     */
    public static <T> Set<Class<? extends T>> getClassesFormPackage(String packagePath, Class<T> clazz) {
        return new HashSet<>(new Reflections(packagePath).getSubTypesOf(clazz));
    }

    /**
     * Creates an instance of a specified class using the provided arguments.
     *
     * @param frame the frame context
     * @param clazz the class to instantiate
     * @param args the arguments to pass to the constructor
     * @param <T> the type of the class
     * @return an instance of the specified class
     * @throws RuntimeException if the class cannot be instantiated
     */
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
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Could not instantiate class: " + clazz.getName(), e);
        }
    }

    public static Object invokeMethod(Object instance, String methodName, Object... args) {
        try {
            Class<?> clazz = instance.getClass();
            Method method = clazz.getDeclaredMethod(methodName, getParameterTypes(args));
            method.setAccessible(true);
            return method.invoke(instance, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Could not invoke method: " + methodName + " on class: " + instance.getClass().getName(), e);
        }
    }

    private static Class<?>[] getParameterTypes(Object... args) {
        return Arrays.stream(args)
                .map(arg -> arg != null ? arg.getClass() : Object.class)
                .toArray(Class<?>[]::new);
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
}

