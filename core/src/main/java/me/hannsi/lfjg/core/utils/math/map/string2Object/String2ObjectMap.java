package me.hannsi.lfjg.core.utils.math.map.string2Object;

import me.hannsi.lfjg.core.utils.math.AssetPath;
import me.hannsi.lfjg.core.utils.math.StringHash;

import java.util.*;

public class String2ObjectMap<V> implements String2ObjectMapInterface<V> {
    protected static final String EMPTY = null;
    protected final float loadFactor;
    protected int maxFill;
    protected volatile int mask;
    protected volatile String[] keys;
    protected volatile V[] values;
    protected volatile long[] hashes;
    protected int capacity;
    protected int size;
    protected boolean hasSpecialKey;
    protected V nullValue;

    public String2ObjectMap() {
        this(16);
    }

    public String2ObjectMap(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    @SuppressWarnings("unchecked")
    public String2ObjectMap(int initialCapacity, float loadFactor) {
        this.loadFactor = loadFactor;
        this.capacity = nextPowerOfTwo(initialCapacity);
        this.mask = this.capacity - 1;
        this.keys = new String[capacity];
        this.hashes = new long[capacity];
        Arrays.fill(this.keys, EMPTY);
        this.values = (V[]) new Object[capacity];
        this.maxFill = (int) (capacity * loadFactor);
    }

    protected int mix(long h64, int mask) {
        return (int) (h64 & mask);
    }

    protected int nextPowerOfTwo(int n) {
        if (n <= 1) {
            return 2;
        }

        return 1 << (32 - Integer.numberOfLeadingZeros(n - 1));
    }

    @SuppressWarnings("unchecked")
    protected void rehash(int newCapacity) {
        final String[] oldKeys = keys;
        final V[] oldValues = values;
        final long[] oldHashes = hashes;

        this.mask = newCapacity - 1;
        this.maxFill = (int) (newCapacity * loadFactor);
        this.keys = new String[newCapacity];
        this.hashes = new long[newCapacity];
        Arrays.fill(this.keys, EMPTY);
        this.values = (V[]) new Object[newCapacity];

        int currentSize = hasSpecialKey ? 1 : 0;

        for (int i = 0; i < oldKeys.length; i++) {
            String k = oldKeys[i];
            if (!Objects.equals(k, EMPTY)) {
                long h64 = oldHashes[i];
                int idx = mix(h64, mask);
                while (!Objects.equals(keys[idx], EMPTY)) {
                    idx = (idx + 1) & mask;
                }
                keys[idx] = k;
                values[idx] = oldValues[i];
                hashes[idx] = h64;
                currentSize++;
            }
        }
        this.size = currentSize;
    }

    protected void shiftKeys(int pos) {
        final String[] keys = this.keys;
        final V[] values = this.values;
        final long[] hashes = this.hashes;
        final int mask = this.mask;
        int last;
        String curr;
        long currHash;

        while (true) {
            last = pos;
            pos = (pos + 1) & mask;
            while ((curr = keys[pos]) != null) {
                currHash = hashes[pos];
                int slot = mix(currHash, mask);
                if (last <= pos ? (last >= slot || slot > pos) : (last >= slot && slot > pos)) {
                    break;
                }
                pos = (pos + 1) & mask;
            }
            if (Objects.equals(curr, EMPTY)) {
                keys[last] = EMPTY;
                values[last] = null;
                hashes[last] = 0;
                return;
            }
            keys[last] = curr;
            values[last] = values[pos];
            hashes[last] = hashes[pos];
        }
    }

    @Override
    public void put(String key, V value) {
        if (key == null) {
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

        int len = key.length();
        long h64;
        if (len <= 16) {
            int sh = key.hashCode();
            if (sh != 0) {
                h64 = sh & 0xFFFFFFFFL;
            } else {
                h64 = StringHash.hash64(key);
            }
        } else {
            h64 = StringHash.hash64(key);
        }
        int idx = mix(h64, mask);
        while (keys[idx] != null) {
            if (hashes[idx] == h64 && (keys[idx] == key || keys[idx].equals(key))) {
                values[idx] = value;
                return;
            }
            idx = (idx + 1) & mask;
        }

        keys[idx] = key;
        values[idx] = value;
        hashes[idx] = h64;
        size++;
    }

    @Override
    public V get(String key) {
        if (key == null) {
            return hasSpecialKey ? nullValue : null;
        }

        final String[] keys = this.keys;
        final int mask = this.mask;

        long h64 = (key.length() <= 16) ? (key.hashCode() & 0xFFFFFFFFL) : StringHash.hash64(key);

        int index = (int) (h64 & mask);
        String k;

        while ((k = keys[index]) != null) {
            if (hashes[index] == h64 && (k == key || k.equals(key))) {
                return values[index];
            }
            index = (index + 1) & mask;
        }
        return null;
    }

    @Override
    public void put(AssetPath assetPath, V value) {
        if (assetPath == null || assetPath.path() == null) {
            put((String) null, value);
            return;
        }

        if (size >= maxFill) {
            rehash(keys.length << 1);
        }

        final String key = assetPath.path();
        final long h64 = assetPath.hash();
        int idx = mix(h64, mask);

        while (keys[idx] != null) {
            if (hashes[idx] == h64 && (keys[idx] == key || keys[idx].equals(key))) {
                values[idx] = value;
                return;
            }
            idx = (idx + 1) & mask;
        }

        keys[idx] = key;
        values[idx] = value;
        hashes[idx] = h64;
        size++;
    }

    @Override
    public V get(AssetPath assetPath) {
        if (assetPath == null) {
            return null;
        }

        final String key = assetPath.path();
        final long h64 = assetPath.hash();
        final String[] keys = this.keys;
        final int mask = this.mask;

        int index = (int) (h64 & mask);
        String k;

        while ((k = keys[index]) != null) {
            if (hashes[index] == h64 && (k == key || k.equals(key))) {
                return values[index];
            }
            index = (index + 1) & mask;
        }
        return null;
    }

    @Override
    public V remove(String key) {
        if (key == null) {
            if (!hasSpecialKey) {
                return null;
            }
            hasSpecialKey = false;
            V old = nullValue;
            nullValue = null;
            size--;
            return old;
        }

        int len = key.length();
        long h64;
        if (len <= 16) {
            int sh = key.hashCode();
            if (sh != 0) {
                h64 = sh & 0xFFFFFFFFL;
            } else {
                h64 = StringHash.hash64(key);
            }
        } else {
            h64 = StringHash.hash64(key);
        }
        int pos = mix(h64, mask);
        while (true) {
            String curr = keys[pos];
            if (curr == null) {
                return null;
            }
            if (hashes[pos] == h64 && curr.equals(key)) {
                V old = values[pos];
                shiftKeys(pos);
                size--;
                return old;
            }
            pos = (pos + 1) & mask;
        }
    }

    @Override
    public V remove(AssetPath assetPath) {
        if (assetPath == null || assetPath.path() == null) {
            return remove((String) null);
        }

        final String key = assetPath.path();
        final long h64 = assetPath.hash();
        final int mask = this.mask;
        int pos = (int) (h64 & mask);

        while (true) {
            String curr = keys[pos];
            if (curr == null) {
                return null;
            }

            if (hashes[pos] == h64 && (curr == key || curr.equals(key))) {
                V old = values[pos];
                shiftKeys(pos);
                size--;
                return old;
            }
            pos = (pos + 1) & mask;
        }
    }

    @Override
    public String getKeyByValue(V value) {
        if (hasSpecialKey && (Objects.equals(nullValue, value))) {
            return null;
        }
        for (int i = 0; i < values.length; i++) {
            if (keys[i] != null && (Objects.equals(values[i], value))) {
                return keys[i];
            }
        }
        return null;
    }


    @Override
    public boolean containsKey(String key) {
        return get(key) != null;
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
        Arrays.fill(keys, null);
        Arrays.fill(hashes, 0L);
        Arrays.fill(values, null);
    }

    @Override
    public void forEach(String2ObjectConsumer<V> action) {
        if (hasSpecialKey) {
            action.accept(null, nullValue);
        }
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null) {
                action.accept(keys[i], values[i]);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterable<Map.Entry<String, V>> entrySet() {
        return () -> new Iterator<>() {
            final Entry entry = new Entry();
            int index = -1;

            @Override
            public boolean hasNext() {
                for (int i = index + 1; i < keys.length; i++) {
                    if (keys[i] != null) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Map.Entry<String, V> next() {
                while (++index < keys.length) {
                    if (keys[index] != null) {
                        entry.key = keys[index];
                        entry.value = values[index];
                        return (Map.Entry<String, V>) entry;
                    }
                }
                throw new NoSuchElementException();
            }
        };
    }

    static class Entry implements Map.Entry<String, Object> {
        String key;
        Object value;

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public Object setValue(Object v) {
            throw new UnsupportedOperationException();
        }
    }
}
