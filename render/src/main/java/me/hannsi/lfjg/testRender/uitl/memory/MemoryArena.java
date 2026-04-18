package me.hannsi.lfjg.testRender.uitl.memory;

import static me.hannsi.lfjg.core.Core.NULL_PTR;

public class MemoryArena {
    public static final long INITIAL_BUFFER_ADDITIONAL = 2048;

    private final long memorySizeBytes;
    private final long address;
    private long head;

    private MemoryArena(MemoryTask memoryTask, long memorySizeBytes, int bufferId) {
        this.memorySizeBytes = align(memorySizeBytes + INITIAL_BUFFER_ADDITIONAL, 16);
        this.address = memoryTask.allocateMemory(this.memorySizeBytes, bufferId);
        this.head = NULL_PTR;
    }

    public static MemoryArena allocateMemory(MemoryTask memoryTasK, long memorySizeBytes, int bufferId) {
        return new MemoryArena(memoryTasK, memorySizeBytes, bufferId);
    }

    private static long align(long value, long alignment) {
        return (value + (alignment - 1)) & -alignment;
    }

    public long alloc(long size, long alignment) {
        if (size <= 0) {
            throw new IllegalArgumentException("size must be > 0");
        }

        if ((alignment & (alignment - 1)) != 0) {
            throw new IllegalArgumentException("alignment must be power of 2");
        }

        long aligned = align(head, alignment);

        if (aligned + size > memorySizeBytes) {
            throw new OutOfMemoryError("Arena overflow");
        }

        head = aligned + size;

        return address + aligned;
    }

    public long remaining() {
        return memorySizeBytes - head;
    }

    public long getMemorySizeBytes() {
        return memorySizeBytes;
    }

    public void reset() {
        head = NULL_PTR;
    }

    public long getAddress(long offset) {
        if (offset < 0 || offset >= memorySizeBytes) {
            throw new IllegalArgumentException("Invalid offset");
        }

        return address + offset;
    }
}