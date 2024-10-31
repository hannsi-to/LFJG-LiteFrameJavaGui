package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum TimeSourceType implements IEnumTypeBase {
    GLFWTime(0, "GLFWTime"), SystemTime(1, "SystemTime"), NanoTime(2, "NanoTime");

    final int id;
    final String name;

    TimeSourceType(int id, String name) {
        this.id = id;
        this.name = name;
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
