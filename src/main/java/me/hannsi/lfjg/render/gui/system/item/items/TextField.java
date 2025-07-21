package me.hannsi.lfjg.render.gui.system.item.items;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.frame.event.events.user.CharEvent;
import me.hannsi.lfjg.frame.event.events.user.KeyEvent;
import me.hannsi.lfjg.frame.event.events.user.MouseButtonEvent;
import me.hannsi.lfjg.render.gui.system.item.ItemRect;
import me.hannsi.lfjg.core.utils.toolkit.StringUtil;

import static org.lwjgl.glfw.GLFW.*;

public class TextField extends ItemRect {
    @Getter
    @Setter
    private boolean typedFocus;
    @Getter
    @Setter
    private String typedField;
    private boolean isHover;

    public TextField(float x, float y, float width, float height, float scale) {
        super(x, y, width, height, scale);

        this.typedFocus = false;
    }

    public TextField typedField(String typedField) {
        this.typedField = typedField;
        return this;
    }

    @Override
    public void charEvent(CharEvent event) {
        if (!typedFocus) {
            return;
        }

        typedField += Character.toString(event.getCodepoint());

        super.charEvent(event);
    }

    @Override
    public void keyEvent(KeyEvent event) {
        if (!typedFocus) {
            return;
        }

        int key = event.getKey();
        if (event.getAction() == GLFW_PRESS) {
            switch (key) {
                case GLFW_KEY_ENTER -> typedFocus = false;
                case GLFW_KEY_BACKSPACE -> {
                    if (!typedField.isEmpty()) {
                        typedField = StringUtil.removeLastChar(typedField);
                    }
                }
            }
        }

        super.keyEvent(event);
    }

    @Override
    public void mouseButtonEvent(MouseButtonEvent event) {
        if (event.getAction() == GLFW_PRESS) {
            typedFocus = isHover;
        }
        super.mouseButtonEvent(event);
    }

    public boolean isHover() {
        return isHover;
    }

    public void setHover(boolean hover) {
        isHover = hover;
    }
}
