package me.hannsi.lfjg.render.system.mesh;

import java.util.Arrays;
import java.util.Objects;

public class TestElementPair {
    public Vertex[] vertices;
    public int[] indices;

    public TestElementPair(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public boolean equalsLength(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TestElementPair that = (TestElementPair) o;
        return vertices.length == that.vertices.length && indices.length == that.indices.length;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestElementPair that = (TestElementPair) o;
        return Objects.deepEquals(vertices, that.vertices) && Objects.deepEquals(indices, that.indices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(vertices), Arrays.hashCode(indices));
    }

    @Override
    public String toString() {
        return "ElementPair{verticesLength: " + vertices.length + ", indicesLength: " + indices.length + "}";
    }
}
