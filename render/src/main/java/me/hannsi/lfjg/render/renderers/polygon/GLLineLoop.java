package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;

import java.util.ArrayList;
import java.util.List;

public class GLLineLoop extends GLObject<GLLineLoop> {
    private final Builder builder;

    protected GLLineLoop(String name, Builder builder) {
        super(name);
        this.builder = builder;
    }

    public static VertexData1Step<GLLineLoop> createGLLineLoop(String name) {
        return new Builder(name);
    }

    @Override
    public GLLineLoop update() {
        for (Vertex vertex : builder.vertices) {
            put(vertex).end();
        }

        drawType(DrawType.LINE_LOOP);

        return super.update();
    }

    public interface VertexData1Step<T> {
        VertexData2Step<T> vertex1(Vertex vertex);
    }

    public interface VertexData2Step<T> {
        VertexData2Step<T> vertex2(Vertex vertex);

        StrokeJointTypeStep<T> vertex2_end(Vertex vertex);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLLineLoop> implements VertexData1Step<GLLineLoop>, VertexData2Step<GLLineLoop> {
        private final String name;
        private final List<Vertex> vertices;

        private GLLineLoop glLines;

        public Builder(String name) {
            this.name = name;
            this.vertices = new ArrayList<>();
        }

        @Override
        public VertexData2Step<GLLineLoop> vertex1(Vertex vertex) {
            this.vertices.add(vertex);

            return this;
        }

        @Override
        public VertexData2Step<GLLineLoop> vertex2(Vertex vertex) {
            this.vertices.add(vertex);

            return this;
        }

        @Override
        public StrokeJointTypeStep<GLLineLoop> vertex2_end(Vertex vertex) {
            this.vertices.add(vertex);

            return this;
        }

        @Override
        protected GLLineLoop createOrGet() {
            if (glLines == null) {
                return glLines = new GLLineLoop(name, this);
            } else {
                return glLines.update();
            }
        }
    }
}