package me.hannsi.lfjg.render.gui.system.item.items;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.frame.event.events.user.MouseButtonEvent;
import me.hannsi.lfjg.render.gui.system.item.ItemRect;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class Button extends ItemRect {
    @Getter
    @Setter
    private String title;
    private boolean isHover;

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

    public boolean isHover() {
        return isHover;
    }

    public void setHover(boolean hover) {
        isHover = hover;
    }
}
