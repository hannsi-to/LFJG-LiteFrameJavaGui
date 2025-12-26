package me.hannsi.lfjg.core.utils.math.map.int2intMap;

import java.util.Arrays;

public class Int2IntMap implements Int2IntMapInterface {
    protected static final int EMPTY = Integer.MIN_VALUE;
    protected final float loadFactor;
    protected int maxFill;
    protected volatile int mask;
    protected volatile int[] keys;
    protected volatile int[] values;
    protected int size;
    protected boolean hasSpecialKey;
    protected int nullValue;

    public Int2IntMap() {
        this(16);
    }

    public Int2IntMap(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public Int2IntMap(int initialCapacity, float loadFactor) {
        this.loadFactor = loadFactor;
        int capacity = nextPowerOfTwo(initialCapacity);
        this.mask = capacity - 1;
        this.keys = new int[capacity];
        Arrays.fill(this.keys, EMPTY);
        this.values = new int[capacity];
        this.maxFill = (int) (capacity * loadFactor);
    }

    protected static int mix(int key, int mask) {
        key ^= (key >>> 16);
        return key & mask;
    }

    protected int nextPowerOfTwo(int n) {
        if (n <= 1) {
            return 2;
        }

        return 1 << (32 - Integer.numberOfLeadingZeros(n - 1));
    }

    protected void rehash(int newCapacity) {
        final int[] oldKeys = keys;
        final int[] oldValues = values;

        this.mask = newCapacity - 1;
        this.maxFill = (int) (newCapacity * loadFactor);
        this.keys = new int[newCapacity];
        Arrays.fill(this.keys, EMPTY);
        this.values = new int[newCapacity];

        int currentSize = hasSpecialKey ? 1 : 0;

        for (int i = 0; i < oldKeys.length; i++) {
            int k = oldKeys[i];
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
        final int[] keys = this.keys;
        final int[] values = this.values;
        final int mask = this.mask;
        int last;
        int curr;

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
                values[last] = 0;
                return;
            }
            keys[last] = curr;
            values[last] = values[pos];
        }
    }

    @Override
    public void put(int key, int value) {
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

        final int[] keys = this.keys;
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
    public int get(int key) {
        if (key == EMPTY) {
            return hasSpecialKey ? nullValue : EMPTY;
        }

        final int[] keys = this.keys;
        final int mask = this.mask;

        int index = mix(key, mask);
        int k;
        while ((k = keys[index]) != EMPTY) {
            if (k == key) {
                return values[index];
            }
            index = (index + 1) & mask;
        }
        return EMPTY;
    }

    @Override
    public int remove(int key) {
        if (key == EMPTY) {
            if (!hasSpecialKey) {
                return EMPTY;
            }
            hasSpecialKey = false;
            int old = nullValue;
            nullValue = EMPTY;
            size--;
            return old;
        }

        final int[] keys = this.keys;
        final int mask = this.mask;

        int pos = mix(key, mask);
        while (true) {
            int curr = keys[pos];
            if (curr == EMPTY) {
                return EMPTY;
            }
            if (curr == key) {
                int old = values[pos];
                shiftKeys(pos);
                size--;
                return old;
            }
            pos = (pos + 1) & mask;
        }
    }

    @Override
    public int getKeyByValue(int value) {
        final int[] keys = this.keys;

        if (hasSpecialKey && nullValue == value) {
            return EMPTY;
        }

        for (int i = 0; i < values.length; i++) {
            int k = keys[i];
            if (k != EMPTY && values[i] == value) {
                return k;
            }
        }
        return EMPTY;
    }


    @Override
    public boolean containsKey(int key) {
        if (key == EMPTY) {
            return hasSpecialKey;
        }

        final int[] keys = this.keys;
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
        nullValue = EMPTY;
        Arrays.fill(keys, EMPTY);
    }

    @Override
    public void forEach(Int2IntConsumer action) {
        final int[] keys = this.keys;

        if (hasSpecialKey) {
            action.accept(EMPTY, nullValue);
        }

        for (int i = 0; i < values.length; i++) {
            int k = keys[i];
            if (k != EMPTY) {
                action.accept(k, values[i]);
            }
        }
    }

    @Override
    public Iterable<java.util.Map.Entry<Integer, Integer>> entrySet() {
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
            public java.util.Map.Entry<Integer, Integer> next() {
                while (++index < keys.length) {
                    if (keys[index] != EMPTY) {
                        entry.key = keys[index];
                        entry.value = values[index];
                        return entry;
                    }
                }
                throw new java.util.NoSuchElementException();
            }
        };
    }

    static class Entry implements java.util.Map.Entry<Integer, Integer> {
        int key;
        int value;

        @Override
        public Integer getKey() {
            return key;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        @Override
        public Integer setValue(Integer value) {
            throw new UnsupportedOperationException();
        }
    }
}
