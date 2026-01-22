package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.event.events.CleanupEvent;
import me.hannsi.lfjg.core.utils.Cleanup;

public class DrawElementsIndirectCommand implements Cleanup {
    public static final int COMMAND_COUNT = 5;
    public static final long BYTES = COMMAND_COUNT * Integer.BYTES;

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

    @Override
    public boolean cleanup(CleanupEvent event) {
        count = 0;
        instanceCount = 0;
        firstIndex = 0;
        baseVertex = 0;
        baseInstance = 0;

        return event.debug(this.getClass(), new CleanupEvent.CleanupData(this.getClass().getSimpleName())
                .addData("count", count == 0, count)
                .addData("instanceCount", instanceCount == 0, instanceCount)
                .addData("firstIndex", firstIndex == 0, firstIndex)
                .addData("baseVertex", baseVertex == 0, baseVertex)
                .addData("baseInstance", baseInstance == 0, baseInstance)
        );
    }
}
