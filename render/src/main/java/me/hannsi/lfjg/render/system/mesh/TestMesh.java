package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.MeshException;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.JointType;
import me.hannsi.lfjg.render.renderers.PointType;
import me.hannsi.lfjg.render.system.rendering.DrawType;

import java.util.*;

import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static me.hannsi.lfjg.render.RenderSystemSetting.MESH_DEBUG_DIRECT_DELETE_OBJECT_REINSERTION;
import static me.hannsi.lfjg.render.RenderSystemSetting.MESH_RENDER_BLEND_ORDER;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class TestMesh {
    private final int vaoId;
    private final Map<BlendType, BlendGroup> blendGroups;
    private boolean needRepack;
    private int initialVBOCapacity;
    private int initialEBOCapacity;
    private int initialIBOCapacity;
    private int currentIndex;
    private int vertexCount;

    TestMesh(int initialVBOCapacity, int initialEBOCapacity, int initialIBOCapacity) {
        this.vaoId = glGenVertexArrays();
        this.blendGroups = new HashMap<>();
        this.needRepack = false;

        this.initialVBOCapacity = initialVBOCapacity;
        this.initialEBOCapacity = initialEBOCapacity;
        this.initialIBOCapacity = initialIBOCapacity;

        this.currentIndex = 0;
        this.vertexCount = 0;
    }

    public static TestMesh createMesh(int initialVBOCapacity, int initialEBOCapacity, int initialIBOCapacity) {
        return new TestMesh(initialVBOCapacity, initialEBOCapacity, initialIBOCapacity);
    }

    public TestMesh initBufferObject() {
        glStateCache.bindVertexArrayForce(vaoId);

        persistentMappedVBO.createVertexAttribute(vaoId, BufferObjectType.POSITION_BUFFER, BufferObjectType.COLOR_BUFFER, BufferObjectType.TEXTURE_BUFFER, BufferObjectType.NORMAL_BUFFER)
                .syncToGPU();

        persistentMappedEBO.linkVertexArrayObject(vaoId)
                .syncToGPU();

        persistentMappedIBO.syncToGPU();

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

        int baseInstance = persistentMappedSSBO.getDataCount(2);
        int baseVertex = vertexCount;
        int startOffset = writeGeometry(elementPair);
        writeInstanceData(builder);

        DrawElementsIndirectCommand command = writeIndirectCommand(
                elementPair.indices.length,
                builder.instanceData.instanceCount,
                startOffset,
                baseVertex,
                baseInstance
        );

        int id = glObjectPool.createId(new GLObjectData(
                command, builder, persistentMappedIBO.getCommandCount() - 1, elementPair
        ));

        if (builder.objectIdPointer != null) {
            builder.objectIdPointer.setValue(id);
        }

        needRepack = true;

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

        GLObjectData glObjectData = glObjectPool.getObjectData(objectId);
        if (glObjectData == null) {
            throw new MeshException("This object ID does not exist. objectId: " + objectId);
        }

        glObjectPool.createDeletedObject(objectId, glObjectData);
        glObjectData.draw = false;

        if (ids != null) {
            Set<Map.Entry<Integer, GLObjectData>> set = glObjectPool.getObjects().entrySet();
            ids.clear();
            for (Map.Entry<Integer, GLObjectData> entry : set) {
                ids.add(new IntRef(entry.getKey()));
            }
        }

        return this;
    }

    public TestMesh directDeleteObjects() {
        new LogGenerator("Direct Delete Objects Start")
                .kv("Total Objects", glObjectPool.getObjects().size())
                .kv("Deleted Objects", glObjectPool.getDeletedObjects().size())
                .kvBytes("VBO Size Before", persistentMappedVBO.getGPUMemorySize())
                .kvBytes("EBO Size Before", persistentMappedEBO.getGPUMemorySize())
                .logging(getClass(), DebugLevel.INFO);

        Map<Integer, GLObjectData> entryObject = new HashMap<>();
        for (Map.Entry<Integer, GLObjectData> objectEntry : glObjectPool.getObjects().entrySet()) {
            if (glObjectPool.getDeletedObjects().containsKey(objectEntry.getKey())) {
                continue;
            }

            entryObject.put(objectEntry.getKey(), objectEntry.getValue());
        }

        Map<BlendType, List<Map.Entry<Integer, GLObjectData>>> groupedObjects = new EnumMap<>(BlendType.class);
        for (Map.Entry<Integer, GLObjectData> entry : entryObject.entrySet()) {
            BlendType type = entry.getValue().builder.getBlendType();
            groupedObjects.computeIfAbsent(type, k -> new ArrayList<>()).add(entry);
        }

        glObjectPool.clearObjects();
        glObjectPool.clearDeletedObjects();

        int newVBOCapacity = 0;
        int newEBOCapacity = 0;
        int newIBOCapacity = 0;
        for (Map.Entry<Integer, GLObjectData> entry : entryObject.entrySet()) {
            GLObjectData glObjectData = entry.getValue();

            newVBOCapacity += glObjectData.elementPair.vertices.length;
            newEBOCapacity += glObjectData.elementPair.indices.length;
            newIBOCapacity += 1;
        }

        long oldVBOCapacity = persistentMappedVBO.getGPUMemorySize();
        long oldEBOCapacity = persistentMappedEBO.getGPUMemorySize();
        long oldIBOCapacity = persistentMappedIBO.getGPUMemorySize();
        initialVBOCapacity = max(newVBOCapacity, initialVBOCapacity);
        initialEBOCapacity = max(newEBOCapacity, initialEBOCapacity);
        initialIBOCapacity = max(newIBOCapacity, initialIBOCapacity);

        new LogGenerator("Capacity Recalculation")
                .kv("Retained Objects", entryObject.size())
                .kv("Required VBO Vertices", newVBOCapacity)
                .kv("Required EBO Indices", newEBOCapacity)
                .kv("New VBO Bytes", persistentMappedVBO.getVerticesSizeByte(initialVBOCapacity))
                .kv("New EBO Bytes", persistentMappedEBO.getIndicesSizeByte(initialEBOCapacity))
                .kv("New IBO Bytes", persistentMappedIBO.getCommandsSizeByte(initialIBOCapacity))
                .logging(getClass(), DebugLevel.INFO);

        persistentMappedVBO.cleanup();
        persistentMappedEBO.cleanup();
        persistentMappedIBO.cleanup();
        persistentMappedSSBO.resetBindingPoint(2);

        persistentMappedVBO.allocationBufferStorage(persistentMappedVBO.getVerticesSizeByte(initialVBOCapacity));
        persistentMappedEBO.allocationBufferStorage(persistentMappedEBO.getIndicesSizeByte(initialEBOCapacity));
        persistentMappedIBO.allocationBufferStorage(persistentMappedIBO.getCommandsSizeByte(initialIBOCapacity));

        new LogGenerator("Buffer Reallocation Complete")
                .kvBytes("Old VBO Size", oldVBOCapacity)
                .kvBytes("New VBO Size", persistentMappedVBO.getGPUMemorySize())
                .kvBytes("Old EBO Size", oldEBOCapacity)
                .kvBytes("New EBO Size", persistentMappedEBO.getGPUMemorySize())
                .kvBytes("Old IBO Size", oldIBOCapacity)
                .kvBytes("New IBO Size", persistentMappedIBO.getGPUMemorySize())
                .kvHex("New VBO Address", persistentMappedVBO.getMappedAddress())
                .logging(getClass(), DebugLevel.INFO);

        blendGroups.clear();

        vertexCount = 0;

        for (BlendType type : MESH_RENDER_BLEND_ORDER) {
            List<Map.Entry<Integer, GLObjectData>> list = groupedObjects.get(type);
            if (list == null || list.isEmpty()) {
                continue;
            }

            int startCommandIndex = persistentMappedIBO.getCommandCount();
            list.sort(Comparator.comparingInt(e -> e.getValue().builder.getRenderOrder()));
            for (Map.Entry<Integer, GLObjectData> entry : list) {
                GLObjectData oldGlObjectData = entry.getValue();
                oldGlObjectData.builder.instanceData.getTransforms()[0].setZ(oldGlObjectData.builder.getRenderOrder() * 0.0000001f);

                int baseInstance = persistentMappedSSBO.getDataCount(2);
                int baseVertex = vertexCount;
                int startOffset = writeGeometry(oldGlObjectData.elementPair);

                writeInstanceData(oldGlObjectData.builder);

                DrawElementsIndirectCommand newCommand = writeIndirectCommand(
                        oldGlObjectData.elementPair.indices.length,
                        oldGlObjectData.builder.instanceData.instanceCount,
                        startOffset,
                        baseVertex,
                        baseInstance
                );

                int newIBOIndex = persistentMappedIBO.getCommandCount() - 1;
                glObjectPool.createObject(entry.getKey(), new GLObjectData(
                        newCommand,
                        oldGlObjectData.builder,
                        newIBOIndex,
                        oldGlObjectData.elementPair
                ));

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

            int count = persistentMappedIBO.getCommandCount() - startCommandIndex;
            blendGroups.put(type, new BlendGroup(startCommandIndex, count));
        }

        initBufferObject();

        new LogGenerator("Direct Delete Objects End")
                .kv("Final Active Objects", glObjectPool.getObjects().size())
                .kv("Total Vertices", vertexCount)
                .kv("Total Commands (IBO)", persistentMappedIBO.getCommandCount())
                .logging(getClass(), DebugLevel.INFO);

        return this;
    }

    public TestMesh restoreDeleteObject(int objectId) {
        GLObjectData glObjectData = glObjectPool.getObjectData(objectId);
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

        glObjectPool.destroyDeletedObject(objectId);
        glObjectData.draw = true;

        return this;
    }

    private void writeInstanceData(Builder builder) {
        InstanceData instanceData = builder.instanceData;
        for (int i = 0; i < instanceData.instanceCount; i++) {
            instanceData.getTransforms()[i].setZ(builder.renderOrder * 0.0000001f);
            persistentMappedSSBO.addObjectParameter(2, instanceData.getTransforms()[i]);
        }
    }

    public TestMesh updateInstanceData(int objectId, InstanceData newInstanceData) {
        GLObjectData glObjectData = glObjectPool.getObjectData(objectId);
        if (glObjectData == null) {
            throw new MeshException("Object ID not found: " + objectId);
        }

        int baseInstanceIndex = glObjectData.drawElementsIndirectCommand.baseInstance;

        int count = Math.min(glObjectData.builder.instanceData.instanceCount, newInstanceData.instanceCount);

        for (int i = 0; i < count; i++) {
            persistentMappedSSBO.updateObjectParameter(2, baseInstanceIndex + i, newInstanceData.getTransforms()[i]);
        }

        glObjectData.builder.instanceData = newInstanceData;

        return this;
    }

    private DrawElementsIndirectCommand writeIndirectCommand(int indexCount, int instanceCountPerObj, int firstIndex, int baseVertex, int instanceCount) {
        DrawElementsIndirectCommand command = new DrawElementsIndirectCommand(
                indexCount,
                instanceCountPerObj,
                firstIndex,
                baseVertex,
                instanceCount
        );

        persistentMappedIBO.add(command);
        return command;
    }

    private int writeGeometry(TestElementPair elementPair) {
        for (Vertex vertex : elementPair.vertices) {
            persistentMappedVBO.add(vertex);
        }

        int startOffset = persistentMappedEBO.getIndexCount();
        for (int index : elementPair.indices) {
            persistentMappedEBO.add(index);
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
                            "BufferID: " + persistentMappedVBO.getBufferId(),
                            "Capacity: " + persistentMappedVBO.getGPUMemorySize(),
                            "Used: " + persistentMappedVBO.getVertexCount()
                    ).createLog()
            );
        }
        if (ebo) {
            log.add(
                    new LogGenerator(
                            "EBO",
                            "BufferID: " + persistentMappedEBO.getBufferId(),
                            "Capacity: " + persistentMappedEBO.getGPUMemorySize(),
                            "Used: " + persistentMappedEBO.getIndexCount()
                    ).createLog()
            );
        }
        if (ibo) {
            log.add(
                    new LogGenerator(
                            "IBO",
                            "BufferID: " + persistentMappedIBO.getBufferId(),
                            "Capacity: " + persistentMappedIBO.getGPUMemorySize(),
                            "Command Count: " + persistentMappedIBO.getCommandCount()
                    ).createLog()
            );
        }

        new LogGenerator(
                getClass().getSimpleName() + " Debug Message",
                log.toArray(new String[0])
        ).bar("=").logging(getClass(), DebugLevel.DEBUG);


        new LogGenerator("GLObjectPool", glObjectPool.toString()).logging(getClass(), DebugLevel.DEBUG);
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

    public boolean isNeedRepack() {
        return needRepack;
    }

    public void setNeedRepack(boolean needRepack) {
        this.needRepack = needRepack;
    }

    public Map<BlendType, BlendGroup> getBlendGroups() {
        return blendGroups;
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
        private InstanceData instanceData = new InstanceData(1);
        private ProjectionType projectionType = ProjectionType.ORTHOGRAPHIC_PROJECTION;
        private int renderOrder = 0;

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

        public Builder renderOrder(int renderOrder) {
            this.renderOrder = renderOrder;

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

        public int getRenderOrder() {
            return renderOrder;
        }
    }
}
