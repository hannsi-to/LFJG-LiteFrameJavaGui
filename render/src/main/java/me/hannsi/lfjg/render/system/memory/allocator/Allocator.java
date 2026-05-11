package me.hannsi.lfjg.render.system.memory.allocator;

import me.hannsi.lfjg.render.system.memory.Allocation;
import me.hannsi.lfjg.render.system.memory.GPUHeap;

public abstract class Allocator {
    protected long size;
    protected int alignment;
    protected Allocation region;

    public Allocator(long size, int alignment) {
        this.size = size;
        this.alignment = alignment;
    }

    public void init(GPUHeap gpuHeap) {
        region = gpuHeap.alloc(size, alignment);
    }

    public abstract void alloc(long size, int alignment);

    public abstract void free(Allocation allocation);

    public abstract void update(long offset, long memorySize, int alignment);

    public abstract void reset();

    public abstract Allocator createLike();

    public long getOffset() {
        return region.getOffset();
    }

    public long getMemorySize() {
        return region.getMemorySize();
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
