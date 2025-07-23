package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum ImageLoaderType implements IEnumTypeBase {
    STB_IMAGE(0, "STBImage"),
    @Deprecated
    JAVA_CV(1, "JavaCV");

    final int id;
    final String name;

    ImageLoaderType(int id, String name) {
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