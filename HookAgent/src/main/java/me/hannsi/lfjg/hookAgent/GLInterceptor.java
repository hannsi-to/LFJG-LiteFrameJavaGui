package me.hannsi.lfjg.hookAgent;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.event.events.OpenGLStaticMethodHookEvent;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import static me.hannsi.lfjg.core.Core.EVENT_MANAGER;
import static me.hannsi.lfjg.core.CoreSystemSetting.GL_INTERCEPTOR_CALL_OPENGL_STATIC_METHOD_HOOK_EVENT;
import static me.hannsi.lfjg.core.CoreSystemSetting.GL_INTERCEPTOR_DEBUG;

public class GLInterceptor {
    @RuntimeType
    public static Object intercept(@Origin Method method, @AllArguments Object[] args, @SuperCall Callable<?> zuper) throws Exception {
        if (GL_INTERCEPTOR_DEBUG) {
            StringBuilder stringBuilder;
            if (args != null) {
                if (args.length == 0) {
                    stringBuilder = new StringBuilder("[]");
                } else {
                    stringBuilder = new StringBuilder("[");
                    for (int i = 0; i < args.length; i++) {
                        stringBuilder.append(args[i]);

                        if (i != args.length - 1) {
                            stringBuilder.append(", ");
                        }
                    }
                    stringBuilder.append(']');
                }
            } else {
                stringBuilder = new StringBuilder("null");
            }

            DebugLog.debug(GLInterceptor.class, "Hook: " + method.getDeclaringClass().getName() + "." + method.getName() + "(); args=" + stringBuilder);
        }

        if (GL_INTERCEPTOR_CALL_OPENGL_STATIC_METHOD_HOOK_EVENT) {
            OpenGLStaticMethodHookEvent openGLStaticMethodHookEvent = new OpenGLStaticMethodHookEvent(method, args, zuper);
            EVENT_MANAGER.call(openGLStaticMethodHookEvent);
            if (openGLStaticMethodHookEvent.isCanceled()) {
                return null;
            }
        }

        try {
            return zuper.call();
        } catch (Throwable t) {
            DebugLog.error(GLInterceptor.class, t);
            throw t;
        }
    }
}