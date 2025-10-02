package me.hannsi.lfjg.core.utils.math.io;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class FloatBufferList {
    private static final int DEFAULT_INITIAL_CAPACITY = (3 + 4 + 2 + 3) * 1024;

    private FloatBuffer buffer;

    public FloatBufferList() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public FloatBufferList(int initialCapacity) {
        this.buffer = BufferUtils.createFloatBuffer(initialCapacity);
    }

    public FloatBufferList add(float value) {
        if (buffer.remaining() == 0) {
            FloatBuffer newBuffer = BufferUtils.createFloatBuffer(buffer.capacity() * 2);
            buffer.flip();
            newBuffer.put(buffer);
            buffer = newBuffer;
        }

        buffer.put(value);

        return this;
    }

    public void addAll(FloatBuffer src) {
        int srcRemaining = src.remaining();
        ensureCapacity(srcRemaining);

        buffer.put(src.duplicate());
    }

    public void add(int index, float value) {
        int size = size();
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        ensureCapacity(1);

        buffer.position(index);
        FloatBuffer tail = buffer.slice();
        tail.limit(size - index);
        buffer.position(index);
        buffer.put(value);
        buffer.put(tail);
        buffer.position(size + 1);
    }

    public void addAll(int index, FloatBuffer src) {
        int size = size();
        int srcRemaining = src.remaining();
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        ensureCapacity(srcRemaining);

        buffer.position(index);
        FloatBuffer tail = buffer.slice();
        tail.limit(size - index);

        buffer.position(index);
        buffer.put(src.duplicate());
        buffer.put(tail);
        buffer.position(size + srcRemaining);
    }

    public int size() {
        return buffer.position();
    }

    public FloatBuffer getBuffer() {
        return getBuffer(true);
    }

    public FloatBuffer getBuffer(boolean trimToSize) {
        if (trimToSize) {
            trimToSize();
        }

        buffer.flip();
        return buffer;
    }

    public void trimToSize() {
        int size = buffer.position();
        FloatBuffer newBuffer = BufferUtils.createFloatBuffer(size);
        buffer.flip();
        newBuffer.put(buffer);
        buffer = newBuffer;
    }

    private void ensureCapacity(int additional) {
        if (buffer.remaining() < additional) {
            int newCapacity = Math.max(buffer.capacity() * 2, buffer.capacity() + additional);
            FloatBuffer newBuffer = BufferUtils.createFloatBuffer(newCapacity);
            buffer.flip();
            newBuffer.put(buffer);
            buffer = newBuffer;
        }
    }
}
