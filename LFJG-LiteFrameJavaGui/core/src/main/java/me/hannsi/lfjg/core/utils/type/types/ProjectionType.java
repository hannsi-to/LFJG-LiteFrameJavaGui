package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum ProjectionType implements IEnumTypeBase {
    ORTHOGRAPHIC_PROJECTION("OrthographicProjection", 0, 2),
    PERSPECTIVE_PROJECTION("PerspectiveProjection", 1, 3);

    final int id;
    final String name;
    final int stride;

    ProjectionType(String name, int id, int stride) {
        this.name = name;
        this.id = id;
        this.stride = stride;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getStride() {
        return stride;
    }
}