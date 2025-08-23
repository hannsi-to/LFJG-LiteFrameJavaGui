package me.hannsi.lfjg.jcef.adapter;

import org.cef.browser.CefBrowserOsr;

import java.awt.*;
import java.awt.event.MouseWheelEvent;

public class MouseWheelEventAdapter {
    private final Component dummyComponent;

    public MouseWheelEventAdapter(CefBrowserOsr cefBrowserOsr) {
        this.dummyComponent = cefBrowserOsr.getUIComponent();
    }

    public MouseWheelEvent convertGLFWScroll(double xoffset, double yoffset, double xpos, double ypos) {
        int scrollAmount = 1;

        return new MouseWheelEvent(
                dummyComponent,
                MouseWheelEvent.MOUSE_WHEEL,
                System.currentTimeMillis(),
                0,
                (int) xpos,
                (int) ypos,
                0,
                false,
                MouseWheelEvent.WHEEL_UNIT_SCROLL,
                scrollAmount,
                (int) (yoffset * 100)
        );
    }
}
