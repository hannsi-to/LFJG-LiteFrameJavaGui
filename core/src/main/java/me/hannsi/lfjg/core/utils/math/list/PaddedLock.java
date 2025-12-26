package me.hannsi.lfjg.core.utils.math.list;

import java.util.concurrent.locks.ReentrantLock;

public class PaddedLock extends ReentrantLock {
    public long p1;
    public long p2;
    public long p3;
    public long p4;
    public long p5;
    public long p6;
    public long p7;
    public long p8;

    public PaddedLock() {
        super();
    }

    public long preventOptimization() {
        return p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
    }
}
