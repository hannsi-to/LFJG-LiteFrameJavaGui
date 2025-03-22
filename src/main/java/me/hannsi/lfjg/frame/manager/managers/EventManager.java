package me.hannsi.lfjg.frame.manager.managers;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.event.system.Event;
import me.hannsi.lfjg.event.system.EventHandler;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Manages event handlers and dispatches events to them.
 */
public class EventManager {
    private final CopyOnWriteArrayList<Object> handlers;

    /**
     * Constructs a new EventManager.
     */
    public EventManager() {
        handlers = new CopyOnWriteArrayList<>();
    }

    /**
     * Registers an event handler.
     *
     * @param handler the event handler to register
     */
    public void register(Object handler) {
        handlers.add(handler);
    }

    /**
     * Unregisters an event handler.
     *
     * @param handler the event handler to unregister
     */
    public void unregister(Object handler) {
        handlers.remove(handler);
    }

    /**
     * Checks if an event handler is registered.
     *
     * @param handler the event handler to check
     * @return true if the handler is registered, false otherwise
     */
    public boolean hasRegistered(Object handler) {
        return handlers.contains(handler);
    }

    /**
     * Calls an event, dispatching it to all registered handlers.
     *
     * @param source the event to call
     * @param <E>    the type of the event
     */
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

    public void cleanup() {
        handlers.clear();

        LogGenerator logGenerator = new LogGenerator("EventManager", "Source: EventManager", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: EventManager cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }
}