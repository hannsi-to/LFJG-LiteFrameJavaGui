package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

/**
 * Enumeration representing different types of sound loaders.
 */
public enum SoundLoaderType implements IEnumTypeBase {
    STBVorbis("STBVorbis", 0),
    @Deprecated JavaCV("JavaCV", 1);

    final String name;
    final int id;

    /**
     * Constructs a new SoundLoaderType enumeration value.
     *
     * @param name the name of the sound loader type
     * @param id the unique identifier of the sound loader type
     */
    SoundLoaderType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Gets the unique identifier of the sound loader type.
     *
     * @return the unique identifier of the sound loader type
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the sound loader type.
     *
     * @return the name of the sound loader type
     */
    @Override
    public String getName() {
        return name;
    }
}