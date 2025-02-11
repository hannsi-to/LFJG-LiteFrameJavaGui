package me.hannsi.lfjg.frame.glfw;

import me.hannsi.lfjg.event.events.user.*;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.IFrame;
import me.hannsi.lfjg.frame.openGL.OpenGLDebug;
import me.hannsi.lfjg.frame.setting.settings.OpenGLDebugSetting;
import org.lwjgl.glfw.*;

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
        GLFW.glfwSetWindowFocusCallback(frame.getWindowID(), new GLFWWindowFocusCallbackI() {
            @Override
            public void invoke(long window, boolean focused) {

            }
        });
        GLFW.glfwSetFramebufferSizeCallback(frame.getWindowID(), new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                if (width == 0 || height == 0) {
                    return;
                }

                frame.setWindowWidth(width);
                frame.setWindowHeight(height);
            }
        });
        GLFW.glfwSetWindowContentScaleCallback(frame.getWindowID(), new GLFWWindowContentScaleCallbackI() {
            @Override
            public void invoke(long window, float contentScaleX, float contentScaleY) {
                if (contentScaleX == 0 || contentScaleY == 0) {
                    return;
                }

                frame.setContentScaleX(contentScaleX);
                frame.setContentScaleY(contentScaleY);
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

        if (frame.getFrameSettingValue(OpenGLDebugSetting.class)) {
            OpenGLDebug.getOpenGLDebug(frame);
        }
    }
}
