package me.hannsi.lfjg.core.utils.math.list;

public interface LongObjectConsumer<V> {
    void accept(long key, V value);
}