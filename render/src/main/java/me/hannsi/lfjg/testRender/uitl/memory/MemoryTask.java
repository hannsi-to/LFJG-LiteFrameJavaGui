package me.hannsi.lfjg.testRender.uitl.memory;

public interface MemoryTask {
    long allocateMemory(long memorySizeBytes, int bufferId);

    void bindBuffer(int bufferId);
}