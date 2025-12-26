package me.hannsi.lfjg.core.utils.math.map.long2Object;

import java.util.Arrays;
import java.util.Objects;

public class Long2ObjectMap<V> implements Long2ObjectMapInterface<V> {
    protected static final long EMPTY = Long.MIN_VALUE;
    protected final float loadFactor;
    protected int maxFill;
    protected volatile int mask;
    protected volatile long[] keys;
    protected volatile V[] values;
    protected int capacity;
    protected int size;
    protected boolean hasSpecialKey;
    protected V nullValue;

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
        Arrays.fill(this.keys, EMPTY);
        this.values = (V[]) new Object[capacity];
        this.maxFill = (int) (capacity * loadFactor);
    }

    protected static int mix(long key, int mask) {
        long h = key ^ (key >>> 32);
        h ^= (h >>> 16);
        return (int) h & mask;
    }

    protected int nextPowerOfTwo(int n) {
        if (n <= 1) {
            return 2;
        }

        return 1 << (32 - Integer.numberOfLeadingZeros(n - 1));
    }

    @SuppressWarnings("unchecked")
    protected void rehash(int newCapacity) {
        final long[] oldKeys = keys;
        final V[] oldValues = values;

        this.mask = newCapacity - 1;
        this.maxFill = (int) (newCapacity * loadFactor);
        this.keys = new long[newCapacity];
        Arrays.fill(this.keys, EMPTY);
        this.values = (V[]) new Object[newCapacity];

        int currentSize = hasSpecialKey ? 1 : 0;

        for (int i = 0; i < oldKeys.length; i++) {
            long k = oldKeys[i];
            if (k != EMPTY) {
                int idx = mix(k, mask);
                while (keys[idx] != EMPTY) {
                    idx = (idx + 1) & mask;
                }
                keys[idx] = k;
                values[idx] = oldValues[i];
                currentSize++;
            }
        }
        this.size = currentSize;
    }

    protected void shiftKeys(int pos) {
        final long[] keys = this.keys;
        final V[] values = this.values;
        final int mask = this.mask;
        int last;
        long curr;

        while (true) {
            last = pos;
            pos = (pos + 1) & mask;
            while ((curr = keys[pos]) != EMPTY) {
                int slot = mix(curr, mask);
                if (last <= pos ? (last >= slot || slot > pos) : (last >= slot && slot > pos)) {
                    break;
                }
                pos = (pos + 1) & mask;
            }
            if (curr == EMPTY) {
                keys[last] = EMPTY;
                values[last] = null;
                return;
            }
            keys[last] = curr;
            values[last] = values[pos];
        }
    }

    @Override
    public void put(long key, V value) {
        if (key == EMPTY) {
            if (!hasSpecialKey) {
                hasSpecialKey = true;
                size++;
            }
            nullValue = value;
            return;
        }

        if (size >= maxFill) {
            rehash(keys.length << 1);
        }

        final long[] keys = this.keys;
        final int mask = this.mask;

        int idx = mix(key, mask);
        while (keys[idx] != EMPTY) {
            if (keys[idx] == key) {
                values[idx] = value;
                return;
            }
            idx = (idx + 1) & mask;
        }

        keys[idx] = key;
        values[idx] = value;
        size++;
    }

    @Override
    public V get(long key) {
        if (key == EMPTY) {
            return hasSpecialKey ? nullValue : null;
        }

        final long[] keys = this.keys;
        final int mask = this.mask;

        int index = mix(key, mask);
        long k;
        while ((k = keys[index]) != EMPTY) {
            if (k == key) {
                return values[index];
            }
            index = (index + 1) & mask;
        }
        return null;
    }

    @Override
    public V remove(long key) {
        if (key == EMPTY) {
            if (!hasSpecialKey) {
                return null;
            }
            hasSpecialKey = false;
            V old = nullValue;
            nullValue = null;
            size--;
            return old;
        }

        final long[] keys = this.keys;
        final int mask = this.mask;

        int pos = mix(key, mask);
        while (true) {
            long curr = keys[pos];
            if (curr == EMPTY) {
                return null;
            }
            if (curr == key) {
                V old = values[pos];
                shiftKeys(pos);
                size--;
                return old;
            }
            pos = (pos + 1) & mask;
        }
    }

    @Override
    public long getKeyByValue(V value) {
        final long[] keys = this.keys;

        if (hasSpecialKey && Objects.equals(nullValue, value)) {
            return EMPTY;
        }

        for (int i = 0; i < capacity; i++) {
            long k = keys[i];
            if (k != EMPTY && Objects.equals(values[i], value)) {
                return k;
            }
        }
        return EMPTY;
    }


    @Override
    public boolean containsKey(long key) {
        if (key == EMPTY) {
            return hasSpecialKey;
        }

        final long[] keys = this.keys;
        final int mask = this.mask;

        int index = mix(key, mask);
        while (keys[index] != EMPTY) {
            if (keys[index] == key) {
                return true;
            }
            index = (index + 1) & mask;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        hasSpecialKey = false;
        nullValue = null;
        Arrays.fill(keys, EMPTY);
        Arrays.fill(values, null);
    }

    @Override
    public void forEach(LongObjectConsumer<V> action) {
        final long[] keys = this.keys;

        if (hasSpecialKey) {
            action.accept(EMPTY, nullValue);
        }

        for (int i = 0; i < capacity; i++) {
            long k = keys[i];
            if (k != EMPTY) {
                action.accept(k, values[i]);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterable<java.util.Map.Entry<Long, V>> entrySet() {
        return () -> new java.util.Iterator<>() {
            final Entry entry = new Entry();
            int index = -1;

            @Override
            public boolean hasNext() {
                for (int i = index + 1; i < keys.length; i++) {
                    if (keys[i] != EMPTY) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public java.util.Map.Entry<Long, V> next() {
                while (++index < keys.length) {
                    if (keys[index] != EMPTY) {
                        entry.key = keys[index];
                        entry.value = values[index];
                        return (java.util.Map.Entry<Long, V>) entry;
                    }
                }
                throw new java.util.NoSuchElementException();
            }
        };
    }

    static class Entry implements java.util.Map.Entry<Long, Object> {
        long key;
        Object value;

        @Override
        public Long getKey() {
            return key;
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public Object setValue(Object value) {
            throw new UnsupportedOperationException();
        }
    }
}
