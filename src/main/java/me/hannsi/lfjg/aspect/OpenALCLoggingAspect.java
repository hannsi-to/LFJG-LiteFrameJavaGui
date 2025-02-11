package me.hannsi.lfjg.aspect;

import me.hannsi.lfjg.frame.openAL.OpenALCDebug;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class OpenALCLoggingAspect {
    @After("call(public static * org.lwjgl.openal.ALC10.*(..))")
    public void checkALC10(JoinPoint joinPoint) {
        if (joinPoint.getSignature().getName().equals("alcGetError")) {
            return;
        }

        OpenALCDebug.getOpenALCError("checkALC10");
    }

    @After("call(public static * org.lwjgl.openal.ALC11.*(..))")
    public void checkALC11(JoinPoint joinPoint) {
        if (joinPoint.getSignature().getName().equals("alcGetError")) {
            return;
        }

        OpenALCDebug.getOpenALCError("checkALC11");
    }
}
