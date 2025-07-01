package me.hannsi.lfjg.render.renderers.polygon;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.debug.exceptions.render.meshBuilder.MeshBuilderException;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.type.types.BufferObjectType;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Vector2f;

import java.util.Arrays;

/**
 * Class representing a polygon renderer in OpenGL.
 */
public class GLPolygon extends GLObject {
    @Getter
    @Setter
    private float[] vertex;
    private float[] latestVertex;
    @Getter
    @Setter
    private float[] color;
    private float[] latestColor;
    @Getter
    @Setter
    private float[] texture;
    private float[] latestTexture;

    private boolean isUpdate;

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
        rendering(Location.fromResource("shader/scene/object/VertexShader.vsh"), Location.fromResource("shader/scene/object/FragmentShader.fsh"));
    }

    public void rendering(boolean isFragmentShaderPath, Location location) {
        rendering(isFragmentShaderPath ? Location.fromResource("shader/scene/object/VertexShader.vsh") : location, isFragmentShaderPath ? location : Location.fromResource("shader/scene/object/FragmentShader.fsh"));
    }

    public void updateData() {
        setGLObjectParameter();
        Mesh mesh = getMesh();
        if (mesh == null) {
            throw new MeshBuilderException("MeshBuilder is null.");
        }

        if (!Arrays.equals(vertex, latestVertex)) {
            mesh.updateVBOData(BufferObjectType.POSITIONS_BUFFER, vertex);
        }
        if (!Arrays.equals(color, latestColor)) {
            mesh.updateVBOData(BufferObjectType.COLORS_BUFFER, color);
        }
        if (!Arrays.equals(texture, latestTexture)) {
            mesh.updateVBOData(BufferObjectType.TEXTURE_BUFFER, texture);
        }

        latestVertex = vertex;
        latestColor = color;
        latestTexture = texture;
        vertex = new float[0];
        color = new float[0];
        texture = new float[0];
    }

    public void rendering(Location vertexShaderPath, Location fragmentShaderPath) {
        if (isUpdate) {
            updateData();
        } else {
            isUpdate = true;

            Mesh mesh = Mesh.initMesh()
                    .projectionType(ProjectionType.ORTHOGRAPHIC_PROJECTION)
                    .createBufferObject2D(vertex, color, texture)
                    .builderClose();

            setMesh(mesh);

            setGLObjectParameter();
            create(vertexShaderPath, fragmentShaderPath);
            latestVertex = vertex;
            latestColor = color;
            latestTexture = texture;
            vertex = new float[0];
            color = new float[0];
            texture = new float[0];
        }
    }

    private void setGLObjectParameter() {
        float[] bounds = getBounds();
        setX(bounds[0]);
        setY(bounds[1]);
        setWidth(bounds[2] - bounds[0]);
        setHeight(bounds[3] - bounds[1]);

        Vector2f center = new Vector2f(bounds[0] + getWidth() / 2f, bounds[1] + getHeight() / 2f);
        getTransform().setCenterX(center.x());
        getTransform().setCenterY(center.y());

        getTransform().setAngleX(0);
        getTransform().setAngleY(0);
        getTransform().setAngleZ(0);

        getTransform().setScaleX(1);
        getTransform().setScaleY(1);
        getTransform().setScaleZ(1);
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

            if (x < minX) {
                minX = x;
            }
            if (x > maxX) {
                maxX = x;
            }
            if (y < minY) {
                minY = y;
            }
            if (y > maxY) {
                maxY = y;
            }
        }

        return new float[]{minX, minY, maxX, maxY};
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }
}