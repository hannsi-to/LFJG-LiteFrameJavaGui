package me.hannsi.lfjg.render.system.buffer.l1;

public class VertexLayout {
    public int[] attributeIndices;
    public int[] attributeSizes;
    public int[] attributeBindings;
    public int[] relativeOffsets;

    public long[] streamOffsets;
    public int[] streamStrides;

    public VertexLayout(int[] attributeIndices, int[] attributeSizes, int[] attributeBindings, int[] relativeOffsets, long[] streamOffsets, int[] streamStrides) {
        this.attributeIndices = attributeIndices;
        this.attributeSizes = attributeSizes;
        this.attributeBindings = attributeBindings;
        this.relativeOffsets = relativeOffsets;
        this.streamOffsets = streamOffsets;
        this.streamStrides = streamStrides;
    }
}
