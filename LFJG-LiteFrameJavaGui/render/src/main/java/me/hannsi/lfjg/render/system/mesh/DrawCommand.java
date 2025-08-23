package me.hannsi.lfjg.render.system.mesh;

import java.nio.IntBuffer;
import java.util.Objects;

public final class DrawCommand {
    public static final int SIZE_BYTE = 5 * Integer.BYTES;
    private final int count;
    private final int instanceCount;
    private final int firstIndex;
    private final int baseVertex;
    private final int baseInstance;

    public DrawCommand(int count, int instanceCount, int firstIndex, int baseVertex, int baseInstance) {
        this.count = count;
        this.instanceCount = instanceCount;
        this.firstIndex = firstIndex;
        this.baseVertex = baseVertex;
        this.baseInstance = baseInstance;
    }

    public void putIntoBuffer(IntBuffer buffer) {
        buffer.put(count);
        buffer.put(instanceCount);
        buffer.put(firstIndex);
        buffer.put(baseVertex);
        buffer.put(baseInstance);
    }

    public int count() {
        return count;
    }

    public int instanceCount() {
        return instanceCount;
    }

    public int firstIndex() {
        return firstIndex;
    }

    public int baseVertex() {
        return baseVertex;
    }

    public int baseInstance() {
        return baseInstance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        DrawCommand that = (DrawCommand) obj;
        return this.count == that.count &&
                this.instanceCount == that.instanceCount &&
                this.firstIndex == that.firstIndex &&
                this.baseVertex == that.baseVertex &&
                this.baseInstance == that.baseInstance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, instanceCount, firstIndex, baseVertex, baseInstance);
    }

    @Override
    public String toString() {
        return "DrawCommand[" +
                "count=" + count + ", " +
                "instanceCount=" + instanceCount + ", " +
                "firstIndex=" + firstIndex + ", " +
                "baseVertex=" + baseVertex + ", " +
                "baseInstance=" + baseInstance + ']';
    }

}
