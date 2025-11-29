package me.hannsi.lfjg.render.debug;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.StackTraceUtil;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.system.MemoryUtil;

import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.core.Core.OPEN_GL_PARAMETER_NAME_MAP;
import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;

public class OpenGLDebug {
    public static final Map<Integer, Integer> OPEN_GL_DEBUG_SEVERITY_MAP = new HashMap<Integer, Integer>() {{
        put(0, OPEN_GL_PARAMETER_NAME_MAP.get("GL_DEBUG_SEVERITY_NOTIFICATION"));
        put(1, OPEN_GL_PARAMETER_NAME_MAP.get("GL_DEBUG_SEVERITY_LOW"));
        put(2, OPEN_GL_PARAMETER_NAME_MAP.get("GL_DEBUG_SEVERITY_MEDIUM"));
        put(3, OPEN_GL_PARAMETER_NAME_MAP.get("GL_DEBUG_SEVERITY_HIGH"));
    }};

    public static void getOpenGLDebug(String mainThreadName, int[] severityTypes) {
        if (GL.getCapabilities().OpenGL43) {
            glStateCache.enable(GL43.GL_DEBUG_OUTPUT);
            glStateCache.enable(GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS);
            GL43.glDebugMessageCallback(new GLDebugMessageCallback() {
                @Override
                public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
                    String errorMessage = MemoryUtil.memUTF8(message);
                    String stackTrace = StackTraceUtil.getStackTraceWithInsert(mainThreadName, "\t\t", "invoke", "callback", "getStackTraceWithInsert", "getStackTrace");
                    String sourceString = getSourceString(source);
                    String typeString = getTypeString(type);
                    String severityString = getSeverityString(severity);

                    for (int checkSeverity : severityTypes) {
                        checkSeverity = OPEN_GL_DEBUG_SEVERITY_MAP.get(checkSeverity);

                        if (checkSeverity == severity) {
                            LogGenerator logGenerator = new LogGenerator(
                                    "OpenGL Debug Message",
                                    "Source: " + sourceString,
                                    "Type: " + typeString,
                                    "ID: " + id, "Severity: " + severityString, "Message: " + errorMessage,
                                    "Stack Trace: \n" + stackTrace
                            );

                            if (type == GL43.GL_DEBUG_TYPE_ERROR || severity == GL43.GL_DEBUG_SEVERITY_HIGH) {
                                logGenerator.logging(getClass(), DebugLevel.ERROR);
                            } else if (severity == GL43.GL_DEBUG_SEVERITY_MEDIUM) {
                                logGenerator.logging(getClass(), DebugLevel.WARNING);
                            } else if (severity == GL43.GL_DEBUG_SEVERITY_LOW) {
                                logGenerator.logging(getClass(), DebugLevel.INFO);
                            } else {
                                logGenerator.logging(getClass(), DebugLevel.DEBUG);
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
            case GL43.GL_DEBUG_SOURCE_API:
                return "API";
            case GL43.GL_DEBUG_SOURCE_WINDOW_SYSTEM:
                return "Window System";
            case GL43.GL_DEBUG_SOURCE_SHADER_COMPILER:
                return "Shader Compiler";
            case GL43.GL_DEBUG_SOURCE_THIRD_PARTY:
                return "Third Party";
            case GL43.GL_DEBUG_SOURCE_APPLICATION:
                return "Application";
            case GL43.GL_DEBUG_SOURCE_OTHER:
                return "Other";
            default:
                return "Unknown";
        }
    }

    public static String getTypeString(int type) {
        switch (type) {
            case GL43.GL_DEBUG_TYPE_ERROR:
                return "Error";
            case GL43.GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR:
                return "Deprecated Behavior";
            case GL43.GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR:
                return "Undefined Behavior";
            case GL43.GL_DEBUG_TYPE_PORTABILITY:
                return "Portability";
            case GL43.GL_DEBUG_TYPE_PERFORMANCE:
                return "Performance";
            case GL43.GL_DEBUG_TYPE_OTHER:
                return "Other";
            default:
                return "Unknown";
        }
    }

    public static String getSeverityString(int severity) {
        switch (severity) {
            case GL43.GL_DEBUG_SEVERITY_NOTIFICATION:
                return "Notification";
            case GL43.GL_DEBUG_SEVERITY_LOW:
                return "Low";
            case GL43.GL_DEBUG_SEVERITY_MEDIUM:
                return "Medium";
            case GL43.GL_DEBUG_SEVERITY_HIGH:
                return "High";
            default:
                return "Unknown";
        }
    }
}