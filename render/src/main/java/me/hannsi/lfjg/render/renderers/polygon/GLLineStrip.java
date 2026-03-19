package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;

import java.util.ArrayList;
import java.util.List;

public class GLLineStrip extends GLObject<GLLineStrip> {
    private final Builder builder;

    protected GLLineStrip(String name, Builder builder) {
        super(name);
        this.builder = builder;
    }

    public static VertexData1Step<GLLineStrip> createGLLineStrip(String name) {
        return new Builder(name);
    }

    @Override
    public GLLineStrip update() {
        for (Vertex vertex : builder.vertices) {
            put(vertex).end();
        }

        drawType(DrawType.LINE_STRIP);

        return super.update();
    }

    public interface VertexData1Step<T> {
        VertexData2Step<T> vertex1(Vertex vertex);
    }

    public interface VertexData2Step<T> {
        VertexData2Step<T> vertex2(Vertex vertex);

        StrokeJointTypeStep<T> vertex2_end(Vertex vertex);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLLineStrip> implements VertexData1Step<GLLineStrip>, VertexData2Step<GLLineStrip> {
        private final String name;
        private final List<Vertex> vertices;

        private GLLineStrip glLines;

        public Builder(String name) {
            this.name = name;
            this.vertices = new ArrayList<>();
        }

        @Override
        public VertexData2Step<GLLineStrip> vertex1(Vertex vertex) {
            this.vertices.add(vertex);

            return this;
        }

        @Override
        public VertexData2Step<GLLineStrip> vertex2(Vertex vertex) {
            this.vertices.add(vertex);

            return this;
        }

        @Override
        public StrokeJointTypeStep<GLLineStrip> vertex2_end(Vertex vertex) {
            this.vertices.add(vertex);

            return this;
        }

        @Override
        protected GLLineStrip createOrGet() {
            if (glLines == null) {
                return glLines = new GLLineStrip(name, this);
            } else {
                return glLines.update();
            }
        }
    }
}