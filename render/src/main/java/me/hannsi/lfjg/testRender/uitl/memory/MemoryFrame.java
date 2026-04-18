package me.hannsi.lfjg.testRender.uitl.memory;

import static me.hannsi.lfjg.core.Core.NULL_PTR;

public class MemoryFrame {
    public int bufferId;
    public MemoryArenaSystem arenaSystem;
    public long fence = NULL_PTR;
    public MemoryFrameState state = MemoryFrameState.IDLE;

    public MemoryFrame(MemoryArenaSystem arenaSystem, int bufferId) {
        this.bufferId = bufferId;
        this.arenaSystem = arenaSystem;
    }
}
