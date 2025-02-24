package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

@Deprecated
public enum JavaCVImageFormat implements IEnumTypeBase {
    png(0, "png"), jpg(1, "jpg"), jpeg(2, "jpeg"), bmp(3, "bmp"), tga(4, "tga"), pbm(5, "pbm"), pgm(6, "pgm"), ppm(7, "ppm"), gif(8, "gif");

    final int id;
    final String name;

    JavaCVImageFormat(int id, String name) {
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
