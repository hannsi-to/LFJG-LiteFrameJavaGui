package me.hannsi.lfjg.core.manager;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.event.Event;
import me.hannsi.lfjg.core.event.EventHandler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager {
    private final CopyOnWriteArrayList<Object> handlers = new CopyOnWriteArrayList<>();

    private final Map<Class<? extends Event>, List<HandlerMethod>> eventHandlers = new ConcurrentHashMap<>();

    public void register(Object handler) {
        if (handlers.contains(handler)) {
            return;
        }

        for (Method method : handler.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(EventHandler.class) || method.getParameterCount() != 1) {
                continue;
            }

            Class<?> param = method.getParameterTypes()[0];
            if (!Event.class.isAssignableFrom(param)) {
                continue;
            }

            @SuppressWarnings("unchecked")
            Class<? extends Event> eventType = (Class<? extends Event>) param;

            method.setAccessible(true);

            boolean isStatic = Modifier.isStatic(method.getModifiers());

            try {
                MethodHandle handle = MethodHandles.lookup().unreflect(method);

                Object target = isStatic ? null : handler;

                eventHandlers
                        .computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>())
                        .add(new HandlerMethod(target, handler.getClass(), handle, isStatic));

            } catch (IllegalAccessException e) {
                DebugLog.warning(getClass(), e);
            }
        }

        handlers.add(handler);
    }

    public void register(Class<?> clazz) {
        if (handlers.contains(clazz)) {
            return;
        }

        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(EventHandler.class) || method.getParameterCount() != 1) {
                continue;
            }

            if (!Modifier.isStatic(method.getModifiers())) {
                continue;
            }

            Class<?> param = method.getParameterTypes()[0];
            if (!Event.class.isAssignableFrom(param)) {
                continue;
            }

            @SuppressWarnings("unchecked")
            Class<? extends Event> eventType = (Class<? extends Event>) param;

            method.setAccessible(true);

            try {
                MethodHandle handle = MethodHandles.lookup().unreflect(method);

                eventHandlers
                        .computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>())
                        .add(new HandlerMethod(null, clazz, handle, true));

            } catch (IllegalAccessException e) {
                DebugLog.warning(getClass(), e);
            }
        }

        handlers.add(clazz);
    }

    public void unregister(Object handler) {
        handlers.remove(handler);

        for (List<HandlerMethod> handlerMethods : eventHandlers.values()) {
            handlerMethods.removeIf(h -> h.target.equals(handler));
        }
    }

    public void unregister(Class<?> clazz) {
        handlers.remove(clazz);

        for (List<HandlerMethod> list : eventHandlers.values()) {
            list.removeIf(h -> h.isStatic && h.owner == clazz);
        }
    }

    public boolean hasRegistered(Object handler) {
        return handlers.contains(handler);
    }

    public boolean hasRegistered(Class<?> clazz) {
        return handlers.contains(clazz);
    }

    public <E extends Event> void call(E event) {
        Class<?> type = event.getClass();

        while (type != null && Event.class.isAssignableFrom(type)) {
            @SuppressWarnings("unchecked")
            List<HandlerMethod> handlers = eventHandlers.get((Class<? extends Event>) type);

            if (handlers != null) {
                for (HandlerMethod handlerMethod : handlers) {
                    if (event.isCanceled()) {
                        return;
                    }
                    try {
                        handlerMethod.invoke(event);
                    } catch (Throwable t) {
                        DebugLog.warning(handlerMethod.owner, t);
                    }
                }
            }

            type = type.getSuperclass();
        }
    }

    public void cleanup() {
        handlers.clear();
        eventHandlers.clear();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                hashCode(),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);
    }

    public CopyOnWriteArrayList<Object> getHandlers() {
        return handlers;
    }

    private record HandlerMethod(Object target, Class<?> owner, MethodHandle handle, boolean isStatic) {
        void invoke(Event event) throws Throwable {
            if (isStatic) {
                handle.invoke(event);
            } else {
                handle.invoke(target, event);
            }
        }

        @Override
        public String toString() {
            return "HandlerMethod[target=" + target + ", owner=" + owner + ", handle=" + handle + ", isStatic" + isStatic + "]";
        }
    }
}