package me.hannsi.lfjg.testRender.uitl.memory;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum MemoryFrameState implements IEnumTypeBase {
    IDLE(0, "Idle"),
    CPU_WRITING(1, "CPU Writing"),
    GPU_IN_FLIGHT(2, "GPU In Flight");

    final int id;
    final String name;

    MemoryFrameState(int id, String name) {
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
