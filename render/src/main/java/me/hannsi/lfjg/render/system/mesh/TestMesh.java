package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.reference.LongRef;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.MeshException;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.JointType;
import me.hannsi.lfjg.render.renderers.PointType;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;

import java.util.*;

import static me.hannsi.lfjg.core.SystemSetting.MESH_DEBUG_DIRECT_DELETE_OBJECT_REINSERTION;
import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL43.glMultiDrawElementsIndirect;

public class TestMesh {
    private static int layer = 0;
    private final int vaoId;
    private int initialVBOCapacity;
    private int initialEBOCapacity;
    private int initialIBOCapacity;
    private int currentIndex;
    private int vertexCount;
    private FragmentShaderType fragmentShaderType;
    private BlendType blendType;

    TestMesh(int initialVBOCapacity, int initialEBOCapacity, int initialIBOCapacity) {
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

        PERSISTENT_MAPPED_VBO.createVertexAttribute(vaoId, BufferObjectType.POSITION_BUFFER, BufferObjectType.COLOR_BUFFER, BufferObjectType.TEXTURE_BUFFER, BufferObjectType.NORMAL_BUFFER)
                .syncToGPU();

        PERSISTENT_MAPPED_EBO.linkVertexArrayObject(vaoId)
                .syncToGPU();

        PERSISTENT_MAPPED_IBO.syncToGPU();

        return this;
    }

    public TestMesh addObject(ProjectionType projectionType, DrawType drawType, FragmentShaderType fragmentShaderType, BlendType blendType, float lineWidth, JointType jointType, float pointSize, PointType pointType, Vertex... vertices) {
        return addObject(null, projectionType, drawType, fragmentShaderType, blendType, lineWidth, jointType, pointSize, pointType, vertices);
    }

    public TestMesh addObject(LongRef objectIdPointer, ProjectionType projectionType, DrawType drawType, FragmentShaderType fragmentShaderType, BlendType blendType, float lineWidth, JointType jointType, float pointSize, PointType pointType, Vertex... vertices) {
        this.fragmentShaderType = fragmentShaderType;
        this.blendType = blendType;

        TestElementPair elementPair = setupElementBufferObject(projectionType, drawType, lineWidth, jointType, pointSize, pointType, vertices);
        int baseVertex = vertexCount;

        for (Vertex vertex : elementPair.vertices) {
            vertex.layer = layer;
            PERSISTENT_MAPPED_VBO.add(vertex);
        }

        int startOffset = PERSISTENT_MAPPED_EBO.getIndexCount();
        for (int index : elementPair.indices) {
            PERSISTENT_MAPPED_EBO.add(index);
        }

        vertexCount += elementPair.vertices.length;

        PERSISTENT_MAPPED_IBO.add(
                new DrawElementsIndirectCommand(
                        elementPair.indices.length,
                        1,
                        startOffset,
                        baseVertex,
                        layer
                )
        );

        long id = GL_OBJECT_POOL.createObject(new GLObjectData(baseVertex, elementPair.vertices.length, startOffset, elementPair.indices.length, PERSISTENT_MAPPED_IBO.getCommandCount() - 1, elementPair));
        if (objectIdPointer != null) {
            objectIdPointer.setValue(id);
        }

        layer++;

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
        new LogGenerator("Direct Delete Objects Start")
                .kv("Total Objects", GL_OBJECT_POOL.getObjects().size())
                .kv("Deleted Objects", GL_OBJECT_POOL.getDeletedObjects().size())
                .kvBytes("VBO Size Before", PERSISTENT_MAPPED_VBO.getGPUMemorySize())
                .kvBytes("EBO Size Before", PERSISTENT_MAPPED_EBO.getGPUMemorySize())
                .logging(getClass(), DebugLevel.INFO);

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

        for (Map.Entry<Long, GLObjectData> entry : entryObject.entrySet()) {
            GLObjectData oldGlObjectData = entry.getValue();
            TestElementPair elementPair = entry.getValue().elementPair;

            int baseVertex = vertexCount;
            for (Vertex vertex : elementPair.vertices) {
                PERSISTENT_MAPPED_VBO.add(vertex);
            }

            int startOffset = PERSISTENT_MAPPED_EBO.getIndexCount();
            for (int index : elementPair.indices) {
                PERSISTENT_MAPPED_EBO.add(index);
            }

            vertexCount += elementPair.vertices.length;

            PERSISTENT_MAPPED_IBO.add(
                    new DrawElementsIndirectCommand(
                            elementPair.indices.length,
                            1,
                            startOffset,
                            baseVertex,
                            0
                    )
            );

            GL_OBJECT_POOL.createObject(
                    entry.getKey(),
                    new GLObjectData(
                            baseVertex,
                            elementPair.vertices.length,
                            startOffset,
                            elementPair.indices.length,
                            PERSISTENT_MAPPED_IBO.getCommandCount() - 1,
                            elementPair
                    )
            );

            if (MESH_DEBUG_DIRECT_DELETE_OBJECT_REINSERTION) {
                new LogGenerator("Object Reinsertion")
                        .kv("ObjectId", entry.getKey())
                        .kv("Old BaseVertex", oldGlObjectData.baseVertex)
                        .kv("New BaseVertex", baseVertex)
                        .kv("Old IndexOffset", oldGlObjectData.baseIndex)
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

            long base = PERSISTENT_MAPPED_IBO.getCommandsSizeByte(glObjectData.baseCommand);
            if (glObjectData.draw) {
                PERSISTENT_MAPPED_IBO.directWriteCommand(base, 0, glObjectData.elementPair.indices.length);
            } else {
                PERSISTENT_MAPPED_IBO.directWriteCommand(base, 0, 0);
            }
        }

        PERSISTENT_MAPPED_VBO.syncToGPU();
        PERSISTENT_MAPPED_EBO.syncToGPU();
        PERSISTENT_MAPPED_IBO.syncToGPU();

        GL_STATE_CACHE.bindVertexArray(vaoId);
        GL_STATE_CACHE.bindElementArrayBuffer(PERSISTENT_MAPPED_EBO.getBufferId());
        GL_STATE_CACHE.bindIndirectBuffer(PERSISTENT_MAPPED_IBO.getBufferId());

        glMultiDrawElementsIndirect(
                mode,
                GL_UNSIGNED_INT,
                0,
                PERSISTENT_MAPPED_IBO.getCommandCount(),
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

    public static class MeshData {

    }
}
