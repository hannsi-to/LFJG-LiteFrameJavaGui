package me.hannsi.lfjg.render.openGL.renderers.polygon;

import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.Mesh;
import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Vector2f;

import java.util.Arrays;

public class GLPolygon extends GLObject {
    private Mesh mesh;

    private float[] vertex;
    private float[] color;
    private float[] texture;

    public GLPolygon(String name) {
        super(name);
    }

    public GLPolygon put() {
        return this;
    }

    public GLPolygon vertex(Vector2f vector2f) {
        if (vertex == null) {
            vertex = new float[0];
        }

        vertex = Arrays.copyOf(vertex, vertex.length + 2);

        vertex[vertex.length - 2] = vector2f.x();
        vertex[vertex.length - 1] = vector2f.y();

        return this;
    }

    public GLPolygon color(Color c) {
        if (color == null) {
            color = new float[0];
        }

        color = Arrays.copyOf(color, color.length + 4);

        color[color.length - 4] = c.getRedF();
        color[color.length - 3] = c.getGreenF();
        color[color.length - 2] = c.getBlueF();
        color[color.length - 1] = c.getAlphaF();

        return this;
    }

    public GLPolygon uv(Vector2f vector2f) {
        if (texture == null) {
            texture = new float[0];
        }

        texture = Arrays.copyOf(texture, texture.length + 2);

        texture[texture.length - 2] = vector2f.x();
        texture[texture.length - 1] = vector2f.y();

        return this;
    }

    public void end() {
    }

    public void rendering() {
        mesh = new Mesh(ProjectionType.OrthographicProjection, vertex, color);

        setVertexShader(new ResourcesLocation("shader/vertexShader.vsh"));
        setFragmentShader(new ResourcesLocation("shader/FragmentShader.fsh"));
        setMesh(mesh);

        create();
    }
}
