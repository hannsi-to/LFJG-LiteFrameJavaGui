package me.hannsi.lfjg.render.renderers;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum PointType implements IEnumTypeBase {
    SQUARE(0, "Square"),
    ROUND(1, "Round");

    final int id;
    final String name;

    PointType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
