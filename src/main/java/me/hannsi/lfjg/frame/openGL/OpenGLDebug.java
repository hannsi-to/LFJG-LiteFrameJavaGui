package me.hannsi.lfjg.frame.openGL;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.CheckSeveritiesSetting;
import me.hannsi.lfjg.utils.type.types.SeverityType;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLDebugMessageCallback;

import static org.lwjgl.system.MemoryUtil.memUTF8;

/**
 * Provides debugging utilities for OpenGL.
 */
public class OpenGLDebug {

    /**
     * Enables OpenGL debug output and sets up a callback to handle debug messages.
     *
     * @param frame the frame to use for retrieving settings
     */
    public static void getOpenGLDebug(Frame frame) {
        if (GL.getCapabilities().OpenGL43) {
            GL43.glEnable(GL43.GL_DEBUG_OUTPUT);
            GL43.glDebugMessageCallback(new GLDebugMessageCallback() {
                @Override
                public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
                    String errorMessage = memUTF8(message);

                    String sourceString = getSourceString(source);
                    String typeString = getTypeString(type);
                    String severityString = getSeverityString(severity);

                    for (SeverityType checkSeverity : ((SeverityType[]) frame.getFrameSettingValue(CheckSeveritiesSetting.class))) {
                        if (checkSeverity.getId() == severity) {
                            LogGenerator logGenerator = new LogGenerator("OpenGL Debug Message", "Source: " + sourceString, "Type: " + typeString, "ID: " + id, "Severity: " + severityString, "Message: " + errorMessage);

                            if (type == GL43.GL_DEBUG_TYPE_ERROR || severity == GL43.GL_DEBUG_SEVERITY_HIGH) {
                                DebugLog.error(getClass(), logGenerator.createLog());
                            } else if (severity == GL43.GL_DEBUG_SEVERITY_MEDIUM) {
                                DebugLog.warning(getClass(), logGenerator.createLog());
                            } else if (severity == GL43.GL_DEBUG_SEVERITY_LOW) {
                                DebugLog.info(getClass(), logGenerator.createLog());
                            } else {
                                DebugLog.debug(getClass(), logGenerator.createLog());
                            }
                        }
                    }
                }
            }, 0);
        } else {
            DebugLog.debug(OpenGLDebug.class, "OpenGL 4.3 or higher is required for debug messages.");
        }
    }

    /**
     * Converts an OpenGL debug source code to a human-readable string.
     *
     * @param source the OpenGL debug source code
     * @return the corresponding source string
     */
    public static String getSourceString(int source) {
        return switch (source) {
            case GL43.GL_DEBUG_SOURCE_API -> "API";
            case GL43.GL_DEBUG_SOURCE_WINDOW_SYSTEM -> "Window System";
            case GL43.GL_DEBUG_SOURCE_SHADER_COMPILER -> "Shader Compiler";
            case GL43.GL_DEBUG_SOURCE_THIRD_PARTY -> "Third Party";
            case GL43.GL_DEBUG_SOURCE_APPLICATION -> "Application";
            case GL43.GL_DEBUG_SOURCE_OTHER -> "Other";
            default -> "Unknown";
        };
    }

    /**
     * Converts an OpenGL debug type code to a human-readable string.
     *
     * @param type the OpenGL debug type code
     * @return the corresponding type string
     */
    public static String getTypeString(int type) {
        return switch (type) {
            case GL43.GL_DEBUG_TYPE_ERROR -> "Error";
            case GL43.GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR -> "Deprecated Behavior";
            case GL43.GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR -> "Undefined Behavior";
            case GL43.GL_DEBUG_TYPE_PORTABILITY -> "Portability";
            case GL43.GL_DEBUG_TYPE_PERFORMANCE -> "Performance";
            case GL43.GL_DEBUG_TYPE_OTHER -> "Other";
            default -> "Unknown";
        };
    }

    /**
     * Converts an OpenGL debug severity code to a human-readable string.
     *
     * @param severity the OpenGL debug severity code
     * @return the corresponding severity string
     */
    public static String getSeverityString(int severity) {
        return switch (severity) {
            case GL43.GL_DEBUG_SEVERITY_NOTIFICATION -> "Notification";
            case GL43.GL_DEBUG_SEVERITY_LOW -> "Low";
            case GL43.GL_DEBUG_SEVERITY_MEDIUM -> "Medium";
            case GL43.GL_DEBUG_SEVERITY_HIGH -> "High";
            default -> "Unknown";
        };
    }
}