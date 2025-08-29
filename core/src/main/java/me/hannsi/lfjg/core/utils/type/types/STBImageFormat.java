package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum STBImageFormat implements IEnumTypeBase {
    PNG(0, "png"),
    JPG(1, "jpg"),
    BMP(2, "bmp"),
    TGA(3, "tga");

    final int id;
    final String name;

    STBImageFormat(int id, String name) {
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
