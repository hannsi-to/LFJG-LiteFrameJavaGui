package me.hannsi.lfjg.core.utils.math;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum RowColumn implements IEnumTypeBase {
    NONE(0, "None"),
    ROW(1, "Row"),
    COLUMN(2, "Column");

    final int id;
    final String name;

    RowColumn(int id, String name) {
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
