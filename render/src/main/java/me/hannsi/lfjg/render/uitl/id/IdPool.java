package me.hannsi.lfjg.render.uitl.id;

import java.util.ArrayDeque;

public class IdPool {
    private final ArrayDeque<Integer> pool = new ArrayDeque<>();
    private int next = Id.initialGLObjectId;

    public int acquire() {
        return pool.isEmpty() ? next++ : pool.pop();
    }

    public void release(int id) {
        pool.push(id);
    }

    public int acquire(int requestedId) {
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
