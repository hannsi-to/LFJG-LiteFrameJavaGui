package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum TextureLoaderType implements IEnumTypeBase {
    STBImage(0, "STBImage"), JavaCV(1, "JavaCV");

    final int id;
    final String name;

    TextureLoaderType(int id, String name) {
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
