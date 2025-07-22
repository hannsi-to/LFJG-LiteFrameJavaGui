package me.hannsi.lfjg.render.system.mesh;

import java.nio.IntBuffer;

public record DrawCommand(int count, int instanceCount, int firstIndex, int baseVertex, int baseInstance) {
    public static final int SIZE_BYTE = 5 * Integer.BYTES;

    public void putIntoBuffer(IntBuffer buffer) {
        buffer.put(count);
        buffer.put(instanceCount);
        buffer.put(firstIndex);
        buffer.put(baseVertex);
        buffer.put(baseInstance);
    }
}
