package me.hannsi.lfjg.render.system.memory;

import me.hannsi.lfjg.render.system.GLFenceTracker;
import me.hannsi.lfjg.render.system.memory.allocator.Allocator;

public class StreamingAllocatorSystem extends AllocatorSystem {
    private final GLFenceTracker glFenceTracker;
    private int writingIndex = -1;

    public StreamingAllocatorSystem(Allocator allocator, int ringCount) {
        super(new Allocator[ringCount]);
        for (int i = 0; i < allocators.length; i++) {
            allocators[i] = allocator.createLike();
        }

        this.glFenceTracker = new GLFenceTracker(ringCount);
    }

    @Override
    public void alloc(long size, int alignment) {
        allocators[writingIndex].alloc(size, alignment);
    }

    @Override
    public void startFrame() {
        writingIndex = glFenceTracker.getCompletedSlot();

        allocators[writingIndex].reset();
    }

    @Override
    public void endFrame() {
        glFenceTracker.endFrame();
    }

    @Override
    public void update(long offset, long memorySize, int alignment) {
        throw new AllocatorSystemException(this.getClass().getSimpleName() + " does not allow partial update operations on values.");
    }

    @Override
    public void free(Allocation allocation) {
        throw new AllocatorSystemException(this.getClass().getSimpleName() + " does not allow the release of reserved portions.");
    }

    @Override
    public void reset() {
        for (Allocator allocator : allocators) {
            allocator.reset();
        }
    }
}
