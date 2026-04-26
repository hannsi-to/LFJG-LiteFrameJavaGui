package me.hannsi.lfjg.render.system.memory;

public class StaticAllocatorSystem extends AllocatorSystem {
    public StaticAllocatorSystem(Allocator allocator) {
        super(allocator.createLike());
    }

    @Override
    public void init(GPUHeap heap) {
        allocators[0].init(heap.alloc(allocators[0].getNeedSizeBytes(), allocators[0].getAlignment()));
    }

    @Override
    public Allocation alloc(long size, int alignment) {
        return allocators[0].alloc(size, alignment);
    }

    @Override
    public void startFrame() {

    }

    @Override
    public void endFrame() {

    }

    @Override
    public long getAllocatedMemorySize() {
        return allocators[0].getNeedSizeBytes();
    }
}