package me.hannsi.lfjg.render.system.buffer.l1;

import org.lwjgl.system.MemoryUtil;

import java.util.function.LongConsumer;

import static me.hannsi.lfjg.core.Core.UNSAFE;

public class Allocation {
    private final long address;
    private final long size;
    private final LongConsumer freeAction;
    private volatile boolean freed;

    public Allocation(long size) {
        this(MemoryUtil.nmemAlloc(size), size, MemoryUtil::nmemFree);
    }

    public Allocation(long address, long size, LongConsumer freeAction) {
        this.address = address;
        this.size = size;
        this.freeAction = freeAction;
        this.freed = false;
    }

    public void putByte(long offset, byte value) {
        checkBounds(offset, Byte.BYTES);
        UNSAFE.putByte(address + offset, value);
    }

    public void putShort(long offset, short value) {
        checkBounds(offset, Short.BYTES);
        UNSAFE.putShort(address + offset, value);
    }

    public void putInt(long offset, int value) {
        checkBounds(offset, Integer.BYTES);
        UNSAFE.putInt(address + offset, value);
    }

    public void putFloat(long offset, float value) {
        checkBounds(offset, Float.BYTES);
        UNSAFE.putFloat(address + offset, value);
    }

    public void putDouble(long offset, double value) {
        checkBounds(offset, Double.BYTES);
        UNSAFE.putDouble(address + offset, value);
    }

    public void putLong(long offset, long value) {
        checkBounds(offset, Long.BYTES);
        UNSAFE.putLong(address + offset, value);
    }

    private void checkBounds(long offset, long writeSize) {
        check();

        if (offset < 0 || writeSize < 0) {
            throw new IndexOutOfBoundsException("Negative offset or size. Offset: " + offset + ", WriteSize: " + writeSize);
        }

        if (offset > size - writeSize) {
            throw new IndexOutOfBoundsException("Offset: " + offset + ", WriteSize: " + writeSize + ", AllocationSize: " + size);
        }
    }

    private void check() {
        if (freed) {
            throw new AllocationException("This allocated memory has already been freed. address: " + address + " | size: " + size);
        }
    }

    public boolean isFreed() {
        return freed;
    }

    public void freed() {
        if (freed) {
            throw new AllocationException("Double free detected! This memory was already freed. address: " + address);
        }

        freeAction.accept(address);

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
