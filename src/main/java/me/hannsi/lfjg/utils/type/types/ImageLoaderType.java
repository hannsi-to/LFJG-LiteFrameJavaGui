package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

/**
 * Enumeration representing different types of texture loaders.
 */
public enum ImageLoaderType implements IEnumTypeBase {
    STBImage(0, "STBImage"),
    @Deprecated
    JavaCV(1, "JavaCV");

    final int id;
    final String name;

    /**
     * Constructs a new TextureLoaderType enumeration value.
     *
     * @param id the unique identifier of the texture loader type
     * @param name the name of the texture loader type
     */
    ImageLoaderType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the unique identifier of the texture loader type.
     *
     * @return the unique identifier of the texture loader type
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the texture loader type.
     *
     * @return the name of the texture loader type
     */
    @Override
    public String getName() {
        return name;
    }
}