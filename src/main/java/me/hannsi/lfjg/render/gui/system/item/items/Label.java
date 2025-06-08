package me.hannsi.lfjg.render.gui.system.item.items;

import me.hannsi.lfjg.render.gui.system.item.ItemPoint;

public class Label extends ItemPoint {
    private String labelText;

    public Label(float x, float y, float scale) {
        super(x, y, scale);
    }

    public Label labelText(String labelText) {
        this.labelText = labelText;
        return this;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }
}
