package me.hannsi.lfjg.testRender.renderers.polygon;

import me.hannsi.lfjg.render.render.Vertex;
import me.hannsi.lfjg.testRender.renderers.GLObject;
import me.hannsi.lfjg.testRender.system.rendering.DrawType;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a rectangle renderer in OpenGL.
 */
public class GLRect extends GLObject<GLRect> {
    private final Builder builder;

    protected GLRect(String name, Builder builder) {
        super(name);
        this.builder = builder;
    }

    public static RectInputStep<GLRect> createGLRect(String name) {
        return new Builder(name);
    }

    @Override
    public GLRect update() {
        for (Vertex vertex : builder.vertices) {
            put(vertex).end();
        }

        switch (builder.paintType) {
            case FILL, STROKE ->
                    drawType(DrawType.QUADS);
            default ->
                    throw new IllegalStateException("Unexpected value: " + builder.paintType);
        }

        return super.update();
    }

    public interface RectInputStep<T> {
        DiagonalStep<T> from(Vertex vertex);

        Vertex2Step<T> vertex1(Vertex vertex);
    }

    public interface DiagonalStep<T> {
        RectInputStep<T> to(Vertex vertex);

        PaintTypeStep<T> end(Vertex vertex);
    }

    public interface Vertex2Step<T> {
        Vertex3Step<T> vertex2(Vertex vertex);
    }

    public interface Vertex3Step<T> {
        Vertex4Step<T> vertex3(Vertex vertex);
    }

    public interface Vertex4Step<T> {
        RectInputStep<T> vertex4(Vertex vertex);

        PaintTypeStep<T> end(Vertex vertex);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLRect> implements RectInputStep<GLRect>, DiagonalStep<GLRect>, Vertex2Step<GLRect>, Vertex3Step<GLRect>, Vertex4Step<GLRect> {
        private final String name;
        private final List<Vertex> vertices;
        private Vertex lastFrom;

        private GLRect glRect;

        public Builder(String name) {
            this.name = name;

            this.vertices = new ArrayList<>();
        }

        @Override
        public DiagonalStep<GLRect> from(Vertex vertex) {
            this.lastFrom = vertex;
            this.vertices.add(vertex);
            return this;
        }

        private void addDiagonalVertices(Vertex to) {
            vertices.add(lastFrom.copy().setX(to.x));
            vertices.add(to);
            vertices.add(lastFrom.copy().setY(to.y));
        }

        @Override
        public RectInputStep<GLRect> to(Vertex vertex) {
            addDiagonalVertices(vertex);

            if (lastFrom != null) {
                autoAssignUVs(lastFrom.u, lastFrom.v, vertex.u, vertex.v);
            }
            return this;
        }

        @Override
        public Vertex2Step<GLRect> vertex1(Vertex vertex) {
            vertices.add(vertex);
            return this;
        }

        @Override
        public Vertex3Step<GLRect> vertex2(Vertex vertex) {
            vertices.add(vertex);
            return this;
        }

        @Override
        public Vertex4Step<GLRect> vertex3(Vertex vertex) {
            vertices.add(vertex);
            return this;
        }

        @Override
        public RectInputStep<GLRect> vertex4(Vertex vertex) {
            vertices.add(vertex);
            return this;
        }

        @Override
        public PaintTypeStep<GLRect> end(Vertex vertex) {
            if (lastFrom != null && vertices.size() % 4 == 1) {
                addDiagonalVertices(vertex);
                autoAssignUVs(lastFrom.u, lastFrom.v, vertex.u, vertex.v);
            } else {
                vertices.add(vertex);
            }

            return this;
        }

        private void autoAssignUVs(float minU, float minV, float maxU, float maxV) {
            int size = vertices.size();
            if (size < 4) {
                return;
            }

            List<Vertex> quad = vertices.subList(size - 4, size);

            float minX = (float) quad.stream().mapToDouble(v -> v.x).min().orElse(0);
            float maxX = (float) quad.stream().mapToDouble(v -> v.x).max().orElse(0);
            float minY = (float) quad.stream().mapToDouble(v -> v.y).min().orElse(0);
            float maxY = (float) quad.stream().mapToDouble(v -> v.y).max().orElse(0);

            float rangeX = maxX - minX;
            float rangeY = maxY - minY;

            for (Vertex vertex : quad) {
                float u = (rangeX == 0) ? minU : minU + (vertex.x - minX) / rangeX * (maxU - minU);
                float v = (rangeY == 0) ? minV : minV + (1.0f - (vertex.y - minY) / rangeY) * (maxV - minV);
                vertex.setU(u).setV(v);
            }
        }

        @Override
        protected GLRect createOrGet() {
            if (glRect == null) {
                return glRect = new GLRect(name, this);
            } else {
                return glRect.update();
            }
        }
    }
}
