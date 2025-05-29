package me.hannsi.lfjg.render.openGL.gui.system.item.items;

import me.hannsi.lfjg.frame.event.events.user.MouseButtonEvent;
import me.hannsi.lfjg.render.openGL.gui.system.item.ItemRect;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class Button extends ItemRect {
    public String title;
    public boolean isHover;

    public Button(float x, float y, float width, float height, float scale) {
        super(x, y, width, height, scale);

    }

    public Button title(String title) {
        this.title = title;

        return this;
    }

    @Override
    public void mouseButtonEvent(MouseButtonEvent event) {
        if (isHover && event.getAction() == GLFW_PRESS) {
            doAction(event);
        }

        super.mouseButtonEvent(event);
    }

    public void doAction(MouseButtonEvent event) {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHover() {
        return isHover;
    }

    public void setHover(boolean hover) {
        isHover = hover;
    }
}
