package me.hannsi.lfjg.core.utils.math.map.string2Object;

public interface String2ObjectConsumer<V> {
    void accept(String key, V value);
}
