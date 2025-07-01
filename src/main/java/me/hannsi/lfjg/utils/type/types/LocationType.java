package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum LocationType implements IEnumTypeBase {
    RESOURCE(0, "Resource"),
    FILE(1, "File"),
    URL(2, "URL");

    final int id;
    final String name;

    LocationType(int id, String name) {
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
