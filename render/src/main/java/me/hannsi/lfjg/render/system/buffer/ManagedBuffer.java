package me.hannsi.lfjg.render.system.buffer;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.render.system.memory.Allocation;
import me.hannsi.lfjg.render.system.memory.allocator.Allocator;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.core.Core.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL11.glFlush;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL45.glCopyNamedBufferSubData;

public class ManagedBuffer {
    private final List<BufferSystem> pendingDeletion = new ArrayList<>();
    private final BufferSystem.Builder builderCache;
    private final int target;
    private final int alignment;
    private BufferSystem activeBufferSystem;
    private long migrationFence = 0L;
    private BufferSystem nextBufferSystem = null;
    private boolean isMigrating;

    public ManagedBuffer(BufferSystem.Builder builderCache, int target, int alignment) {
        this.activeBufferSystem = new BufferSystem(builderCache, glGenBuffers(), target, alignment);
        this.builderCache = builderCache;
        this.isMigrating = false;
        this.alignment = alignment;
        this.target = target;

        newSystemCall(activeBufferSystem);
    }

    public void requestAsyncResize() {
        if (isMigrating) {
            return;
        }

        isMigrating = true;
        long transferId = Core.LFJGFrameContext.getTransferId();
        if (transferId != -1L) {
            new Thread(() -> {
                glfwMakeContextCurrent(transferId);
                GL.createCapabilities();

                requestResize();

                glFlush();

                glfwMakeContextCurrent(0);
            }, "TransferThread").start();
        } else {
            requestResize();
        }
    }

    private void requestResize() {
        for (BufferSystem.BufferWriteConfig bufferWriteConfig : builderCache.bufferWriteConfigs.values()) {
            for (Allocator allocator : bufferWriteConfig.allocatorSystem.getAllocators()) {
                allocator.setSize(allocator.getMemorySize() * 2);
            }
        }

        nextBufferSystem = new BufferSystem(builderCache, glGenBuffers(), activeBufferSystem.getTarget(), activeBufferSystem.getAlignment());

        switch (builderCache.bufferProperty) {
            case BIND_BUFFER -> {
                glCopyBufferSubData(
                        activeBufferSystem.getBufferId(),
                        nextBufferSystem.getBufferId(),
                        0,
                        0,
                        activeBufferSystem.getGpuHeap().getMemorySize()
                );
            }
            case DIRECT_STATE_ACCESS, NV_UNIFIED_BINDLESS -> {
                glCopyNamedBufferSubData(
                        activeBufferSystem.getBufferId(),
                        nextBufferSystem.getBufferId(),
                        0,
                        0,
                        activeBufferSystem.getGpuHeap().getMemorySize()
                );
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + builderCache.bufferProperty);
        }

        migrationFence = glFenceSync(GL_SYNC_GPU_COMMANDS_COMPLETE, 0);
    }

    public void startFrame() {
        if (isMigrating && migrationFence != 0L) {
            int result = glClientWaitSync(migrationFence, 0, 0);

            if (result == GL_ALREADY_SIGNALED || result == GL_CONDITION_SATISFIED) {
                completeMigration();
            }
        }

        activeBufferSystem.startFrame();
    }

    public void endFrame() {
        activeBufferSystem.endFrame();

        if (!pendingDeletion.isEmpty() && !isMigrating) {
            for (BufferSystem oldSystem : pendingDeletion) {
                oldSystem.destroy();
            }
            pendingDeletion.clear();
        }
    }

    public void alloc(int writeBufferConfigPointer, long size, int alignment) {
        activeBufferSystem.alloc(writeBufferConfigPointer, size, alignment);

        if (isMigrating && nextBufferSystem != null) {
            nextBufferSystem.alloc(writeBufferConfigPointer, size, alignment);
        }
    }

    public void update(int writeBufferConfigPointer, long offset, long memorySize, int alignment) {
        activeBufferSystem.update(writeBufferConfigPointer, offset, memorySize, alignment);

        if (isMigrating && nextBufferSystem != null) {
            nextBufferSystem.update(writeBufferConfigPointer, offset, memorySize, alignment);
        }
    }

    public void free(int writeBufferConfigPointer, Allocation allocation) {
        activeBufferSystem.free(writeBufferConfigPointer, allocation);

        if (isMigrating && nextBufferSystem != null) {
            nextBufferSystem.free(writeBufferConfigPointer, allocation);
        }
    }

    public void reset(int writeBufferConfigPointer) {
        activeBufferSystem.reset(writeBufferConfigPointer);

        if (isMigrating && nextBufferSystem != null) {
            nextBufferSystem.reset(writeBufferConfigPointer);
        }
    }

    public void newSystemCall(BufferSystem activeBufferSystem) {

    }

    private void completeMigration() {
        pendingDeletion.add(activeBufferSystem);

        this.activeBufferSystem = nextBufferSystem;

        glDeleteSync(migrationFence);
        migrationFence = 0L;
        nextBufferSystem = null;
        isMigrating = false;

        newSystemCall(activeBufferSystem);
    }

    public int getTarget() {
        return target;
    }

    public int getAlignment() {
        return alignment;
    }

    public BufferSystem getActiveBufferSystem() {
        return activeBufferSystem;
    }

    public BufferSystem getNextBufferSystem() {
        return nextBufferSystem;
    }

    public boolean isMigrating() {
        return isMigrating;
    }
}
