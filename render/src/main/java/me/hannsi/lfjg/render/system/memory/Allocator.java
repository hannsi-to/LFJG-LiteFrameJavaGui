package me.hannsi.lfjg.render.system.memory;

public abstract class Allocator {
    protected Allocation region;
    private final long needSizeBytes;
    private final int alignment;

    public Allocator(long needSizeBytes, int alignment) {
        this.needSizeBytes = needSizeBytes;
        this.alignment = alignment;
    }

    public abstract Allocator createLike();

    public abstract Allocation alloc(long size, int alignment);

    public abstract void free(Allocation allocation);

    public abstract void reset();

    public Allocation getRegion() {
        return region;
    }

    public void init(Allocation region) {
        this.region = region;
    }

    public long getNeedSizeBytes() {
        return needSizeBytes;
    }

    public int getAlignment() {
        return alignment;
    }
}
