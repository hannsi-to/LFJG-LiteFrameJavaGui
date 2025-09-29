package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GLBezierLine extends GLPolygon<GLBezierLine> {
    public static final int DEFAULT_SEGMENT = 16;

    private final Builder builder;

    public GLBezierLine(String name, Builder builder) {
        super(name);
        this.builder = builder;
    }

    public static BezierPointStep createGLBezierLine(String name) {
        return new Builder(name);
    }

    public GLBezierLine update() {
        if (builder.bezierPoints.size() < 2) {
            throw new IllegalArgumentException("There are too few control points. current: " + builder.bezierPoints.size() + " | need: 3");
        }

        BezierPoint[] bezierPoints = new BezierPoint[builder.bezierPoints.size()];
        for (int i = 0; i < builder.bezierPoints.size(); i++) {
            bezierPoints[i] = builder.bezierPoints.get(i);
        }

        BezierPoint prev = computeBezierPoint(0f, bezierPoints);
        for (int i = 1; i <= builder.segment; i++) {
            float t = (float) i / builder.segment;
            BezierPoint current = computeBezierPoint(t, bezierPoints);

            put().vertex(prev.pos).color(prev.color).end();
            put().vertex(current.pos).color(current.color).end();

            prev = current;
        }

        setDrawType(DrawType.LINES).setLineWidth(builder.lineWidth);
        rendering();

        return this;
    }

    private BezierPoint computeBezierPoint(float t, BezierPoint[] points) {
        if (points.length == 1) {
            return points[0];
        }

        BezierPoint[] nextPoints = new BezierPoint[points.length - 1];
        for (int i = 0; i < points.length - 1; i++) {
            Vector2f pos = MathHelper.lerpVertex2f(points[i].pos, points[i + 1].pos, t);
            Color color = MathHelper.lerpColor(points[i].color, points[i + 1].color, t);
            nextPoints[i] = new BezierPoint(pos, color);
        }

        return computeBezierPoint(t, nextPoints);
    }

    public interface BezierPointStep {
        BezierPointStep addControlPoint(BezierPoint bezierPoint);

        SegmentStep end();
    }

    public interface SegmentStep {
        LineWidthStep segment();

        LineWidthStep segment(int segment);
    }

    public interface LineWidthStep {
        GLBezierLine lineWidth(float lineWidth);
    }

    public static class BezierPoint {
        public Vector2f pos;
        public Color color;

        public BezierPoint(Vector2f pos, Color color) {
            this.pos = pos;
            this.color = color;
        }
    }

    public static class Builder implements BezierPointStep, SegmentStep, LineWidthStep {
        protected final String name;
        protected List<BezierPoint> bezierPoints;
        protected int segment;
        protected float lineWidth;

        private GLBezierLine glBezierLine;

        public Builder(String name) {
            this.name = name;
            this.bezierPoints = new ArrayList<>();
        }

        @Override
        public BezierPointStep addControlPoint(BezierPoint bezierPoint) {
            this.bezierPoints.add(bezierPoint);

            return this;
        }

        @Override
        public SegmentStep end() {
            return this;
        }

        @Override
        public LineWidthStep segment() {
            this.segment = DEFAULT_SEGMENT;

            return this;
        }

        @Override
        public LineWidthStep segment(int segment) {
            this.segment = segment;

            return this;
        }

        @Override
        public GLBezierLine lineWidth(float lineWidth) {
            this.lineWidth = lineWidth;

            return build();
        }

        private GLBezierLine build() {
            if (glBezierLine == null) {
                return glBezierLine = new GLBezierLine(name, this);
            } else {
                return glBezierLine.update();
            }
        }
    }
}
