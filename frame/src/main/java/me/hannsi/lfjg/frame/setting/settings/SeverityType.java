package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

/**
 * Enumeration representing different types of severity levels for OpenGL debug messages.
 */
public enum SeverityType implements IEnumTypeBase {
    NOTIFICATION("Low", 0),
    LOW("Low", 1),
    MEDIUM("Medium", 2),
    HIGH("High", 3);

    final String name;
    final int id;

    /**
     * Constructs a new SeverityType enumeration value.
     *
     * @param name the name of the severity level
     * @param id   the unique identifier of the severity level
     */
    SeverityType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Gets the name of the severity level.
     *
     * @return the name of the severity level
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the unique identifier of the severity level.
     *
     * @return the unique identifier of the severity level
     */
    @Override
    public int getId() {
        return id;
    }
}