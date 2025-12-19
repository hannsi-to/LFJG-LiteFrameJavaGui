package me.hannsi.lfjg.core.utils.math;

import java.util.Arrays;

public final class IntArrayList {
    private int[] data;
    private int size;

    public IntArrayList(int initialCapacity) {
        this.data = new int[initialCapacity];
    }

    public IntArrayList() {
        this(8);
    }

    public void add(int value) {
        int i = size;
        if (i == data.length) {
            data = Arrays.copyOf(data, i << 1);
        }
        data[i] = value;
        size = i + 1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int[] toIntArray() {
        return Arrays.copyOf(data, size);
    }

    public void clear() {
        size = 0;
    }
}