package me.hannsi.lfjg.render.system.buffer;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum BufferUpdateType implements IEnumTypeBase {
    STATIC(0, "Static"),
    DYNAMIC(1, "Dynamic"),
    STREAM(2, "Stream");

    final int id;
    final String name;

    BufferUpdateType(int id, String name) {
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
