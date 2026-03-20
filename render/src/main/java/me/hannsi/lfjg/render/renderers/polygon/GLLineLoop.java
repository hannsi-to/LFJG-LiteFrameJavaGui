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

    public static VertexDataStep<GLLineLoop> createGLLineLoop(String name) {
        return new Builder(name);
    }

    @Override
    public GLLineLoop update() {
        for (Vertex vertex : builder.vertices) {
            put(vertex).end();
        }

        put(new Vertex()).end();

        drawType(DrawType.LINE_LOOP);

        return super.update();
    }

    public interface VertexData1Step<T> {
        VertexData2Step<T> addVertex1(Vertex vertex);
    }

    public interface VertexData2Step<T> {
        VertexDataStep<T> addVertex2(Vertex vertex);
    }

    public interface VertexDataStep<T> {
        VertexDataStep<T> addVertex(Vertex vertex);

        StrokeJointTypeStep<T> end();
    }

    public static class Builder extends AbstractGLObjectBuilder<GLLineLoop> implements VertexData1Step<GLLineLoop>, VertexData2Step<GLLineLoop>, VertexDataStep<GLLineLoop> {
        private final String name;
        private final List<Vertex> vertices = new ArrayList<>();
        private GLLineLoop glLines;

        public Builder(String name) {
            this.name = name;
        }

        @Override
        public VertexData2Step<GLLineLoop> addVertex1(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public VertexDataStep<GLLineLoop> addVertex2(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public VertexDataStep<GLLineLoop> addVertex(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public StrokeJointTypeStep<GLLineLoop> end() {
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