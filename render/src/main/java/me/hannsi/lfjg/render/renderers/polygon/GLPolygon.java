package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.MeshBuilderException;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.BufferObjectType;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.Arrays;

/**
 * Class representing a polygon renderer in OpenGL.
 */
public class GLPolygon<T extends GLPolygon<T>> extends GLObject {
    protected float[] latestVertex;
    protected float[] latestColor;
    protected float[] latestTexture;

    private float[] vertex;
    private float[] color;
    private float[] texture;

    private boolean isUpdate;

    public GLPolygon(String name) {
        super(name);
    }

    public GLPolygon<T> put() {
        return this;
    }

    public GLPolygon<T> vertex(Vector2f vector2f) {
        if (vertex == null) {
            vertex = new float[0];
        }

        vertex = Arrays.copyOf(vertex, vertex.length + 2);

        vertex[vertex.length - 2] = vector2f.x();
        vertex[vertex.length - 1] = vector2f.y();

        return this;
    }

    public GLPolygon<T> color(Color c) {
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

    @SuppressWarnings("unchecked")
    public T uv(float u1, float v1, float u2, float v2) {
        put().uv(new Vector2f(u1, v1)).end();
        put().uv(new Vector2f(u2, v1)).end();
        put().uv(new Vector2f(u2, v2)).end();
        put().uv(new Vector2f(u1, v2)).end();

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T uv(Vector2f vector2f) {
        if (texture == null) {
            texture = new float[0];
        }

        texture = Arrays.copyOf(texture, texture.length + 2);

        texture[texture.length - 2] = vector2f.x();
        texture[texture.length - 1] = vector2f.y();

        return (T) this;
    }

    public void end() {
    }

    public void rendering(boolean isFragmentShaderPath, Location location) {
        rendering();
    }

    public void updateData() {
        setGLObjectParameter();
        Mesh mesh = getMesh();
        if (mesh == null) {
            throw new MeshBuilderException("MeshBuilder is null.");
        }

        if (!Arrays.equals(vertex, latestVertex)) {
            mesh.updateVBOData(getDrawType(), BufferObjectType.POSITION_2D_BUFFER, vertex);
        }
        if (!Arrays.equals(color, latestColor)) {
            mesh.updateVBOData(getDrawType(), BufferObjectType.COLOR_BUFFER, color);
        }
        if (!Arrays.equals(texture, latestTexture)) {
            mesh.updateVBOData(getDrawType(), BufferObjectType.TEXTURE_BUFFER, texture);
        }

        latestVertex = vertex;
        latestColor = color;
        latestTexture = texture;
        vertex = new float[0];
        color = new float[0];
        texture = new float[0];
    }

    public void rendering() {
        if (isUpdate) {
            updateData();
        } else {
            isUpdate = true;

            Mesh mesh = Mesh.createMesh().projectionType(ProjectionType.ORTHOGRAPHIC_PROJECTION).createBufferObject2D(getDrawType(), vertex, color, texture).builderClose();

            setMesh(mesh);

            setGLObjectParameter();
            create();
            latestVertex = vertex;
            latestColor = color;
            latestTexture = texture;
            vertex = new float[0];
            color = new float[0];
            texture = new float[0];
        }
    }

    private void setGLObjectParameter() {
        getTransform().setBound(getBounds());

        Vector2f center = new Vector2f(getTransform().getX() + getTransform().getWidth() / 2f, getTransform().getY() + getTransform().getHeight() / 2f);
        getTransform().setCenterX(center.x());
        getTransform().setCenterY(center.y());

        getTransform().setAngleX(0);
        getTransform().setAngleY(0);
        getTransform().setAngleZ(0);

        getTransform().setScaleX(1);
        getTransform().setScaleY(1);
        getTransform().setScaleZ(1);
    }

    private Vector4f getBounds() {
        if (vertex == null || vertex.length == 0) {
            return new Vector4f(0, 0, 0, 0);
        }

        float minX = vertex[0];
        float minY = vertex[1];
        float maxX = vertex[0];
        float maxY = vertex[1];


        for (int v = 0; v < vertex.length; v += 2) {
            minX = Math.min(minX, vertex[v]);
            minY = Math.min(minY, vertex[v + 1]);
            maxX = Math.max(maxX, vertex[v]);
            maxY = Math.max(maxY, vertex[v + 1]);
        }

        if (getLineWidth() != -1 || getPointSize() != -1) {
            float expandLine = getLineWidth() / 2.0f;
            float expandPoint = getPointSize() / 2.0f;

            float expand = Math.max(expandLine, expandPoint);

            minX -= expand;
            minY -= expand;
            maxX += expand;
            maxY += expand;
        }

        return new Vector4f(minX, minY, maxX, maxY);
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
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