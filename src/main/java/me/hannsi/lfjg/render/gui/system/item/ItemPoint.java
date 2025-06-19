package me.hannsi.lfjg.render.gui.system.item;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ItemPoint extends Item {
    private final float x;
    private final float y;
    @Setter
    private float scale;

    public ItemPoint(float x, float y, float scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

}
