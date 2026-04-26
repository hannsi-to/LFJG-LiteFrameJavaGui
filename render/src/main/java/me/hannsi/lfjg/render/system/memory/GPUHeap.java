package me.hannsi.lfjg.render.system.memory;

import static me.hannsi.lfjg.core.Core.NULL_PTR;

public class GPUHeap {
    private final long address;
    private final long memorySizeBytes;
    private long pointer;

    public GPUHeap(long address, long memorySizeBytes) {
        this.address = address;
        this.memorySizeBytes = memorySizeBytes;
    }

    public static long align(long value, int alignment) {
        if ((alignment & (alignment - 1)) != 0) {
            throw new IllegalArgumentException("alignment must be power of 2");
        }

        return (value + (alignment - 1)) & -alignment;
    }

    public Allocation alloc(long size, int alignment) {
        if (size <= 0) {
            throw new IllegalArgumentException("size must be > 0");
        }

        size = align(size, alignment);

        if (pointer + size > memorySizeBytes) {
            throw new OutOfMemoryError("Arena overflow");
        }

        long oldPointer = pointer;
        pointer += size;

        return new Allocation(address, oldPointer, size, -1);
    }

    public long remaining() {
        return memorySizeBytes - pointer;
    }

    public void reset() {
        pointer = NULL_PTR;
    }

    public long getAddress(long offset) {
        if (offset < 0 || offset >= memorySizeBytes) {
            throw new IllegalArgumentException("Invalid offset");
        }

        return address + offset;
    }
}