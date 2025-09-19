package me.hannsi.lfjg.render.system;

import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.system.mesh.MeshConstants;
import me.hannsi.lfjg.render.system.mesh.persistent.PersistentMappedVBO;
import org.lwjgl.opengl.GL30;

public class TestMesh {
    private final int vaoId;

    private final ProjectionType projectionType;
    private final int flagsHint;
    private final boolean useElementBufferObject;
    private final boolean useIndirect;

    private PersistentMappedVBO persistentMappedVBO;

    public TestMesh() {
        this.vaoId = GL30.glGenVertexArrays();

        this.projectionType = MeshConstants.DEFAULT_PROJECTION_TYPE;
        this.flagsHint = MeshConstants.DEFAULT_FLAGS_HINT;
        this.useElementBufferObject = MeshConstants.DEFAULT_USE_ELEMENT_BUFFER_OBJECT;
        this.useIndirect = MeshConstants.DEFAULT_USE_INDIRECT;

        GL30.glBindVertexArray(vaoId);
    }


}
