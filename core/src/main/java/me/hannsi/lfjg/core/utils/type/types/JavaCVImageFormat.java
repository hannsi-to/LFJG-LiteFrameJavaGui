package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

@Deprecated
public enum JavaCVImageFormat implements IEnumTypeBase {
    PNG(0, "png"), JPG(1, "jpg"), JPEG(2, "jpeg"), BMP(3, "bmp"), TGA(4, "tga"), PBM(5, "pbm"), PGM(6, "pgm"), PPM(7, "ppm"), GIF(8, "gif");

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
