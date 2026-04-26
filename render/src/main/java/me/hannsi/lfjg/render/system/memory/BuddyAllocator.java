package me.hannsi.lfjg.render.system.memory;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static me.hannsi.lfjg.core.utils.math.MathHelper.min;

public class BuddyAllocator extends Allocator {
    private final int minBlockSize;
    private final int maxLevel;
    private List<Long>[] freeLists;

    public BuddyAllocator(long needSizeBytes, int minBlockSize, int alignment) {
        super(needSizeBytes, alignment);

        if ((minBlockSize & (minBlockSize - 1)) != 0) {
            throw new IllegalArgumentException("minBlockSize must be power of 2");
        }

        this.minBlockSize = minBlockSize;
        this.maxLevel = log2(needSizeBytes) - log2(minBlockSize);
    }

    private static long nextPow2(long v) {
        long p = 1;
        while (p < v) {
            p <<= 1;
        }
        return p;
    }

    private static int log2(long v) {
        return 63 - Long.numberOfLeadingZeros(v);
    }

    @Override
    public Allocator createLike() {
        return new BuddyAllocator(this.getNeedSizeBytes(), this.minBlockSize, this.getAlignment());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void init(Allocation region) {
        super.init(region);

        freeLists = new ArrayList[maxLevel + 1];
        for (int i = 0; i <= maxLevel; i++) {
            freeLists[i] = new ArrayList<>();
        }

        freeLists[maxLevel].add(0L);
    }

    @Override
    public Allocation alloc(long size, int alignment) {
        long required = max(size, alignment);
        required = nextPow2(required);

        int level = getLevel(required);
        int currentLevel = level;

        while ((currentLevel <= maxLevel && freeLists[currentLevel].isEmpty())) {
            currentLevel++;
        }

        if (currentLevel > maxLevel) {
            throw new OutOfMemoryError(getClass().getSimpleName() + ": out of memory");
        }

        long offset = freeLists[currentLevel].removeLast();
        while ((currentLevel > level)) {
            currentLevel--;

            long half = blockSize(currentLevel);
            long buddy = offset + half;

            freeLists[currentLevel].add(buddy);
        }

        return new Allocation(region.address() + offset, region.offset() + offset, required, level);
    }

    @Override
    public void free(Allocation allocation) {
        long offset = allocation.offset() - region.offset();
        int level = allocation.level();

        while (true) {
            long buddy = offset ^ blockSize(level);

            List<Long> list = freeLists[level];
            int index = list.indexOf(buddy);

            if (index == -1) {
                list.add(offset);
                return;
            }

            list.remove(index);

            offset = min(offset, buddy);
            level++;

            if (level > maxLevel) {
                freeLists[maxLevel].add(offset);
                return;
            }
        }
    }

    @Override
    public void reset() {
        for (int i = 0; i <= maxLevel; i++) {
            freeLists[i].clear();
        }
        freeLists[maxLevel].add(0L);
    }

    private int getLevel(long size) {
        long s = max(size, minBlockSize);
        s = nextPow2(s);
        return log2(s) - log2(minBlockSize);
    }

    private long blockSize(int level) {
        return (long) minBlockSize << level;
    }
}
