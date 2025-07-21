package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

/**
 * Enumeration representing different types of anti-aliasing.
 */
public enum AntiAliasingType implements IEnumTypeBase {
    OFF(0, "Off"), MSAA(1, "MultiSampleAntiAliasing");

    final int id;
    final String name;

    /**
     * Constructs a new AntiAliasingType enumeration value.
     *
     * @param id   the unique identifier of the anti-aliasing type
     * @param name the name of the anti-aliasing type
     */
    AntiAliasingType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the unique identifier of the anti-aliasing type.
     *
     * @return the unique identifier of the anti-aliasing type
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the anti-aliasing type.
     *
     * @return the name of the anti-aliasing type
     */
    @Override
    public String getName() {
        return name;
    }
}