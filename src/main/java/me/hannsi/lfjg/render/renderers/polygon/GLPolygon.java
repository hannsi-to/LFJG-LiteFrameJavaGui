package me.hannsi.lfjg.render.renderers.polygon;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.debug.exceptions.render.meshBuilder.MeshBuilderException;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.Mesh;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.location.FileLocation;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.BufferObjectType;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import me.hannsi.lfjg.utils.type.types.UpdateBufferType;
import org.joml.Vector2f;

import java.util.Arrays;

/**
 * Class representing a polygon renderer in OpenGL.
 */
public class GLPolygon extends GLObject {
    public static final UpdateBufferType DEFAULT_UPDATE_BUFFER_TYPE = UpdateBufferType.MAP_BUFFER_RANGE;

    @Getter
    private UpdateBufferType updateBufferType;
    @Getter
    @Setter
    private float[] vertex;
    @Getter
    @Setter
    private float[] color;
    @Getter
    @Setter
    private float[] texture;
    private boolean isUpdate;

    /**
     * Constructs a new GLPolygon with the specified name.
     *
     * @param name the name of the polygon renderer
     */
    public GLPolygon(String name) {
        super(name);

        this.updateBufferType = DEFAULT_UPDATE_BUFFER_TYPE;
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
        rendering(new ResourcesLocation("shader/scene/object/VertexShader.vsh"), new ResourcesLocation("shader/scene/object/FragmentShader.fsh"));
    }

    public void rendering(boolean isFragmentShaderPath, FileLocation fileLocation) {
        rendering(isFragmentShaderPath ? new ResourcesLocation("shader/scene/object/VertexShader.vsh") : fileLocation, isFragmentShaderPath ? fileLocation : new ResourcesLocation("shader/scene/object/FragmentShader.fsh"));
    }

    public void updateSubData() {
        setGLObjectParameter();
        Mesh mesh = getMesh();
        if (mesh == null) {
            throw new MeshBuilderException("MeshBuilder is null.");
        }

        mesh.updateVBOSubData(BufferObjectType.POSITIONS_BUFFER, vertex);
        mesh.updateVBOSubData(BufferObjectType.COLORS_BUFFER, color);
        mesh.updateVBOSubData(BufferObjectType.TEXTURE_BUFFER, texture);

        vertex = new float[0];
        color = new float[0];
        texture = new float[0];
    }

    public void updateData() {
        setGLObjectParameter();
        Mesh mesh = getMesh();
        if (mesh == null) {
            throw new MeshBuilderException("MeshBuilder is null.");
        }

        mesh.updateVBOData(BufferObjectType.POSITIONS_BUFFER, vertex);
        mesh.updateVBOData(BufferObjectType.COLORS_BUFFER, color);
        mesh.updateVBOData(BufferObjectType.TEXTURE_BUFFER, texture);

        vertex = new float[0];
        color = new float[0];
        texture = new float[0];
    }

    public void updateMapBufferRange() {
        setGLObjectParameter();
        Mesh mesh = getMesh();
        if (mesh == null) {
            throw new MeshBuilderException("MeshBuilder is null.");
        }

        mesh.updateVBOMapBufferRange(BufferObjectType.POSITIONS_BUFFER, vertex);
        mesh.updateVBOMapBufferRange(BufferObjectType.COLORS_BUFFER, color);
        mesh.updateVBOMapBufferRange(BufferObjectType.TEXTURE_BUFFER, texture);

        vertex = new float[0];
        color = new float[0];
        texture = new float[0];
    }

    public void rendering(FileLocation vertexShaderPath, FileLocation fragmentShaderPath) {
        if (isUpdate) {
            switch (updateBufferType) {
                case BUFFER_DATA -> updateData();
                case BUFFER_SUB_DATA -> updateSubData();
                case MAP_BUFFER_RANGE -> updateMapBufferRange();
                default -> throw new IllegalStateException("Unexpected value: " + updateBufferType);
            }
        } else {
            isUpdate = true;

            Mesh mesh = Mesh.initMesh()
                    .projectionType(ProjectionType.ORTHOGRAPHIC_PROJECTION)
                    .createBufferObjects(vertex, color, texture)
                    .builderClose();

            setMesh(mesh);

            setGLObjectParameter();
            create(vertexShaderPath, fragmentShaderPath);
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
        setCenterX(center.x());
        setCenterY(center.y());

        setAngleX(0);
        setAngleY(0);
        setAngleZ(0);

        setScaleX(1);
        setScaleY(1);
        setScaleZ(1);
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

    public GLPolygon setUpdateBufferType(UpdateBufferType updateBufferType) {
        this.updateBufferType = updateBufferType;
        return this;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }
}