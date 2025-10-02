package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.system.mesh.persistent.TestPersistentMappedEBO;
import me.hannsi.lfjg.render.system.mesh.persistent.TestPersistentMappedVBO;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class TestMesh {
    private final TestPersistentMappedVBO persistentMappedVBO;
    private final TestPersistentMappedEBO persistentMappedEBO;
    private final List<Integer> counts;
    private final List<Integer> offsets;
    private final int[] vaoIds;
    private DrawCommand drawCommand;
    private int currentIndex;
    private int vertexCount;

    TestMesh() {
        this.persistentMappedVBO = new TestPersistentMappedVBO(MeshConstants.DEFAULT_FLAGS_HINT, 64);
        this.persistentMappedEBO = new TestPersistentMappedEBO(MeshConstants.DEFAULT_FLAGS_HINT, 64);
        this.counts = new ArrayList<>();
        this.offsets = new ArrayList<>();
        this.vaoIds = new int[MeshConstants.DEFAULT_BUFFER_COUNT];

        this.currentIndex = 0;
        this.vertexCount = 0;

        for (int i = 0; i < MeshConstants.DEFAULT_BUFFER_COUNT; i++) {
            this.vaoIds[i] = GL30.glGenVertexArrays();
        }
    }

    public TestMesh initBufferObject() {
        persistentMappedVBO.createVertexAttribute(vaoIds, BufferObjectType.POSITION_BUFFER, BufferObjectType.COLOR_BUFFER, BufferObjectType.TEXTURE_BUFFER, BufferObjectType.NORMAL_BUFFER)
                .syncToGPU();

        persistentMappedEBO.linkVertexArrayObject(vaoIds)
                .syncToGPU();

        return this;
    }

    public TestMesh addObject(ProjectionType projectionType, DrawType drawType, Vertex... vertices) {
        float[] positions = new float[vertices.length * 3];
        for (int i = 0, index = 0; i < vertices.length; i++, index += 3) {
            Vertex vertex = vertices[i];
            float[] pos = vertex.getPositions();
            positions[index] = pos[0];
            positions[index + 1] = pos[1];
            positions[index + 2] = pos[2];

            persistentMappedVBO.add(vertex);
        }

        ElementPair elementPair = setupElementBufferObject(projectionType, drawType, positions);
        int startOffset = persistentMappedEBO.getIndexCount();
        for (int index : elementPair.indices) {
            persistentMappedEBO.add(index + vertexCount);
        }

        counts.add(elementPair.indices.length);
        offsets.add(startOffset * Integer.BYTES);

        vertexCount += vertices.length;

        return this;
    }

    private ElementPair setupElementBufferObject(ProjectionType projectionType, DrawType drawType, float[] positions) {
        return PolygonTriangulator.createPolygonTriangulator()
                .drawType(drawType)
                .projectionType(projectionType)
                .positions(positions)
                .process()
                .getResult();
    }

    public TestMesh rotate() {
        currentIndex = (currentIndex + 1) % MeshConstants.DEFAULT_BUFFER_COUNT;
        return this;
    }
}
