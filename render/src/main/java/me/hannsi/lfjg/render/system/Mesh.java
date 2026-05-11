package me.hannsi.lfjg.render.system;

import me.hannsi.lfjg.render.render.Vertex;

import java.util.ArrayList;
import java.util.List;

public class Mesh {
    private final List<MeshBuilder> pendingMeshBuilders;
    private final int vboId;
    private final int eboId;
    private final int iboId;
    private boolean needRepack;

    Mesh(int vboId, int eboId, int iboId) {
        this.pendingMeshBuilders = new ArrayList<>();
        this.vboId = vboId;
        this.eboId = eboId;
        this.iboId = iboId;
        this.needRepack = false;

        int stride = Vertex.BYTES;
    }

    public static Mesh createMesh(int vboId, int eboId, int iboId) {
        return new Mesh(vboId, eboId, iboId);
    }

    public Mesh addObject(MeshBuilder meshBuilder) {
        pendingMeshBuilders.add(meshBuilder);

        needRepack = true;

        return this;
    }
}
