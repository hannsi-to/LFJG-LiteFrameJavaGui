package me.hannsi.lfjg.render.system.memory.allocator;

import me.hannsi.lfjg.render.system.memory.Allocation;
import me.hannsi.lfjg.render.system.memory.GPUHeap;

import static me.hannsi.lfjg.render.LFJGRenderContext.currentAllocation;

public class LinearAllocator extends Allocator {
    private long pointer;

    public LinearAllocator(long size, int alignment) {
        super(size, alignment);

        this.pointer = 0L;
    }

    @Override
    public void alloc(long size, int alignment) {
        long startOffset = GPUHeap.align(pointer, alignment);
        long alignedSize = GPUHeap.align(size, alignment);

        if (startOffset + alignedSize > this.size) {
            throw new AllocatorException.AllocatorOutOfMemoryException("LinearAllocator out of memory! Requested: " + (startOffset + alignedSize) + " but max is " + this.size);
        }

        pointer = startOffset + alignedSize;
        currentAllocation.offset = region.getOffset() + startOffset;
        currentAllocation.memorySize = alignedSize;
    }

    @Override
    public void update(long offset, long memorySize, int alignment) {
        long alignedOffset = GPUHeap.align(offset, alignment);
        long alignedSize = GPUHeap.align(memorySize, alignment);

        if (alignedOffset + alignedSize > this.pointer) {
            throw new AllocatorException("Update range out of bounds!");
        }

        currentAllocation.offset = region.getOffset() + alignedOffset;
        currentAllocation.memorySize = alignedSize;
    }

    @Override
    public void free(Allocation allocation) {
        throw new AllocatorException(this.getClass().getSimpleName() + " does not allow the release of reserved portions.");
    }

    @Override
    public Allocator createLike() {
        return new LinearAllocator(size, alignment);
    }

    @Override
    public void reset() {
        pointer = 0L;
    }
}
