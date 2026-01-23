package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.MeshException;
import me.hannsi.lfjg.render.renderers.InstanceParameter;
import me.hannsi.lfjg.render.system.rendering.DrawBatch;
import me.hannsi.lfjg.render.system.rendering.Pipeline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class TestMesh {
    private final List<MeshBuilder> pendingMeshBuilders;
    private final int vaoId;
    private boolean needRepack;
    private int currentIndex;
    private int vertexCount;
    private int indexCount;
    private int commandCount;

    TestMesh() {
        this.vaoId = glGenVertexArrays();
        this.pendingMeshBuilders = new ArrayList<>();
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

    public TestMesh addObject(MeshBuilder meshBuilder) {
        pendingMeshBuilders.add(meshBuilder);

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

        drawBatch.clear();
        needUpdateBuilders.clear();
        persistentMappedVBO.reset();
        persistentMappedEBO.reset();
        persistentMappedIBO.reset();
        persistentMappedSSBO.resetBindingPoint(INSTANCE_PARAMETERS_BINDING_POINT);

        Pipeline currentPipeline = null;
        for (MeshBuilder meshBuilder : pendingMeshBuilders) {
            Pipeline newPipeline = new Pipeline(meshBuilder.getBlendType());

            if (currentPipeline == null || currentPipeline.getBlendType() != newPipeline.getBlendType()) {
                currentPipeline = newPipeline;
                drawBatch.addPass(new DrawBatch.Pass(commandCount, newPipeline));
            }

            drawBatch.increment();

            TestElementPair elementPair = TestPolygonTriangulator.createPolygonTriangulator()
                    .drawType(meshBuilder.getDrawType())
                    .lineWidth(meshBuilder.getLineWidth())
                    .lineJointType(meshBuilder.getJointType())
                    .pointSize(meshBuilder.getPointSize())
                    .pointType(meshBuilder.getPointType())
                    .projectionType(meshBuilder.getProjectionType())
                    .vertices(meshBuilder.getVertices())
                    .process()
                    .getResult();

            meshBuilder.setBytes(elementPair.vertices.length * Vertex.BYTES + (long) elementPair.indices.length * Float.BYTES + DrawElementsIndirectCommand.BYTES);
            meshBuilder.setBaseCommand(commandCount);

            int baseVertex = vertexCount;

            int startOffset = writeGeometry(elementPair);

            ObjectData objectData = meshBuilder.getObjectData();
            for (InstanceParameter instanceParameter : objectData.getInstanceParameters()) {
                instanceParameter.objectId(objectCount);
            }
            for (int i = 0; i < objectData.drawElementsIndirectCommand.instanceCount; i++) {
                persistentMappedSSBO.addInstanceParameter(INSTANCE_PARAMETERS_BINDING_POINT, objectData.getInstanceParameters()[i]);
            }

            objectData.drawElementsIndirectCommand.count = elementPair.indices.length;
            objectData.drawElementsIndirectCommand.firstIndex = startOffset;
            objectData.drawElementsIndirectCommand.baseVertex = baseVertex;
            objectData.drawElementsIndirectCommand.baseInstance = baseInstance;
            writeIndirectCommand(objectData.drawElementsIndirectCommand);

            if (meshBuilder.getObjectIdPointer().isNullptr()) {
                int id = glObjectPool.createId(meshBuilder);
                meshBuilder.getObjectIdPointer().setValue(id);
            } else {
                glObjectPool.createObject(meshBuilder.getObjectIdPointer().getValue(), meshBuilder);
            }

            baseInstance += objectData.drawElementsIndirectCommand.instanceCount;
            objectCount++;
        }

        return this;
    }

    public TestMesh update() {
        Iterator<IntRef> it = needUpdateBuilders.iterator();

        while (it.hasNext()) {
            IntRef objectIdPointer = it.next();
            MeshBuilder meshBuilder = glObjectPool.getBuilder(objectIdPointer.getValue());

            if (meshBuilder == null) {
                throw new MeshException("Object id pointer error. Pointer: " + objectIdPointer);
            }

            if (meshBuilder.isFlagDraw()) {
                long base = meshBuilder.getBaseCommand() * DrawElementsIndirectCommand.BYTES;
                int count = meshBuilder.getObjectData().drawElementsIndirectCommand.count;
                if (!meshBuilder.isDraw()) {
                    count = 0;
                }
                persistentMappedIBO.update(base, 0, count);
                meshBuilder.setFlagDraw(false);
            }

            if (meshBuilder.isFlagObjectData()) {
                int baseInstanceIndex = meshBuilder.getObjectData().drawElementsIndirectCommand.baseInstance;

                int index = 0;
                for (InstanceParameter instanceParameter : meshBuilder.getObjectData().getInstanceParameters()) {

                    persistentMappedSSBO.updateInstanceParameter(INSTANCE_PARAMETERS_BINDING_POINT, baseInstanceIndex + index, instanceParameter);
                    index++;
                }
                meshBuilder.setFlagObjectData(false);
            }

            it.remove();
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

        MeshBuilder meshBuilder = glObjectPool.getBuilder(objectId);
        if (meshBuilder == null) {
            throw new MeshException("This object ID does not exist. objectId: " + objectId);
        }

        meshBuilder.draw(false);
        glObjectPool.createDeletedObject(objectId);

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

        pendingMeshBuilders.clear();
        for (Map.Entry<Integer, MeshBuilder> entry : glObjectPool.getObjects().entrySet()) {
            if (glObjectPool.getDeletedObjects().containsKey(entry.getKey())) {
                continue;
            }

            for (InstanceParameter instanceParameter : entry.getValue().getObjectData().getInstanceParameters()) {
                instanceParameter.setDirtyFlag(true);
            }
            pendingMeshBuilders.add(entry.getValue());
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
        MeshBuilder meshBuilder = glObjectPool.getBuilder(objectId);
        if (meshBuilder == null) {
            throw new MeshException("This object ID does not exist. objectId: " + objectId);
        }

        if (meshBuilder.isDraw()) {
            new LogGenerator(
                    "RestoreDeleteObject Info",
                    "ObjectId: " + objectId,
                    "Message: This object ID is already authorized for drawing."
            ).logging(getClass(), DebugLevel.INFO);

            return this;
        }

        glObjectPool.restoreDeletedObject(objectId);
        meshBuilder.draw(true);

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

    public List<MeshBuilder> getPendingBuilders() {
        return pendingMeshBuilders;
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
}
