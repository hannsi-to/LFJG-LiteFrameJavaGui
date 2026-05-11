package me.hannsi.lfjg.render.system.buffer;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum BufferProperty implements IEnumTypeBase {
    BIND_BUFFER(0, "BindBuffer"),
    DIRECT_STATE_ACCESS(1, "DirectStateAccess"),
    NV_UNIFIED_BINDLESS(2, "NVUnifiedBindless");

    final int id;
    final String name;

    BufferProperty(int id, String name) {
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
