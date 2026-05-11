package me.hannsi.lfjg.render.system.memory;

import me.hannsi.lfjg.render.system.memory.allocator.Allocator;

public abstract class AllocatorSystem {
    protected Allocator[] allocators;

    public AllocatorSystem(Allocator... allocators) {
        this.allocators = allocators;
    }

    public abstract void alloc(long size, int alignment);

    public abstract void startFrame();

    public abstract void endFrame();

    public abstract void update(long offset, long memorySize, int alignment);

    public abstract void free(Allocation allocation);

    public abstract void reset();

    public long getMemorySize() {
        long memorySize = 0L;
        for (Allocator allocator : allocators) {
            memorySize += allocator.getMemorySize();
        }

        return memorySize;
    }

    public Allocator[] getAllocators() {
        return allocators;
    }
}
