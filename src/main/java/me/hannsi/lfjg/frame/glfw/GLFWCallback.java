package me.hannsi.lfjg.frame.glfw;

import me.hannsi.lfjg.event.events.monitor.MonitorEvent;
import me.hannsi.lfjg.event.events.monitor.window.*;
import me.hannsi.lfjg.event.events.user.*;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.IFrame;
import me.hannsi.lfjg.frame.openGL.OpenGLDebug;
import me.hannsi.lfjg.frame.setting.settings.OpenGLDebugSetting;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * The GLFWCallback class is responsible for handling GLFW callbacks and managing events related to window focus, framebuffer size, content scale, key input, cursor position, cursor enter, and mouse button input.
 */
public class GLFWCallback implements IFrame {
    private final Frame frame;

    /**
     * Constructs a GLFWCallback object and registers it with the event manager.
     *
     * @param frame The Frame object associated with this callback.
     */
    public GLFWCallback(Frame frame) {
        eventManager.register(this);
        this.frame = frame;
    }

    /**
     * Sets up GLFW callbacks for window focus, framebuffer size, content scale, key input, cursor position, cursor enter, and mouse button input.
     */
    public void glfwInvoke() {
        GLFWWindowSizeCallback windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                eventManager.call(new WindowSizeEvent(window, width, height));
            }
        };
        glfwSetWindowSizeCallback(frame.getWindowID(), windowSizeCallback);

        GLFWWindowPosCallback windowPosCallback = new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int xpos, int ypos) {
                eventManager.call(new WindowSizeEvent(window, xpos, ypos));
            }
        };
        glfwSetWindowPosCallback(frame.getWindowID(), windowPosCallback);

        GLFWWindowCloseCallback windowCloseCallback = new GLFWWindowCloseCallback() {
            @Override
            public void invoke(long window) {
                eventManager.call(new WindowCloseEvent(window));
            }
        };
        glfwSetWindowCloseCallback(frame.getWindowID(), windowCloseCallback);

        GLFWFramebufferSizeCallback framebufferSizeCallback = new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                eventManager.call(new FramebufferSizeEvent(window, width, height));

                if (width == 0 || height == 0) {
                    return;
                }

                frame.setWindowWidth(width);
                frame.setWindowHeight(height);
            }
        };
        glfwSetFramebufferSizeCallback(frame.getWindowID(), framebufferSizeCallback);

        GLFWWindowRefreshCallback windowRefreshCallback = new GLFWWindowRefreshCallback() {
            @Override
            public void invoke(long window) {
                eventManager.call(new WindowRefreshEvent(window));
            }
        };
        glfwSetWindowRefreshCallback(frame.getWindowID(), windowRefreshCallback);

        GLFWWindowFocusCallback windowFocusCallback = new GLFWWindowFocusCallback() {
            @Override
            public void invoke(long window, boolean focused) {
                eventManager.call(new WindowFocusEvent(window, focused));
            }
        };
        glfwSetWindowFocusCallback(frame.getWindowID(), windowFocusCallback);

        GLFWWindowIconifyCallback windowIconifyCallback = new GLFWWindowIconifyCallback() {
            @Override
            public void invoke(long window, boolean iconified) {
                eventManager.call(new WindowIconifyEvent(window, iconified));
            }
        };
        glfwSetWindowIconifyCallback(frame.getWindowID(), windowIconifyCallback);

        GLFWWindowMaximizeCallback windowMaximizeCallback = new GLFWWindowMaximizeCallback() {
            @Override
            public void invoke(long window, boolean maximized) {
                eventManager.call(new WindowMaximizeEvent(window, maximized));
            }
        };
        glfwSetWindowMaximizeCallback(frame.getWindowID(), windowMaximizeCallback);

        GLFWWindowContentScaleCallback windowContentScaleCallback = new GLFWWindowContentScaleCallback() {
            @Override
            public void invoke(long window, float xscale, float yscale) {
                eventManager.call(new WindowContentScaleEvent(window, xscale, yscale));

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
                eventManager.call(new KeyEvent(window, key, scancode, action, mods));
                if (action == GLFW.GLFW_PRESS) {
                    eventManager.call(new KeyPressEvent(key, scancode, mods, window));
                } else if (action == GLFW.GLFW_RELEASE) {
                    eventManager.call(new KeyReleasedEvent(key, scancode, mods, window));
                }
            }
        };
        glfwSetKeyCallback(frame.getWindowID(), keyCallback);

        GLFWCharCallback charCallback = new GLFWCharCallback() {
            @Override
            public void invoke(long window, int codepoint) {
                eventManager.call(new CharEvent(window, codepoint));
            }
        };
        glfwSetCharCallback(frame.getWindowID(), charCallback);

        GLFWMouseButtonCallback mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                eventManager.call(new MouseButtonCallbackEvent(window, button, action, mods));
                if (action == GLFW.GLFW_PRESS) {
                    eventManager.call(new MouseButtonPressEvent(button, mods, window));
                } else if (action == GLFW.GLFW_RELEASE) {
                    eventManager.call(new MouseButtonReleasedEvent(button, mods, window));
                }
            }
        };
        glfwSetMouseButtonCallback(frame.getWindowID(), mouseButtonCallback);

        GLFWCursorPosCallback cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                eventManager.call(new CursorPosEvent(xpos, ypos, window));
            }
        };
        glfwSetCursorPosCallback(frame.getWindowID(), cursorPosCallback);

        GLFWCursorEnterCallback cursorEnterCallback = new GLFWCursorEnterCallback() {
            @Override
            public void invoke(long window, boolean entered) {
                eventManager.call(new CursorEnterEvent(window, entered));
            }
        };
        glfwSetCursorEnterCallback(frame.getWindowID(), cursorEnterCallback);

        GLFWScrollCallback scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                eventManager.call(new ScrollEvent(window, xoffset, yoffset));
            }
        };
        glfwSetScrollCallback(frame.getWindowID(), scrollCallback);

        GLFWMonitorCallback monitorCallback = new GLFWMonitorCallback() {
            @Override
            public void invoke(long monitor, int event) {
                eventManager.call(new MonitorEvent(monitor, event));
            }
        };
        glfwSetMonitorCallback(monitorCallback);

        GLFWDropCallback dropCallback = new GLFWDropCallback() {
            @Override
            public void invoke(long window, int count, long names) {
                eventManager.call(new DropEvent(window, count, names));
            }
        };
        glfwSetDropCallback(frame.getWindowID(), dropCallback);

        GLFWJoystickCallback joystickCallback = new GLFWJoystickCallback() {
            @Override
            public void invoke(int jid, int event) {
                eventManager.call(new JoystickEvent(jid, event));
            }
        };
        glfwSetJoystickCallback(joystickCallback);

        if (frame.getFrameSettingValue(OpenGLDebugSetting.class)) {
            OpenGLDebug.getOpenGLDebug(frame);
        }
    }
}
