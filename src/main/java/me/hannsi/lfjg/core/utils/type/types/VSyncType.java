package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

/**
 * Enumeration representing different types of VSync configurations.
 */
public enum VSyncType implements IEnumTypeBase {
    V_SYNC_OFF(0, "VSyncOff"),
    V_SYNC_ON(1, "VSyncOn"),
    DOUBLE_BUFFER_V_SYNC(2, "DoubleBufferVSync"),
    TRIPLE_BUFFER_V_SYNC(3, "TripleBufferVSync"),
    ADAPTIVE_V_SYNC(-1, "AdaptiveVSync");

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