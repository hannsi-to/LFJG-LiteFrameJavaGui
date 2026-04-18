package me.hannsi.lfjg.testRender.renderers.polygon;

import me.hannsi.lfjg.testRender.renderers.GLObject;
import me.hannsi.lfjg.testRender.system.mesh.Vertex;
import me.hannsi.lfjg.testRender.system.rendering.DrawType;

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
        VertexData2Step<T> addVertex1(Vertex vertex);
    }

    public interface VertexData2Step<T> {
        VertexDataEndStep<T> addVertex2(Vertex vertex);
    }

    public interface VertexDataEndStep<T> {
        VertexData2Step<T> addVertex(Vertex vertex);

        StrokeJointTypeStep<T> end();
    }

    public static class Builder extends AbstractGLObjectBuilder<GLLines> implements VertexData1Step<GLLines>, VertexData2Step<GLLines>, VertexDataEndStep<GLLines> {
        private final String name;
        private final List<Vertex> vertices = new ArrayList<>();
        private GLLines glLines;

        public Builder(String name) {
            this.name = name;
        }

        @Override
        public VertexData2Step<GLLines> addVertex(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public VertexData2Step<GLLines> addVertex1(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public VertexDataEndStep<GLLines> addVertex2(Vertex vertex) {
            this.vertices.add(vertex);
            return this;
        }

        @Override
        public StrokeJointTypeStep<GLLines> end() {
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