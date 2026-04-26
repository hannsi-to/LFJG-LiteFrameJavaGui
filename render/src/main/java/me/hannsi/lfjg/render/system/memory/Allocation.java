package me.hannsi.lfjg.render.system.memory;

public final class Allocation {
    private final long address;
    private final long offset;
    private final long sizeBytes;
    private final int level;

    public Allocation(long address, long offset, long sizeBytes, int level) {
        this.address = address;
        this.offset = offset;
        this.sizeBytes = sizeBytes;
        this.level = level;
    }

    public Allocation(Allocation other) {
        this.address = other.address;
        this.offset = other.offset;
        this.sizeBytes = other.sizeBytes;
        this.level = other.level;
    }

    @Override
    public String toString() {
        return "Allocation[address=" + address + ", offset=" + offset + ", sizeBytes=" + sizeBytes + ", level=" + level + "]";
    }

    public long address() {
        return address;
    }

    public long offset() {
        return offset;
    }

    public long sizeBytes() {
        return sizeBytes;
    }

    public int level() {
        return level;
    }
}
