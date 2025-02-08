package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

/**
 * Enumeration representing different types of projection modes.
 */
public enum ProjectionType implements IEnumTypeBase {
    OrthographicProjection("OrthographicProjection", 0),
    PerspectiveProjection("PerspectiveProjection", 1);

    final int id;
    final String name;

    /**
     * Constructs a new ProjectionType enumeration value.
     *
     * @param name the name of the projection type
     * @param id the unique identifier of the projection type
     */
    ProjectionType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Gets the unique identifier of the projection type.
     *
     * @return the unique identifier of the projection type
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the projection type.
     *
     * @return the name of the projection type
     */
    @Override
    public String getName() {
        return name;
    }
}