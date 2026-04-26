package me.hannsi.lfjg.render.system.memory;

public class LinearAllocator extends Allocator {
    private long pointer = 0;

    public LinearAllocator(long needSizeBytes, int alignment) {
        super(needSizeBytes, alignment);
    }

    @Override
    public Allocator createLike() {
        return new LinearAllocator(this.getNeedSizeBytes(), this.getAlignment());
    }

    @Override
    public Allocation alloc(long size, int alignment) {
        long aligned = GPUHeap.align(pointer, alignment);

        long end = aligned + size;
        if (end > region.sizeBytes()) {
            throw new OutOfMemoryError();
        }

        pointer = end;

        return new Allocation(
                region.address() + aligned,
                region.offset() + aligned,
                size,
                -1
        );
    }

    @Override
    @Deprecated
    public void free(Allocation ignore) {

    }

    @Override
    public void reset() {
        pointer = 0;
    }
}