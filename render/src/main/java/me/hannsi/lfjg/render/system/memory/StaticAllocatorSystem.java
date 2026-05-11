package me.hannsi.lfjg.render.system.memory;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.render.system.memory.allocator.Allocator;

public class StaticAllocatorSystem extends AllocatorSystem {
    public StaticAllocatorSystem(Allocator allocator) {
        super(allocator.createLike());
    }

    @Override
    public void alloc(long size, int alignment) {
        allocators[0].alloc(size, alignment);
    }

    @Override
    public void startFrame() {

    }

    @Override
    public void endFrame() {

    }

    @Override
    public void update(long offset, long memorySize, int alignment) {
        DebugLog.warning(getClass(), "It is not recommended to modify the reserved content in " + this.getClass().getSimpleName() + ".");

        allocators[0].update(offset, memorySize, alignment);
    }

    @Override
    public void free(Allocation allocation) {
        throw new AllocatorSystemException(this.getClass().getSimpleName() + " does not allow the release of reserved portions.");
    }

    @Override
    public void reset() {
        allocators[0].reset();
    }
}
