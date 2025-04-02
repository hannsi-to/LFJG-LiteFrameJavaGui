package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum BooleanButtonType implements IEnumTypeBase {
    Switch(0, "Switch"), CheckBox(1, "CheckBox");

    final int id;
    final String name;

    BooleanButtonType(int id, String name) {
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
