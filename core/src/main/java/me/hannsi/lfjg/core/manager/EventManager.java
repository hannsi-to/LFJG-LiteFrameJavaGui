package me.hannsi.lfjg.core.manager;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.event.Event;
import me.hannsi.lfjg.core.event.EventHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager {
    private final CopyOnWriteArrayList<Object> handlers;

    public EventManager() {
        handlers = new CopyOnWriteArrayList<>();
    }

    public void register(Object handler) {
        handlers.add(handler);
    }

    public void unregister(Object handler) {
        handlers.remove(handler);
    }

    public boolean hasRegistered(Object handler) {
        return handlers.contains(handler);
    }

    public <E extends Event> void call(E source) {
        for (Object handler : handlers) {
            Class<?> clazz = handler.getClass();
            Arrays.stream(clazz.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(EventHandler.class)).filter(method -> method.getParameters().length == 1 && method.getParameters()[0].getType().isAssignableFrom(source.getClass())).forEach(method -> {
                if (!source.isCanceled()) {
                    try {
                        method.invoke(handler, source);
                    } catch (IllegalAccessException | InvocationTargetException ignore) {

                    }
                }
            });
        }
    }

    public void cleanup() {
        handlers.clear();

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
}