package me.hannsi.lfjg.utils.math.io;

import lombok.Getter;
import org.lwjgl.system.MemoryUtil;

import java.nio.*;

@Getter
public class BufferHolder<T extends Buffer> {
    private final T buffer;
    private final int size;

    public BufferHolder(T buffer, int size) {
        this.buffer = buffer;
        this.size = size;
    }

    public static BufferHolder<ByteBuffer> allocateByteBuffer(int size) {
        return new BufferHolder<>(MemoryUtil.memAlloc(size), size);
    }

    public static BufferHolder<FloatBuffer> allocateFloatBuffer(int elementCount) {
        return new BufferHolder<>(MemoryUtil.memAllocFloat(elementCount), elementCount);
    }

    public static BufferHolder<IntBuffer> allocateIntBuffer(int elementCount) {
        return new BufferHolder<>(MemoryUtil.memAllocInt(elementCount), elementCount);
    }

    public static BufferHolder<ShortBuffer> allocateShortBuffer(int elementCount) {
        return new BufferHolder<>(MemoryUtil.memAllocShort(elementCount), elementCount);
    }

    public static BufferHolder<LongBuffer> allocateLongBuffer(int elementCount) {
        return new BufferHolder<>(MemoryUtil.memAllocLong(elementCount), elementCount);
    }

    public static BufferHolder<ByteBuffer> from(byte[] array) {
        ByteBuffer buffer = MemoryUtil.memAlloc(array.length);
        buffer.put(array).flip();
        return new BufferHolder<>(buffer, array.length);
    }

    public static BufferHolder<FloatBuffer> from(float[] array) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(array.length);
        buffer.put(array).flip();
        return new BufferHolder<>(buffer, array.length);
    }

    public static BufferHolder<IntBuffer> from(int[] array) {
        IntBuffer buffer = MemoryUtil.memAllocInt(array.length);
        buffer.put(array).flip();
        return new BufferHolder<>(buffer, array.length);
    }

    public static BufferHolder<ShortBuffer> from(short[] array) {
        ShortBuffer buffer = MemoryUtil.memAllocShort(array.length);
        buffer.put(array).flip();
        return new BufferHolder<>(buffer, array.length);
    }

    public static BufferHolder<LongBuffer> from(long[] array) {
        LongBuffer buffer = MemoryUtil.memAllocLong(array.length);
        buffer.put(array).flip();
        return new BufferHolder<>(buffer, array.length);
    }

    public void memFree() {
        MemoryUtil.memFree(buffer);
    }
}
