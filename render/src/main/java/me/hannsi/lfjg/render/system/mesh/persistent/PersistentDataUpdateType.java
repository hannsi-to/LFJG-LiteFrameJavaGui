package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum PersistentDataUpdateType implements IEnumTypeBase {
    DATA_FULL_UPDATE(0, "DataFullUpdate"),
    DATA_DIRTY(1, "DataDirty"),
    DATA_ALLOC(2, "AllocData");

    final int id;
    final String name;

    PersistentDataUpdateType(int id, String name) {
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
