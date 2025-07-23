package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15C.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL40.GL_DRAW_INDIRECT_BUFFER;

public enum BufferObjectType implements IEnumTypeBase {
    VERTEX_ARRAY_BUFFER(0, GL_ARRAY_BUFFER, "VertexArrayBuffer"),
    POSITIONS_BUFFER(1, GL_ARRAY_BUFFER, "PositionsBuffer"),
    COLORS_BUFFER(4, GL_ARRAY_BUFFER, "ColorsBuffer"),
    TEXTURE_BUFFER(5, GL_ARRAY_BUFFER, "TextureBuffer"),
    INDIRECT_BUFFER(6, GL_DRAW_INDIRECT_BUFFER, "IndirectBuffer"),
    ELEMENT_ARRAY_BUFFER(7, GL_ELEMENT_ARRAY_BUFFER, "ElementArrayBuffer"),
    NORMALS_BUFFER(8, GL_ARRAY_BUFFER, "NormalsBuffer");

    final int id;
    final int glId;
    final String name;

    BufferObjectType(int id, int glId, String name) {
        this.id = id;
        this.glId = glId;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getGlId() {
        return glId;
    }
}
