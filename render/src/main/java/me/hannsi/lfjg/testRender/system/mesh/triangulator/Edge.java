package me.hannsi.lfjg.testRender.system.mesh.triangulator;

import me.hannsi.lfjg.render.render.Vertex;

import java.util.Objects;

public class Edge {
    public Vertex a;
    public Vertex b;

    public Edge(Vertex a, Vertex b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Edge edge)) {
            return false;
        }

        return (a == edge.a && b == edge.b) || (a == edge.b && b == edge.a);
    }

    @Override
    public int hashCode() {
        return Objects.hash(System.identityHashCode(a) + System.identityHashCode(b));
    }
}
