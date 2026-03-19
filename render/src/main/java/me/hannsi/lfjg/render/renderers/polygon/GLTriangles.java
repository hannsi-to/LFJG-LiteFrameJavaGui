package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;

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
        VertexData2Step<T> vertex1(Vertex vertex1);
    }

    public interface VertexData2Step<T> {
        VertexData3Step<T> vertex2(Vertex vertex2);
    }

    public interface VertexData3Step<T> {
        VertexData1Step<T> vertex3(Vertex vertex3);

        PaintTypeStep<T> vertex3_end(Vertex vertex3);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLTriangles> implements VertexData1Step<GLTriangles>, VertexData2Step<GLTriangles>, VertexData3Step<GLTriangles> {
        private final String name;
        private final List<Vertex> vertices;

        private GLTriangles glTriangles;

        public Builder(String name) {
            this.name = name;

            this.vertices = new ArrayList<>();
        }

        @Override
        public VertexData2Step<GLTriangles> vertex1(Vertex vertex1) {
            this.vertices.add(vertex1);

            return this;
        }

        @Override
        public VertexData3Step<GLTriangles> vertex2(Vertex vertex2) {
            this.vertices.add(vertex2);

            return this;
        }

        @Override
        public VertexData1Step<GLTriangles> vertex3(Vertex vertex3) {
            this.vertices.add(vertex3);

            return this;
        }

        @Override
        public PaintTypeStep<GLTriangles> vertex3_end(Vertex vertex3) {
            this.vertices.add(vertex3);

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