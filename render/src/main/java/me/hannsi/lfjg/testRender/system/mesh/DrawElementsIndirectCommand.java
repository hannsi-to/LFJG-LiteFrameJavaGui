package me.hannsi.lfjg.testRender.system.mesh;

import me.hannsi.lfjg.core.event.events.CleanupEvent;
import me.hannsi.lfjg.core.utils.Cleanup;
import me.hannsi.lfjg.testRender.uitl.memory.MemoryFrameArena;
import me.hannsi.lfjg.testRender.uitl.memory.ObjectMemory;

import static me.hannsi.lfjg.core.Core.UNSAFE;

public class DrawElementsIndirectCommand implements ObjectMemory, Cleanup {
    public static final int COMMAND_COUNT = 5;
    public static final int BYTES = COMMAND_COUNT * Integer.BYTES;

    public int count;
    public int instanceCount;
    public int firstIndex;
    public int baseVertex;
    public int baseInstance;

    public DrawElementsIndirectCommand(int count, int instanceCount, int firstIndex, int baseVertex, int baseInstance) {
        this.count = count;
        this.instanceCount = instanceCount;
        this.firstIndex = firstIndex;
        this.baseVertex = baseVertex;
        this.baseInstance = baseInstance;
    }

    private DrawElementsIndirectCommand i(MemoryFrameArena arena, int value) {
        long address = arena.alloc(Integer.BYTES, Integer.BYTES);
        UNSAFE.putInt(address, value);
        return this;
    }

    @Override
    public void putMemory(MemoryFrameArena arena) {
        i(arena, count)
                .i(arena, instanceCount)
                .i(arena, firstIndex)
                .i(arena, baseVertex)
                .i(arena, baseInstance);
    }

    @Override
    public int getBytes() {
        return BYTES;
    }

    @Override
    public boolean cleanup(CleanupEvent event) {
        count = 0;
        instanceCount = 0;
        firstIndex = 0;
        baseVertex = 0;
        baseInstance = 0;

        return event.debug(this.getClass(), new CleanupEvent.CleanupData(this.getClass())
                .addData("count", count == 0, count)
                .addData("instanceCount", instanceCount == 0, instanceCount)
                .addData("firstIndex", firstIndex == 0, firstIndex)
                .addData("baseVertex", baseVertex == 0, baseVertex)
                .addData("baseInstance", baseInstance == 0, baseInstance)
        );
    }
}
