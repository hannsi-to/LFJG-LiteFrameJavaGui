package me.hannsi.lfjg.render.system.memory;

public final class Allocation {
    public long offset;
    public long memorySize;

    public Allocation(long offset, long memorySize) {
        this.offset = offset;
        this.memorySize = memorySize;
    }

    public long getOffset() {
        return offset;
    }

    public long getMemorySize() {
        return memorySize;
    }

    @Override
    public String toString() {
        return "Allocation[" +
                "offset=" + offset + ", " +
                "memorySize=" + memorySize + ']';
    }

}