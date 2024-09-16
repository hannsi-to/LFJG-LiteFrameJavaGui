package me.hannsi.lfjg.util.type.types;

import me.hannsi.lfjg.util.type.system.IEnumTypeBase;

public enum AntiAliasingType implements IEnumTypeBase {
    OFF(0, "Off"), MSAA(1, "MultiSampleAntiAliasing");

    final int id;
    final String name;

    AntiAliasingType(int id, String name) {
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