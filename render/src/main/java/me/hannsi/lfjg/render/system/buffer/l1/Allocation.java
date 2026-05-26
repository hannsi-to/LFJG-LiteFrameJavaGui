package me.hannsi.lfjg.render.system.buffer.l1;

public class Allocation {
    private final long address;
    private final long size;
    private boolean freed;

    public Allocation(long address, long size) {
        this.address = address;
        this.size = size;
        this.freed = false;
    }

    private void check() {
        if (freed) {
            throw new AllocationException("This allocated memory has already been freed. address: " + address + " | size: " + size);
        }
    }

    public boolean isFreed() {
        return freed;
    }

    public void setFreed() {
        this.freed = true;
    }

    public long getAddress() {
        check();

        return address;
    }

    public long getSize() {
        check();

        return size;
    }
}
