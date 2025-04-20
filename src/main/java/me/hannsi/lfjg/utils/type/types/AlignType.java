package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum AlignType implements IEnumTypeBase {
    ;

    final String name;
    final int id;

    AlignType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }
}
