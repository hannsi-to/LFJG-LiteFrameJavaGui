package me.hannsi.lfjg.render.gui.system.item;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ItemRect extends Item {
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    @Setter
    private float scale;

    public ItemRect(float x, float y, float width, float height, float scale) {
        super();

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scale = scale;
    }

    public float getWidthWithScale() {
        return width * scale;
    }

    public float getHeightWithScale() {
        return height * scale;
    }
}
