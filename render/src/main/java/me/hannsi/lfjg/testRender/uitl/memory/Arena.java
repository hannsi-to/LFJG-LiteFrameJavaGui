package me.hannsi.lfjg.testRender.uitl.memory;

import me.hannsi.lfjg.render.system.memory.MemoryTask;

public abstract class Arena {
    private final MemoryTask memoryTask;

    public Arena(MemoryTask memoryTask) {
        this.memoryTask = memoryTask;
    }

    abstract MemoryFrame currentMemoryFrame();

    abstract MemoryArenaSystem currentMemoryArenaSystem();

    abstract long alloc(long size, long alignment);
}