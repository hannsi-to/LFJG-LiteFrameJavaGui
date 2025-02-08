package me.hannsi.lfjg.utils.reflection;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.frame.Frame;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public static <T> T createInstance(Frame frame, Class<T> clazz, Object... args) {
        T instance;
        List<Class<?>> parameterTypes = new ArrayList<>();

        for (Object arg : args) {
            parameterTypes.add(arg != null ? arg.getClass() : Object.class);
        }

        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(parameterTypes.toArray(new Class<?>[0]));
            instance = constructor.newInstance(args);
        } catch (InstantiationException e) {
            DebugLog.error(ClassUtil.class, "Could not instantiate class: " + clazz.getName() + "\n" + e);
            throw new RuntimeException("Could not instantiate class: " + clazz.getName(), e);
        } catch (IllegalAccessException e) {
            DebugLog.error(ClassUtil.class, "Illegal access to constructor: " + clazz.getName() + "\n" + e);
            throw new RuntimeException("Illegal access to constructor: " + clazz.getName(), e);
        } catch (InvocationTargetException e) {
            DebugLog.error(ClassUtil.class, "Constructor threw an exception: " + clazz.getName() + "\n" + e);
            throw new RuntimeException("Constructor threw an exception: " + clazz.getName(), e);
        } catch (NoSuchMethodException e) {
            DebugLog.error(ClassUtil.class, "No matching constructor found: " + clazz.getName() + "\n" + e);
            throw new RuntimeException("No matching constructor found: " + clazz.getName(), e);
        }

        return instance;
    }
}