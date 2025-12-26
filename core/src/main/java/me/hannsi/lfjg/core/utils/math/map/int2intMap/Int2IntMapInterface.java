package me.hannsi.lfjg.core.utils.math.map.int2intMap;

public interface Int2IntMapInterface {
    void put(int key, int value);

    int get(int key);

    int remove(int key);

    int getKeyByValue(int value);

    boolean containsKey(int key);

    int size();

    void clear();

    void forEach(Int2IntConsumer action);

    Iterable<java.util.Map.Entry<Integer, Integer>> entrySet();

}
