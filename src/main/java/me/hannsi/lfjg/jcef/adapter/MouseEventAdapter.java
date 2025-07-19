package me.hannsi.lfjg.jcef.adapter;

import org.cef.browser.CefBrowserOsr;

import java.awt.*;
import java.awt.event.MouseEvent;

import static org.lwjgl.glfw.GLFW.*;

public class MouseEventAdapter {
    private final Component dummyComponent;

    public MouseEventAdapter(CefBrowserOsr cefBrowserOsr) {
        this.dummyComponent = cefBrowserOsr.getUIComponent();
    }

    public MouseEvent convertGLFWMouseEvent(int glfwButton, int action, int mods, double xpos, double ypos) {
        int awtButton = mapGLFWToAWTButton(glfwButton);
        int awtID = mapGLFWActionToAWTEventID(action);

        return new MouseEvent(
                dummyComponent,
                awtID,
                System.currentTimeMillis(),
                mods,
                (int) xpos,
                (int) ypos,
                1,
                false,
                awtButton
        );
    }

    private int mapGLFWToAWTButton(int glfwButton) {
        return switch (glfwButton) {
            case GLFW_MOUSE_BUTTON_LEFT -> MouseEvent.BUTTON1;
            case GLFW_MOUSE_BUTTON_MIDDLE -> MouseEvent.BUTTON2;
            case GLFW_MOUSE_BUTTON_RIGHT -> MouseEvent.BUTTON3;
            default -> MouseEvent.NOBUTTON;
        };
    }

    private int mapGLFWActionToAWTEventID(int action) {
        return switch (action) {
            case GLFW_PRESS -> MouseEvent.MOUSE_PRESSED;
            case GLFW_RELEASE -> MouseEvent.MOUSE_RELEASED;
            default -> MouseEvent.MOUSE_MOVED;
        };
    }

    public MouseEvent createMouseMovedEvent(double xpos, double ypos) {
        return new MouseEvent(
                dummyComponent,
                MouseEvent.MOUSE_MOVED,
                System.currentTimeMillis(),
                0,
                (int) xpos,
                (int) ypos,
                0,
                false,
                MouseEvent.NOBUTTON
        );
    }
}
