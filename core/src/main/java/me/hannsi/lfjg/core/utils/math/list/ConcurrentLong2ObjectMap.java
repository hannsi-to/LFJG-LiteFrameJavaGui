package me.hannsi.lfjg.core.utils.math.list;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class ConcurrentLong2ObjectMap<V> extends Long2ObjectMap<V> implements Long2ObjectMapInterface<V> {
    protected static final long MOVED = Long.MAX_VALUE - 1;
    protected static final int MIN_TRANSFER_STRIDE = 1024;
    protected static final VarHandle KH;
    protected static final VarHandle VH;

    static {
        try {
            KH = MethodHandles.arrayElementVarHandle(long[].class);
            VH = MethodHandles.arrayElementVarHandle(Object[].class);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    protected final AtomicInteger transferIndex;
    protected final LongAdder counter;
    protected volatile long[] nextKeys;
    protected volatile Object[] nextValues;

    public ConcurrentLong2ObjectMap() {
        this(16);
    }

    public ConcurrentLong2ObjectMap(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public ConcurrentLong2ObjectMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);

        transferIndex = new AtomicInteger();
        counter = new LongAdder();
    }

    @Override
    public void put(long key, V value) {
        if (key == EMPTY) {
            synchronized (this) {
                if (!hasSpecialKey) {
                    hasSpecialKey = true;
                    counter.increment();
                }
                nullValue = value;
            }
            return;
        }

        if (counter.sum() >= maxFill) {
            helpTransfer();
        }

        while (true) {
            long[] ksd = this.keys;
            int m = this.mask;
            int idx = mix(key, m);

            while (true) {
                long k = (long) KH.getVolatile(ksd, idx);

                if (k == MOVED) {
                    helpTransfer();
                    break;
                }

                if (k == EMPTY) {
                    if (KH.compareAndSet(ksd, idx, EMPTY, key)) {
                        VH.setVolatile(this.values, idx, value);
                        counter.increment();
                        return;
                    }
                    continue;
                }

                if (k == key) {
                    VH.setVolatile(this.values, idx, value);
                    return;
                }
                idx = (idx + 1) & m;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void helpTransfer() {
        long[] oldKeys = this.keys;
        int n = oldKeys.length;

        if (nextKeys == null) {
            synchronized (this) {
                if (nextKeys == null) {
                    int newCapacity = n << 1;
                    long[] nk = new long[newCapacity];
                    java.util.Arrays.fill(nk, EMPTY);
                    nextValues = new Object[newCapacity];
                    nextKeys = nk;
                    transferIndex.set(n);
                }
            }
        }

        long[] nextK = this.nextKeys;
        Object[] nextV = this.nextValues;
        int nextMask = nextK.length - 1;

        int stride = (n > MIN_TRANSFER_STRIDE) ? (n >>> 3) / Runtime.getRuntime().availableProcessors() : MIN_TRANSFER_STRIDE;
        if (stride < MIN_TRANSFER_STRIDE) {
            stride = MIN_TRANSFER_STRIDE;
        }

        int bound;
        int i;
        while ((i = transferIndex.getAndAdd(-stride)) > 0) {
            bound = Math.max(i - stride, 0);

            for (int j = i - 1; j >= bound; j--) {
                while (true) {
                    long k = (long) KH.getVolatile(oldKeys, j);

                    if (k == EMPTY) {
                        if (KH.compareAndSet(oldKeys, j, EMPTY, MOVED)) {
                            break;
                        }
                    } else if (k == MOVED) {
                        break;
                    } else {
                        V v = (V) VH.getVolatile(this.values, j);
                        if (KH.compareAndSet(oldKeys, j, k, MOVED)) {
                            int idx = mix(k, nextMask);
                            while ((long) KH.getOpaque(nextK, idx) != EMPTY) {
                                idx = (idx + 1) & nextMask;
                            }
                            KH.setOpaque(nextK, idx, k);
                            VH.setOpaque(nextV, idx, v);
                            break;
                        }
                    }
                }
            }
        }

        if (transferIndex.get() <= 0 && this.keys == oldKeys) {
            synchronized (this) {
                if (this.keys == oldKeys) {
                    this.mask = nextMask;
                    this.values = (V[]) nextV;
                    this.keys = nextK;
                    this.maxFill = (int) (nextK.length * loadFactor);
                    this.nextKeys = null;
                    this.nextValues = null;
                }
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(long key) {
        if (key == EMPTY) {
            return hasSpecialKey ? nullValue : null;
        }

        long[] ksd = this.keys;
        Object[] vsd = this.values;
        int m = this.mask;
        int idx = mix(key, m);

        while (true) {
            long k = (long) KH.getAcquire(ksd, idx);

            if (k == MOVED) {
                long[] nk = this.nextKeys;
                if (nk != null) {
                    ksd = nk;
                    vsd = this.nextValues;
                    m = ksd.length - 1;
                    idx = mix(key, m);
                    continue;
                }
                ksd = this.keys;
                vsd = this.values;
                m = this.mask;
                idx = mix(key, m);
                continue;
            }

            if (k == EMPTY) {
                return null;
            }
            if (k == key) {
                return (V) VH.getAcquire(vsd, idx);
            }

            idx = (idx + 1) & m;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public V remove(long key) {
        if (key == EMPTY) {
            synchronized (this) {
                if (!hasSpecialKey) {
                    return null;
                }
                hasSpecialKey = false;
                V old = nullValue;
                nullValue = null;
                counter.decrement();
                return old;
            }
        }

        while (true) {
            long[] ksd = this.keys;
            int m = this.mask;
            int idx = mix(key, m);

            while (true) {
                long k = (long) KH.getVolatile(ksd, idx);

                if (k == MOVED) {
                    helpTransfer();
                    break;
                }

                if (k == EMPTY) {
                    return null;
                }

                if (k == key) {
                    V old = (V) VH.getVolatile(this.values, idx);
                    if (VH.compareAndSet(this.values, idx, old, null)) {
                        if (old != null) {
                            counter.decrement();
                        }
                        return old;
                    }
                    continue;
                }
                idx = (idx + 1) & m;
            }
        }
    }

    @Override
    public void clear() {
        synchronized (this) {
            long[] ksd = this.keys;
            java.util.Arrays.fill(ksd, EMPTY);
            java.util.Arrays.fill(this.values, null);
            hasSpecialKey = false;
            nullValue = null;
            counter.reset();
        }
    }

    @Override
    public int size() {
        return (int) counter.sum();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void forEach(LongObjectConsumer<V> action) {
        long[] ksd = this.keys;
        Object[] vsd = this.values;

        if (hasSpecialKey) {
            action.accept(EMPTY, nullValue);
        }

        for (int i = 0; i < ksd.length; i++) {
            long k = (long) KH.getAcquire(ksd, i);
            if (k != EMPTY && k != MOVED) {
                V v = (V) VH.getAcquire(vsd, i);
                if (v != null) {
                    action.accept(k, v);
                }
            }
        }
    }

    @Override
    protected void rehash(int newCapacity) {
        helpTransfer();

        while (this.nextKeys != null) {
            helpTransfer();
            Thread.yield();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterable<java.util.Map.Entry<Long, V>> entrySet() {
        return () -> new java.util.Iterator<>() {
            final Entry entry = new Entry();
            final long[] ksd = keys;
            final Object[] vsd = values;
            int index = 0;

            @Override
            public boolean hasNext() {
                while (index < ksd.length) {
                    long k = (long) KH.getAcquire(ksd, index);
                    if (k != EMPTY && k != MOVED) {
                        return true;
                    }
                    index++;
                }
                return false;
            }

            @Override
            public java.util.Map.Entry<Long, V> next() {
                while (index < ksd.length) {
                    long k = (long) KH.getAcquire(ksd, index);
                    if (k != EMPTY && k != MOVED) {
                        Object v = VH.getAcquire(vsd, index);
                        entry.key = k;
                        entry.value = v;
                        index++;
                        return (java.util.Map.Entry<Long, V>) entry;
                    }
                    index++;
                }
                throw new java.util.NoSuchElementException();
            }
        };
    }
}
