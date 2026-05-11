package me.hannsi.lfjg.render.system.buffer;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum BufferAllocationMode implements IEnumTypeBase {
    BUFFER_DATA(0, "BufferData"),
    BUFFER_STORAGE(1, "BufferStorage");

    final int id;
    final String name;

    BufferAllocationMode(int id, String name) {
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
