package me.hannsi.lfjg.frame.glfw;

import me.hannsi.lfjg.event.events.render.DrawFrameWithOpenGLEvent;
import me.hannsi.lfjg.event.events.user.*;
import me.hannsi.lfjg.event.system.EventHandler;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.IFrame;
import me.hannsi.lfjg.frame.openAL.OpenALDebug;
import me.hannsi.lfjg.frame.openGL.OpenGLDebug;
import me.hannsi.lfjg.frame.setting.settings.MonitorSetting;
import me.hannsi.lfjg.frame.setting.settings.OpenALDebugSetting;
import me.hannsi.lfjg.frame.setting.settings.OpenGLDebugSetting;
import me.hannsi.lfjg.utils.graphics.GLFWUtil;
import org.joml.Vector2i;
import org.lwjgl.glfw.*;

public class GLFWCallback implements IFrame {
    private final Frame frame;

    public GLFWCallback(Frame frame) {
        eventManager.register(this);
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

        if (frame.getFrameSettingValue(OpenGLDebugSetting.class)) {
            OpenGLDebug.getOpenGLDebug(frame);
        }
    }

    @EventHandler
    public void drawFrameWidthOpenGLEvent(DrawFrameWithOpenGLEvent event) {
        if (frame.getFrameSettingValue(OpenALDebugSetting.class)) {
            OpenALDebug.getOpenALError();
        }
    }
}
