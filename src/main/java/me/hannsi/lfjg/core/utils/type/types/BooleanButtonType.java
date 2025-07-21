package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum BooleanButtonType implements IEnumTypeBase {
    SWITCH(0, "Switch"), CHECK_BOX(1, "CheckBox");

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
