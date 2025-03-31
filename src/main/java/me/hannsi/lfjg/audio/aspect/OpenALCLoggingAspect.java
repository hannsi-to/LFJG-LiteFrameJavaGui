package me.hannsi.lfjg.audio.aspect;

import me.hannsi.lfjg.audio.openAL.OpenALCDebug;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class OpenALCLoggingAspect {
    @After("execution(* org.lwjgl.openal.ALC10.*(..)) || execution(* org.lwjgl.openal.ALC11.*(..))")
    public void checkALC(JoinPoint joinPoint) {
        if (joinPoint.getSignature().getName().equals("alcGetError")) {
            return;
        }

        OpenALCDebug.getOpenALCError("checkALC");
    }
}
