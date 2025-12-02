package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.renderers.JointType;
import me.hannsi.lfjg.render.renderers.PointType;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.render.LFJGRenderContext.MESH;

/**
 * Class representing a polygon renderer in OpenGL.
 */
public class GLPolygon<T extends GLPolygon<T>> extends GLObject {
    public static final JointType DEFAULT_JOINT_TYPE = JointType.NONE;
    public static final PointType DEFAULT_POINT_TYPE = PointType.ROUND;
    private final List<Vertex> vertices;
    protected Vertex currentVertex;
    protected float[] rectUV;
    private List<Vertex> lastVertices;
    private DrawType drawType;
    private JointType jointType;
    private PointType pointType;
    private float lineWidth;
    private float pointSize;
    private boolean isUpdate;

    public GLPolygon(String name) {
        super(name);

        this.lastVertices = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.drawType = null;
        this.jointType = DEFAULT_JOINT_TYPE;
        this.pointType = DEFAULT_POINT_TYPE;
        this.lineWidth = -1;
        this.pointSize = -1;
        this.isUpdate = false;
        this.currentVertex = null;
        this.rectUV = new float[4];
    }

    public GLPolygon<T> put() {
        currentVertex = new Vertex(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        return this;
    }

    public GLPolygon<T> position(Vector2f positions) {
        currentVertex.x = positions.x;
        currentVertex.y = positions.y;
        currentVertex.z = 0;

        return this;
    }

    public GLPolygon<T> color(Color color) {
        currentVertex.red = color.getRedF();
        currentVertex.green = color.getGreenF();
        currentVertex.blue = color.getBlueF();
        currentVertex.alpha = color.getAlphaF();

        return this;
    }

    @SuppressWarnings("unchecked")
    public T rectUV(float u1, float v1, float u2, float v2) {
        rectUV[0] = u1;
        rectUV[1] = v1;
        rectUV[2] = u2;
        rectUV[3] = v2;

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T uv(Vector2f uv) {
        currentVertex.u = uv.x;
        currentVertex.v = uv.y;

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setDrawType(DrawType drawType) {
        this.drawType = drawType;

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setJointType(JointType jointType) {
        this.jointType = jointType;

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setPointType(PointType pointType) {
        this.pointType = pointType;

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setPointSize(float pointSize) {
        this.pointSize = pointSize;

        return (T) this;
    }

    public void end() {
        vertices.add(currentVertex.copy());
    }

    public void rendering(boolean isFragmentShaderPath, Location location) {
        rendering();
    }

    public void updateData() {
//        setGLObjectParameter();
//
//        if (!Arrays.equals(vertex, latestVertex)) {
//            mesh.updateVBOData(drawType, BufferObjectType.POSITION_BUFFER, vertex);
//        }
//        if (!Arrays.equals(color, latestColor)) {
//            mesh.updateVBOData(drawType, BufferObjectType.COLOR_BUFFER, color);
//        }
//        if (!Arrays.equals(texture, latestTexture)) {
//            mesh.updateVBOData(drawType, BufferObjectType.TEXTURE_BUFFER, texture);
//        }
//
//        latestVertex = vertex;
//        latestColor = color;
//        latestTexture = texture;
//        vertex = new float[0];
//        color = new float[0];
//        texture = new float[0];
    }

    public void rendering() {
        vertices.get(0).u = rectUV[0];
        vertices.get(0).v = rectUV[1];
        vertices.get(1).u = rectUV[2];
        vertices.get(1).v = rectUV[1];
        vertices.get(2).u = rectUV[2];
        vertices.get(2).v = rectUV[3];
        vertices.get(3).u = rectUV[0];
        vertices.get(3).v = rectUV[3];

        if (isUpdate) {
            updateData();
        } else {
            isUpdate = true;

            Vertex[] vertices = this.vertices.toArray(new Vertex[0]);
            MESH.addObject(ProjectionType.ORTHOGRAPHIC_PROJECTION, drawType, lineWidth, jointType, pointSize, pointType, vertices);

            setGLObjectParameter();
            create();
            lastVertices = this.vertices;
        }

        rectUV = new float[4];
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
//        if (vertex == null || vertex.length == 0) {
//            return new Vector4f(0, 0, 0, 0);
//        }
//
//        float minX = vertex[0];
//        float minY = vertex[1];
//        float maxX = vertex[0];
//        float maxY = vertex[1];
//
//
//        for (int v = 0; v < vertex.length; v += 2) {
//            minX = Math.min(minX, vertex[v]);
//            minY = Math.min(minY, vertex[v + 1]);
//            maxX = Math.max(maxX, vertex[v]);
//            maxY = Math.max(maxY, vertex[v + 1]);
//        }
//
//        if (getLineWidth() != -1 || getPointSize() != -1) {
//            float expandLine = getLineWidth() / 2.0f;
//            float expandPoint = getPointSize() / 2.0f;
//
//            float expand = Math.max(expandLine, expandPoint);
//
//            minX -= expand;
//            minY -= expand;
//            maxX += expand;
//            maxY += expand;
//        }
//
//        return new Vector4f(minX, minY, maxX, maxY);

        return null;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }
}