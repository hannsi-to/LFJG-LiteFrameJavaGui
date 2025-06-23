package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.utils.math.io.BufferHolder;

import java.nio.FloatBuffer;

public class VertexKey {
    private final BufferHolder<FloatBuffer> data;
    private final int offset;
    private final int length;
    private final int hash;

    public VertexKey(BufferHolder<FloatBuffer> data, int offset, int length) {
        this.data = data;
        this.offset = offset;
        this.length = length;
        this.hash = computeHash();
    }

    private int computeHash() {
        int h = 1;
        for (int i = 0; i < length; i++) {
            h = 31 * h + Float.hashCode(data.getBuffer().get(offset + i));
        }
        return h;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VertexKey other)) {
            return false;
        }
        if (this.length != other.length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (Float.compare(this.data.getBuffer().get(offset + i), other.data.getBuffer().get(offset + i)) != 0) {
                return false;
            }
        }
        return true;
    }
}
