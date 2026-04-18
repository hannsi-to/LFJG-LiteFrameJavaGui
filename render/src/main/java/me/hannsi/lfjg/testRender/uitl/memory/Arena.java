package me.hannsi.lfjg.testRender.uitl.memory;

public abstract class Arena {
    private final MemoryTask memoryTask;

    public Arena(MemoryTask memoryTask) {
        this.memoryTask = memoryTask;
    }

    abstract MemoryFrame currentMemoryFrame();

    abstract MemoryArenaSystem currentMemoryArenaSystem();

    abstract long alloc(long size, long alignment);
}