package me.hannsi.lfjg.render.system.mesh.triangulator;

import me.hannsi.lfjg.render.system.mesh.Vertex;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static me.hannsi.lfjg.core.utils.math.MathHelper.min;

public class SelfIntersectionChecker {
    public static List<int[]> detect(List<Vertex> polygon) {
        List<int[]> intersections = new ArrayList<>();
        int n = polygon.size();

        for (int i = 0; i < n; i++) {
            Vertex a = polygon.get(i);
            Vertex b = polygon.get((i + 1) % n);

            for (int j = i + 2; j < n; j++) {
                if (i == 0 && j == n - 1) {
                    continue;
                }

                Vertex c = polygon.get(j);
                Vertex d = polygon.get((j + 1) % n);

                if (segmentsIntersect(a, b, c, d)) {
                    intersections.add(new int[]{i, j});
                }
            }
        }
        return intersections;
    }

    private static boolean segmentsIntersect(Vertex a, Vertex b, Vertex c, Vertex d) {
        double d1 = cross(c, d, a);
        double d2 = cross(c, d, b);
        double d3 = cross(a, b, c);
        double d4 = cross(a, b, d);

        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) &&
                ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))) {
            return true;
        }

        if (d1 == 0 && onSegment(c, d, a)) {
            return true;
        }
        if (d2 == 0 && onSegment(c, d, b)) {
            return true;
        }
        if (d3 == 0 && onSegment(a, b, c)) {
            return true;
        }
        return d4 == 0 && onSegment(a, b, d);
    }

    private static double cross(Vertex o, Vertex a, Vertex b) {
        return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
    }

    private static boolean onSegment(Vertex p, Vertex q, Vertex r) {
        return min(p.x, q.x) <= r.x && r.x <= max(p.x, q.x) && min(p.y, q.y) <= r.y && r.y <= max(p.y, q.y);
    }
}
