package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;

import java.util.ArrayList;
import java.util.List;

public class GLLines extends GLObject<GLLines> {
    private final Builder builder;

    protected GLLines(String name, Builder builder) {
        super(name);
        this.builder = builder;
    }

    public static VertexData1Step<GLLines> createGLLines(String name) {
        return new Builder(name);
    }

    @Override
    public GLLines update() {
        for (Vertex vertex : builder.vertices) {
            put(vertex).end();
        }

        drawType(DrawType.LINES);

        return super.update();
    }

    public interface VertexData1Step<T> {
        VertexData2Step<T> vertex1(Vertex vertex);
    }

    public interface VertexData2Step<T> {
        VertexData1Step<T> vertex2(Vertex vertex);

        StrokeJointTypeStep<T> vertex2_end(Vertex vertex);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLLines> implements VertexData1Step<GLLines>, VertexData2Step<GLLines> {
        private final String name;
        private final List<Vertex> vertices;

        private GLLines glLines;

        public Builder(String name) {
            this.name = name;
            this.vertices = new ArrayList<>();
        }

        @Override
        public VertexData2Step<GLLines> vertex1(Vertex vertex) {
            this.vertices.add(vertex);

            return this;
        }

        @Override
        public VertexData1Step<GLLines> vertex2(Vertex vertex) {
            this.vertices.add(vertex);

            return this;
        }

        @Override
        public StrokeJointTypeStep<GLLines> vertex2_end(Vertex vertex) {
            this.vertices.add(vertex);

            return this;
        }

        @Override
        protected GLLines createOrGet() {
            if (glLines == null) {
                return glLines = new GLLines(name, this);
            } else {
                return glLines.update();
            }
        }
    }
}