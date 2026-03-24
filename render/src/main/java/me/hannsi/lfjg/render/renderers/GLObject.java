package me.hannsi.lfjg.render.renderers;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.render.system.mesh.MeshBuilder;
import me.hannsi.lfjg.render.system.mesh.ObjectData;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.render.LFJGRenderContext.*;

public class GLObject<T extends GLObject<T>> {
    private final String name;
    private final MeshBuilder meshBuilder;
    private final List<Vertex> vertices;
    private IntRef objectId;
    private Vertex currentVertex;

    protected GLObject(String name, boolean useDrawFrame) {
        this.name = name;
        this.objectId = new IntRef();
        this.meshBuilder = MeshBuilder.createBuilder();
        this.vertices = new ArrayList<>();

        if (useDrawFrame) {
            useDrawFrameObjects.add(this);
        }
    }

    protected GLObject(String name) {
        this(name, false);
    }

    @SuppressWarnings("unchecked")
    public T update() {
        rendering();

        return (T) this;
    }

    public void drawFrame() {

    }

    public GLObject<T> put() {
        currentVertex = new Vertex(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        return this;
    }

    public GLObject<T> put(Vertex vertex) {
        currentVertex = vertex;

        return this;
    }

    public GLObject<T> position(float x, float y, float z) {
        currentVertex.replaceXYZ(x, y, z);

        return this;
    }

    public GLObject<T> position(float x, float y) {
        return position(x, y, 0);
    }

    public GLObject<T> color(float red, float green, float blue, float alpha) {
        currentVertex.replaceColor(red, green, blue, alpha);

        return this;
    }

    public GLObject<T> color(Color color) {
        return color(color.getRedF(), color.getGreenF(), color.getBlueF(), color.getAlphaF());
    }

    public GLObject<T> uv(float u, float v) {
        currentVertex.replaceUV(u, v);

        return this;
    }

    public GLObject<T> normal(float normalX, float normalY, float normalZ) {
        currentVertex.replaceNormal(normalX, normalY, normalZ);

        return this;
    }

    public GLObject<T> end() {
        vertices.add(currentVertex);

        return this;
    }

    public GLObject<T> drawType(DrawType drawType) {
        meshBuilder.drawType(drawType);

        return this;
    }

    public GLObject<T> paintType(PaintType paintType) {
        meshBuilder.paintType(paintType);

        return this;
    }

    public GLObject<T> blendType(BlendType blendType) {
        meshBuilder.blendType(blendType);

        return this;
    }

    public GLObject<T> strokeJointType(JointType strokeJointType) {
        meshBuilder.strokeJoinType(strokeJointType);

        return this;
    }

    public GLObject<T> pointType(PointType pointType) {
        meshBuilder.pointType(pointType);

        return this;
    }

    public GLObject<T> strokeWidth(float strokeWidth) {
        meshBuilder.strokeWidth(strokeWidth);

        return this;
    }

    public GLObject<T> pointSize(float pointSize) {
        meshBuilder.pointSize(pointSize);

        return this;
    }

    public GLObject<T> objectData(ObjectData objectData) {
        meshBuilder.objectData(objectData);

        return this;
    }

    protected void rendering() {
        meshBuilder.vertices(vertices.toArray(new Vertex[0]));

        objectId = meshBuilder.getObjectIdPointer();

        if (!objectId.isNullptr() && glObjectPool.getBuilder(objectId.getValue(), false) != null) {
            return;
        }

        mesh.addObject(meshBuilder);
    }

    public String getName() {
        return name;
    }

    public int getObjectId() {
        return objectId.getValue();
    }

    public MeshBuilder getMeshBuilder() {
        return meshBuilder;
    }

    public ObjectData getObjectData() {
        return meshBuilder.getObjectData();
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public interface PaintTypeStep<T> {
        T fill();

        StrokeJointTypeStep<T> stroke();
    }

    public interface StrokeJointTypeStep<T> {
        strokeWidthStep<T> strokeJointType(JointType strokeJointType);
    }

    public interface strokeWidthStep<T> {
        T strokeWidth(float strokeWidth);
    }

    public abstract static class AbstractGLObjectBuilder<T extends GLObject<T>> implements PaintTypeStep<T>, StrokeJointTypeStep<T>, strokeWidthStep<T> {
        public PaintType paintType;
        public JointType strokeJoinType;
        public float strokeWidth = -1;

        @Override
        public T fill() {
            this.paintType = PaintType.FILL;
            this.strokeJoinType = JointType.NONE;
            this.strokeWidth = -1;

            return build();
        }

        @Override
        public StrokeJointTypeStep<T> stroke() {
            this.paintType = PaintType.STROKE;

            return this;
        }

        @Override
        public strokeWidthStep<T> strokeJointType(JointType strokeJointType) {
            this.strokeJoinType = strokeJointType;
            this.paintType = PaintType.STROKE;

            return this;
        }

        @Override
        public T strokeWidth(float strokeWidth) {
            this.strokeWidth = strokeWidth;
            this.paintType = PaintType.STROKE;

            return build();
        }

        protected T build() {
            T polygon = createOrGet();

            polygon
                    .paintType(paintType)
                    .strokeJointType(strokeJoinType)
                    .strokeWidth(strokeWidth);

            return polygon.update();
        }

        protected abstract T createOrGet();
    }
}