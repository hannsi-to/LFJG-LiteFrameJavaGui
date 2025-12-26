package me.hannsi.lfjg.core.utils.math.map.long2objectMap;

public interface Long2ObjectConsumer<V> {
    void accept(long key, V value);
}