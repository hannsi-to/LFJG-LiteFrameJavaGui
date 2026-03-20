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
        put(new Vertex()).end();

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

    public interface VertexData1Step<T> {
        VertexData2Step<T> addVertex1(Vertex vertex);
    }

    public interface VertexData2Step<T> {
        VertexDataStep<T> addVertex2(Vertex vertex);
    }

    public interface VertexDataStep<T> {
        VertexDataStep<T> addVertex(Vertex vertex);

        PaintTypeStep<T> end();
    }

    public static class Builder extends AbstractGLObjectBuilder<GLPolygon> implements VertexData1Step<GLPolygon>, VertexData2Step<GLPolygon>, VertexDataStep<GLPolygon> {
        private final String name;
        private final List<Vertex> vertices = new ArrayList<>();

        private GLPolygon glPolygon;

        public Builder(String name) {
            this.name = name;
        }

        @Override
        public VertexData2Step<GLPolygon> addVertex1(Vertex vertex) {
            vertices.add(vertex);
            return this;
        }

        @Override
        public VertexDataStep<GLPolygon> addVertex2(Vertex vertex) {
            vertices.add(vertex);
            return this;
        }

        @Override
        public VertexDataStep<GLPolygon> addVertex(Vertex vertex) {
            vertices.add(vertex);
            return this;
        }

        @Override
        public PaintTypeStep<GLPolygon> end() {
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