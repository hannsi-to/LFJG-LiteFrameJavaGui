package me.hannsi.lfjg.render.renderers;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum JointType implements IEnumTypeBase {
    NONE(0, "None"),
    MITER(1, "Miter"),
    BEVEL(2, "Bevel"),
    ROUND(3, "Round");

    final int id;
    final String name;

    JointType(int id, String name) {
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
