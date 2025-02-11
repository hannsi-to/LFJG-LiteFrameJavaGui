package me.hannsi.lfjg.aspect;

import me.hannsi.lfjg.frame.openAL.OpenALDebug;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class OpenALLoggingAspect {
    @After("call(public static * org.lwjgl.openal.AL10.*(..))")
    public void checkAL10(JoinPoint joinPoint) {
        if (joinPoint.getSignature().getName().equals("alGetError")) {
            return;
        }

        OpenALDebug.getOpenALError("checkAL10");
    }

    @After("call(public static * org.lwjgl.openal.AL11.*(..))")
    public void checkAL11(JoinPoint joinPoint) {
        if (joinPoint.getSignature().getName().equals("alGetError")) {
            return;
        }

        OpenALDebug.getOpenALError("checkAL11");
    }
}
