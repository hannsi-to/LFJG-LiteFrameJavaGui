package me.hannsi.lfjg.testRender.uitl.memory;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.core.Core.NULL_PTR;
import static org.lwjgl.opengl.ARBShaderImageLoadStore.glMemoryBarrier;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL44.GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT;

public class MemoryFrameArena {
    public static final int FRAME_COUNT = 3;

    private final MemoryTask memoryTask;
    private final List<MemoryFrame> frames = new ArrayList<>();
    private int writeIndex = 0;

    public MemoryFrameArena(MemoryTask memoryTask, long initialSizeBytes) {
        for (int i = 0; i < FRAME_COUNT; i++) {
            int bufferId = glGenBuffers();
            MemoryArenaSystem system = new MemoryArenaSystem(memoryTask, initialSizeBytes, bufferId);
            frames.add(new MemoryFrame(system, bufferId));
        }

        this.memoryTask = memoryTask;
        frames.getFirst().state = MemoryFrameState.CPU_WRITING;
    }

    public MemoryFrame currentMemoryFrame() {
        return frames.get(writeIndex);
    }

    public MemoryArenaSystem currentMemoryArenaSystem() {
        return currentMemoryFrame().arenaSystem;
    }

    public long alloc(long size, long alignment) {
        return currentMemoryArenaSystem().alloc(size, alignment, frames.get(writeIndex).bufferId);
    }

    private void waitForFence(MemoryFrame frame) {
        if (frame.fence == NULL_PTR) {
            return;
        }

        int result = glClientWaitSync(
                frame.fence,
                GL_SYNC_FLUSH_COMMANDS_BIT,
                1_000_000_000L
        );

        if (result == GL_TIMEOUT_EXPIRED || result == GL_WAIT_FAILED) {
            glWaitSync(frame.fence, 0, GL_TIMEOUT_IGNORED);
        }

        glDeleteSync(frame.fence);
        frame.fence = NULL_PTR;
        frame.state = MemoryFrameState.IDLE;
    }

    public void link() {
        memoryTask.bindBuffer(currentMemoryFrame().bufferId);
    }

    public void nextFrame() {
        MemoryFrame current = frames.get(writeIndex);
        if (current.state == MemoryFrameState.CPU_WRITING && current.arenaSystem.isUsed()) {
            current.fence = glFenceSync(GL_SYNC_GPU_COMMANDS_COMPLETE, 0);
            current.state = MemoryFrameState.GPU_IN_FLIGHT;
        }

        writeIndex = (writeIndex + 1) % FRAME_COUNT;
        MemoryFrame next = frames.get(writeIndex);

        if (next.state == MemoryFrameState.GPU_IN_FLIGHT) {
            waitForFence(next);
        }

        next.state = MemoryFrameState.CPU_WRITING;
        next.arenaSystem.reset();

        memoryTask.bindBuffer(next.bufferId);
    }

    public void signalGPU() {
        MemoryFrame frame = frames.get(writeIndex);
        glMemoryBarrier(GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT);
        if (frame.fence != NULL_PTR) {
            glDeleteSync(frame.fence);
        }
        frame.fence = glFenceSync(GL_SYNC_GPU_COMMANDS_COMPLETE, 0);
        frame.state = MemoryFrameState.GPU_IN_FLIGHT;
    }
}
