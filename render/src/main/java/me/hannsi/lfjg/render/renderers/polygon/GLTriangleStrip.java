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
        VertexData2Step<T> vertex1(Vertex vertex1);
    }

    public interface VertexData2Step<T> {
        VertexData3Step<T> vertex2(Vertex vertex2);
    }

    public interface VertexData3Step<T> {
        VertexData3Step<T> vertex3(Vertex vertex3);

        PaintTypeStep<T> vertex3_end(Vertex vertex3);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLTriangleStrip> implements VertexData1Step<GLTriangleStrip>, VertexData2Step<GLTriangleStrip>, VertexData3Step<GLTriangleStrip> {
        private final String name;
        private final List<Vertex> vertices;

        private GLTriangleStrip glTriangle;

        public Builder(String name) {
            this.name = name;

            this.vertices = new ArrayList<>();
        }

        @Override
        public VertexData2Step<GLTriangleStrip> vertex1(Vertex vertex1) {
            this.vertices.add(vertex1);

            return this;
        }

        @Override
        public VertexData3Step<GLTriangleStrip> vertex2(Vertex vertex2) {
            this.vertices.add(vertex2);

            return this;
        }

        @Override
        public VertexData3Step<GLTriangleStrip> vertex3(Vertex vertex3) {
            this.vertices.add(vertex3);

            return this;
        }

        @Override
        public PaintTypeStep<GLTriangleStrip> vertex3_end(Vertex vertex3) {
            this.vertices.add(vertex3);

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