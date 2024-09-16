package me.hannsi.lfjg.util;

import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.frame.Frame;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassUtil {
    public static <T> Set<Class<? extends T>> getClassesFormPackage(String packagePath, Class<T> clazz) {
        return new HashSet<>(new Reflections(packagePath).getSubTypesOf(clazz));
    }

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
            DebugLog.error(frame, "Could not instantiate class: " + clazz.getName() + "\n" + e);
            throw new RuntimeException("Could not instantiate class: " + clazz.getName(), e);
        } catch (IllegalAccessException e) {
            DebugLog.error(frame, "Illegal access to constructor: " + clazz.getName() + "\n" + e);
            throw new RuntimeException("Illegal access to constructor: " + clazz.getName(), e);
        } catch (InvocationTargetException e) {
            DebugLog.error(frame, "Constructor threw an exception: " + clazz.getName() + "\n" + e);
            throw new RuntimeException("Constructor threw an exception: " + clazz.getName(), e);
        } catch (NoSuchMethodException e) {
            DebugLog.error(frame, "No matching constructor found: " + clazz.getName() + "\n" + e);
            throw new RuntimeException("No matching constructor found: " + clazz.getName(), e);
        }

        return instance;
    }
}
