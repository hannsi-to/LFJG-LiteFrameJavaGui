package me.hannsi.lfjg.core.utils.math.map.long2Object;

public interface LongObjectConsumer<V> {
    void accept(long key, V value);
}