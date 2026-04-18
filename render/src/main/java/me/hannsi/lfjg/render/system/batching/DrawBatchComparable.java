package me.hannsi.lfjg.render.system.batching;

public interface DrawBatchComparable<T> {
    int compareTo(T main, T other);
}
