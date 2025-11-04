package me.hannsi.lfjg.render;

import java.util.ArrayDeque;

public class IdPool {
    private final ArrayDeque<Long> pool = new ArrayDeque<>();
    private long next = Id.initialGLObjectId;

    public synchronized long acquire() {
        return pool.isEmpty() ? next++ : pool.pop();
    }

    public synchronized void release(long id) {
        pool.push(id);
    }
}
