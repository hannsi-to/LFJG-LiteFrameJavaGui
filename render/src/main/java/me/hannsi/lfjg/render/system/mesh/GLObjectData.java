package me.hannsi.lfjg.render.system.mesh;

public class GLObjectData {
    public boolean draw;
    public DrawElementsIndirectCommand drawElementsIndirectCommand;
    public TestMesh.Builder builder;
    public int baseCommand;
    public TestElementPair elementPair;

    public GLObjectData(DrawElementsIndirectCommand drawElementsIndirectCommand, TestMesh.Builder builder, int baseCommand, TestElementPair elementPair) {
        this.draw = true;
        this.drawElementsIndirectCommand = drawElementsIndirectCommand;
        this.builder = builder;
        this.baseCommand = baseCommand;
        this.elementPair = elementPair;
    }

    @Override
    public String toString() {
        return "GLObjectData{drawElementIndirectCommand: " + drawElementsIndirectCommand + ", baseCommand: " + baseCommand + ", " + elementPair.toString() + "}";
    }
}
