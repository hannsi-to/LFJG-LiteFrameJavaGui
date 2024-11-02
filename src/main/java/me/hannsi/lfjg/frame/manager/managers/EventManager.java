package me.hannsi.lfjg.frame.manager.managers;

import me.hannsi.lfjg.event.system.Event;
import me.hannsi.lfjg.event.system.EventHandler;

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
                try {
                    if (!source.isCanceled()) {
                        method.invoke(handler, source);
                    }
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
