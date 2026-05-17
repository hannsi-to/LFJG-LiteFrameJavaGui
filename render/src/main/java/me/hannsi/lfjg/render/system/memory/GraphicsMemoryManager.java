package me.hannsi.lfjg.render.system.memory;

import me.hannsi.lfjg.render.system.buffer.BufferManager;

public class GraphicsMemoryManager {
    private final BufferManager.BufferPool[] bufferPools;

    public GraphicsMemoryManager(BufferManager.BufferPool[] bufferPools) {
        this.bufferPools = bufferPools;
    }


}
