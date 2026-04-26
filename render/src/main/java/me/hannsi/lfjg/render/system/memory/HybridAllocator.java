package me.hannsi.lfjg.render.system.memory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HybridAllocator extends BuddyAllocator {
    private final int pageSize;
    private final int pageCount;
    private List<Page> pages;

    public HybridAllocator(int pageSize, int pageCount, int alignment) {
        super((long) pageSize * pageCount, pageSize, alignment);

        this.pageSize = pageSize;
        this.pageCount = pageCount;
    }

    @Override
    public void init(Allocation region) {
        super.init(region);
        this.pages = new ArrayList<>();
    }

    @Override
    public Allocator createLike() {
        return new HybridAllocator(this.pageSize, this.pageCount, this.getAlignment());
    }

    @Override
    public Allocation alloc(long size, int alignment) {
        for (Page page : pages) {
            try {
                return page.allocator.alloc(size, alignment);
            } catch (OutOfMemoryError ignore) {
            }
        }

        Allocation pageAlloc = super.alloc(pageSize, alignment);

        FreeListAllocator allocator = new FreeListAllocator(pageAlloc.sizeBytes(), alignment);
        allocator.init(pageAlloc);

        Page page = new Page(pageAlloc, allocator);
        pages.add(page);

        return allocator.alloc(size, alignment);
    }

    @Override
    public void free(Allocation allocation) {
        Iterator<Page> it = pages.iterator();

        while (it.hasNext()) {
            Page page = it.next();

            long start = page.region.offset();
            long end = start + page.region.sizeBytes();

            if (allocation.offset() >= start && allocation.offset() < end) {
                page.allocator.free(allocation);

                if (page.isEmpty()) {
                    it.remove();
                    super.free(page.region);
                }

                return;
            }
        }

        throw new IllegalStateException("Allocation does not belong to any page");
    }

    @Override
    public void reset() {
        for (Page page : pages) {
            page.allocator.reset();
        }

        pages.clear();
        super.reset();
    }

    private record Page(Allocation region, FreeListAllocator allocator) {
        boolean isEmpty() {
            return allocator.isFullFree();
        }
    }
}
