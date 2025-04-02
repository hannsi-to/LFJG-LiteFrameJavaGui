package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum NumberButtonType implements IEnumTypeBase {
    Slider(0, "Slider"), Steppy(1, "Steppy");

    final int id;
    final String name;

    NumberButtonType(int id, String name) {
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
