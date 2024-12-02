package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum ProjectionType implements IEnumTypeBase {
    OrthographicProjection("OrthographicProjection", 0), PerspectiveProjection("PerspectiveProjection", 1);

    final int id;
    final String name;

    ProjectionType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
