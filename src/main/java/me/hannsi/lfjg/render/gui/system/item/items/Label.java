package me.hannsi.lfjg.render.gui.system.item.items;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.gui.system.item.ItemPoint;

@Getter
@Setter
public class Label extends ItemPoint {
    private String labelText;

    public Label(float x, float y, float scale) {
        super(x, y, scale);
    }

    public Label labelText(String labelText) {
        this.labelText = labelText;
        return this;
    }

}
