package me.hannsi.lfjg.render.system.mesh.persistent;

import java.nio.ByteBuffer;

public interface TestPersistentMappedBuffer {
    void allocationBufferStorage(long capacity);

    TestPersistentMappedBuffer syncToGPU();

    void flushMappedRange(long byteOffset, long byteLength);

    void growBuffer(long newGPUMemorySizeBytes);

    void cleanup();

    int getBufferId();

    ByteBuffer getMappedBuffer();

    long getMappedAddress();

    long getGPUMemorySize();
}
