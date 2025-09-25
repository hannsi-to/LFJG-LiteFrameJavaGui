package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL40;

public enum BufferObjectType implements IEnumTypeBase {
    POSITION_2D_BUFFER(0, GL15.GL_ARRAY_BUFFER, 0, 2, "Position2DBuffer"),
    POSITION_3D_BUFFER(1, GL15.GL_ARRAY_BUFFER, 0, 3, "Position3DBuffer"),
    COLOR_BUFFER(2, GL15.GL_ARRAY_BUFFER, 1, 4, "ColorBuffer"),
    TEXTURE_BUFFER(3, GL15.GL_ARRAY_BUFFER, 2, 2, "TextureBuffer"),
    NORMAL_2D_BUFFER(4, GL15.GL_ARRAY_BUFFER, 3, 2, "Normal2DBuffer"),
    NORMAL_3D_BUFFER(5, GL15.GL_ARRAY_BUFFER, 3, 3, "Normal3DBuffer"),
    INDIRECT_BUFFER(6, GL40.GL_DRAW_INDIRECT_BUFFER, -1, -1, "IndirectBuffer"),
    ELEMENT_ARRAY_BUFFER(7, GL15.GL_ELEMENT_ARRAY_BUFFER, -1, -1, "ElementArrayBuffer");

    final int id;
    final int glId;
    final int attributeIndex;
    final int attributeSize;
    final String name;

    BufferObjectType(int id, int glId, int attributeIndex, int attributeSize, String name) {
        this.id = id;
        this.glId = glId;
        this.attributeIndex = attributeIndex;
        this.attributeSize = attributeSize;
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

    public int getAttributeIndex() {
        return attributeIndex;
    }

    public int getAttributeSize() {
        return attributeSize;
    }
}
