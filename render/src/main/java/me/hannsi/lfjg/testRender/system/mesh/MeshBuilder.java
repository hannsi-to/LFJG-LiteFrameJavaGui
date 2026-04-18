package me.hannsi.lfjg.testRender.system.mesh;

import me.hannsi.lfjg.core.event.events.CleanupEvent;
import me.hannsi.lfjg.core.utils.Cleanup;
import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.testRender.debug.exceptions.render.mesh.MeshBuilderException;
import me.hannsi.lfjg.testRender.effect.system.EffectBase;
import me.hannsi.lfjg.testRender.effect.system.EffectCache;
import me.hannsi.lfjg.testRender.renderers.BlendType;
import me.hannsi.lfjg.testRender.renderers.JointType;
import me.hannsi.lfjg.testRender.renderers.PaintType;
import me.hannsi.lfjg.testRender.renderers.PointType;
import me.hannsi.lfjg.testRender.system.rendering.DrawType;

import static me.hannsi.lfjg.testRender.LFJGRenderContext.*;

public class MeshBuilder implements Cleanup {
    private final IntRef objectIdPointer = new IntRef();
    private final EffectCache effectCache = EffectCache.createEffectCache();
    private Vertex[] vertices = null;
    private DrawType drawType = DrawType.TRIANGLES;
    private BlendType blendType = BlendType.UI_DEFAULT;
    private PaintType paintType = PaintType.FILL;
    private float strokeWidth = 1f;
    private float pointSize = 1f;
    private JointType strokeJointType = JointType.NONE;
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
        if (blendType != null) {
            this.blendType = blendType;
        }

        return this;
    }

    public MeshBuilder paintType(PaintType paintType) {
        if (paintType != null) {
            this.paintType = paintType;
        }

        return this;
    }

    public MeshBuilder drawType(DrawType drawType) {
        if (drawType != null) {
            this.drawType = drawType;
        }

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder strokeJoinType(JointType strokeJointType) {
        if (strokeJointType != null) {
            this.strokeJointType = strokeJointType;
        }

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder strokeWidth(float strokeWidth) {
        if (strokeWidth != -1f) {
            this.strokeWidth = strokeWidth;
        }

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder pointType(PointType pointType) {
        if (pointType != null) {
            this.pointType = pointType;
        }

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder projectionType(ProjectionType projectionType) {
        if (projectionType != null) {
            this.projectionType = projectionType;
        }

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder vertices(Vertex... vertices) {
        if (vertices != null) {
            this.vertices = vertices;
        }

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder pointSize(float pointSize) {
        if (pointSize != -1f) {
            this.pointSize = pointSize;
        }

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder objectData(ObjectData objectData) {
        if (objectData != null) {
            this.objectData = objectData;
        }

        mesh.setNeedRepack(true);

        return this;
    }

    public MeshBuilder addEffect(EffectBase effect) {
        if (effect != null) {
            this.effectCache.createCache(effect);
        }

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

    public PaintType getPaintType() {
        return paintType;
    }

    public BlendType getBlendType() {
        return blendType;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public float getPointSize() {
        return pointSize;
    }

    public JointType getStrokeJointType() {
        return strokeJointType;
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

    public EffectCache getEffectCache() {
        return effectCache;
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

    @Override
    public boolean cleanup(CleanupEvent event) {
        objectIdPointer.setNullptr();

        boolean verticesState = true;
        for (Vertex vertex : vertices) {
            if (!vertex.cleanup(event)) {
                verticesState = false;
            }
        }
        vertices = null;

        return event.debug(MeshBuilder.class, new CleanupEvent.CleanupData(MeshBuilder.class)
                .addData("objectIdPointer", objectIdPointer.isNullptr(), objectIdPointer.getValue())
                .addData("vertices", verticesState, vertices)
                .addData("objectData", objectData.cleanup(event), objectData)
        );
    }
}
