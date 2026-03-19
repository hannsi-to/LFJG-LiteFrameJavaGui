package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;

import java.util.ArrayList;
import java.util.List;

public class GLPolygon extends GLObject<GLPolygon> {
    private final Builder builder;

    protected GLPolygon(String name, Builder builder) {
        super(name);

        this.builder = builder;
    }

    public static VertexDataStep<GLPolygon> createGLPolygon(String name) {
        return new Builder(name);
    }

    @Override
    public GLPolygon update() {
        for (Vertex vertex : builder.vertices) {
            put(vertex).end();
        }

        switch (builder.paintType) {
            case FILL ->
                    drawType(DrawType.POLYGON);
            case STROKE ->
                    drawType(DrawType.LINE_LOOP);
            default ->
                    throw new IllegalStateException("Unexpected value: " + builder.paintType);
        }

        return super.update();
    }

    public interface VertexDataStep<T> {
        VertexDataStep<T> vertex(Vertex vertex);

        PaintTypeStep<T> vertex_end(Vertex vertex);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLPolygon> implements VertexDataStep<GLPolygon> {
        private final String name;
        private final List<Vertex> vertices;

        private GLPolygon glPolygon;

        public Builder(String name) {
            this.name = name;

            this.vertices = new ArrayList<>();
        }

        @Override
        public VertexDataStep<GLPolygon> vertex(Vertex vertex) {
            vertices.add(vertex);

            return this;
        }

        @Override
        public PaintTypeStep<GLPolygon> vertex_end(Vertex vertex) {
            vertices.add(vertex);

            return this;
        }

        @Override
        protected GLPolygon createOrGet() {
            if (glPolygon == null) {
                return glPolygon = new GLPolygon(name, this);
            } else {
                return glPolygon.update();
            }
        }
    }
}