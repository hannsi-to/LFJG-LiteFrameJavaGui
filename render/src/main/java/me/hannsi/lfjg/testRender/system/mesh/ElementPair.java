package me.hannsi.lfjg.testRender.system.mesh;

public class ElementPair {
    public float[] positions;
    public int[] indices;

    public ElementPair(float[] positions, int[] indices) {
        this.positions = positions.clone();
        this.indices = indices.clone();
    }
}
