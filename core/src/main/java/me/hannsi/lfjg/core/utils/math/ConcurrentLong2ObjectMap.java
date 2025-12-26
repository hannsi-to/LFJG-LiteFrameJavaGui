package me.hannsi.lfjg.core.utils.math;

import java.util.concurrent.locks.StampedLock;
import java.util.function.BiConsumer;

public class ConcurrentLong2ObjectMap<V> {
    private final Long2ObjectMap<V> internalMap;
    private final StampedLock lock = new StampedLock();

    public ConcurrentLong2ObjectMap() {
        this.internalMap = new Long2ObjectMap<>();
    }

    public ConcurrentLong2ObjectMap(int initialCapacity) {
        this.internalMap = new Long2ObjectMap<>(initialCapacity);
    }

    public void put(long key, V value) {
        long stamp = lock.writeLock();
        try {
            internalMap.put(key, value);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public V get(long key) {
        long stamp = lock.tryOptimisticRead();
        V value = internalMap.get(key);

        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                value = internalMap.get(key);
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return value;
    }

    public boolean containsKey(long key) {
        long stamp = lock.tryOptimisticRead();
        boolean exists = internalMap.containsKey(key);
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                exists = internalMap.containsKey(key);
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return exists;
    }

    public int size() {
        long stamp = lock.tryOptimisticRead();
        int s = internalMap.size();
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                s = internalMap.size();
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return s;
    }

    public void clear() {
        long stamp = lock.writeLock();
        try {
            internalMap.clear();
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public void forEach(BiConsumer<Long, V> action) {
        long stamp = lock.readLock();
        try {
            internalMap.forEach(action);
        } finally {
            lock.unlockRead(stamp);
        }
    }
}
