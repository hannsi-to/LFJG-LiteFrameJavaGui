package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL44;

public final class MeshConstants {
    public static final ProjectionType DEFAULT_PROJECTION_TYPE = ProjectionType.ORTHOGRAPHIC_PROJECTION;
    public static final int FLOATS_PER_VERTEX = BufferObjectType.POSITION_BUFFER.getAttributeSize() + BufferObjectType.COLOR_BUFFER.getAttributeSize() + BufferObjectType.TEXTURE_BUFFER.getAttributeSize() + BufferObjectType.NORMAL_BUFFER.getAttributeSize();
    public static final boolean DEFAULT_USE_ELEMENT_BUFFER_OBJECT = false;
    public static final boolean DEFAULT_USE_INDIRECT = false;
    public static final int DEFAULT_FLAGS_HINT = GL30.GL_MAP_WRITE_BIT | GL44.GL_MAP_PERSISTENT_BIT | GL44.GL_MAP_COHERENT_BIT;
    public static final int DEFAULT_BUFFER_COUNT = 3;

    private MeshConstants() {
    }
}