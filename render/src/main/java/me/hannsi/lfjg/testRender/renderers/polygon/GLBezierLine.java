package me.hannsi.lfjg.testRender.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.animation.BezierPoint;
import me.hannsi.lfjg.core.utils.math.animation.MultiBezierEasing;
import me.hannsi.lfjg.testRender.renderers.GLObject;
import me.hannsi.lfjg.testRender.system.mesh.Vertex;
import me.hannsi.lfjg.testRender.system.rendering.DrawType;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.core.utils.math.MathHelper.*;
import static me.hannsi.lfjg.testRender.RenderSystemSetting.*;

public class GLBezierLine extends GLObject<GLBezierLine> {
    private final Builder builder;

    public GLBezierLine(String name, Builder builder) {
        super(name);
        this.builder = builder;
    }

    public static BezierPointStep createGLBezierLine(String name) {
        return new Builder(name);
    }

    @Override
    public GLBezierLine update() {
        List<Vertex> controlPoints = builder.controlPoints;
        int n = controlPoints.size();

        BezierPoint[] bezierPoints = new BezierPoint[n];
        Color[] colors = new Color[n];

        for (int i = 0; i < n; i++) {
            Vertex vertex = controlPoints.get(i);
            bezierPoints[i] = new BezierPoint(vertex.x, vertex.y, vertex.z);
            colors[i] = vertex.getColor();
        }

        MultiBezierEasing easing = new MultiBezierEasing(bezierPoints);

        int segment = builder.segment;
        int numPoints = segment + 1;
        for (int i = 0; i < numPoints; i++) {
            float t = i / (float) segment;

            BezierPoint point = easing.evaluateDeCasteljau(t);
            Color color = lerpColor(colors, t);

            put().position(point.coords[0], point.coords[1], point.coords[2]).color(color).end();
        }

        drawType(DrawType.LINE_STRIP);

        return super.update();
    }

    public interface BezierPoint1Step<T> {
        BezierPointStep<T> addControlPoint1(Vertex controlPoint);
    }

    public interface BezierPointStep<T> {
        BezierPointStep<T> addControlPoint(Vertex controlPoint);

        SegmentStep<T> end();
    }

    public interface SegmentStep<T> {
        StrokeJointTypeStep<T> segment();

        StrokeJointTypeStep<T> segment(int segment);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLBezierLine> implements BezierPoint1Step<GLBezierLine>, BezierPointStep<GLBezierLine>, SegmentStep<GLBezierLine> {
        protected final String name;
        protected List<Vertex> controlPoints = new ArrayList<>();
        protected int segment;

        private GLBezierLine glBezierLine;

        public Builder(String name) {
            this.name = name;
        }

        @Override
        public BezierPointStep<GLBezierLine> addControlPoint1(Vertex controlPoint) {
            this.controlPoints.add(controlPoint);
            return this;
        }

        @Override
        public BezierPointStep<GLBezierLine> addControlPoint(Vertex controlPoint) {
            this.controlPoints.add(controlPoint);
            return this;
        }

        @Override
        public SegmentStep<GLBezierLine> end() {
            return this;
        }

        @Override
        public StrokeJointTypeStep<GLBezierLine> segment() {
            int n = controlPoints.size();
            if (n < 2) {
                segment = GL_BEZIER_LINE_MIN_SEGMENT;
                return this;
            }

            float polygonLineLength = 0f;
            for (int i = 0; i < n - 1; i++) {
                polygonLineLength += distance(controlPoints.get(i).toVector2f(), controlPoints.get(i + 1).toVector2f());
            }

            float chordLength = distance(controlPoints.getFirst().toVector2f(), controlPoints.get(n - 1).toVector2f());
            float curvatureRatio = (chordLength > 0f) ? polygonLineLength / chordLength : 1f;
            int s = round(polygonLineLength * GL_BEZIER_LINE_SEGMENTS_PER_UNIT * curvatureRatio);

            segment = max(GL_BEZIER_LINE_MIN_SEGMENT, min(GL_BEZIER_LINE_MAX_SEGMENT, s));
            return this;
        }

        @Override
        public StrokeJointTypeStep<GLBezierLine> segment(int segment) {
            this.segment = segment;
            return this;
        }

        @Override
        protected GLBezierLine createOrGet() {
            if (glBezierLine == null) {
                return glBezierLine = new GLBezierLine(name, this);
            } else {
                return glBezierLine.update();
            }
        }
    }
}
