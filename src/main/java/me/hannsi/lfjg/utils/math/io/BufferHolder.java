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

    public void memFree() {
        MemoryUtil.memFree(buffer);
    }
}
