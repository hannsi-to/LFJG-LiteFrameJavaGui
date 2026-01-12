package me.hannsi.lfjg.render.system.mesh.persistent;

public interface Test2PersistentMappedBuffer {
    void allocationBufferStorage(long capacity);

    Test2PersistentMappedBuffer syncToGPU();

    Test2PersistentMappedBuffer link();

    void ensure(long addSize);

    void debug(String text);

    void cleanup();

    int getFlags();

    int getBufferId();

    long getMappedAddress();

    long getMemorySize();

    boolean isNeedFlush();
}
