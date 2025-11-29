package me.hannsi.lfjg.core.event.events;

import me.hannsi.lfjg.core.event.Event;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class OpenGLStaticMethodHookEvent extends Event {
    private final Method method;
    private final Object[] args;
    private final Callable<?> zuper;

    public OpenGLStaticMethodHookEvent(Method method, Object[] args, Callable<?> zuper) {
        this.method = method;
        this.args = args;
        this.zuper = zuper;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    public Callable<?> getSuper() {
        return zuper;
    }
}
