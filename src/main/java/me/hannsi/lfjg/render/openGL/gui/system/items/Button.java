package me.hannsi.lfjg.render.openGL.gui.system.items;

import me.hannsi.lfjg.render.openGL.gui.system.Item;

public class Button extends Item {
    public String title;

    public Button(float x, float y, float width, float height, float scale) {
        super(x, y, width, height, scale);
    }

    public Button title(String title) {
        this.title = title;

        return this;
    }
}
