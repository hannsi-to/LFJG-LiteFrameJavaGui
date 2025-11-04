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
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.glObjectPool;

public class TestMesh {
    private final TestPersistentMappedVBO persistentMappedVBO;
    private final TestPersistentMappedEBO persistentMappedEBO;
    private final TestPersistentMappedIBO persistentMappedIBO;

    private final int vaoId;
    private int currentIndex;
    private int vertexCount;

    TestMesh(int initialVBOCapacity, int initialEBOCapacity, int initialIBOCapacity) {
        this.persistentMappedVBO = new TestPersistentMappedVBO(MeshConstants.DEFAULT_FLAGS_HINT, initialVBOCapacity);
        this.persistentMappedEBO = new TestPersistentMappedEBO(MeshConstants.DEFAULT_FLAGS_HINT, initialEBOCapacity);
        this.persistentMappedIBO = new TestPersistentMappedIBO(MeshConstants.DEFAULT_FLAGS_HINT, initialIBOCapacity);

        this.vaoId = GL30.glGenVertexArrays();
        this.currentIndex = 0;
        this.vertexCount = 0;
    }

    public static TestMesh createMesh(int initialVBOCapacity, int initialEBOCapacity, int initialIBOCapacity) {
        return new TestMesh(initialVBOCapacity, initialEBOCapacity, initialIBOCapacity);
    }

    public TestMesh initBufferObject() {
        persistentMappedVBO.createVertexAttribute(vaoId, BufferObjectType.POSITION_BUFFER, BufferObjectType.COLOR_BUFFER, BufferObjectType.TEXTURE_BUFFER, BufferObjectType.NORMAL_BUFFER)
                .syncToGPU();

        persistentMappedEBO.linkVertexArrayObject(vaoId)
                .syncToGPU();

        persistentMappedIBO.syncToGPU();

        GLStateCache.bindVertexArray(0);
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

        persistentMappedIBO.addCommand(
                new DrawElementsIndirectCommand(
                        elementPair.indices.length,
                        1,
                        startOffset,
                        baseVertex,
                        0
                )
        );

        long id = glObjectPool.createObject(new GLObjectData(baseVertex, elementPair.vertices.length, startOffset, elementPair.indices.length, persistentMappedIBO.getCommandCount() - 1, elementPair));
        if (objectIdPointer != null) {
            objectIdPointer.setValue(id);
        }

        return this;
    }

    public TestMesh deleteObject(long objectId) {
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

        return this;
    }

    public TestMesh restoreDeleteObject(long objectId) {
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
            GLStateCache.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
            GLStateCache.lineWidth(0.1f);
        } else {
            GLStateCache.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
            GLStateCache.lineWidth(1.0f);
        }

        for (Map.Entry<Long, GLObjectData> entry : glObjectPool.getObjects().entrySet()) {
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

        GLStateCache.bindVertexArrayForce(vaoId);
        GLStateCache.bindElementArrayBufferForce(persistentMappedEBO.getBufferId());
        GLStateCache.bindIndirectBufferForce(persistentMappedIBO.getBufferId());

        GL43.glMultiDrawElementsIndirect(
                mode,
                GL11.GL_UNSIGNED_INT,
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

    public TestPersistentMappedVBO getPersistentMappedVBO() {
        return persistentMappedVBO;
    }

    public TestMesh rotate() {
        currentIndex = (currentIndex + 1) % MeshConstants.DEFAULT_BUFFER_COUNT;
        return this;
    }
}
