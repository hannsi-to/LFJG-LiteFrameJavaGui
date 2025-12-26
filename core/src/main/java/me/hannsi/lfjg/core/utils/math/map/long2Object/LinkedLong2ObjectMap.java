package me.hannsi.lfjg.core.utils.math.map.long2Object;

import java.util.Arrays;

public class LinkedLong2ObjectMap<V> extends Long2ObjectMap<V> implements Long2ObjectMapInterface<V> {
    protected long[] links;
    protected int first = -1;
    protected int last = -1;
    protected int nullKeyIdx;

    public LinkedLong2ObjectMap() {
        this(16);
    }

    public LinkedLong2ObjectMap(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public LinkedLong2ObjectMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);

        this.nullKeyIdx = keys.length;
        this.links = new long[keys.length + 1];
    }

    protected static long pack(int prev, int next) {
        return (((long) prev) << 32) | ((next) & 0xFFFFFFFFL);
    }

    protected static int getPrev(long link) {
        return (int) (link >>> 32);
    }

    protected static int getNext(long link) {
        return (int) link;
    }

    @Override
    public void put(long key, V value) {
        int index;
        if (key == EMPTY) {
            if (hasSpecialKey) {
                nullValue = value;
                return;
            }
            hasSpecialKey = true;
            nullValue = value;
            index = nullKeyIdx;
        } else {
            if (size >= maxFill) {
                rehash(keys.length << 1);
            }

            final long[] keys = this.keys;
            final int mask = this.mask;

            index = mix(key, mask);
            while (keys[index] != EMPTY) {
                if (keys[index] == key) {
                    values[index] = value;
                    return;
                }
                index = (index + 1) & mask;
            }

            keys[index] = key;
            values[index] = value;
        }

        if (size == 0) {
            first = last = index;
            links[index] = pack(-1, -1);
        } else {
            links[last] = pack(getPrev(links[last]), index);
            links[index] = pack(last, -1);
            last = index;
        }

        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void rehash(int newCapacity) {
        long[] oldKeys = this.keys;
        V[] oldValues = this.values;
        int oldFirst = this.first;
        long[] oldLinks = this.links;
        int oldNullIdx = this.nullKeyIdx;
        V oldNullValue = this.nullValue;

        this.mask = newCapacity - 1;
        this.maxFill = (int) (newCapacity * loadFactor);
        this.keys = new long[newCapacity];
        Arrays.fill(this.keys, EMPTY);
        this.values = (V[]) new Object[newCapacity];

        this.nullKeyIdx = newCapacity;
        this.links = new long[newCapacity + 1];

        this.first = -1;
        this.last = -1;
        this.size = 0;
        this.hasSpecialKey = false;

        int curr = oldFirst;
        while (curr != -1) {
            if (curr == oldNullIdx) {
                put(EMPTY, oldNullValue);
            } else {
                put(oldKeys[curr], oldValues[curr]);
            }
            curr = getNext(oldLinks[curr]);
        }
    }

    @Override
    public V remove(long key) {
        int pos;
        if (key == EMPTY) {
            if (!hasSpecialKey) {
                return null;
            }
            pos = nullKeyIdx;
        } else {
            final int mask = this.mask;
            pos = mix(key, mask);
            while (true) {
                long curr = keys[pos];
                if (curr == EMPTY) {
                    return null;
                }
                if (curr == key) {
                    break;
                }
                pos = (pos + 1) & mask;
            }
        }

        V old = (pos == nullKeyIdx) ? nullValue : values[pos];

        int prev = getPrev(links[pos]);
        int next = getNext(links[pos]);
        if (prev != -1) {
            links[prev] = pack(getPrev(links[prev]), next);
        } else {
            first = next;
        }
        if (next != -1) {
            links[next] = pack(prev, getNext(links[next]));
        } else {
            last = prev;
        }

        if (pos == nullKeyIdx) {
            hasSpecialKey = false;
            nullValue = null;
        } else {
            removeAndFixLinks(pos);
        }

        size--;
        return old;
    }

    protected void removeAndFixLinks(int pos) {
        final long[] keys = this.keys;
        final V[] values = this.values;
        final long[] links = this.links;
        final int mask = this.mask;

        keys[pos] = EMPTY;
        values[pos] = null;

        int i = pos;
        while (true) {
            i = (i + 1) & mask;
            if (keys[i] == EMPTY) {
                break;
            }

            int slot = mix(keys[i], mask);
            if (i <= pos ? (i < slot && slot <= pos) : (i < slot || slot <= pos)) {
                keys[pos] = keys[i];
                values[pos] = values[i];
                links[pos] = links[i];

                int p = getPrev(links[pos]);
                int n = getNext(links[pos]);
                if (p != -1) {
                    links[p] = pack(getPrev(links[p]), pos);
                } else {
                    first = pos;
                }
                if (n != -1) {
                    links[n] = pack(pos, getNext(links[n]));
                } else {
                    last = pos;
                }

                keys[i] = EMPTY;
                values[i] = null;
                pos = i;
            }
        }
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
    public long getKeyByValue(V value) {
        if (hasSpecialKey && java.util.Objects.equals(nullValue, value)) {
            return EMPTY;
        }

        int curr = first;
        while (curr != -1) {
            if (java.util.Objects.equals(values[curr], value)) {
                return keys[curr];
            }
            curr = getNext(links[curr]);
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
        long k;
        while ((k = keys[index]) != EMPTY) {
            if (k == key) {
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
        super.clear();
        first = -1;
        last = -1;
    }

    @Override
    public void forEach(LongObjectConsumer<V> action) {
        int curr = first;
        while (curr != -1) {
            if (curr == nullKeyIdx) {
                action.accept(EMPTY, nullValue);
            } else {
                action.accept(keys[curr], values[curr]);
            }
            curr = getNext(links[curr]);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterable<java.util.Map.Entry<Long, V>> entrySet() {
        return () -> new java.util.Iterator<>() {
            final Entry entry = new Entry();
            int curr = first;

            @Override
            public boolean hasNext() {
                return curr != -1;
            }

            @Override
            public java.util.Map.Entry<Long, V> next() {
                if (curr == -1) {
                    throw new java.util.NoSuchElementException();
                }

                if (curr == nullKeyIdx) {
                    entry.key = EMPTY;
                    entry.value = nullValue;
                } else {
                    entry.key = keys[curr];
                    entry.value = values[curr];
                }

                curr = getNext(links[curr]);
                return (java.util.Map.Entry<Long, V>) entry;
            }
        };
    }
}
