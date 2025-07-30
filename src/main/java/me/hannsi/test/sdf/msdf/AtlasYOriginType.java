package me.hannsi.test.sdf.msdf;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum AtlasYOriginType implements IEnumTypeBase {
    BOTTOM("bottom", 0),
    top("top", 1);

    final String name;
    final int id;

    AtlasYOriginType(String name, int id) {
        this.name = name;
        this.id = id;
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
