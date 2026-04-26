package me.hannsi.lfjg.testRender.renderers.polygon;

import me.hannsi.lfjg.render.render.Vertex;
import me.hannsi.lfjg.testRender.renderers.GLObject;
import me.hannsi.lfjg.testRender.renderers.PointType;
import me.hannsi.lfjg.testRender.system.rendering.DrawType;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a point renderer in OpenGL.
 */
public class GLPoints extends GLObject<GLPoints> {
    private final Builder builder;

    protected GLPoints(String name, Builder builder) {
        super(name);

        this.builder = builder;
    }

    public static VertexDataStep<GLPoints> createGLPoint(String name) {
        return new Builder(name);
    }

    @Override
    public GLPoints update() {
        for (Vertex vertex : builder.vertices) {
            put(vertex).end();
        }
        pointSize(builder.pointSize).pointType(builder.pointType);
        switch (builder.paintType) {
            case FILL, STROKE ->
                    drawType(DrawType.POINTS);
            default ->
                    throw new IllegalStateException("Unexpected value: " + builder.paintType);
        }

        return super.update();
    }

    public interface VertexData1Step<T> {
        VertexDataStep<T> addVertex1(Vertex vertex);
    }

    public interface VertexDataStep<T> {
        VertexDataStep<T> addVertex(Vertex vertex);

        PointTypeStep<T> end();
    }

    public interface PointTypeStep<T> {
        PointSizeStep<T> pointType(PointType pointType);
    }

    public interface PointSizeStep<T> {
        PaintTypeStep<T> pointSize(float pointSize);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLPoints> implements VertexData1Step<GLPoints>, VertexDataStep<GLPoints>, PointTypeStep<GLPoints>, PointSizeStep<GLPoints> {
        private final String name;
        private final List<Vertex> vertices = new ArrayList<>();
        private PointType pointType;
        private float pointSize;
        private GLPoints glPoints;

        public Builder(String name) {
            this.name = name;
        }

        @Override
        public VertexDataStep<GLPoints> addVertex1(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public VertexDataStep<GLPoints> addVertex(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public PointTypeStep<GLPoints> end() {
            return this;
        }

        @Override
        public PointSizeStep<GLPoints> pointType(PointType pointType) {
            this.pointType = pointType;
            return this;
        }

        @Override
        public PaintTypeStep<GLPoints> pointSize(float pointSize) {
            this.pointSize = pointSize;
            return this;
        }

        @Override
        protected GLPoints createOrGet() {
            if (glPoints == null) {
                return glPoints = new GLPoints(name, this);
            } else {
                return glPoints.update();
            }
        }
    }
}