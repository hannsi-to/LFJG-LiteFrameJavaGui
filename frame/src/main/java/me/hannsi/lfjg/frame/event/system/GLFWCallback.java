package me.hannsi.lfjg.frame.event.system;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.event.events.monitor.MonitorEvent;
import me.hannsi.lfjg.frame.event.events.monitor.window.*;
import me.hannsi.lfjg.frame.event.events.user.*;
import me.hannsi.lfjg.frame.setting.settings.CheckSeveritiesSetting;
import me.hannsi.lfjg.frame.setting.settings.OpenGLDebugSetting;
import me.hannsi.lfjg.frame.setting.settings.SeverityType;
import me.hannsi.lfjg.frame.system.IFrame;
import org.lwjgl.glfw.*;

import java.util.Arrays;

import static me.hannsi.lfjg.core.Core.EVENT_MANAGER;
import static me.hannsi.lfjg.core.Core.OpenGLDebug.getOpenGLDebug;
import static org.lwjgl.glfw.GLFW.*;

public class GLFWCallback implements IFrame {
    private final Frame frame;

    public GLFWCallback(Frame frame) {
        EVENT_MANAGER.register(this);
        this.frame = frame;
    }

    public void glfwInvoke() {
        GLFWWindowSizeCallback windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                EVENT_MANAGER.call(new WindowSizeEvent(window, width, height));
            }
        };
        glfwSetWindowSizeCallback(frame.getWindowID(), windowSizeCallback);

        GLFWWindowPosCallback windowPosCallback = new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int xpos, int ypos) {
                EVENT_MANAGER.call(new WindowPosEvent(window, xpos, ypos));
            }
        };
        glfwSetWindowPosCallback(frame.getWindowID(), windowPosCallback);

        GLFWWindowCloseCallback windowCloseCallback = new GLFWWindowCloseCallback() {
            @Override
            public void invoke(long window) {
                EVENT_MANAGER.call(new WindowCloseEvent(window));
            }
        };
        glfwSetWindowCloseCallback(frame.getWindowID(), windowCloseCallback);

        GLFWFramebufferSizeCallback framebufferSizeCallback = new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                EVENT_MANAGER.call(new FramebufferSizeEvent(window, width, height));

                if (width == 0 || height == 0) {
                    return;
                }

                frame.setFrameBufferWidth(width);
                frame.setFrameBufferHeight(height);
            }
        };
        glfwSetFramebufferSizeCallback(frame.getWindowID(), framebufferSizeCallback);

        GLFWWindowRefreshCallback windowRefreshCallback = new GLFWWindowRefreshCallback() {
            @Override
            public void invoke(long window) {
                EVENT_MANAGER.call(new WindowRefreshEvent(window));
            }
        };
        glfwSetWindowRefreshCallback(frame.getWindowID(), windowRefreshCallback);

        GLFWWindowFocusCallback windowFocusCallback = new GLFWWindowFocusCallback() {
            @Override
            public void invoke(long window, boolean focused) {
                EVENT_MANAGER.call(new WindowFocusEvent(window, focused));
            }
        };
        glfwSetWindowFocusCallback(frame.getWindowID(), windowFocusCallback);

        GLFWWindowIconifyCallback windowIconifyCallback = new GLFWWindowIconifyCallback() {
            @Override
            public void invoke(long window, boolean iconified) {
                EVENT_MANAGER.call(new WindowIconifyEvent(window, iconified));
            }
        };
        glfwSetWindowIconifyCallback(frame.getWindowID(), windowIconifyCallback);

        GLFWWindowMaximizeCallback windowMaximizeCallback = new GLFWWindowMaximizeCallback() {
            @Override
            public void invoke(long window, boolean maximized) {
                EVENT_MANAGER.call(new WindowMaximizeEvent(window, maximized));
            }
        };
        glfwSetWindowMaximizeCallback(frame.getWindowID(), windowMaximizeCallback);

        GLFWWindowContentScaleCallback windowContentScaleCallback = new GLFWWindowContentScaleCallback() {
            @Override
            public void invoke(long window, float xscale, float yscale) {
                EVENT_MANAGER.call(new WindowContentScaleEvent(window, xscale, yscale));

                if (xscale == 0 || yscale == 0) {
                    return;
                }

                frame.setContentScaleX(xscale);
                frame.setContentScaleY(yscale);
            }
        };
        glfwSetWindowContentScaleCallback(frame.getWindowID(), windowContentScaleCallback);

        GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                EVENT_MANAGER.call(new KeyEvent(window, key, scancode, action, mods));
                if (action == GLFW_PRESS) {
                    EVENT_MANAGER.call(new KeyPressEvent(key, scancode, mods, window));
                } else if (action == GLFW_RELEASE) {
                    EVENT_MANAGER.call(new KeyReleasedEvent(key, scancode, mods, window));
                }
            }
        };
        glfwSetKeyCallback(frame.getWindowID(), keyCallback);

        GLFWCharCallback charCallback = new GLFWCharCallback() {
            @Override
            public void invoke(long window, int codepoint) {
                EVENT_MANAGER.call(new CharEvent(window, codepoint));
            }
        };
        glfwSetCharCallback(frame.getWindowID(), charCallback);

        GLFWMouseButtonCallback mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                EVENT_MANAGER.call(new MouseButtonEvent(window, button, action, mods));
                if (action == GLFW_PRESS) {
                    EVENT_MANAGER.call(new MouseButtonPressEvent(button, mods, window));
                } else if (action == GLFW_RELEASE) {
                    EVENT_MANAGER.call(new MouseButtonReleasedEvent(button, mods, window));
                }
            }
        };
        glfwSetMouseButtonCallback(frame.getWindowID(), mouseButtonCallback);

        GLFWCursorPosCallback cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                EVENT_MANAGER.call(new CursorPosEvent(xpos, frame.getWindowHeight() - ypos, window));
            }
        };
        glfwSetCursorPosCallback(frame.getWindowID(), cursorPosCallback);

        GLFWCursorEnterCallback cursorEnterCallback = new GLFWCursorEnterCallback() {
            @Override
            public void invoke(long window, boolean entered) {
                EVENT_MANAGER.call(new CursorEnterEvent(window, entered));
            }
        };
        glfwSetCursorEnterCallback(frame.getWindowID(), cursorEnterCallback);

        GLFWScrollCallback scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                EVENT_MANAGER.call(new ScrollEvent(window, xoffset, yoffset));
            }
        };
        glfwSetScrollCallback(frame.getWindowID(), scrollCallback);

        GLFWMonitorCallback monitorCallback = new GLFWMonitorCallback() {
            @Override
            public void invoke(long monitor, int event) {
                EVENT_MANAGER.call(new MonitorEvent(monitor, event));
            }
        };
        glfwSetMonitorCallback(monitorCallback);

        GLFWDropCallback dropCallback = new GLFWDropCallback() {
            @Override
            public void invoke(long window, int count, long names) {
                EVENT_MANAGER.call(new DropEvent(window, count, names));
            }
        };
        glfwSetDropCallback(frame.getWindowID(), dropCallback);

        GLFWJoystickCallback joystickCallback = new GLFWJoystickCallback() {
            @Override
            public void invoke(int jid, int event) {
                EVENT_MANAGER.call(new JoystickEvent(jid, event));
            }
        };
        glfwSetJoystickCallback(joystickCallback);

        if (frame.getFrameSettingValue(OpenGLDebugSetting.class)) {
            int[] ids = Arrays.stream((SeverityType[]) frame.getFrameSettingValue(CheckSeveritiesSetting.class)).mapToInt(SeverityType::getId).toArray();

            getOpenGLDebug(frame.getThreadName(), ids);
        }
    }
}
