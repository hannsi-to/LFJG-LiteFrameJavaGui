package me.hannsi.lfjg.render.system.mesh;

public class GLObjectData {
    public boolean draw;
    public DrawElementsIndirectCommand drawElementsIndirectCommand;
    public MeshBuilder meshBuilder;
    public int baseCommand;
    public TestElementPair elementPair;

    public GLObjectData(DrawElementsIndirectCommand drawElementsIndirectCommand, MeshBuilder meshBuilder, int baseCommand, TestElementPair elementPair) {
        this.draw = true;
        this.drawElementsIndirectCommand = drawElementsIndirectCommand;
        this.meshBuilder = meshBuilder;
        this.baseCommand = baseCommand;
        this.elementPair = elementPair;
    }

    @Override
    public String toString() {
        return "GLObjectData{drawElementIndirectCommand: " + drawElementsIndirectCommand + ", baseCommand: " + baseCommand + ", " + elementPair.toString() + "}";
    }
}
