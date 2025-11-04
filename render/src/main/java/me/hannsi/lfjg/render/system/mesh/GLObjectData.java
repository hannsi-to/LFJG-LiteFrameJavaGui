package me.hannsi.lfjg.render.system.mesh;

public class GLObjectData {
    public int baseVertex;
    public int vertexCount;
    public int baseIndex;
    public int indexCount;
    public int baseCommand;
    public TestElementPair elementPair;

    public GLObjectData(int baseVertex, int vertexCount, int baseIndex, int indexCount, int baseCommand, TestElementPair elementPair) {
        this.baseVertex = baseVertex;
        this.vertexCount = vertexCount;
        this.baseIndex = baseIndex;
        this.indexCount = indexCount;
        this.baseCommand = baseCommand;
        this.elementPair = elementPair;
    }

    @Override
    public String toString() {
        return "GLObjectData{baseVertex: " + baseVertex + ", vertexCount: " + vertexCount + ", baseIndex: " + baseIndex + ", indexCount: " + indexCount + ", baseCommand: " + baseCommand + ", " + elementPair.toString() + "}";
    }
}
