package me.hannsi.lfjg.render.renderers;

import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.render.system.mesh.MeshBuilder;

import static me.hannsi.lfjg.render.LFJGRenderContext.mesh;

public class GLObject {
    private final String name;
    private IntRef objectId;
    private MeshBuilder meshBuilder;

    public GLObject(String name) {
        this.name = name;
        this.objectId = new IntRef();
    }

    public void create(MeshBuilder meshBuilder) {
        this.meshBuilder = meshBuilder;

        objectId = meshBuilder.getObjectIdPointer();
        mesh.addObject(meshBuilder);
    }

    public GLObject copy(String objectName) {
        return this;
    }

    public String getName() {
        return name;
    }

    public int getObjectId() {
        return objectId.getValue();
    }
}