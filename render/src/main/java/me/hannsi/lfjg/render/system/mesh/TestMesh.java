package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.MeshException;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.InstanceParameter;
import me.hannsi.lfjg.render.renderers.JointType;
import me.hannsi.lfjg.render.renderers.PointType;
import me.hannsi.lfjg.render.system.rendering.DrawType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class TestMesh {
    private final List<Builder> pendingBuilders;
    private final int vaoId;
    private boolean needRepack;
    private int currentIndex;
    private int vertexCount;
    private int indexCount;
    private int commandCount;

    TestMesh() {
        this.vaoId = glGenVertexArrays();
        this.pendingBuilders = new ArrayList<>();
        this.needRepack = false;

        this.currentIndex = 0;
        this.vertexCount = 0;
        this.indexCount = 0;
        this.commandCount = 0;
    }

    public static TestMesh createMesh() {
        return new TestMesh();
    }

    public TestMesh initBufferObject() {
        glStateCache.bindVertexArrayForce(vaoId);

        persistentMappedVBO.link();
        persistentMappedEBO.link();
        persistentMappedIBO.link();
        persistentMappedSSBO.link();

        return this;
    }

    public TestMesh addObject(Builder builder) {
        pendingBuilders.add(builder);

        needRepack = true;

        return this;
    }

    public TestMesh build() {
        currentIndex = 0;
        vertexCount = 0;
        indexCount = 0;
        commandCount = 0;
        int baseInstance = 0;
        int objectCount = 0;

        persistentMappedVBO.reset();
        persistentMappedEBO.reset();
        persistentMappedIBO.reset();
        persistentMappedSSBO.resetBindingPoint(INSTANCE_PARAMETERS_BINDING_POINT);

        for (Builder builder : pendingBuilders) {
            TestElementPair elementPair = TestPolygonTriangulator.createPolygonTriangulator()
                    .drawType(builder.drawType)
                    .lineWidth(builder.lineWidth)
                    .lineJointType(builder.jointType)
                    .pointSize(builder.pointSize)
                    .pointType(builder.pointType)
                    .projectionType(builder.projectionType)
                    .vertices(builder.vertices)
                    .process()
                    .getResult();

            builder.setBytes(elementPair.vertices.length * Vertex.BYTES + (long) elementPair.indices.length * Float.BYTES + DrawElementsIndirectCommand.BYTES);
            builder.setBaseCommand(commandCount);

            int baseVertex = vertexCount;

            int startOffset = writeGeometry(elementPair);

            ObjectData objectData = builder.objectData;
            for (InstanceParameter instanceParameter : objectData.getInstanceParameters()) {
                instanceParameter.objectId(objectCount);
            }
            for (int i = 0; i < objectData.drawElementsIndirectCommand.instanceCount; i++) {
                persistentMappedSSBO.addInstanceParameter(INSTANCE_PARAMETERS_BINDING_POINT, objectData.getInstanceParameters()[i]);
            }

            builder.objectData.drawElementsIndirectCommand.count = elementPair.indices.length;
            builder.objectData.drawElementsIndirectCommand.firstIndex = startOffset;
            builder.objectData.drawElementsIndirectCommand.baseVertex = baseVertex;
            builder.objectData.drawElementsIndirectCommand.baseInstance = baseInstance;
            writeIndirectCommand(builder.objectData.drawElementsIndirectCommand);

            if (builder.objectIdPointer.isNullptr()) {
                int id = glObjectPool.createId(builder);
                builder.objectIdPointer.setValue(id);
            } else {
                glObjectPool.createObject(builder.objectIdPointer.getValue(), builder);
            }

            baseInstance += builder.objectData.drawElementsIndirectCommand.instanceCount;
            objectCount++;
        }

        return this;
    }

    public TestMesh deleteObject(int objectId) {
        return deleteObject(null, objectId);
    }

    public TestMesh deleteObject(List<IntRef> ids, int objectId) {
        if (glObjectPool.getDeletedObjects().get(objectId) != null) {
            new LogGenerator(
                    "DeleteObject Info",
                    "ObjectId: " + objectId,
                    "Message: This object ID has already been deleted."
            ).logging(getClass(), DebugLevel.INFO);

            return this;
        }

        TestMesh.Builder builder = glObjectPool.getBuilder(objectId);
        if (builder == null) {
            throw new MeshException("This object ID does not exist. objectId: " + objectId);
        }

        glObjectPool.createDeletedObject(objectId);
        builder.draw = false;

        if (ids == null) {
            return this;
        }

        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i).getValue() == objectId) {
                ids.remove(ids.get(i));
                break;
            }
        }

        return this;
    }

    public TestMesh directDeleteObjects() {
        new LogGenerator("Direct Delete Objects Start")
                .kv("Total Objects", glObjectPool.getObjects().size())
                .kv("Deleted Objects", glObjectPool.getDeletedObjects().size())
                .kvBytes("VBO Size Before", persistentMappedVBO.getMemorySize())
                .kvBytes("EBO Size Before", persistentMappedEBO.getMemorySize())
                .logging(getClass(), DebugLevel.INFO);

        pendingBuilders.clear();
        for (Map.Entry<Integer, Builder> entry : glObjectPool.getObjects().entrySet()) {
            if (glObjectPool.getDeletedObjects().containsKey(entry.getKey())) {
                continue;
            }

            for (InstanceParameter instanceParameter : entry.getValue().getObjectData().getInstanceParameters()) {
                instanceParameter.setDirtyFlag(true);
            }
            pendingBuilders.add(entry.getValue());
        }

        needRepack = true;

        new LogGenerator("Direct Delete Objects End")
                .kv("Final Active Objects", glObjectPool.getObjects().size())
                .kv("Total Vertices", vertexCount)
                .kv("Total Commands (IBO)", commandCount)
                .logging(getClass(), DebugLevel.INFO);

        return this;
    }

    public TestMesh restoreDeleteObject(int objectId) {
        TestMesh.Builder builder = glObjectPool.getBuilder(objectId);
        if (builder == null) {
            throw new MeshException("This object ID does not exist. objectId: " + objectId);
        }

        if (builder.draw) {
            new LogGenerator(
                    "RestoreDeleteObject Info",
                    "ObjectId: " + objectId,
                    "Message: This object ID is already authorized for drawing."
            ).logging(getClass(), DebugLevel.INFO);

            return this;
        }

        glObjectPool.restoreDeletedObject(objectId);
        builder.draw = true;

        return this;
    }

    public TestMesh updateObjectData(int objectId, ObjectData newObjectData) {
        TestMesh.Builder builder = glObjectPool.getBuilder(objectId);
        if (builder == null) {
            throw new MeshException("Object ID not found: " + objectId);
        }

        int baseInstanceIndex = builder.getObjectData().drawElementsIndirectCommand.baseInstance;

        int count = Math.min(builder.objectData.drawElementsIndirectCommand.instanceCount, newObjectData.drawElementsIndirectCommand.instanceCount);

        for (int i = 0; i < count; i++) {
            persistentMappedSSBO.updateInstanceParameter(INSTANCE_PARAMETERS_BINDING_POINT, baseInstanceIndex + i, newObjectData.getInstanceParameters()[i]);
        }

        builder.objectData = newObjectData;

        return this;
    }

    private void writeIndirectCommand(DrawElementsIndirectCommand drawElementsIndirectCommand) {
        persistentMappedIBO.add(drawElementsIndirectCommand);
        commandCount++;
    }

    private int writeGeometry(TestElementPair elementPair) {
        for (Vertex vertex : elementPair.vertices) {
            persistentMappedVBO.add(vertex);
        }

        int startOffset = indexCount;
        for (int index : elementPair.indices) {
            persistentMappedEBO.add(index);
        }

        vertexCount += elementPair.vertices.length;
        indexCount += elementPair.indices.length;
        return startOffset;
    }

    public void debugLogging(boolean vaoID, boolean currentIndex, boolean totalVertexCount, boolean vbo, boolean ebo, boolean ibo) {
        List<String> log = new ArrayList<>();
        if (vaoID) {
            log.add("VAO ID: " + vaoId);
        }
        if (currentIndex) {
            log.add("Current Index (rotation buffer): " + this.currentIndex);
        }
        if (totalVertexCount) {
            log.add("Total Vertex Count: " + vertexCount);
        }
        if (vbo) {
            log.add(
                    new LogGenerator(
                            "VBO",
                            "BufferID: " + persistentMappedVBO.getBufferId(),
                            "Capacity: " + persistentMappedVBO.getMemorySize(),
                            "Pointer: " + persistentMappedVBO.getPointer()
                    ).createLog()
            );
        }
        if (ebo) {
            log.add(
                    new LogGenerator(
                            "EBO",
                            "BufferID: " + persistentMappedEBO.getBufferId(),
                            "Capacity: " + persistentMappedEBO.getMemorySize(),
                            "Pointer: " + persistentMappedEBO.getPointer()
                    ).createLog()
            );
        }
        if (ibo) {
            log.add(
                    new LogGenerator(
                            "IBO",
                            "BufferID: " + persistentMappedIBO.getBufferId(),
                            "Capacity: " + persistentMappedIBO.getMemorySize(),
                            "Pointer: " + persistentMappedIBO.getPointer()
                    ).createLog()
            );
        }

        new LogGenerator(
                getClass().getSimpleName() + " Debug Message",
                log.toArray(new String[0])
        ).bar("=").logging(getClass(), DebugLevel.DEBUG);


        new LogGenerator("GLObjectPool", glObjectPool.toString()).logging(getClass(), DebugLevel.DEBUG);
    }

    public List<Builder> getPendingBuilders() {
        return pendingBuilders;
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getIndexCount() {
        return indexCount;
    }

    public int getCommandCount() {
        return commandCount;
    }

    public boolean isNeedRepack() {
        return needRepack;
    }

    public void setNeedRepack(boolean needRepack) {
        this.needRepack = needRepack;
    }

    public TestMesh rotate() {
        currentIndex = (currentIndex + 1) % MeshConstants.DEFAULT_BUFFER_COUNT;
        return this;
    }

    public void cleanup() {

    }

    public static class Builder {
        private final IntRef objectIdPointer = new IntRef();
        private final int baseInstance = 0;
        private Vertex[] vertices = null;
        private DrawType drawType = DrawType.TRIANGLES;
        private BlendType blendType = BlendType.PREMULTIPLIED_ALPHA;
        private float lineWidth = -1f;
        private float pointSize = -1f;
        private JointType jointType = JointType.NONE;
        private PointType pointType = PointType.SQUARE;
        private ObjectData objectData = new ObjectData(1);
        private ProjectionType projectionType = ProjectionType.ORTHOGRAPHIC_PROJECTION;
        private boolean draw = true;
        private long bytes = 0L;
        private int baseCommand = 0;

        Builder() {
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public Builder blendType(BlendType blendType) {
            this.blendType = blendType;

            return this;
        }

        public Builder drawType(DrawType drawType) {
            this.drawType = drawType;

            return this;
        }

        public Builder jointType(JointType jointType) {
            this.jointType = jointType;

            return this;
        }

        public Builder lineWidth(float lineWidth) {
            this.lineWidth = lineWidth;

            return this;
        }

        public Builder pointType(PointType pointType) {
            this.pointType = pointType;

            return this;
        }

        public Builder projectionType(ProjectionType projectionType) {
            this.projectionType = projectionType;

            return this;
        }

        public Builder vertices(Vertex... vertices) {
            this.vertices = vertices;

            return this;
        }

        public Builder pointSize(float pointSize) {
            this.pointSize = pointSize;

            return this;
        }

        public Builder objectData(ObjectData objectData) {
            this.objectData = objectData;

            return this;
        }

        public Builder draw(boolean draw) {
            this.draw = draw;

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

        public int getBaseInstance() {
            return baseInstance;
        }

        public int getBaseCommand() {
            return baseCommand;
        }

        void setBaseCommand(int baseCommand) {
            this.baseCommand = baseCommand;
        }
    }
}
