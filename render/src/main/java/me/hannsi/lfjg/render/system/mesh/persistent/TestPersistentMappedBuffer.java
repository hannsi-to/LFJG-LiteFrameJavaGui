package me.hannsi.lfjg.render.system.mesh.persistent;

public interface TestPersistentMappedBuffer {
    void allocationBufferStorage(long capacity);

    TestPersistentMappedBuffer syncToGPU();

    void flushMappedRange(long byteOffset, long byteLength);

    void growBuffer(long newGPUMemorySizeBytes);

    void cleanup();

    int getBufferId();

    long getMappedAddress();

    long getGPUMemorySize();
}
