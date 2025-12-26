package me.hannsi.lfjg.core.utils.math.map.string2objectMap;

import me.hannsi.lfjg.core.utils.math.AssetPath;

import java.util.Map;

public interface String2ObjectMapInterface<V> {
    void put(String key, V value);

    V get(String key);

    V remove(String key);

    void put(AssetPath assetPath, V value);

    V get(AssetPath assetPath);

    V remove(AssetPath assetPath);

    String getKeyByValue(V value);

    boolean containsKey(String key);

    int size();

    void clear();

    void forEach(String2ObjectConsumer<V> action);

    Iterable<Map.Entry<String, V>> entrySet();
}
