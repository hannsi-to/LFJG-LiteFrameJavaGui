package me.hannsi.lfjg.hookAgent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LFJGHookAgent {
    public static void premain(String arguments, Instrumentation instrumentation) {
        System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] [INFO] [" + LFJGHookAgent.class.getSimpleName() + "] LFJGHookAgent is load");

        new AgentBuilder.Default()
                .type(ElementMatchers.nameStartsWith("org.lwjgl.opengl.GL").and(ElementMatchers.not(ElementMatchers.nameEndsWith("Capabilities"))))
                .transform((builder, type, classLoader, module, protectionDomain) -> builder
                        .method(ElementMatchers.isStatic().and(ElementMatchers.not(ElementMatchers.isNative())))
                        .intercept(MethodDelegation.to(GLInterceptor.class)))
                .installOn(instrumentation);
    }
}
