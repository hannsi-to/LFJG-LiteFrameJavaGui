package me.hannsi.lfjg.core.utils.math.map.string2objectMap;

public interface String2ObjectConsumer<V> {
    void accept(String key, V value);
}
