package me.hannsi.lfjg.render.system.memory;

import me.hannsi.lfjg.render.system.GLFenceTracker;

public class PerFrameAllocatorSystem extends AllocatorSystem {
    private final GLFenceTracker glFenceTracker;
    private final int ringCount;
    private int writingIndex = -1;

    public PerFrameAllocatorSystem(Allocator allocator, int ringCount) {
        super(new Allocator[ringCount]);
        this.glFenceTracker = new GLFenceTracker(ringCount);
        for (int i = 0; i < allocators.length; i++) {
            allocators[i] = allocator.createLike();
        }
        this.ringCount = ringCount;
    }

    @Override
    public void init(GPUHeap heap) {
        writingIndex = nextWritingIndex();
        for (Allocator allocator : allocators) {
            allocator.init(heap.alloc(allocator.getNeedSizeBytes(), allocator.getAlignment()));
        }
    }

    @Override
    public Allocation alloc(long size, int alignment) {
        return allocators[writingIndex].alloc(size, alignment);
    }

    @Override
    public void startFrame() {
        int safeSlot = glFenceTracker.getCompletedSlot();
        allocators[safeSlot].reset();

        writingIndex = nextWritingIndex();
    }

    @Override
    public void endFrame() {
        glFenceTracker.endFrame();
    }

    private int nextWritingIndex() {
        return (glFenceTracker.getCurrentFrame()) % ringCount;
    }

    @Override
    public long getAllocatedMemorySize() {
        long allocatedMemorySize = 0L;
        for (Allocator allocator : allocators) {
            allocatedMemorySize += allocator.getNeedSizeBytes();
        }
        return allocatedMemorySize;
    }
}