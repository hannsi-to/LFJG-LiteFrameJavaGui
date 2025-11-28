package me.hannsi.lfjg.hookAgent;

import me.hannsi.lfjg.core.debug.DebugLog;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

import static me.hannsi.lfjg.core.SystemSetting.GL_INTERCEPTOR_DEBUG;

public class GLInterceptor {
    @RuntimeType
    public static Object intercept(@Origin Method method, @AllArguments Object[] args, @SuperCall Callable<?> zuper) throws Exception {
        if (GL_INTERCEPTOR_DEBUG) {
            DebugLog.debug(GLInterceptor.class, "Hook: " + method.getDeclaringClass().getName() + "." + method.getName() + "(); args=" + Arrays.toString(args));
        }

        try {
            return zuper.call();
        } catch (Throwable t) {
            DebugLog.error(GLInterceptor.class, t);
            throw t;
        }
    }
}