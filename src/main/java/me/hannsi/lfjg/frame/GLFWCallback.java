package me.hannsi.lfjg.frame;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.event.events.user.*;
import me.hannsi.lfjg.frame.setting.settings.CheckSeveritiesSetting;
import me.hannsi.lfjg.frame.setting.settings.MonitorSetting;
import me.hannsi.lfjg.utils.graphics.GLFWUtil;
import me.hannsi.lfjg.utils.type.types.SeverityType;
import org.joml.Vector2i;
import org.lwjgl.glfw.*;
import org.lwjgl.openal.AL11;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLDebugMessageCallback;

import static org.lwjgl.system.MemoryUtil.memUTF8;

public class GLFWCallback implements IFrame {
    private final Frame frame;

    public GLFWCallback(Frame frame) {
        this.frame = frame;
    }

    public void glfwInvoke() {
        GLFW.glfwSetWindowFocusCallback(frame.getWindowID(), new GLFWWindowFocusCallbackI() {
            @Override
            public void invoke(long window, boolean focused) {

            }
        });
        GLFW.glfwSetFramebufferSizeCallback(frame.getWindowID(), new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                Vector2i windowSizes = GLFWUtil.getWindowSizes(frame, frame.getFrameSettingValue(MonitorSetting.class));
                frame.setWindowWidth(windowSizes.x());
                frame.setWindowHeight(windowSizes.y());

                frame.updateViewport();
            }
        });

        GLFW.glfwSetKeyCallback(frame.getWindowID(), new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW.GLFW_PRESS) {
                    eventManager.call(new KeyPressEvent(key, scancode, mods, window));
                } else if (action == GLFW.GLFW_RELEASE) {
                    eventManager.call(new KeyReleasedEvent(key, scancode, mods, window));
                }
            }
        });

        GLFW.glfwSetCursorPosCallback(frame.getWindowID(), new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                eventManager.call(new CursorPosEvent(xpos, ypos, window));
            }
        });

        GLFW.glfwSetCursorEnterCallback(frame.getWindowID(), new GLFWCursorEnterCallback() {
            @Override
            public void invoke(long window, boolean entered) {
                eventManager.call(new CursorEnterEvent(window, entered));
            }
        });

        GLFW.glfwSetMouseButtonCallback(frame.getWindowID(), new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                eventManager.call(new MouseButtonCallbackEvent(window, button, action, mods));
                if (action == GLFW.GLFW_PRESS) {
                    eventManager.call(new MouseButtonPressEvent(button, mods, window));
                } else if (action == GLFW.GLFW_RELEASE) {
                    eventManager.call(new MouseButtonReleasedEvent(button, mods, window));
                }
            }
        });

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
            DebugLog.debug(getClass(), "OpenGL 4.3 or higher is required for debug messages.");
        }
    }

    private String getSourceString(int source) {
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

    private String getTypeString(int type) {
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

    private String getSeverityString(int severity) {
        return switch (severity) {
            case GL43.GL_DEBUG_SEVERITY_NOTIFICATION -> "Notification";
            case GL43.GL_DEBUG_SEVERITY_LOW -> "Low";
            case GL43.GL_DEBUG_SEVERITY_MEDIUM -> "Medium";
            case GL43.GL_DEBUG_SEVERITY_HIGH -> "High";
            default -> "Unknown";
        };
    }
}
