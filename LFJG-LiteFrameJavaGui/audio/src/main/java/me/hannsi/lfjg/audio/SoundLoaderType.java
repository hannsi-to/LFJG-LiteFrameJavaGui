package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum SoundLoaderType implements IEnumTypeBase {
    STB_VORBIS("STBVorbis", 0),
    @Deprecated JAVA_CV("JavaCV", 1);

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