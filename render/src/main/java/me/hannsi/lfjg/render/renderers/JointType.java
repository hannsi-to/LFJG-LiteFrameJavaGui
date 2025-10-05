package me.hannsi.lfjg.render.renderers;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum JointType implements IEnumTypeBase {
    MITER(0, "Miter"),
    BEVEL(1, "Bevel"),
    ROUND(2, "Round");

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
