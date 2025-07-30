package me.hannsi.test.sdf.msdf;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum AtlasSizeType implements IEnumTypeBase {
    NONE("none", -1),
    POTS("pots", 0),
    POTR("potr", 1),
    SQUARE("square", 2),
    SQUARE2("square2", 3),
    SQUARE4("square4", 4);

    final int id;
    final String name;

    AtlasSizeType(String name, int id) {
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
