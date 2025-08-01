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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager {
    private final CopyOnWriteArrayList<Object> handlers = new CopyOnWriteArrayList<>();

    private final Map<Class<? extends Event>, List<HandlerMethod>> eventHandlers = new ConcurrentHashMap<>();

    public void register(Object handler) {
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

            try {
                MethodHandle handle = MethodHandles.lookup().unreflect(method);
                eventHandlers
                        .computeIfAbsent(eventType, k -> new ArrayList<>())
                        .add(new HandlerMethod(handler, handle));
            } catch (IllegalAccessException e) {
                DebugLog.warning(this.getClass(), e);
            }
        }
    }

    public boolean hasRegistered(Object handler) {
        return handlers.contains(handler);
    }

    public <E extends Event> void call(E event) {
        List<HandlerMethod> handlers = eventHandlers.get(event.getClass());
        if (handlers == null) {
            return;
        }

        for (HandlerMethod handlerMethod : handlers) {
            if (!event.isCanceled()) {
                try {
                    handlerMethod.handle.invoke(handlerMethod.target, event);
                } catch (Throwable ignored) {
                }
            }
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
        ).logging(DebugLevel.DEBUG);
    }

    public CopyOnWriteArrayList<Object> getHandlers() {
        return handlers;
    }

    private record HandlerMethod(Object target, MethodHandle handle) {
    }
}