package me.hannsi.lfjg.render.system.mesh.triangulator;

import me.hannsi.lfjg.render.system.mesh.Vertex;

public class Triangle {
    public Vertex a;
    public Vertex b;
    public Vertex c;

    public Triangle(Vertex a, Vertex b, Vertex c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public boolean circumcircleContains(Vertex p) {
        float ax = a.x - p.x;
        float ay = a.y - p.y;
        float bx = b.x - p.x;
        float by = b.y - p.y;
        float cx = c.x - p.x;
        float cy = c.y - p.y;

        float det = ax * (by * (cx * cx + cy * cy) - cy * (bx * bx + by * by)) - ay * (bx * (cx * cx + cy * cy) - cx * (bx * bx + by * by)) + (ax * ax + ay * ay) * (bx * cy - by * cx);

        float orient = cross(a, b, c);
        return (orient > 0) ? det > 0 : det < 0;
    }

    private float cross(Vertex o, Vertex a, Vertex b) {
        return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
    }

    public boolean sharesVertex(Triangle t) {
        return a == t.a || a == t.b || a == t.c || b == t.a || b == t.b || b == t.c || c == t.a || c == t.b || c == t.c;
    }

    public Edge[] edges() {
        return new Edge[]{new Edge(a, b), new Edge(b, c), new Edge(c, a)};
    }

    @Override
    public String toString() {
        return "Triangle[" + a + ", " + b + ", " + c + "]";
    }
}
