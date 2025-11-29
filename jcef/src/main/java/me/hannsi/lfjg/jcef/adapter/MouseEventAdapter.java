package me.hannsi.lfjg.jcef.adapter;

import org.cef.browser.CefBrowserOsr;

import java.awt.*;
import java.awt.event.MouseEvent;

import static me.hannsi.lfjg.core.Core.GLFW.*;


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
        if (glfwButton == GLFW_MOUSE_BUTTON_LEFT) {
            return MouseEvent.BUTTON1;
        } else if (glfwButton == GLFW_MOUSE_BUTTON_MIDDLE) {
            return MouseEvent.BUTTON2;
        } else if (glfwButton == GLFW_MOUSE_BUTTON_RIGHT) {
            return MouseEvent.BUTTON3;
        } else {
            return MouseEvent.NOBUTTON;
        }
    }

    private int mapGLFWActionToAWTEventID(int action) {
        if (action == GLFW_PRESS) {
            return MouseEvent.MOUSE_PRESSED;
        } else if (action == GLFW_RELEASE) {
            return MouseEvent.MOUSE_RELEASED;
        } else {
            return MouseEvent.MOUSE_MOVED;
        }
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
