package me.hannsi.lfjg.util.type.types;

import me.hannsi.lfjg.util.type.system.IEnumTypeBase;

public enum MonitorType implements IEnumTypeBase {
    Window(0, "Window"), FullScreen(1, "FullScreen"), Borderless(0, "Borderless");

    final int id;
    final String name;

    MonitorType(int id, String name) {
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
