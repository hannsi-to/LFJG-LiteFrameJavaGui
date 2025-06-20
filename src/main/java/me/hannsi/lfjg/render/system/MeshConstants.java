package me.hannsi.lfjg.render.system;

import me.hannsi.lfjg.utils.type.types.ProjectionType;

import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL30.*;

public final class MeshConstants {
    public static final ProjectionType PROJECTION_TYPE = ProjectionType.ORTHOGRAPHIC_PROJECTION;
    public static final boolean USE_ELEMENT_BUFFER_OBJECT = true;
    public static final boolean USE_INDIRECT = true;
    public static final int USAGE_HINT = GL_DYNAMIC_DRAW;
    public static final int ACCESS_HINT = GL_MAP_WRITE_BIT | GL_MAP_INVALIDATE_BUFFER_BIT | GL_MAP_UNSYNCHRONIZED_BIT;
    public static final int BUFFER_COUNT = 1;

    private MeshConstants() {
    }
}