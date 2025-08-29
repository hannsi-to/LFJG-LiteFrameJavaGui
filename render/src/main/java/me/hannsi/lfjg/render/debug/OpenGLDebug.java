package me.hannsi.lfjg.render.debug;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.StackTraceUtil;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.opengl.GLDebugMessageCallback;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL.getCapabilities;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.system.MemoryUtil.memUTF8;

public class OpenGLDebug {
    public static final Map<Integer, Integer> SEVERITY_MAP = new HashMap<>();

    static {
        SEVERITY_MAP.put(0, GL_DEBUG_SEVERITY_NOTIFICATION);
        SEVERITY_MAP.put(1, GL_DEBUG_SEVERITY_LOW);
        SEVERITY_MAP.put(2, GL_DEBUG_SEVERITY_MEDIUM);
        SEVERITY_MAP.put(3, GL_DEBUG_SEVERITY_HIGH);
    }

    public static void getOpenGLDebug(String mainThreadName, int[] severityTypes) {
        if (getCapabilities().OpenGL43) {
            GLStateCache.enable(GL_DEBUG_OUTPUT);
            GLStateCache.enable(GL_DEBUG_OUTPUT_SYNCHRONOUS);
            glDebugMessageCallback(new GLDebugMessageCallback() {
                @Override
                public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
                    String errorMessage = memUTF8(message);
                    String stackTrace = StackTraceUtil.getStackTraceWithInsert(mainThreadName, "\t\t", "invoke", "callback", "getStackTraceWithInsert", "getStackTrace");
                    String sourceString = getSourceString(source);
                    String typeString = getTypeString(type);
                    String severityString = getSeverityString(severity);

                    for (int checkSeverity : severityTypes) {
                        checkSeverity = SEVERITY_MAP.get(checkSeverity);

                        if (checkSeverity == severity) {
                            LogGenerator logGenerator = new LogGenerator(
                                    "OpenGL Debug Message",
                                    "Source: " + sourceString,
                                    "Type: " + typeString,
                                    "ID: " + id, "Severity: " + severityString, "Message: " + errorMessage,
                                    "Stack Trace: \n" + stackTrace
                            );

                            if (type == GL_DEBUG_TYPE_ERROR || severity == GL_DEBUG_SEVERITY_HIGH) {
                                logGenerator.logging(DebugLevel.ERROR);
                            } else if (severity == GL_DEBUG_SEVERITY_MEDIUM) {
                                logGenerator.logging(DebugLevel.WARNING);
                            } else if (severity == GL_DEBUG_SEVERITY_LOW) {
                                logGenerator.logging(DebugLevel.INFO);
                            } else {
                                logGenerator.logging(DebugLevel.DEBUG);
                            }
                        }
                    }
                }
            }, 0);
        } else {
            DebugLog.debug(OpenGLDebug.class, "OpenGL 4.3 or higher is required for debug messages.");
        }
    }

    public static String getSourceString(int source) {
        switch (source) {
            case GL_DEBUG_SOURCE_API:
                return "API";
            case GL_DEBUG_SOURCE_WINDOW_SYSTEM:
                return "Window System";
            case GL_DEBUG_SOURCE_SHADER_COMPILER:
                return "Shader Compiler";
            case GL_DEBUG_SOURCE_THIRD_PARTY:
                return "Third Party";
            case GL_DEBUG_SOURCE_APPLICATION:
                return "Application";
            case GL_DEBUG_SOURCE_OTHER:
                return "Other";
            default:
                return "Unknown";
        }
    }

    public static String getTypeString(int type) {
        switch (type) {
            case GL_DEBUG_TYPE_ERROR:
                return "Error";
            case GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR:
                return "Deprecated Behavior";
            case GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR:
                return "Undefined Behavior";
            case GL_DEBUG_TYPE_PORTABILITY:
                return "Portability";
            case GL_DEBUG_TYPE_PERFORMANCE:
                return "Performance";
            case GL_DEBUG_TYPE_OTHER:
                return "Other";
            default:
                return "Unknown";
        }
    }

    public static String getSeverityString(int severity) {
        switch (severity) {
            case GL_DEBUG_SEVERITY_NOTIFICATION:
                return "Notification";
            case GL_DEBUG_SEVERITY_LOW:
                return "Low";
            case GL_DEBUG_SEVERITY_MEDIUM:
                return "Medium";
            case GL_DEBUG_SEVERITY_HIGH:
                return "High";
            default:
                return "Unknown";
        }
    }
}