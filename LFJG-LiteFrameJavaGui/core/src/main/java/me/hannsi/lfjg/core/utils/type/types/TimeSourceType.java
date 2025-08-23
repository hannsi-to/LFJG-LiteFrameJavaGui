package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum TimeSourceType implements IEnumTypeBase {
    GLFW_TIME(0, "GLFWTime"),
    SYSTEM_TIME(1, "SystemTime"),
    NANO_TIME(2, "NanoTime");

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