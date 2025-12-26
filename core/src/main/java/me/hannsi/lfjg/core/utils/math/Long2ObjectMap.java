package me.hannsi.lfjg.core.utils.math;

import java.util.Arrays;

public class Long2ObjectMap<V> {
    private final float loadFactor;
    private int maxFill;
    private int mask;
    private long[] keys;
    private V[] values;
    private int capacity;
    private boolean hasZeroKey = false;
    private int size;
    private V zeroValue;

    public Long2ObjectMap() {
        this(16);
    }

    public Long2ObjectMap(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    @SuppressWarnings("unchecked")
    public Long2ObjectMap(int initialCapacity, float loadFactor) {
        this.loadFactor = loadFactor;
        this.capacity = nextPowerOfTwo(initialCapacity);
        this.mask = this.capacity - 1;
        this.keys = new long[capacity];
        this.values = (V[]) new Object[capacity];
        this.maxFill = (int) (capacity * loadFactor);
    }

    private static int mix(long key, int mask) {
        long h = key * 0x9E3779B9L;
        return (int) (h ^ (h >>> 16)) & mask;
    }

    private int nextPowerOfTwo(int n) {
        if (n <= 1) {
            return 2;
        }

        return 1 << (32 - Integer.numberOfLeadingZeros(n - 1));
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        final int newCapacity = this.capacity << 1;
        final int newMask = newCapacity - 1;
        final long[] newKeys = new long[newCapacity];
        final V[] newValues = (V[]) new Object[newCapacity];

        final long[] oldKeys = this.keys;
        final V[] oldValues = this.values;

        for (int i = 0; i < capacity; i++) {
            long key = oldKeys[i];
            if (key != 0) {
                int idx = mix(key, newMask);
                while (newKeys[idx] != 0) {
                    idx = (idx + 1) & newMask;
                }
                newKeys[idx] = key;
                newValues[idx] = oldValues[i];
            }
        }

        this.capacity = newCapacity;
        this.mask = newMask;
        this.keys = newKeys;
        this.values = newValues;
        this.maxFill = (int) (newCapacity * loadFactor);
    }

    public void put(long key, V value) {
        if (key == 0) {
            if (!hasZeroKey) {
                size++;
            }
            hasZeroKey = true;
            zeroValue = value;
            return;
        }

        final long[] keys = this.keys;
        final int mask = this.mask;
        int idx = mix(key, mask);

        while (keys[idx] != 0) {
            if (keys[idx] == key) {
                values[idx] = value;
                return;
            }
            idx = (idx + 1) & mask;
        }

        keys[idx] = key;
        values[idx] = value;

        if (++size >= maxFill) {
            rehash();
        }
    }

    public V get(long key) {
        if (key == 0) {
            return hasZeroKey ? zeroValue : null;
        }

        final long[] keys = this.keys;
        final int mask = this.mask;
        int idx = mix(key, mask);

        long k;
        while ((k = keys[idx]) != 0) {
            if (k == key) {
                return values[idx];
            }
            idx = (idx + 1) & mask;
        }
        return null;
    }

    public boolean containsKey(long key) {
        if (key == 0) {
            return hasZeroKey;
        }
        int idx = mix(key, mask);
        while (keys[idx] != 0) {
            if (keys[idx] == key) {
                return true;
            }
            idx = (idx + 1) & mask;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
        hasZeroKey = false;
        zeroValue = null;
        Arrays.fill(keys, 0);
        Arrays.fill(values, null);
    }

    public void forEach(java.util.function.BiConsumer<Long, V> action) {
        if (hasZeroKey) {
            action.accept(0L, zeroValue);
        }
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != 0) {
                action.accept(keys[i], values[i]);
            }
        }
    }

    private static class Entry<V> implements java.util.Map.Entry<Long, V> {
        private final long key;
        private V value;

        Entry(long key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public Long getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }
    }
}
