package me.hannsi.test.sdf.msdf;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum AtlasUnifomOrignType implements IEnumTypeBase {
    OFF("off", 0),
    ON("on", 1),
    HORIZONTAL("horizontal", 2),
    VERTICAL("vertical", 3);

    final int id;
    final String name;

    AtlasUnifomOrignType(String name, int id) {
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
