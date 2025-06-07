package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum UpdateBufferType implements IEnumTypeBase {
    BUFFER_DATA(0, "BufferData"),
    BUFFER_SUB_DATA(1, "BufferSubData"),
    MAP_BUFFER_RANGE(2, "MapBufferRange");

    final int id;
    final String name;

    UpdateBufferType(int id, String name) {
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
