package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum NumberButtonType implements IEnumTypeBase {
    SLIDER(0, "Slider"), STEPPY(1, "Steppy");

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
