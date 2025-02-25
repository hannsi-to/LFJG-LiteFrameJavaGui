package me.hannsi.lfjg.render.openGL.renderers.polygon;

import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.Mesh;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Vector2f;

import java.util.Arrays;

/**
 * Class representing a polygon renderer in OpenGL.
 */
public class GLPolygon extends GLObject {
    private float[] vertex;
    private float[] color;
    private float[] texture;

    /**
     * Constructs a new GLPolygon with the specified name.
     *
     * @param name the name of the polygon renderer
     */
    public GLPolygon(String name) {
        super(name);
    }

    /**
     * Prepares the polygon for rendering.
     *
     * @return the current instance of GLPolygon
     */
    public GLPolygon put() {
        return this;
    }

    /**
     * Adds a vertex to the polygon.
     *
     * @param vector2f the vertex to add
     * @return the current instance of GLPolygon
     */
    public GLPolygon vertex(Vector2f vector2f) {
        if (vertex == null) {
            vertex = new float[0];
        }

        vertex = Arrays.copyOf(vertex, vertex.length + 2);

        vertex[vertex.length - 2] = vector2f.x();
        vertex[vertex.length - 1] = vector2f.y();

        return this;
    }

    /**
     * Adds a color to the polygon.
     *
     * @param c the color to add
     * @return the current instance of GLPolygon
     */
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

    /**
     * Adds texture coordinates to the polygon.
     *
     * @param u1 the u-coordinate of the first vertex
     * @param v1 the v-coordinate of the first vertex
     * @param u2 the u-coordinate of the second vertex
     * @param v2 the v-coordinate of the second vertex
     * @return the current instance of GLPolygon
     */
    public GLPolygon uv(float u1, float v1, float u2, float v2) {
        put().uv(new Vector2f(u1, v1)).end();
        put().uv(new Vector2f(u2, v1)).end();
        put().uv(new Vector2f(u2, v2)).end();
        put().uv(new Vector2f(u1, v2)).end();

        return this;
    }

    /**
     * Adds a texture coordinate to the polygon.
     *
     * @param vector2f the texture coordinate to add
     * @return the current instance of GLPolygon
     */
    public GLPolygon uv(Vector2f vector2f) {
        if (texture == null) {
            texture = new float[0];
        }

        texture = Arrays.copyOf(texture, texture.length + 2);

        texture[texture.length - 2] = vector2f.x();
        texture[texture.length - 1] = vector2f.y();

        return this;
    }

    /**
     * Ends the current polygon definition.
     */
    public void end() {
    }

    /**
     * Renders the polygon.
     */
    public void rendering() {
        Mesh mesh = new Mesh(ProjectionType.OrthographicProjection, vertex, color, texture);

        setVertexShader(new ResourcesLocation("shader/scene/object/VertexShader.vsh"));
        setFragmentShader(new ResourcesLocation("shader/scene/object/FragmentShader.fsh"));
        setMesh(mesh);

        float[] bounds = getBounds();
        setX(bounds[0]);
        setY(bounds[1]);
        setWidth(bounds[2] - bounds[0]);
        setHeight(bounds[3] - bounds[1]);

        create();
    }

    private float[] getBounds() {
        if (vertex == null || vertex.length < 2) {
            throw new IllegalArgumentException("Vertex array is null or invalid.");
        }

        float minX = vertex[0];
        float maxX = vertex[0];
        float minY = vertex[1];
        float maxY = vertex[1];

        for (int i = 2; i < vertex.length; i += 2) {
            float x = vertex[i];
            float y = vertex[i + 1];

            if (x < minX) minX = x;
            if (x > maxX) maxX = x;
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        }

        return new float[]{minX, minY, maxX, maxY};
    }

    public float[] getVertex() {
        return vertex;
    }

    public void setVertex(float[] vertex) {
        this.vertex = vertex;
    }

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    public float[] getTexture() {
        return texture;
    }

    public void setTexture(float[] texture) {
        this.texture = texture;
    }
}