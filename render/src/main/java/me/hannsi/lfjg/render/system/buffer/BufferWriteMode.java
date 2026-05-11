package me.hannsi.lfjg.render.system.buffer;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum BufferWriteMode implements IEnumTypeBase {
    BUFFER_SUB(0, "BufferSub"),
    MAP(1, "Map"),
    MAP_RANGE(2, "MapRange"),
    PERSISTENT(3, "Persistent"),
    PERSISTENT_COHERENT(4, "PersistentCoherent");

    final int id;
    final String name;

    BufferWriteMode(int id, String name) {
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

