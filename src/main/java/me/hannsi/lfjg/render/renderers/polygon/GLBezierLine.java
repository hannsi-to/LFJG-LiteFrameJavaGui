package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Vector2f;

public class GLBezierLine extends GLPolygon {
    public GLBezierLine(String name) {
        super(name);
    }

    public void bezierLine(BezierPoint[] bezierPoints, float lineWidth, int segments) {
        if (bezierPoints.length < 2) return;

        BezierPoint prev = computeBezierPoint(0f, bezierPoints);
        for (int i = 1; i <= segments; i++) {
            float t = (float) i / segments;
            BezierPoint current = computeBezierPoint(t, bezierPoints);

            put().vertex(prev.pos).color(prev.color).end();
            put().vertex(current.pos).color(current.color).end();

            prev = current;
        }

        setDrawType(DrawType.LINES).setLineWidth(lineWidth);
        rendering();
    }

    private BezierPoint computeBezierPoint(float t, BezierPoint[] points) {
        if (points.length == 1) {
            return points[0];
        }

        BezierPoint[] nextPoints = new BezierPoint[points.length - 1];
        for (int i = 0; i < points.length - 1; i++) {
            Vector2f pos = lerp(points[i].pos, points[i + 1].pos, t);
            Color color = lerpColor(points[i].color, points[i + 1].color, t);
            nextPoints[i] = new BezierPoint(pos, color);
        }

        return computeBezierPoint(t, nextPoints);
    }

    private Vector2f lerp(Vector2f a, Vector2f b, float t) {
        float x = a.x + t * (b.x - a.x);
        float y = a.y + t * (b.y - a.y);
        return new Vector2f(x, y);
    }

    private Color lerpColor(Color color1, Color color2, float t) {
        int r = (int) (color1.getRed() + t * (color2.getRed() - color1.getRed()));
        int g = (int) (color1.getGreen() + t * (color2.getGreen() - color1.getGreen()));
        int b = (int) (color1.getBlue() + t * (color2.getBlue() - color1.getBlue()));
        int a = (int) (color1.getAlpha() + t * (color2.getAlpha() - color1.getAlpha()));
        return new Color(r, g, b, a);
    }

    public static class BezierPoint {
        public Vector2f pos;
        public Color color;

        public BezierPoint(Vector2f pos, Color color) {
            this.pos = pos;
            this.color = color;
        }
    }
}
