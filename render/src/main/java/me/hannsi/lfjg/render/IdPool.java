package me.hannsi.lfjg.render;

import java.util.ArrayDeque;

public class IdPool {
    private final ArrayDeque<Long> pool = new ArrayDeque<>();
    private long next = Id.initialGLObjectId;

    public long acquire() {
        return pool.isEmpty() ? next++ : pool.pop();
    }

    public void release(long id) {
        pool.push(id);
    }

    public long acquire(long requestedId) {
        if (pool.remove(requestedId)) {
            return requestedId;
        }

        if (requestedId < next) {
            throw new IllegalArgumentException("ID already issued or invalid: " + requestedId);
        }

        next = requestedId + 1;
        return requestedId;
    }
}
