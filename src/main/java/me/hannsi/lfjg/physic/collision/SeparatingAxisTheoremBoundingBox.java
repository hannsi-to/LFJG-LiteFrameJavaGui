package me.hannsi.lfjg.physic.collision;

import org.joml.Vector2f;

public class SeparatingAxisTheoremBoundingBox {
    private final Vector2f[] vertices;

    public SeparatingAxisTheoremBoundingBox(Vector2f... vertices) {
        if (vertices.length != 4) {
            throw new IllegalArgumentException("A bounding box must have exactly 4 vertices.");
        }
        this.vertices = vertices;
    }

    public boolean intersects(SeparatingAxisTheoremBoundingBox other) {
        Vector2f[] axes = getAxes();
        Vector2f[] otherAxes = other.getAxes();

        for (Vector2f axis : axes) {
            if (!overlapsOnAxis(axis, other)) {
                return false;
            }
        }

        for (Vector2f axis : otherAxes) {
            if (!overlapsOnAxis(axis, other)) {
                return false;
            }
        }
        return true;
    }

    private Vector2f[] getAxes() {
        Vector2f[] axes = new Vector2f[2];
        for (int i = 0; i < 2; i++) {
            Vector2f edge = new Vector2f(vertices[i + 1]).sub(vertices[i]);
            axes[i] = new Vector2f(-edge.y, edge.x).normalize();
        }
        return axes;
    }

    private boolean overlapsOnAxis(Vector2f axis, SeparatingAxisTheoremBoundingBox other) {
        float minA = Float.MAX_VALUE, maxA = -Float.MAX_VALUE;
        float minB = Float.MAX_VALUE, maxB = -Float.MAX_VALUE;

        for (Vector2f vertex : this.vertices) {
            float projection = vertex.dot(axis);
            minA = Math.min(minA, projection);
            maxA = Math.max(maxA, projection);
        }

        for (Vector2f vertex : other.vertices) {
            float projection = vertex.dot(axis);
            minB = Math.min(minB, projection);
            maxB = Math.max(maxB, projection);
        }

        return !(maxA < minB || maxB < minA);
    }
}
