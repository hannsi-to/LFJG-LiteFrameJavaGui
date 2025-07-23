package me.hannsi.lfjg.core.utils.type.types;

import lombok.Getter;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

/**
 * Enumeration representing different types of projection modes.
 */
public enum ProjectionType implements IEnumTypeBase {
    ORTHOGRAPHIC_PROJECTION("OrthographicProjection", 0, 2),
    PERSPECTIVE_PROJECTION("PerspectiveProjection", 1, 3);

    final int id;
    final String name;
    @Getter
    final int stride;

    /**
     * Constructs a new ProjectionType enumeration value.
     *
     * @param name the name of the projection type
     * @param id   the unique identifier of the projection type
     */
    ProjectionType(String name, int id, int stride) {
        this.name = name;
        this.id = id;
        this.stride = stride;
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