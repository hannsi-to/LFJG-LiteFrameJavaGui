package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum InfoType implements IEnumTypeBase {
    INFO(0, "Info"), WARNING(1, "Warning"), ERROR(2, "Error");

    final int id;
    final String name;

    InfoType(int id, String name) {
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
