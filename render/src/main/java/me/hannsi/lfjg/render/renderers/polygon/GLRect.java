package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;

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

    public static VertexData1Step<GLRect> createGLRect(String name) {
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

    public interface VertexData1Step<T> {
        VertexData2Step<T> vertex1(Vertex vertex1);

        VertexData22pStep<T> vertex1_2p(Vertex vertex1);
    }

    public interface VertexData2Step<T> {
        VertexData3Step<T> vertex2(Vertex vertex2);
    }

    public interface VertexData22pStep<T> {
        VertexData1Step<T> vertex2_2p(Vertex vertex2);

        PaintTypeStep<T> vertex2_2p_end(Vertex vertex2);
    }

    public interface VertexData3Step<T> {
        VertexData4Step<T> vertex3(Vertex vertex3);
    }

    public interface VertexData4Step<T> {
        VertexData1Step<T> vertex4(Vertex vertex4);

        PaintTypeStep<T> vertex4_end(Vertex vertex4);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLRect> implements VertexData1Step<GLRect>, VertexData2Step<GLRect>, VertexData22pStep<GLRect>, VertexData3Step<GLRect>, VertexData4Step<GLRect>, PaintTypeStep<GLRect>, strokeWidthStep<GLRect> {
        private final String name;
        private final List<Vertex> vertices;
        private Vertex lastVertex2p;

        private GLRect glRect;

        public Builder(String name) {
            this.name = name;

            this.vertices = new ArrayList<>();
        }

        @Override
        public VertexData2Step<GLRect> vertex1(Vertex vertex1) {
            this.vertices.add(vertex1);

            return this;
        }

        @Override
        public VertexData22pStep<GLRect> vertex1_2p(Vertex vertex1) {
            this.lastVertex2p = vertex1;
            this.vertices.add(vertex1);

            return this;
        }

        @Override
        public VertexData1Step<GLRect> vertex2_2p(Vertex vertex2) {
            this.vertices.add(lastVertex2p.copy().setX(vertex2.x));
            this.vertices.add(vertex2);
            this.vertices.add(lastVertex2p.copy().setY(vertex2.y));

            return this;
        }

        @Override
        public PaintTypeStep<GLRect> vertex2_2p_end(Vertex vertex2) {
            this.vertices.add(lastVertex2p.copy().setX(vertex2.x));
            this.vertices.add(vertex2);
            this.vertices.add(lastVertex2p.copy().setY(vertex2.y));

            return this;
        }

        @Override
        public VertexData3Step<GLRect> vertex2(Vertex vertex2) {
            this.vertices.add(vertex2);

            return this;
        }

        @Override
        public VertexData4Step<GLRect> vertex3(Vertex vertex3) {
            this.vertices.add(vertex3);

            return this;
        }

        @Override
        public VertexData1Step<GLRect> vertex4(Vertex vertex4) {
            this.vertices.add(vertex4);

            return this;
        }

        @Override
        public PaintTypeStep<GLRect> vertex4_end(Vertex vertex4) {
            this.vertices.add(vertex4);

            return this;
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
