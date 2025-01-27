package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum SoundLoaderType implements IEnumTypeBase {
    STBVorbis("STBVorbis", 0), @Deprecated JavaCV("JavaCV", 1);

    final String name;
    final int id;

    SoundLoaderType(String name, int id) {
        this.name = name;
        this.id = id;
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
