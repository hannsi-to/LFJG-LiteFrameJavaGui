package me.hannsi.lfjg.aspect;

import me.hannsi.lfjg.frame.openAL.OpenALCDebug;
import me.hannsi.lfjg.frame.openAL.OpenALDebug;
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

        OpenALDebug.getOpenALError("checkALC");
    }
}
