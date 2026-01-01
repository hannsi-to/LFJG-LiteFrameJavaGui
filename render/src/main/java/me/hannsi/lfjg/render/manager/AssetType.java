package me.hannsi.lfjg.render.manager;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum AssetType implements IEnumTypeBase {
    TEXT(0, "Text"),
    TEXTURE(1, "Texture");

    final int id;
    final String name;

    AssetType(int id, String name) {
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
