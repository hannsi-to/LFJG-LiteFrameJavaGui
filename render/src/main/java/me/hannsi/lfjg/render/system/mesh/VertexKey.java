package me.hannsi.lfjg.render.system.mesh;

public class VertexKey {
    private final float[] data;
    private final int offset;
    private final int length;
    private final int hash;

    public VertexKey(float[] data, int offset, int length) {
        this.data = data;
        this.offset = offset;
        this.length = length;
        this.hash = computeHash();
    }

    private int computeHash() {
        int h = 1;
        for (int i = 0; i < length; i++) {
            h = 31 * h + Float.hashCode(data[offset + i]);
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
        if (!(o instanceof VertexKey)) {
            return false;
        }
        VertexKey other = (VertexKey) o;
        if (this.length != other.length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (Float.compare(this.data[offset + i], other.data[other.offset + i]) != 0) {
                return false;
            }
        }
        return true;
    }
}
