package me.hannsi.lfjg.render.renderers;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum PaintType implements IEnumTypeBase {
    FILL(0, "Fill"),
    OUT_LINE(1, "OutLine");

    final int id;
    final String name;

    PaintType(int id, String name) {
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
