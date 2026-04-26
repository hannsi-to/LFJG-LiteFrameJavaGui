package me.hannsi.lfjg.testRender.system.mesh.triangulator;

import me.hannsi.lfjg.render.render.Vertex;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static me.hannsi.lfjg.core.utils.math.MathHelper.min;

public class Triangulator {
    private final List<Vertex> polygon;
    private final List<List<Vertex>> holes;

    public Triangulator(List<Vertex> polygon) {
        this.polygon = polygon;
        this.holes = new ArrayList<>();
    }

    public void addHole(List<Vertex> hole) {
        holes.add(hole);
    }

    public List<Triangle> triangulate() {
        if (!SelfIntersectionChecker.detect(polygon).isEmpty()) {
//            throw new PolygonTriangulatorException("Self intersection");
        }

        List<Vertex> points = new ArrayList<>(polygon);
        for (List<Vertex> hole : holes) {
            points.addAll(hole);
        }

        List<Triangle> tris = bowyerWatson(points);

        List<Edge> constraints = buildConstraints();
        for (Edge e : constraints) {
            insertConstraint(tris, e);
        }

        tris = filterTriangles(tris);

        return tris;
    }

    public List<Triangle> bowyerWatson(List<Vertex> points) {
        List<Triangle> triangulation = new ArrayList<>();

        Triangle supper_ = makeSuperTriangle(points);
        triangulation.add(supper_);

        for (Vertex p : points) {
            List<Triangle> bad = new ArrayList<>();
            for (Triangle t : triangulation) {
                if (t.circumcircleContains(p)) {
                    bad.add(t);
                }
            }

            List<Edge> boundary = findBoundary(bad);

            triangulation.removeAll(bad);

            for (Edge e : boundary) {
                triangulation.add(new Triangle(e.a, e.b, p));
            }
        }

        triangulation.removeIf(t -> t.sharesVertex(supper_));
        return triangulation;
    }

    private Triangle makeSuperTriangle(List<Vertex> points) {
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = -Float.MAX_VALUE;
        float maxY = -Float.MAX_VALUE;

        for (Vertex point : points) {
            minX = min(minX, point.x);
            minY = min(minY, point.y);
            maxX = max(maxX, point.x);
            maxY = max(maxY, point.y);
        }

        float dx = maxX - minX;
        float dy = maxY - minY;
        float delta = max(dx, dy) * 10;

        Vertex s1 = new Vertex(minX - delta, minY - delta);
        Vertex s2 = new Vertex(minX + 2 * delta, minY - delta);
        Vertex s3 = new Vertex(minX - delta, minY + 2 * delta);

        return new Triangle(s1, s2, s3);
    }

    private List<Edge> findBoundary(List<Triangle> badTriangles) {
        Map<Edge, Integer> edgeCount = new LinkedHashMap<>();
        for (Triangle t : badTriangles) {
            for (Edge e : t.edges()) {
                edgeCount.merge(e, 1, Integer::sum);
            }
        }
        List<Edge> boundary = new ArrayList<>();
        for (Map.Entry<Edge, Integer> entry : edgeCount.entrySet()) {
            if (entry.getValue() == 1) {
                boundary.add(entry.getKey());
            }
        }
        return boundary;
    }

    private List<Edge> buildConstraints() {
        List<Edge> edges = new ArrayList<>();
        addEdgesFromLoop(edges, polygon);
        for (List<Vertex> hole : holes) {
            addEdgesFromLoop(edges, hole);
        }
        return edges;
    }

    private void addEdgesFromLoop(List<Edge> edges, List<Vertex> loop) {
        for (int i = 0; i < loop.size(); i++) {
            edges.add(new Edge(loop.get(i), loop.get((i + 1) % loop.size())));
        }
    }

    private void insertConstraint(List<Triangle> tris, Edge constraint) {
        if (edgeExists(tris, constraint)) {
            return;
        }

        int maxAttempts = tris.size() * tris.size();
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            if (edgeExists(tris, constraint)) {
                return;
            }
            if (!flipTowardConstraint(tris, constraint)) {
                break;
            }
        }
    }

    private boolean edgeExists(List<Triangle> tris, Edge e) {
        for (Triangle t : tris) {
            for (Edge te : t.edges()) {
                if (te.equals(e)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean flipTowardConstraint(List<Triangle> tris, Edge constraint) {
        for (int i = 0; i < tris.size(); i++) {
            Triangle t1 = tris.get(i);
            for (Edge e : t1.edges()) {
                if (segmentsIntersect(e, constraint)) {
                    for (int j = i + 1; j < tris.size(); j++) {
                        Triangle t2 = tris.get(j);
                        if (sharesEdge(t1, t2, e)) {
                            Vertex p1 = oppositeVertex(t1, e);
                            Vertex p2 = oppositeVertex(t2, e);
                            if (p1 != null && p2 != null) {
                                tris.remove(j);
                                tris.remove(i);
                                tris.add(new Triangle(e.a, p1, p2));
                                tris.add(new Triangle(e.b, p1, p2));
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private List<Triangle> filterTriangles(List<Triangle> tris) {
        List<Triangle> result = new ArrayList<>();
        for (Triangle t : tris) {
            Vector2f centroid = centroid(t);
            if (isInsidePolygon(centroid, polygon) && !isInsideAnyHole(centroid)) {
                result.add(t);
            }
        }
        return result;
    }

    private Vector2f centroid(Triangle t) {
        return new Vector2f((t.a.x + t.b.x + t.c.x) / 3.0f, (t.a.y + t.b.y + t.c.y) / 3.0f);
    }

    private boolean isInsidePolygon(Vector2f p, List<Vertex> poly) {
        int n = poly.size();
        boolean inside = false;
        for (int i = 0, j = n - 1; i < n; j = i++) {
            Vertex pi = poly.get(i);
            Vertex pj = poly.get(j);
            if ((pi.y > p.y) != (pj.y > p.y) && p.x < (pj.x - pi.x) * (p.y - pi.y) / (pj.y - pi.y) + pi.x) {
                inside = !inside;
            }
        }
        return inside;
    }

    private boolean isInsideAnyHole(Vector2f p) {
        for (List<Vertex> hole : holes) {
            if (isInsidePolygon(p, hole)) {
                return true;
            }
        }
        return false;
    }

    private boolean segmentsIntersect(Edge e1, Edge e2) {
        Vertex p = e1.a;
        Vertex q = e1.b;
        Vertex r = e2.a;
        Vertex s = e2.b;
        if (p == r || p == s || q == r || q == s) {
            return false;
        }
        double d1 = cross(r, s, p);
        double d2 = cross(r, s, q);
        double d3 = cross(p, q, r);
        double d4 = cross(p, q, s);
        return ((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) && ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0));
    }

    private double cross(Vertex o, Vertex a, Vertex b) {
        return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
    }

    private boolean sharesEdge(Triangle t1, Triangle t2, Edge e) {
        boolean t1has = false;
        boolean t2has = false;
        for (Edge te : t1.edges()) {
            if (te.equals(e)) {
                t1has = true;
                break;
            }
        }
        for (Edge te : t2.edges()) {
            if (te.equals(e)) {
                t2has = true;
                break;
            }
        }
        return t1has && t2has;
    }

    private Vertex oppositeVertex(Triangle t, Edge e) {
        if (t.a != e.a && t.a != e.b) {
            return t.a;
        }
        if (t.b != e.a && t.b != e.b) {
            return t.b;
        }
        if (t.c != e.a && t.c != e.b) {
            return t.c;
        }
        return null;
    }
}
