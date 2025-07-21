package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

/**
 * Enumeration representing different types of monitor modes.
 */
public enum MonitorType implements IEnumTypeBase {
    WINDOW(0, "Window"),
    FULL_SCREEN(1, "FullScreen"),
    BORDERLESS(0, "Borderless");

    final int id;
    final String name;

    /**
     * Constructs a new MonitorType enumeration value.
     *
     * @param id the unique identifier of the monitor type
     * @param name the name of the monitor type
     */
    MonitorType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the unique identifier of the monitor type.
     *
     * @return the unique identifier of the monitor type
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the monitor type.
     *
     * @return the name of the monitor type
     */
    @Override
    public String getName() {
        return name;
    }
}