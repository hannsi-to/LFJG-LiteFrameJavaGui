package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum MaterialType implements IEnumTypeBase {
    NO_MATERIAL(0, "NoMaterial"),
    COLOR(1, "Color"),
    TEXTURE(2, "Texture");

    final int id;
    final String name;

    MaterialType(int id, String name) {
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
