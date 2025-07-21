package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum SelectorButtonType implements IEnumTypeBase {
    DROPDOWN(0,"Dropdown");

    final int id;
    final String name;

    SelectorButtonType(int id, String name) {
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
