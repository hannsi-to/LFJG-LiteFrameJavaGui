package me.hannsi.lfjg.core.utils.math.io;

import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ByteBufferPool {
    private final BlockingQueue<ByteBuffer> pool;
    private final int bufferSize;

    public ByteBufferPool(int poolSize, int bufferSize) {
        this.pool = new ArrayBlockingQueue<>(poolSize);
        this.bufferSize = bufferSize;

        for (int i = 0; i < poolSize; i++) {
            pool.offer(ByteBuffer.allocateDirect(bufferSize));
        }
    }

    public ByteBuffer borrow() {
        ByteBuffer buffer = pool.poll();
        if (buffer == null) {
            return ByteBuffer.allocateDirect(bufferSize);
        }

        buffer.clear();
        return buffer;
    }

    public void release(ByteBuffer buffer) {
        if (buffer == null) {
            return;
        }

        buffer.clear();
        pool.offer(buffer);
    }
}
