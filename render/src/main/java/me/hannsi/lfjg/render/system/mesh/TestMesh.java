package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.render.system.mesh.persistent.TestPersistentMappedVBO;
import org.lwjgl.opengl.GL30;

public class TestMesh {
    private final int[] vaoIds;
    private int currentIndex;
    private TestPersistentMappedVBO persistentMappedVBO;

    TestMesh() {
        this.vaoIds = new int[MeshConstants.DEFAULT_BUFFER_COUNT];
        this.currentIndex = 0;

        for (int i = 0; i < MeshConstants.DEFAULT_BUFFER_COUNT; i++) {
            this.vaoIds[i] = GL30.glGenVertexArrays();
        }
    }

    public TestMesh addVertex(float[] positions, float[] colors, float[] uvs, float[] normals) {


        return this;
    }

    public TestMesh rotate() {
        currentIndex = (currentIndex + 1) % MeshConstants.DEFAULT_BUFFER_COUNT;
        return this;
    }
}
