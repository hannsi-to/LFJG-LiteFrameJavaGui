package me.hannsi.lfjg.render.system.batching;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum UILayer implements IEnumTypeBase {
    BACKGROUND(0, 0, "Background"),
    HUD(1, 100, "HUD"),
    WINDOW(2, 200, "Window"),
    POPUP(3, 300, "Popup"),
    MODAL(4, 400, "Modal"),
    TOOLTIP(5, 500, "Tooltip"),
    DEBUG(6, 600, "Debug");

    final int id;
    final int order;
    final String name;

    UILayer(int id, int order, String name) {
        this.id = id;
        this.order = order;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public String getName() {
        return name;
    }
}
