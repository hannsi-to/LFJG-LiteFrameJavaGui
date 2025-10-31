package me.hannsi.lfjg.render.system.mesh;

public class ObjectData {
    public int baseVertex;
    public int vertexCount;
    public int baseIndex;
    public int indexCount;
    public int baseCommand;
    public TestElementPair elementPair;

    public ObjectData(int baseVertex, int vertexCount, int baseIndex, int indexCount, int baseCommand, TestElementPair elementPair) {
        this.baseVertex = baseVertex;
        this.vertexCount = vertexCount;
        this.baseIndex = baseIndex;
        this.indexCount = indexCount;
        this.baseCommand = baseCommand;
        this.elementPair = elementPair;
    }
}
