package me.hannsi.lfjg.core.utils.math.map.string2intMap;

import me.hannsi.lfjg.core.utils.math.AssetPath;

import java.util.Map;

public interface String2IntMapInterface {
    void put(String key, int value);

    int get(String key);

    int remove(String key);

    void put(AssetPath assetPath, int value);

    int get(AssetPath assetPath);

    int remove(AssetPath assetPath);

    String getKeyByValue(int value);

    boolean containsKey(String key);

    int size();

    void clear();

    void forEach(String2IntConsumer action);

    Iterable<Map.Entry<String, Integer>> entrySet();
}
