package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.MeshException;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.JointType;
import me.hannsi.lfjg.render.renderers.PointType;
import me.hannsi.lfjg.render.system.rendering.DrawType;

import java.util.*;

import static me.hannsi.lfjg.core.SystemSetting.MESH_DEBUG_DIRECT_DELETE_OBJECT_REINSERTION;
import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class TestMesh {
    private final int vaoId;
    private int initialVBOCapacity;
    private int initialEBOCapacity;
    private int initialIBOCapacity;
    private int currentIndex;
    private int vertexCount;
    private int instanceCount;

    TestMesh(int initialVBOCapacity, int initialEBOCapacity, int initialIBOCapacity) {
        this.initialVBOCapacity = initialVBOCapacity;
        this.initialEBOCapacity = initialEBOCapacity;
        this.initialIBOCapacity = initialIBOCapacity;

        this.vaoId = glGenVertexArrays();
        this.currentIndex = 0;
        this.vertexCount = 0;
        this.instanceCount = 0;
    }

    public static TestMesh createMesh(int initialVBOCapacity, int initialEBOCapacity, int initialIBOCapacity) {
        return new TestMesh(initialVBOCapacity, initialEBOCapacity, initialIBOCapacity);
    }

    public TestMesh initBufferObject() {
        GL_STATE_CACHE.bindVertexArrayForce(vaoId);

        PERSISTENT_MAPPED_VBO.createVertexAttribute(vaoId, BufferObjectType.POSITION_BUFFER, BufferObjectType.COLOR_BUFFER, BufferObjectType.TEXTURE_BUFFER, BufferObjectType.NORMAL_BUFFER)
                .syncToGPU();

        PERSISTENT_MAPPED_EBO.linkVertexArrayObject(vaoId)
                .syncToGPU();

        PERSISTENT_MAPPED_IBO.syncToGPU();

        return this;
    }

    public TestMesh addObject(Builder builder) {
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

        int baseVertex = vertexCount;
        int startOffset = writeGeometry(elementPair);
        writeInstanceData(builder.instanceData);

        DrawElementsIndirectCommand command = writeIndirectCommand(
                elementPair.indices.length,
                builder.instanceData.instanceCount,
                startOffset,
                baseVertex
        );

        int id = GL_OBJECT_POOL.createObject(new GLObjectData(
                command, builder, PERSISTENT_MAPPED_IBO.getCommandCount() - 1, elementPair
        ));

        if (builder.objectIdPointer != null) {
            builder.objectIdPointer.setValue(id);
        }

        return this;
    }

    public TestMesh deleteObject(int objectId) {
        return deleteObject(null, objectId);
    }

    public TestMesh deleteObject(List<IntRef> ids, int objectId) {
        if (GL_OBJECT_POOL.getDeletedObjects().get(objectId) != null) {
            new LogGenerator(
                    "DeleteObject Info",
                    "ObjectId: " + objectId,
                    "Message: This object ID has already been deleted."
            ).logging(getClass(), DebugLevel.INFO);

            return this;
        }

        GLObjectData glObjectData = GL_OBJECT_POOL.getObjectData(objectId);
        if (glObjectData == null) {
            throw new MeshException("This object ID does not exist. objectId: " + objectId);
        }

        GL_OBJECT_POOL.createDeletedObject(objectId, glObjectData);
        glObjectData.draw = false;

        if (ids != null) {
            Set<Map.Entry<Integer, GLObjectData>> set = GL_OBJECT_POOL.getObjects().entrySet();
            ids.clear();
            for (Map.Entry<Integer, GLObjectData> entry : set) {
                ids.add(new IntRef(entry.getKey()));
            }
        }

        return this;
    }

    public TestMesh directDeleteObjects() {
        new LogGenerator("Direct Delete Objects Start")
                .kv("Total Objects", GL_OBJECT_POOL.getObjects().size())
                .kv("Deleted Objects", GL_OBJECT_POOL.getDeletedObjects().size())
                .kvBytes("VBO Size Before", PERSISTENT_MAPPED_VBO.getGPUMemorySize())
                .kvBytes("EBO Size Before", PERSISTENT_MAPPED_EBO.getGPUMemorySize())
                .logging(getClass(), DebugLevel.INFO);

        Map<Integer, GLObjectData> entryObject = new HashMap<>();
        for (Map.Entry<Integer, GLObjectData> objectEntry : GL_OBJECT_POOL.getObjects().entrySet()) {
            if (GL_OBJECT_POOL.getDeletedObjects().containsKey(objectEntry.getKey())) {
                continue;
            }

            entryObject.put(objectEntry.getKey(), objectEntry.getValue());
        }

        GL_OBJECT_POOL.clearObjects();
        GL_OBJECT_POOL.clearDeletedObjects();

        int newVBOCapacity = 0;
        int newEBOCapacity = 0;
        int newIBOCapacity = 0;
        for (Map.Entry<Integer, GLObjectData> entry : entryObject.entrySet()) {
            GLObjectData glObjectData = entry.getValue();

            newVBOCapacity += glObjectData.elementPair.vertices.length;
            newEBOCapacity += glObjectData.elementPair.indices.length;
            newIBOCapacity += 1;
        }

        long oldVBOCapacity = PERSISTENT_MAPPED_VBO.getGPUMemorySize();
        long oldEBOCapacity = PERSISTENT_MAPPED_EBO.getGPUMemorySize();
        long oldIBOCapacity = PERSISTENT_MAPPED_IBO.getGPUMemorySize();
        initialVBOCapacity = max(newVBOCapacity, initialVBOCapacity);
        initialEBOCapacity = max(newEBOCapacity, initialEBOCapacity);
        initialIBOCapacity = max(newIBOCapacity, initialIBOCapacity);

        new LogGenerator("Capacity Recalculation")
                .kv("Retained Objects", entryObject.size())
                .kv("Required VBO Vertices", newVBOCapacity)
                .kv("Required EBO Indices", newEBOCapacity)
                .kv("New VBO Bytes", PERSISTENT_MAPPED_VBO.getVerticesSizeByte(initialVBOCapacity))
                .kv("New EBO Bytes", PERSISTENT_MAPPED_EBO.getIndicesSizeByte(initialEBOCapacity))
                .kv("New IBO Bytes", PERSISTENT_MAPPED_IBO.getCommandsSizeByte(initialIBOCapacity))
                .logging(getClass(), DebugLevel.INFO);

        PERSISTENT_MAPPED_VBO.cleanup();
        PERSISTENT_MAPPED_EBO.cleanup();
        PERSISTENT_MAPPED_IBO.cleanup();
        PERSISTENT_MAPPED_SSBO.resetBindingPoint(2);
        PERSISTENT_MAPPED_SSBO.resetBindingPoint(3);

        PERSISTENT_MAPPED_VBO.allocationBufferStorage(PERSISTENT_MAPPED_VBO.getVerticesSizeByte(initialVBOCapacity));
        PERSISTENT_MAPPED_EBO.allocationBufferStorage(PERSISTENT_MAPPED_EBO.getIndicesSizeByte(initialEBOCapacity));
        PERSISTENT_MAPPED_IBO.allocationBufferStorage(PERSISTENT_MAPPED_IBO.getCommandsSizeByte(initialIBOCapacity));

        new LogGenerator("Buffer Reallocation Complete")
                .kvBytes("Old VBO Size", oldVBOCapacity)
                .kvBytes("New VBO Size", PERSISTENT_MAPPED_VBO.getGPUMemorySize())
                .kvBytes("Old EBO Size", oldEBOCapacity)
                .kvBytes("New EBO Size", PERSISTENT_MAPPED_EBO.getGPUMemorySize())
                .kvBytes("Old IBO Size", oldIBOCapacity)
                .kvBytes("New IBO Size", PERSISTENT_MAPPED_IBO.getGPUMemorySize())
                .kvHex("New VBO Address", PERSISTENT_MAPPED_VBO.getMappedAddress())
                .logging(getClass(), DebugLevel.INFO);

        vertexCount = 0;
        instanceCount = 0;

        for (Map.Entry<Integer, GLObjectData> entry : entryObject.entrySet()) {
            GLObjectData oldGlObjectData = entry.getValue();

            int baseVertex = vertexCount;
            int startOffset = writeGeometry(oldGlObjectData.elementPair);
            writeInstanceData(oldGlObjectData.builder.instanceData);

            DrawElementsIndirectCommand newCommand = writeIndirectCommand(
                    oldGlObjectData.elementPair.indices.length,
                    oldGlObjectData.builder.instanceData.instanceCount,
                    startOffset,
                    baseVertex
            );

            GL_OBJECT_POOL.createObject(entry.getKey(), new GLObjectData(newCommand, oldGlObjectData.builder, PERSISTENT_MAPPED_IBO.getCommandCount() - 1, oldGlObjectData.elementPair));

            if (oldGlObjectData.builder.objectIdPointer != null) {
                oldGlObjectData.builder.objectIdPointer.setValue(entry.getKey());
            }

            if (MESH_DEBUG_DIRECT_DELETE_OBJECT_REINSERTION) {
                new LogGenerator("Object Reinsertion")
                        .kv("ObjectId", entry.getKey())
                        .kv("Old BaseVertex", oldGlObjectData.drawElementsIndirectCommand.baseVertex)
                        .kv("New BaseVertex", baseVertex)
                        .kv("Old IndexOffset", oldGlObjectData.drawElementsIndirectCommand.firstIndex)
                        .kv("New IndexOffset", startOffset)
                        .logging(getClass(), DebugLevel.DEBUG);
            }
        }

        initBufferObject();

        new LogGenerator("Direct Delete Objects End")
                .kv("Final Active Objects", GL_OBJECT_POOL.getObjects().size())
                .kv("Total Vertices", vertexCount)
                .kv("Total Commands (IBO)", PERSISTENT_MAPPED_IBO.getCommandCount())
                .logging(getClass(), DebugLevel.INFO);

        return this;
    }

    public TestMesh restoreDeleteObject(int objectId) {
        GLObjectData glObjectData = GL_OBJECT_POOL.getObjectData(objectId);
        if (glObjectData == null) {
            throw new MeshException("This object ID does not exist. objectId: " + objectId);
        }

        if (glObjectData.draw) {
            new LogGenerator(
                    "RestoreDeleteObject Info",
                    "ObjectId: " + objectId,
                    "Message: This object ID is already authorized for drawing."
            ).logging(getClass(), DebugLevel.INFO);

            return this;
        }

        GL_OBJECT_POOL.destroyDeletedObject(objectId);
        glObjectData.draw = true;

        return this;
    }

    private void writeInstanceData(InstanceData instanceData) {
        for (int i = 0; i < instanceData.instanceCount; i++) {
            PERSISTENT_MAPPED_SSBO.addTransform(2, instanceData.getTransforms()[i]);
        }
    }

    public TestMesh updateInstanceData(int objectId, InstanceData newInstanceData) {
        GLObjectData glObjectData = GL_OBJECT_POOL.getObjectData(objectId);
        if (glObjectData == null) {
            throw new MeshException("Object ID not found: " + objectId);
        }

        int baseInstanceIndex = glObjectData.drawElementsIndirectCommand.baseInstance;

        int count = Math.min(glObjectData.builder.instanceData.instanceCount, newInstanceData.instanceCount);

        for (int i = 0; i < count; i++) {
            PERSISTENT_MAPPED_SSBO.updateTransform(2, baseInstanceIndex + i, newInstanceData.getTransforms()[i]);
        }

        return this;
    }

    private DrawElementsIndirectCommand writeIndirectCommand(int indexCount, int instanceCountPerObj, int firstIndex, int baseVertex) {
        DrawElementsIndirectCommand command = new DrawElementsIndirectCommand(
                indexCount,
                instanceCountPerObj,
                firstIndex,
                baseVertex,
                this.instanceCount
        );

        PERSISTENT_MAPPED_IBO.add(command);
        this.instanceCount += instanceCountPerObj;
        return command;
    }

    private int writeGeometry(TestElementPair elementPair) {
        for (Vertex vertex : elementPair.vertices) {
            PERSISTENT_MAPPED_VBO.add(vertex);
        }

        int startOffset = PERSISTENT_MAPPED_EBO.getIndexCount();
        for (int index : elementPair.indices) {
            PERSISTENT_MAPPED_EBO.add(index);
        }

        vertexCount += elementPair.vertices.length;
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
                            "BufferID: " + PERSISTENT_MAPPED_VBO.getBufferId(),
                            "Capacity: " + PERSISTENT_MAPPED_VBO.getMappedBuffer().capacity(),
                            "Used: " + PERSISTENT_MAPPED_VBO.getVertexCount()
                    ).createLog()
            );
        }
        if (ebo) {
            log.add(
                    new LogGenerator(
                            "EBO",
                            "BufferID: " + PERSISTENT_MAPPED_EBO.getBufferId(),
                            "Capacity: " + PERSISTENT_MAPPED_EBO.getMappedBuffer().capacity(),
                            "Used: " + PERSISTENT_MAPPED_EBO.getIndexCount()
                    ).createLog()
            );
        }
        if (ibo) {
            log.add(
                    new LogGenerator(
                            "IBO",
                            "BufferID: " + PERSISTENT_MAPPED_IBO.getBufferId(),
                            "Capacity: " + PERSISTENT_MAPPED_IBO.getMappedBuffer().capacity(),
                            "Command Count: " + PERSISTENT_MAPPED_IBO.getCommandCount()
                    ).createLog()
            );
        }

        new LogGenerator(
                getClass().getSimpleName() + " Debug Message",
                log.toArray(new String[0])
        ).bar("=").logging(getClass(), DebugLevel.DEBUG);


        new LogGenerator("GLObjectPool", GL_OBJECT_POOL.toString()).logging(getClass(), DebugLevel.DEBUG);
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

    public int getInitialVBOCapacity() {
        return initialVBOCapacity;
    }

    public int getInitialEBOCapacity() {
        return initialEBOCapacity;
    }

    public int getInitialIBOCapacity() {
        return initialIBOCapacity;
    }

    public TestMesh rotate() {
        currentIndex = (currentIndex + 1) % MeshConstants.DEFAULT_BUFFER_COUNT;
        return this;
    }

    public void cleanup() {

    }

    public void update() {

    }

    public static class Builder {
        private IntRef objectIdPointer = null;
        private Vertex[] vertices = null;
        private DrawType drawType = DrawType.TRIANGLES;
        private BlendType blendType = BlendType.NORMAL;
        private float lineWidth = -1f;
        private float pointSize = -1f;
        private JointType jointType = JointType.NONE;
        private PointType pointType = PointType.SQUARE;
        private InstanceData instanceData = new InstanceData(1, Color.RED);
        private ProjectionType projectionType = ProjectionType.ORTHOGRAPHIC_PROJECTION;

        Builder() {
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public Builder objectIdPointer(IntRef objectIdPointer) {
            this.objectIdPointer = objectIdPointer;

            return this;
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

        public Builder instanceData(InstanceData instanceData) {
            this.instanceData = instanceData;

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

        public InstanceData getInstanceData() {
            return instanceData;
        }

        public ProjectionType getProjectionType() {
            return projectionType;
        }
    }
}
