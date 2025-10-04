package me.hannsi.lfjg.render.system.mesh;

public class DrawElementsIndirectCommand {
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
}
