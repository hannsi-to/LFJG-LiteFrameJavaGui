package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum InfoTypeButton implements IEnumTypeBase {
    NORMAL(0, "Normal");

    final int id;
    final String name;

    InfoTypeButton(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }
}
