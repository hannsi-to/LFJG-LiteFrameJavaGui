package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum InfoType implements IEnumTypeBase {
    Info(0, "Info"), Warning(1, "Warning"), Error(2, "Error");

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
