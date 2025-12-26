package me.hannsi.lfjg.core.utils.math.map.long2objectMap;

public interface Long2ObjectMapInterface<V> {
    void put(long key, V value);

    V get(long key);

    V remove(long key);

    long getKeyByValue(V value);

    boolean containsKey(long key);

    int size();

    void clear();

    void forEach(Long2ObjectConsumer<V> action);

    Iterable<java.util.Map.Entry<Long, V>> entrySet();
}