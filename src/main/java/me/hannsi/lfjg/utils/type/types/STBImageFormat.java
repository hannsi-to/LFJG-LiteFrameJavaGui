package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum STBImageFormat implements IEnumTypeBase {
    png(0, "png"), jpg(1, "jpg"), bmp(2, "bmp"), tga(3, "tga");

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
