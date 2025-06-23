package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.utils.math.io.BufferHolder;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class ElementPair {
    public BufferHolder<FloatBuffer> positions;
    public BufferHolder<IntBuffer> indices;

    public ElementPair(BufferHolder<FloatBuffer> positions, BufferHolder<IntBuffer> indices) {
        this.positions = positions;
        this.indices = indices;
    }

    public void memFree() {
        positions.memFree();
        indices.memFree();
    }
}
