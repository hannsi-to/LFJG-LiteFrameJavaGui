package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum KeyBindButtonType implements IEnumTypeBase {
    NORMAL(0, "Normal");

    final int id;
    final String name;

    KeyBindButtonType(int id, String name) {
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
