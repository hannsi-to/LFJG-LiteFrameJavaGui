package me.hannsi.lfjg.render.system.mesh.persistent;

import me.hannsi.lfjg.render.system.rendering.GLStateCache;

public class TestPersistentMappedIBO implements PersistentMappedBuffer {
    private final int DRAW_COMMAND_SIZE = 5;
    private final int flags;
    private int bufferId;
    private int maxDrawCommands;

    public TestPersistentMappedIBO(int flags, int initialDrawCommands) {
        this.flags = flags;
        updateBufferStorage(initialDrawCommands);
    }

    private void updateBufferStorage(int maxDrawCommands) {
        this.maxDrawCommands = maxDrawCommands;

        int sizeInBytes = maxDrawCommands * DRAW_COMMAND_SIZE * Integer.BYTES;
        if (bufferId != 0) {
            GLStateCache.deleteIndirectBuffer(bufferId);
        }
    }

    @Override
    public void cleanup() {

    }
}
