package me.hannsi.lfjg.render.openGL.gui;

import me.hannsi.lfjg.render.openGL.gui.item.Item;
import me.hannsi.lfjg.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.utils.toolkit.MouseInfo;

import java.util.ArrayList;
import java.util.List;

public class Gui {
    private List<Item> items;

    public Gui() {
    }

    public Gui builder() {
        items = new ArrayList<>();
        return this;
    }

    public Gui addItem(Item item) {
        items.add(item);
        return this;
    }

    public void renderItems(MouseInfo mouseInfo, KeyboardInfo keyboardInfo) {
        for (Item item : items) {
            item.render(mouseInfo.getCurrentPos());
        }
    }
}
