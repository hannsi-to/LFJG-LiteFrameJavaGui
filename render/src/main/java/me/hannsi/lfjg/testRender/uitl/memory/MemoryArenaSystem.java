package me.hannsi.lfjg.testRender.uitl.memory;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.core.utils.math.MathHelper.max;

public class MemoryArenaSystem {
    public static final int GROW_BUFFER_FACTOR = 2;
    private static final int MAX_ARENAS = 8;
    private final MemoryTask memoryTask;
    private final List<MemoryArena> arenas = new ArrayList<>();
    private MemoryArena currentMemoryArena;
    private int arenaIndex = 0;
    private boolean used = false;

    public MemoryArenaSystem(MemoryTask memoryTask, long initialSizeBytes, int bufferId) {
        this.memoryTask = memoryTask;
        this.currentMemoryArena = MemoryArena.allocateMemory(memoryTask, initialSizeBytes, bufferId);
        arenas.add(currentMemoryArena);
    }

    public long alloc(long size, long alignment, int bufferId) {
        used = true;
        if (currentMemoryArena.remaining() < size) {
            if (arenaIndex + 1 < arenas.size()) {
                arenaIndex++;
                currentMemoryArena = arenas.get(arenaIndex);
                currentMemoryArena.reset();
            } else if (arenas.size() < MAX_ARENAS) {
                long newSize = max(size, currentMemoryArena.getMemorySizeBytes() * GROW_BUFFER_FACTOR);

                currentMemoryArena = MemoryArena.allocateMemory(memoryTask, newSize, bufferId);
                arenas.add(currentMemoryArena);
                arenaIndex++;
            } else {
                currentMemoryArena = arenas.getLast();
                currentMemoryArena.reset();
            }
        }

        return currentMemoryArena.alloc(size, alignment);
    }

    public MemoryArena getCurrentMemoryArena() {
        return currentMemoryArena;
    }

    public void reset() {
        used = false;
        arenaIndex = 0;
        currentMemoryArena = arenas.getFirst();
        for (MemoryArena arena : arenas) {
            arena.reset();
        }
    }

    public boolean isUsed() {
        return used;
    }
}
