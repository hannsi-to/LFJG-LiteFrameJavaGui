package me.hannsi.lfjg.testRender.renderers.polygon;

import me.hannsi.lfjg.testRender.renderers.GLObject;
import me.hannsi.lfjg.testRender.system.mesh.Vertex;
import me.hannsi.lfjg.testRender.system.rendering.DrawType;

import java.util.ArrayList;
import java.util.List;

public class GLTriangles extends GLObject<GLTriangles> {
    private final Builder builder;

    protected GLTriangles(String name, Builder builder) {
        super(name);

        this.builder = builder;
    }

    public static VertexData1Step<GLTriangles> createGLTriangle(String name) {
        return new Builder(name);
    }

    @Override
    public GLTriangles update() {
        for (Vertex vertex : builder.vertices) {
            put(vertex).end();
        }

        switch (builder.paintType) {
            case FILL, STROKE ->
                    drawType(DrawType.TRIANGLES);
            default ->
                    throw new IllegalStateException("Unexpected value: " + builder.paintType);
        }

        return super.update();
    }

    public interface VertexData1Step<T> {
        VertexData2Step<T> addVertex1(Vertex vertex);
    }

    public interface VertexData2Step<T> {
        VertexData3Step<T> addVertex2(Vertex vertex);
    }

    public interface VertexData3Step<T> {
        VertexData2Step<T> addVertex(Vertex vertex);

        PaintTypeStep<T> end(Vertex vertex);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLTriangles> implements VertexData1Step<GLTriangles>, VertexData2Step<GLTriangles>, VertexData3Step<GLTriangles> {
        private final String name;
        private final List<Vertex> vertices = new ArrayList<>();
        private GLTriangles glTriangles;

        public Builder(String name) {
            this.name = name;
        }

        @Override
        public VertexData2Step<GLTriangles> addVertex1(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public VertexData3Step<GLTriangles> addVertex2(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public VertexData2Step<GLTriangles> addVertex(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public PaintTypeStep<GLTriangles> end(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        protected GLTriangles createOrGet() {
            if (glTriangles == null) {
                return glTriangles = new GLTriangles(name, this);
            } else {
                return glTriangles.update();
            }
        }
    }
}