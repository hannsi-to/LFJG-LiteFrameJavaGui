package me.hannsi.lfjg.util.type.types;

import me.hannsi.lfjg.util.type.system.IEnumTypeBase;

public enum VSyncType implements IEnumTypeBase {
    VSyncOff(0,"VSyncOff"),
    VSyncOn(1,"VSyncOn"),
    DoubleBufferVSync(2,"DoubleBufferVSync"),
    TripleBufferVSync(3,"TripleBufferVSync"),
    AdaptiveVSync(-1,"AdaptiveVSync");

    final int id;
    final String name;

    VSyncType(int id, String name) {
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
