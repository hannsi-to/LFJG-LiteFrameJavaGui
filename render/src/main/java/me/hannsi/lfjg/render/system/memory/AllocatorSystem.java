package me.hannsi.lfjg.render.system.memory;

public class AllocatorSystem {
    protected final Allocator[] allocators;

    public AllocatorSystem(Allocator... allocators) {
        this.allocators = allocators;
    }

    public void init(GPUHeap heap) {

    }

    public Allocation alloc(long size, int aligment) {
        return null;
    }

    public void startFrame() {

    }

    public void endFrame() {

    }

    public Allocator[] getAllocators() {
        return allocators;
    }

    public long getAllocatedMemorySize() {
        return 0L;
    }
}
