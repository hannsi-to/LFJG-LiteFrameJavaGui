package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.renderers.JointType;
import me.hannsi.lfjg.render.system.mesh.persistent.TestPersistentMappedEBO;
import me.hannsi.lfjg.render.system.mesh.persistent.TestPersistentMappedIBO;
import me.hannsi.lfjg.render.system.mesh.persistent.TestPersistentMappedVBO;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;

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

        persistentMappedEBO.linkVertexArrayObject(vaoId).
                syncToGPU();

        persistentMappedIBO.syncToGPU();

        GLStateCache.bindVertexArray(0);
        return this;
    }

    public TestMesh addObject(ProjectionType projectionType, DrawType drawType, float lineWidth, JointType jointType, Vertex... vertices) {
//        float[] positions = new float[vertices.length * 3];
//        for (int i = 0, index = 0; i < vertices.length; i++, index += 3) {
//            Vertex vertex = vertices[i];
//            float[] pos = vertex.getPositions();
//            positions[index] = pos[0];
//            positions[index + 1] = pos[1];
//            positions[index + 2] = pos[2];
//
//
//        }

        TestElementPair elementPair = setupElementBufferObject(projectionType, drawType, lineWidth, jointType, vertices);
        for (Vertex vertex : elementPair.vertices) {
            persistentMappedVBO.add(vertex);
        }

        int startOffset = persistentMappedEBO.getIndexCount();
        for (int index : elementPair.indices) {
            persistentMappedEBO.add(index);
        }

        persistentMappedIBO.addCommand(
                new DrawElementsIndirectCommand(
                        elementPair.indices.length,
                        1,
                        startOffset,
                        vertexCount,
                        0
                )
        );

        vertexCount += vertices.length;

        return this;
    }

    private TestElementPair setupElementBufferObject(ProjectionType projectionType, DrawType drawType, float lineWidth, JointType jointType, Vertex[] vertices) {
        return TestPolygonTriangulator.createPolygonTriangulator()
                .drawType(drawType)
                .lineWidth(lineWidth)
                .lineJointType(jointType)
                .projectionType(projectionType)
                .vertices(vertices)
                .process()
                .getResult();
    }

    public void debugDraw(int mode) {
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
