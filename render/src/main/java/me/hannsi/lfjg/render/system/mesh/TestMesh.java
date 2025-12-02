package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.reference.LongRef;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.MeshException;
import me.hannsi.lfjg.render.renderers.JointType;
import me.hannsi.lfjg.render.renderers.PointType;
import me.hannsi.lfjg.render.system.mesh.persistent.TestPersistentMappedEBO;
import me.hannsi.lfjg.render.system.mesh.persistent.TestPersistentMappedIBO;
import me.hannsi.lfjg.render.system.mesh.persistent.TestPersistentMappedVBO;
import me.hannsi.lfjg.render.system.rendering.DrawType;

import java.util.*;

import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static me.hannsi.lfjg.render.LFJGRenderContext.GL_OBJECT_POOL;
import static me.hannsi.lfjg.render.LFJGRenderContext.GL_STATE_CACHE;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL43.glMultiDrawElementsIndirect;

public class TestMesh {
    private final int vaoId;
    private int initialVBOCapacity;
    private int initialEBOCapacity;
    private int initialIBOCapacity;
    private TestPersistentMappedVBO persistentMappedVBO;
    private TestPersistentMappedEBO persistentMappedEBO;
    private TestPersistentMappedIBO persistentMappedIBO;
    private int currentIndex;
    private int vertexCount;

    TestMesh(int initialVBOCapacity, int initialEBOCapacity, int initialIBOCapacity) {
        this.persistentMappedVBO = new TestPersistentMappedVBO(MeshConstants.DEFAULT_FLAGS_HINT, initialVBOCapacity);
        this.persistentMappedEBO = new TestPersistentMappedEBO(MeshConstants.DEFAULT_FLAGS_HINT, initialEBOCapacity);
        this.persistentMappedIBO = new TestPersistentMappedIBO(MeshConstants.DEFAULT_FLAGS_HINT, initialIBOCapacity);
        this.initialVBOCapacity = initialVBOCapacity;
        this.initialEBOCapacity = initialEBOCapacity;
        this.initialIBOCapacity = initialIBOCapacity;

        this.vaoId = glGenVertexArrays();
        this.currentIndex = 0;
        this.vertexCount = 0;
    }

    public static TestMesh createMesh(int initialVBOCapacity, int initialEBOCapacity, int initialIBOCapacity) {
        return new TestMesh(initialVBOCapacity, initialEBOCapacity, initialIBOCapacity);
    }

    public TestMesh initBufferObject() {
        GL_STATE_CACHE.bindVertexArrayForce(vaoId);

        persistentMappedVBO.createVertexAttribute(vaoId, BufferObjectType.POSITION_BUFFER, BufferObjectType.COLOR_BUFFER, BufferObjectType.TEXTURE_BUFFER, BufferObjectType.NORMAL_BUFFER)
                .syncToGPU();

        persistentMappedEBO.linkVertexArrayObject(vaoId)
                .syncToGPU();

        persistentMappedIBO.syncToGPU();

        return this;
    }

    public TestMesh addObject(ProjectionType projectionType, DrawType drawType, float lineWidth, JointType jointType, float pointSize, PointType pointType, Vertex... vertices) {
        return addObject(null, projectionType, drawType, lineWidth, jointType, pointSize, pointType, vertices);
    }

    public TestMesh addObject(LongRef objectIdPointer, ProjectionType projectionType, DrawType drawType, float lineWidth, JointType jointType, float pointSize, PointType pointType, Vertex... vertices) {
        TestElementPair elementPair = setupElementBufferObject(projectionType, drawType, lineWidth, jointType, pointSize, pointType, vertices);
        int baseVertex = vertexCount;

        for (Vertex vertex : elementPair.vertices) {
            persistentMappedVBO.add(vertex);
        }

        int startOffset = persistentMappedEBO.getIndexCount();
        for (int index : elementPair.indices) {
            persistentMappedEBO.add(index);
        }

        vertexCount += elementPair.vertices.length;

        persistentMappedIBO.add(
                new DrawElementsIndirectCommand(
                        elementPair.indices.length,
                        1,
                        startOffset,
                        baseVertex,
                        0
                )
        );

        long id = GL_OBJECT_POOL.createObject(new GLObjectData(baseVertex, elementPair.vertices.length, startOffset, elementPair.indices.length, persistentMappedIBO.getCommandCount() - 1, elementPair));
        if (objectIdPointer != null) {
            objectIdPointer.setValue(id);
        }

        return this;
    }

    public TestMesh deleteObject(long objectId) {
        return deleteObject(null, objectId);
    }

    public TestMesh deleteObject(List<LongRef> ids, long objectId) {
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
            Set<Map.Entry<Long, GLObjectData>> set = GL_OBJECT_POOL.getObjects().entrySet();
            ids.clear();
            for (Map.Entry<Long, GLObjectData> entry : set) {
                ids.add(new LongRef(entry.getKey()));
            }
        }

        return this;
    }

    public TestMesh directDeleteObjects() {
        Map<Long, GLObjectData> entryObject = new HashMap<>();
        for (Map.Entry<Long, GLObjectData> objectEntry : GL_OBJECT_POOL.getObjects().entrySet()) {
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
        for (Map.Entry<Long, GLObjectData> entry : entryObject.entrySet()) {
            GLObjectData glObjectData = entry.getValue();

            newVBOCapacity += glObjectData.elementPair.vertices.length;
            newEBOCapacity += glObjectData.elementPair.indices.length;
            newIBOCapacity += 1;
        }
        initialVBOCapacity = max(newVBOCapacity, initialVBOCapacity);
        initialEBOCapacity = max(newEBOCapacity, initialEBOCapacity);
        initialIBOCapacity = max(newIBOCapacity, initialIBOCapacity);

        persistentMappedVBO.cleanup();
        persistentMappedEBO.cleanup();
        persistentMappedIBO.cleanup();

        persistentMappedVBO = new TestPersistentMappedVBO(MeshConstants.DEFAULT_FLAGS_HINT, initialVBOCapacity);
        persistentMappedEBO = new TestPersistentMappedEBO(MeshConstants.DEFAULT_FLAGS_HINT, initialEBOCapacity);
        persistentMappedIBO = new TestPersistentMappedIBO(MeshConstants.DEFAULT_FLAGS_HINT, initialIBOCapacity);

        vertexCount = 0;

        for (Map.Entry<Long, GLObjectData> entry : entryObject.entrySet()) {
            TestElementPair elementPair = entry.getValue().elementPair;

            int baseVertex = vertexCount;
            for (Vertex vertex : elementPair.vertices) {
                persistentMappedVBO.add(vertex);
            }

            int startOffset = persistentMappedEBO.getIndexCount();
            for (int index : elementPair.indices) {
                persistentMappedEBO.add(index);
            }

            vertexCount += elementPair.vertices.length;

            persistentMappedIBO.add(
                    new DrawElementsIndirectCommand(
                            elementPair.indices.length,
                            1,
                            startOffset,
                            baseVertex,
                            0
                    )
            );

            GL_OBJECT_POOL.createObject(entry.getKey(), new GLObjectData(baseVertex, elementPair.vertices.length, startOffset, elementPair.indices.length, persistentMappedIBO.getCommandCount() - 1, elementPair));
        }

        initBufferObject();

        return this;
    }

    public TestMesh restoreDeleteObject(long objectId) {
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

    private TestElementPair setupElementBufferObject(ProjectionType projectionType, DrawType drawType, float lineWidth, JointType jointType, float pointSize, PointType pointType, Vertex[] vertices) {
        return TestPolygonTriangulator.createPolygonTriangulator()
                .drawType(drawType)
                .lineWidth(lineWidth)
                .lineJointType(jointType)
                .pointSize(pointSize)
                .pointType(pointType)
                .projectionType(projectionType)
                .vertices(vertices)
                .process()
                .getResult();
    }

    public void debugDraw(int mode, boolean frontAndBack) {
        if (frontAndBack) {
            GL_STATE_CACHE.polygonMode(GL_FRONT_AND_BACK, GL_LINE);
            GL_STATE_CACHE.lineWidth(0.1f);
        } else {
            GL_STATE_CACHE.polygonMode(GL_FRONT_AND_BACK, GL_FILL);
            GL_STATE_CACHE.lineWidth(1.0f);
        }

        for (Map.Entry<Long, GLObjectData> entry : GL_OBJECT_POOL.getObjects().entrySet()) {
            GLObjectData glObjectData = entry.getValue();

            long base = persistentMappedIBO.getCommandsSizeByte(glObjectData.baseCommand);
            if (glObjectData.draw) {
                persistentMappedIBO.directWriteCommand(base, 0, glObjectData.elementPair.indices.length);
            } else {
                persistentMappedIBO.directWriteCommand(base, 0, 0);
            }
        }

        persistentMappedVBO.syncToGPU();
        persistentMappedEBO.syncToGPU();
        persistentMappedIBO.syncToGPU();

        GL_STATE_CACHE.bindVertexArray(vaoId);
        GL_STATE_CACHE.bindElementArrayBuffer(persistentMappedEBO.getBufferId());
        GL_STATE_CACHE.bindIndirectBuffer(persistentMappedIBO.getBufferId());

        glMultiDrawElementsIndirect(
                mode,
                GL_UNSIGNED_INT,
                0,
                persistentMappedIBO.getCommandCount(),
                0
        );
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
                            "Capacity: " + persistentMappedVBO.getMappedBuffer().capacity(),
                            "Used: " + persistentMappedVBO.getVertexCount()
                    ).createLog()
            );
        }
        if (ebo) {
            log.add(
                    new LogGenerator(
                            "EBO",
                            "BufferID: " + persistentMappedEBO.getBufferId(),
                            "Capacity: " + persistentMappedEBO.getMappedBuffer().capacity(),
                            "Used: " + persistentMappedEBO.getIndexCount()
                    ).createLog()
            );
        }
        if (ibo) {
            log.add(
                    new LogGenerator(
                            "IBO",
                            "BufferID: " + persistentMappedIBO.getBufferId(),
                            "Capacity: " + persistentMappedIBO.getMappedBuffer().capacity(),
                            "Command Count: " + persistentMappedIBO.getCommandCount()
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

    public TestPersistentMappedVBO getPersistentMappedVBO() {
        return persistentMappedVBO;
    }

    public TestMesh rotate() {
        currentIndex = (currentIndex + 1) % MeshConstants.DEFAULT_BUFFER_COUNT;
        return this;
    }

    public void cleanup() {

    }
}
