package me.hannsi.lfjg.render.system.memory;

public class FreeListAllocator extends Allocator {
    private Block freeList;

    public FreeListAllocator(long needSizeBytes, int alignment) {
        super(needSizeBytes, alignment);
    }

    @Override
    public Allocator createLike() {
        return new FreeListAllocator(this.getNeedSizeBytes(), this.getAlignment());
    }

    @Override
    public void init(Allocation region) {
        super.init(region);

        freeList = new Block(0, region.sizeBytes());
    }

    @Override
    public Allocation alloc(long size, int alignment) {
        Block prev = null;
        Block current = freeList;

        while (current != null) {
            long alignedOffset = GPUHeap.align(current.offset, alignment);
            long end = alignedOffset + size;
            long blockEnd = current.offset + current.size;

            long remaining = blockEnd - end;
            if (end <= blockEnd) {
                if (alignedOffset > current.offset) {
                    current.size = alignedOffset - current.offset;
                    if (remaining > 0) {
                        Block tail = new Block(end, remaining);
                        tail.next = current.next;
                        current.next = tail;
                    }
                } else {
                    if (remaining > 0) {
                        current.offset = end;
                        current.size = remaining;
                    } else {
                        if (prev == null) {
                            freeList = current.next;
                        } else {
                            prev.next = current.next;
                        }
                    }
                }

                return new Allocation(
                        region.address() + alignedOffset,
                        region.offset() + alignedOffset,
                        size,
                        -1
                );
            }

            prev = current;
            current = current.next;
        }

        throw new OutOfMemoryError("FreeListAllocator: no suitable block");
    }

    @Override
    public void free(Allocation allocation) {
        long offset = allocation.offset() - region.offset();
        long size = allocation.sizeBytes();

        Block newBlock = new Block(offset, size);

        if (freeList == null || offset < freeList.offset) {
            newBlock.next = freeList;
            freeList = newBlock;
        } else {
            Block current = freeList;
            while (current.next != null && current.next.offset < offset) {
                current = current.next;
            }
            newBlock.next = current.next;
            current.next = newBlock;
        }

        coalesce();
    }

    @Override
    public void reset() {
        freeList = new Block(0, region.sizeBytes());
    }

    public boolean isFullFree() {
        return freeList != null && freeList.next == null && freeList.offset == 0 && freeList.size == region.sizeBytes();
    }

    private void coalesce() {
        Block current = freeList;

        while (current != null && current.next != null) {
            if (current.offset + current.size == current.next.offset) {
                current.size += current.next.size;
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
    }

    private static class Block {
        long offset;
        long size;
        Block next;

        Block(long offset, long size) {
            this.offset = offset;
            this.size = size;
        }
    }
}