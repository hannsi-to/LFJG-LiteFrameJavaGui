package me.hannsi.lfjg.core.manager;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.event.Event;
import me.hannsi.lfjg.core.event.EventHandler;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class EventManager {
    private final CopyOnWriteArrayList<Object> handlers;
    private final Map<Class<? extends Event>, List<Consumer<Event>>> invokerCache = new HashMap<>();

    public EventManager() {
        handlers = new CopyOnWriteArrayList<>();
    }

    public void register(Object handler) {
        handlers.add(handler);
        buildInvokerCache(handler);
    }

    @SuppressWarnings("unchecked")
    private void buildInvokerCache(Object handler) {
        Class<?> clazz = handler.getClass();

        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if (!declaredMethod.isAnnotationPresent(EventHandler.class)) {
                continue;
            }
            if (declaredMethod.getParameterCount() != 1) {
                continue;
            }

            Class<?> parameterType = declaredMethod.getParameterTypes()[0];
            if (!Event.class.isAssignableFrom(parameterType)) {
                continue;
            }

            Class<? extends Event> eventClass = (Class<? extends Event>) parameterType;

            declaredMethod.setAccessible(true);
            try {
                Consumer<Event> lambda = createLambda(handler, declaredMethod);
                invokerCache.computeIfAbsent(eventClass, k -> new ArrayList<>()).add(lambda);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Consumer<Event> createLambda(Object handler, Method method) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(
                method.getDeclaringClass(),
                MethodHandles.lookup()
        );

        MethodHandle targetHandle = lookup.unreflect(method).bindTo(handler);

        MethodType samMethodType = MethodType.methodType(void.class, Object.class);
        MethodType invokedType = MethodType.methodType(Consumer.class);
        MethodType actualMethodType = MethodType.methodType(void.class, method.getParameterTypes()[0]);

        CallSite site = LambdaMetafactory.metafactory(
                lookup,
                "accept",
                invokedType,
                samMethodType,
                targetHandle,
                actualMethodType
        );

        return (Consumer<Event>) site.getTarget().invoke();
    }
    
    public void unregister(Object handler) {
        handlers.remove(handler);
    }

    public boolean hasRegistered(Object handler) {
        return handlers.contains(handler);
    }

    public <E extends Event> void call(E event) {
        List<Consumer<Event>> consumers = invokerCache.get(event.getClass());
        if (consumers == null) return;

        for (Consumer<Event> consumer : consumers) {
            if (!event.isCanceled()) {
                consumer.accept(event);
            }
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