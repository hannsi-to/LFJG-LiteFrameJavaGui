package me.hannsi.lfjg.utils.type;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum LogGenerateType implements IEnumTypeBase {
    CREATE_CACHE(0, "CreateCache"),
    CLEANUP(1, "Cleanup");

    final int id;
    final String name;

    LogGenerateType(int id, String name) {
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
