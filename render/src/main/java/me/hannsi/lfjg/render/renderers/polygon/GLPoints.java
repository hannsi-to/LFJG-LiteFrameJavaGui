package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.renderers.PointType;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;

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

    public interface VertexDataStep<T> {
        VertexDataStep<T> vertex(Vertex vertex);

        PointTypeStep<T> vertex_end(Vertex vertex);
    }

    public interface PointTypeStep<T> {
        PointSizeStep<T> pointType(PointType pointType);
    }

    public interface PointSizeStep<T> {
        PaintTypeStep<T> pointSize(float pointSize);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLPoints> implements VertexDataStep<GLPoints>, PointTypeStep<GLPoints>, PointSizeStep<GLPoints> {
        private final String name;
        private final List<Vertex> vertices;
        private PointType pointType;
        private float pointSize;

        private GLPoints glPoints;

        public Builder(String name) {
            this.name = name;

            this.vertices = new ArrayList<>();
        }

        @Override
        public VertexDataStep<GLPoints> vertex(Vertex vertex) {
            this.vertices.add(vertex);

            return this;
        }

        @Override
        public PointTypeStep<GLPoints> vertex_end(Vertex vertex) {
            this.vertices.add(vertex);

            return this;
        }

        @Override
        public PaintTypeStep<GLPoints> pointSize(float pointSize) {
            this.pointSize = pointSize;

            return this;
        }

        @Override
        public PointSizeStep<GLPoints> pointType(PointType pointType) {
            this.pointType = pointType;

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