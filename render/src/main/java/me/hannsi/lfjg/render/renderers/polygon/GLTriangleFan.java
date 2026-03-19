package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;

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
        VertexData2Step<T> vertex1(Vertex vertex1);
    }

    public interface VertexData2Step<T> {
        VertexData3Step<T> vertex2(Vertex vertex2);
    }

    public interface VertexData3Step<T> {
        VertexData3Step<T> vertex3(Vertex vertex3);

        PaintTypeStep<T> vertex3_end(Vertex vertex3);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLTriangleFan> implements VertexData1Step<GLTriangleFan>, VertexData2Step<GLTriangleFan>, VertexData3Step<GLTriangleFan> {
        private final String name;
        private final List<Vertex> vertices;

        private GLTriangleFan glTriangle;

        public Builder(String name) {
            this.name = name;

            this.vertices = new ArrayList<>();
        }

        @Override
        public VertexData2Step<GLTriangleFan> vertex1(Vertex vertex1) {
            this.vertices.add(vertex1);

            return this;
        }

        @Override
        public VertexData3Step<GLTriangleFan> vertex2(Vertex vertex2) {
            this.vertices.add(vertex2);

            return this;
        }

        @Override
        public VertexData3Step<GLTriangleFan> vertex3(Vertex vertex3) {
            this.vertices.add(vertex3);

            return this;
        }

        @Override
        public PaintTypeStep<GLTriangleFan> vertex3_end(Vertex vertex3) {
            this.vertices.add(vertex3);

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