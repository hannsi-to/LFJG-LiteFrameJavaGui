package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.renderers.JointType;
import me.hannsi.lfjg.render.renderers.PointType;
import me.hannsi.lfjg.render.system.mesh.MeshBuilder;
import me.hannsi.lfjg.render.system.mesh.ObjectData;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a polygon renderer in OpenGL.
 */
public class GLPolygon<T extends GLPolygon<T>> extends GLObject {
    public static final JointType DEFAULT_JOINT_TYPE = JointType.NONE;
    public static final PointType DEFAULT_POINT_TYPE = PointType.ROUND;
    public static final BlendType DEFAULT_BLEND_TYPE = BlendType.NORMAL;
    protected Vertex currentVertex;
    protected float[] rectUV;
    private final List<Vertex> vertices;
    private DrawType drawType;
    private JointType jointType;
    private PointType pointType;
    private float lineWidth;
    private float pointSize;
    private BlendType blendType;
    private ObjectData objectData;

    public GLPolygon(String name) {
        super(name);

        this.vertices = new ArrayList<>();
        this.drawType = null;
        this.jointType = DEFAULT_JOINT_TYPE;
        this.pointType = DEFAULT_POINT_TYPE;
        this.lineWidth = -1;
        this.pointSize = -1;
        this.blendType = DEFAULT_BLEND_TYPE;
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
    public T drawType(DrawType drawType) {
        this.drawType = drawType;

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T jointType(JointType jointType) {
        this.jointType = jointType;

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T pointType(PointType pointType) {
        this.pointType = pointType;

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T lineWidth(float lineWidth) {
        this.lineWidth = lineWidth;

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T pointSize(float pointSize) {
        this.pointSize = pointSize;

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T blendType(BlendType blendType) {
        this.blendType = blendType;

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T objectData(ObjectData objectData) {
        this.objectData = objectData;

        return (T) this;
    }

    public void end() {
        vertices.add(currentVertex.copy());
    }

    public void rendering(boolean isFragmentShaderPath, Location location) {
        rendering();
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

        Vertex[] vertices = this.vertices.toArray(new Vertex[0]);

        create(
                MeshBuilder.createBuilder()
                        .drawType(drawType)
                        .blendType(blendType)
                        .jointType(jointType)
                        .pointType(pointType)
                        .lineWidth(lineWidth)
                        .pointSize(pointSize)
                        .vertices(vertices)
                        .objectData(objectData)
        );
    }
}