package me.hannsi.lfjg.testRender.renderers.polygon;

import me.hannsi.lfjg.testRender.renderers.GLObject;
import me.hannsi.lfjg.testRender.system.mesh.Vertex;
import me.hannsi.lfjg.testRender.system.rendering.DrawType;

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
        VertexData2Step<T> addVertex1(Vertex vertex);
    }

    public interface VertexData2Step<T> {
        VertexDataStep<T> addVertex2(Vertex vertex);
    }

    public interface VertexDataStep<T> {
        VertexDataStep<T> addVertex(Vertex vertex);

        StrokeJointTypeStep<T> end();
    }

    public static class Builder extends AbstractGLObjectBuilder<GLLineStrip> implements VertexData1Step<GLLineStrip>, VertexData2Step<GLLineStrip>, VertexDataStep<GLLineStrip> {
        private final String name;
        private final List<Vertex> vertices = new ArrayList<>();
        private GLLineStrip glLines;

        public Builder(String name) {
            this.name = name;
        }

        @Override
        public VertexData2Step<GLLineStrip> addVertex1(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public VertexDataStep<GLLineStrip> addVertex2(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public VertexDataStep<GLLineStrip> addVertex(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public StrokeJointTypeStep<GLLineStrip> end() {
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