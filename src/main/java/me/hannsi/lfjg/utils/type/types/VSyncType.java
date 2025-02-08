package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

/**
 * Enumeration representing different types of VSync configurations.
 */
public enum VSyncType implements IEnumTypeBase {
    VSyncOff(0, "VSyncOff"),
    VSyncOn(1, "VSyncOn"),
    DoubleBufferVSync(2, "DoubleBufferVSync"),
    TripleBufferVSync(3, "TripleBufferVSync"),
    AdaptiveVSync(-1, "AdaptiveVSync");

    final int id;
    final String name;

    /**
     * Constructs a new VSyncType enumeration value.
     *
     * @param id the unique identifier of the VSync type
     * @param name the name of the VSync type
     */
    VSyncType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the unique identifier of the VSync type.
     *
     * @return the unique identifier of the VSync type
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the VSync type.
     *
     * @return the name of the VSync type
     */
    @Override
    public String getName() {
        return name;
    }
}