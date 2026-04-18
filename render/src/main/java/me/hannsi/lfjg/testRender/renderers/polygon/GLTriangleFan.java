package me.hannsi.lfjg.testRender.renderers.polygon;

import me.hannsi.lfjg.testRender.renderers.GLObject;
import me.hannsi.lfjg.testRender.system.mesh.Vertex;
import me.hannsi.lfjg.testRender.system.rendering.DrawType;

import java.util.ArrayList;
import java.util.List;

public class GLTriangleFan extends GLObject<GLTriangleFan> {
    private final Builder builder;

    protected GLTriangleFan(String name, Builder builder) {
        super(name);

        this.builder = builder;
    }

    public static VertexData1Step<GLTriangleFan> createGLTriangleFan(String name) {
        return new Builder(name);
    }

    @Override
    public GLTriangleFan update() {
        for (Vertex vertex : builder.vertices) {
            put(vertex).end();
        }

        switch (builder.paintType) {
            case FILL, STROKE ->
                    drawType(DrawType.TRIANGLE_FAN);
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

    public static class Builder extends AbstractGLObjectBuilder<GLTriangleFan> implements VertexData1Step<GLTriangleFan>, VertexData2Step<GLTriangleFan>, VertexDataStep<GLTriangleFan> {
        private final String name;
        private final List<Vertex> vertices = new ArrayList<>();
        private GLTriangleFan glTriangle;

        public Builder(String name) {
            this.name = name;
        }

        @Override
        public VertexData2Step<GLTriangleFan> vertex1(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public VertexDataStep<GLTriangleFan> vertex2(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public VertexDataStep<GLTriangleFan> addVertex(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public PaintTypeStep<GLTriangleFan> end() {
            return this;
        }

        @Override
        protected GLTriangleFan createOrGet() {
            if (glTriangle == null) {
                return glTriangle = new GLTriangleFan(name, this);
            } else {
                return glTriangle.update();
            }
        }
    }
}