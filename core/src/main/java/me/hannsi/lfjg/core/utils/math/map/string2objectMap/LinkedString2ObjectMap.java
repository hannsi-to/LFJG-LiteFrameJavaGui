package me.hannsi.lfjg.core.utils.math.map.string2objectMap;

import me.hannsi.lfjg.core.utils.math.AssetPath;
import me.hannsi.lfjg.core.utils.math.StringHash;

import java.util.*;

public class LinkedString2ObjectMap<V> extends String2ObjectMap<V> {
    protected long[] links;
    protected int first = -1;
    protected int last = -1;
    protected int nullKeyIdx;

    public LinkedString2ObjectMap() {
        this(16);
    }

    public LinkedString2ObjectMap(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public LinkedString2ObjectMap(int initialCapacity, float loadFactor) {
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
    public void put(String key, V value) {
        int index;
        if (key == null) {
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

            long h64 = (key.length() <= 16) ? (key.hashCode() & 0xFFFFFFFFL) : StringHash.hash64(key);
            index = mix(h64, mask);

            while (keys[index] != null) {
                if (hashes[index] == h64 && Objects.equals(keys[index], key)) {
                    values[index] = value;
                    return;
                }
                index = (index + 1) & mask;
            }
            keys[index] = key;
            values[index] = value;
            hashes[index] = h64;
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
    public void put(AssetPath assetPath, V value) {
        if (assetPath == null) {
            put((String) null, value);
            return;
        }
        if (size >= maxFill) {
            rehash(keys.length << 1);
        }

        final String key = assetPath.path();
        final long h64 = assetPath.hash();
        int index = mix(h64, mask);

        while (keys[index] != null) {
            if (hashes[index] == h64 && Objects.equals(keys[index], key)) {
                values[index] = value;
                return;
            }
            index = (index + 1) & mask;
        }

        keys[index] = key;
        values[index] = value;
        hashes[index] = h64;

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
        String[] oldKeys = this.keys;
        V[] oldValues = this.values;
        int oldFirst = this.first;
        long[] oldLinks = this.links;
        int oldNullIdx = this.nullKeyIdx;
        V oldNullValue = this.nullValue;

        this.mask = newCapacity - 1;
        this.maxFill = (int) (newCapacity * loadFactor);
        this.keys = new String[newCapacity];
        this.hashes = new long[newCapacity];
        this.values = (V[]) new Object[newCapacity];
        this.nullKeyIdx = newCapacity;
        this.links = new long[newCapacity + 1];
        Arrays.fill(this.links, -1L);

        this.first = -1;
        this.last = -1;
        this.size = 0;
        this.hasSpecialKey = false;

        int curr = oldFirst;
        while (curr != -1) {
            if (curr == oldNullIdx) {
                put((String) null, oldNullValue);
            } else {
                put(oldKeys[curr], oldValues[curr]);
            }
            curr = getNext(oldLinks[curr]);
        }
    }

    @Override
    public V remove(String key) {
        int pos;
        if (key == null) {
            if (!hasSpecialKey) {
                return null;
            }
            pos = nullKeyIdx;
        } else {
            long h64 = (key.length() <= 16) ? (key.hashCode() & 0xFFFFFFFFL) : StringHash.hash64(key);
            pos = mix(h64, mask);
            while (true) {
                String curr = keys[pos];
                if (curr == null) {
                    return null;
                }
                if (hashes[pos] == h64 && Objects.equals(curr, key)) {
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
        final String[] keys = this.keys;
        final V[] values = this.values;
        final long[] hashes = this.hashes;
        final long[] links = this.links;
        final int mask = this.mask;

        keys[pos] = null;
        values[pos] = null;
        hashes[pos] = 0;

        int i = pos;
        while (true) {
            i = (i + 1) & mask;
            if (keys[i] == null) {
                break;
            }

            int slot = mix(hashes[i], mask);
            if (i <= pos ? (i < slot && slot <= pos) : (i < slot || slot <= pos)) {
                keys[pos] = keys[i];
                values[pos] = values[i];
                hashes[pos] = hashes[i];
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

                keys[i] = null;
                values[i] = null;
                pos = i;
            }
        }
    }

    @Override
    public V get(String key) {
        return super.get(key);
    }

    @Override
    public V get(AssetPath assetPath) {
        return super.get(assetPath);
    }

    @Override
    public V remove(AssetPath assetPath) {
        if (assetPath == null) {
            return remove((String) null);
        }
        return remove(assetPath.path());
    }

    @Override
    public String getKeyByValue(V value) {
        int curr = first;
        while (curr != -1) {
            V v = (curr == nullKeyIdx) ? nullValue : values[curr];
            if (Objects.equals(v, value)) {
                return (curr == nullKeyIdx) ? null : keys[curr];
            }
            curr = getNext(links[curr]);
        }
        return null;
    }

    @Override
    public boolean containsKey(String key) {
        return super.containsKey(key);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void forEach(String2ObjectConsumer<V> action) {
        int curr = first;
        while (curr != -1) {
            if (curr == nullKeyIdx) {
                action.accept(null, nullValue);
            } else {
                action.accept(keys[curr], values[curr]);
            }
            curr = getNext(links[curr]);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterable<Map.Entry<String, V>> entrySet() {
        return () -> new Iterator<>() {
            final Entry entry = new Entry();
            int curr = first;

            @Override
            public boolean hasNext() {
                return curr != -1;
            }

            @Override
            public Map.Entry<String, V> next() {
                if (curr == -1) {
                    throw new NoSuchElementException();
                }

                if (curr == nullKeyIdx) {
                    entry.key = null;
                    entry.value = nullValue;
                } else {
                    entry.key = keys[curr];
                    entry.value = values[curr];
                }

                curr = getNext(links[curr]);
                return (Map.Entry<String, V>) entry;
            }
        };
    }

    @Override
    public void clear() {
        super.clear();
        Arrays.fill(links, -1L);
        first = -1;
        last = -1;
    }
}