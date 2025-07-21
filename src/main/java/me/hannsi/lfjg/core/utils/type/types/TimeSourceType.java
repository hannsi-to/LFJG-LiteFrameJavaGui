package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

/**
 * Enumeration representing different types of time sources.
 */
public enum TimeSourceType implements IEnumTypeBase {
    GLFW_TIME(0, "GLFWTime"),
    SYSTEM_TIME(1, "SystemTime"),
    NANO_TIME(2, "NanoTime");

    final int id;
    final String name;

    /**
     * Constructs a new TimeSourceType enumeration value.
     *
     * @param id the unique identifier of the time source type
     * @param name the name of the time source type
     */
    TimeSourceType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the unique identifier of the time source type.
     *
     * @return the unique identifier of the time source type
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the time source type.
     *
     * @return the name of the time source type
     */
    @Override
    public String getName() {
        return name;
    }
}