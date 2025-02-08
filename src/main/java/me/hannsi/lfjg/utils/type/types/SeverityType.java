package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;
import org.lwjgl.opengl.GL43;

/**
 * Enumeration representing different types of severity levels for OpenGL debug messages.
 */
public enum SeverityType implements IEnumTypeBase {
    Notification("Low", GL43.GL_DEBUG_SEVERITY_NOTIFICATION),
    Low("Low", GL43.GL_DEBUG_SEVERITY_LOW),
    Medium("Medium", GL43.GL_DEBUG_SEVERITY_MEDIUM),
    High("High", GL43.GL_DEBUG_SEVERITY_HIGH);

    final String name;
    final int id;

    /**
     * Constructs a new SeverityType enumeration value.
     *
     * @param name the name of the severity level
     * @param id the unique identifier of the severity level
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