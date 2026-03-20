package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;

import java.util.ArrayList;
import java.util.List;

public class GLTriangleStrip extends GLObject<GLTriangleStrip> {
    private final Builder builder;

    protected GLTriangleStrip(String name, Builder builder) {
        super(name);

        this.builder = builder;
    }

    public static VertexData1Step<GLTriangleStrip> createGLTriangleStrip(String name) {
        return new Builder(name);
    }

    @Override
    public GLTriangleStrip update() {
        for (Vertex vertex : builder.vertices) {
            put(vertex).end();
        }

        switch (builder.paintType) {
            case FILL, STROKE ->
                    drawType(DrawType.TRIANGLE_STRIP);
            default ->
                    throw new IllegalStateException("Unexpected value: " + builder.paintType);
        }

        return super.update();
    }

    public interface VertexData1Step<T> {
        VertexData2Step<T> vertex1(Vertex vertex);
    }

    public interface VertexData2Step<T> {
        VertexDataStep<T> vertex2(Vertex vertex);
    }

    public interface VertexDataStep<T> {
        VertexDataStep<T> addVertex(Vertex vertex);

        PaintTypeStep<T> end();
    }

    public static class Builder extends AbstractGLObjectBuilder<GLTriangleStrip> implements VertexData1Step<GLTriangleStrip>, VertexData2Step<GLTriangleStrip>, VertexDataStep<GLTriangleStrip> {
        private final String name;
        private final List<Vertex> vertices = new ArrayList<>();
        private GLTriangleStrip glTriangle;

        public Builder(String name) {
            this.name = name;
        }

        @Override
        public VertexData2Step<GLTriangleStrip> vertex1(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public VertexDataStep<GLTriangleStrip> vertex2(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public VertexDataStep<GLTriangleStrip> addVertex(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public PaintTypeStep<GLTriangleStrip> end() {
            return this;
        }

        @Override
        protected GLTriangleStrip createOrGet() {
            if (glTriangle == null) {
                return glTriangle = new GLTriangleStrip(name, this);
            } else {
                return glTriangle.update();
            }
        }
    }
}