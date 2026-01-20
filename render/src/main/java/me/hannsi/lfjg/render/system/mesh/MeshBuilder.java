package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.MeshBuilderException;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.JointType;
import me.hannsi.lfjg.render.renderers.PointType;
import me.hannsi.lfjg.render.system.rendering.DrawType;

import static me.hannsi.lfjg.render.LFJGRenderContext.*;

public class MeshBuilder {
    private final IntRef objectIdPointer = new IntRef();
    private Vertex[] vertices = null;
    private DrawType drawType = DrawType.TRIANGLES;
    private BlendType blendType = BlendType.PREMULTIPLIED_ALPHA;
    private float lineWidth = -1f;
    private float pointSize = -1f;
    private JointType jointType = JointType.NONE;
    private PointType pointType = PointType.SQUARE;
    private ObjectData objectData = new ObjectData(1);
    private boolean flagObjectData = true;
    private ProjectionType projectionType = ProjectionType.ORTHOGRAPHIC_PROJECTION;
    private boolean draw = true;
    private boolean flagDraw = true;
    private long bytes = 0L;
    private int baseCommand = 0;

    MeshBuilder() {
    }

    public static MeshBuilder createBuilder() {
        return new MeshBuilder();
    }

    public MeshBuilder blendType(BlendType blendType) {
        this.blendType = blendType;

        return this;
    }

    public MeshBuilder drawType(DrawType drawType) {
        this.drawType = drawType;

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder jointType(JointType jointType) {
        this.jointType = jointType;

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder lineWidth(float lineWidth) {
        this.lineWidth = lineWidth;

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder pointType(PointType pointType) {
        this.pointType = pointType;

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder projectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder vertices(Vertex... vertices) {
        this.vertices = vertices;

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder pointSize(float pointSize) {
        this.pointSize = pointSize;

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder objectData(ObjectData objectData) {
        this.objectData = objectData;

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder draw(boolean draw) {
        this.draw = draw;

        if (!objectIdPointer.isNullptr() && glObjectPool.getDeletedBuilder(objectIdPointer.getValue(), false) != null) {
            throw new MeshBuilderException("Do not execute the draw method on deleted objects. Id: " + objectIdPointer);
        }

        flagDraw = true;

        needUpdateBuilders.add(objectIdPointer);

        return this;
    }

    public IntRef getObjectIdPointer() {
        return objectIdPointer;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public DrawType getDrawType() {
        return drawType;
    }

    public BlendType getBlendType() {
        return blendType;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public float getPointSize() {
        return pointSize;
    }

    public JointType getJointType() {
        return jointType;
    }

    public PointType getPointType() {
        return pointType;
    }

    public ObjectData getObjectData() {
        flagObjectData = true;

        needUpdateBuilders.add(objectIdPointer);

        return objectData;
    }

    public ProjectionType getProjectionType() {
        return projectionType;
    }

    public boolean isDraw() {
        return draw;
    }

    public long getBytes() {
        return bytes;
    }

    void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public int getBaseCommand() {
        return baseCommand;
    }

    void setBaseCommand(int baseCommand) {
        this.baseCommand = baseCommand;
    }

    public boolean isFlagDraw() {
        return flagDraw;
    }

    public void setFlagDraw(boolean flagDraw) {
        this.flagDraw = flagDraw;
    }

    public boolean isFlagObjectData() {
        return flagObjectData;
    }

    public void setFlagObjectData(boolean flagObjectData) {
        this.flagObjectData = flagObjectData;
    }
}
