package me.hannsi.lfjg.render.system;

public class MeshObject {
    public float[] vertices;
    public int vertexCount;
    public int stride;

    public MeshObject(float[] vertices, int vertexCount, int stride) {
        this.vertices = vertices;
        this.vertexCount = vertexCount;
        this.stride = stride;
    }
}
