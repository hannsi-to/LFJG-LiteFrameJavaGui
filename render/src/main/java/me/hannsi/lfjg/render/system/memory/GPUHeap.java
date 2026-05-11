package me.hannsi.lfjg.render.system.memory;

public class GPUHeap {
    private final long memorySize;
    private long pointer;

    public GPUHeap(int alignment) {
        this.memorySize = align(0L, alignment);
        this.pointer = 0L;
    }

    public static long align(long value, int alignment) {
        if ((alignment & (alignment - 1)) != 0) {
            throw new IllegalArgumentException("alignment must be power of 2");
        }

        return (value + (alignment - 1)) & -alignment;
    }

    public Allocation alloc(long size, int alignment) {
        if (size <= 0) {
            throw new IllegalArgumentException("memorySize must be > 0");
        }

        size = align(size, alignment);
        if (pointer + size > memorySize) {
            throw new OutOfMemoryError("Arena overflow");
        }

        long oldPointer = pointer;
        pointer += size;
        return new Allocation(oldPointer, size);
    }

    public void reset() {
        pointer = 0;
    }

    public long getMemorySize() {
        return memorySize;
    }
}
