package me.hannsi.lfjg.render.system.mesh;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.IntBuffer;

@Getter
@AllArgsConstructor
public class DrawCommand {
    public static final int SIZE_BYTE = 5 * Integer.BYTES;

    private final int count;
    private final int instanceCount;
    private final int firstIndex;
    private final int baseVertex;
    private final int baseInstance;

    public void putIntoBuffer(IntBuffer buffer) {
        buffer.put(count);
        buffer.put(instanceCount);
        buffer.put(firstIndex);
        buffer.put(baseVertex);
        buffer.put(baseInstance);
    }
}
