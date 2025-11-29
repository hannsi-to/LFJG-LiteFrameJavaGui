package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.render.system.shader.STD140UniformBlockType;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL44;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.render.LFJGRenderContext.glStateCache;


public class TestPersistentMappedUBO implements TestPersistentMappedBuffer {
    public static long DEFAULT_UBO_CAPACITY = STD140UniformBlockType.MAT4.getByteSize() * 3L;
    private final int flags;
    private final List<UBOData> uboDatum;
    private ByteBuffer mappedBuffer;
    private long mappedAddress;
    private int bufferId;
    private long gpuMemorySize;

    public TestPersistentMappedUBO(int flags, int initialCapacity) {
        this.flags = flags;
        this.uboDatum = new ArrayList<>();

        allocationBufferStorageUBOs(initialCapacity);
    }

    private void allocationBufferStorageUBOs(int capacity) {
        allocationBufferStorage(DEFAULT_UBO_CAPACITY * capacity);
    }

    @Override
    public void allocationBufferStorage(long capacity) {
        gpuMemorySize = capacity;
        if (bufferId != 0) {
            glStateCache.deleteUniformBuffer(bufferId);
            bufferId = 0;
        }

        bufferId = GL15.glGenBuffers();
        glStateCache.bindUniformBuffer(bufferId);
        GL44.glBufferStorage(GL31.GL_UNIFORM_BUFFER, gpuMemorySize, flags);

        ByteBuffer byteBuffer = GL30.glMapBufferRange(
                GL31.GL_UNIFORM_BUFFER,
                0,
                gpuMemorySize,
                flags
        );
        if (byteBuffer == null) {
            throw new RuntimeException("glMapBufferRange failed");
        }
        mappedBuffer = byteBuffer;
        mappedAddress = MemoryUtil.memAddress(byteBuffer);
    }

    public TestPersistentMappedUBO add(int bindingPoint, STD140UniformBlockType[] types, Object... values) {
        if (types.length != values.length) {
            throw new IllegalArgumentException("The length of the types does not match the length of the values. typesLength: " + types.length + " | valuesLength: " + values.length);
        }

        ensureCapacityForUBOs(types);

        long baseOffset = getUBOsSizeBytes();

        uboDatum.add(new UBOData(bindingPoint, baseOffset, getUBOsSizeBytes(types)));

        return this;
    }

    @Override
    public TestPersistentMappedUBO syncToGPU() {
        flushMappedRange(0, getUBOsSizeBytes());

        return null;
    }

    @Override
    public void flushMappedRange(long byteOffset, long byteLength) {
        final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
        if ((flags & GL_MAP_COHERENT_BIT) == 0) {
            GL44.glFlushMappedBufferRange(GL15.GL_ARRAY_BUFFER, byteOffset, byteLength);
        }
    }

    private void ensureCapacityForUBOs(STD140UniformBlockType[] std140UniformBlockTypes) {
        long requiredBytes = getUBOsSizeBytes() + getUBOsSizeBytes(std140UniformBlockTypes);
        if (requiredBytes <= gpuMemorySize) {
            return;
        }

        long newCapacity = gpuMemorySize + (gpuMemorySize >> 1);
        if (newCapacity < requiredBytes) {
            newCapacity = requiredBytes;
        }

        if (newCapacity == 0) {
            newCapacity = requiredBytes;
        }

        growBuffer((int) newCapacity);
    }

    @Override
    public void growBuffer(long newGPUMemorySizeBytes) {

    }

    public void writeUBO(long baseByteOffset, STD140UniformBlockType[] std140UniformBlockTypes, Object[] values) {
        long dst = mappedAddress + baseByteOffset;

//        UNSAFE.putByte(dst,);
    }

    public long getUBOsSizeBytes(STD140UniformBlockType... std140UniformBlockTypes) {
        long bytes = 0L;
        for (STD140UniformBlockType std140UniformBlockType : std140UniformBlockTypes) {
            bytes += std140UniformBlockType.getByteSize();
        }

        return bytes;
    }

    public long getUBOsSizeBytes() {
        long bytes = 0L;
        for (UBOData uboData : uboDatum) {
            bytes += uboData.size;
        }

        return bytes;
    }

    @Override
    public void cleanup() {

    }

    @Override
    public int getBufferId() {
        return bufferId;
    }

    @Override
    public ByteBuffer getMappedBuffer() {
        return mappedBuffer;
    }

    @Override
    public long getMappedAddress() {
        return mappedAddress;
    }

    @Override
    public long getGPUMemorySize() {
        return gpuMemorySize;
    }

    public static class UBOData {
        public int bindingPoint;
        public long offset;
        public long size;

        public UBOData(int bindingPoint, long offset, long size) {
            this.bindingPoint = bindingPoint;
            this.offset = offset;
            this.size = size;
        }
    }
}
