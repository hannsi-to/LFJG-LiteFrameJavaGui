package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.utils.type.types.ProjectionType;

import static me.hannsi.lfjg.core.SystemSetting.MESH_CONSTANTS_BUFFER_COUNT;
import static me.hannsi.lfjg.core.SystemSetting.MESH_CONSTANTS_DEFAULT_PROJECTION_TYPE;
import static org.lwjgl.opengl.GL30.GL_MAP_WRITE_BIT;
import static org.lwjgl.opengl.GL44.GL_MAP_COHERENT_BIT;
import static org.lwjgl.opengl.GL44.GL_MAP_PERSISTENT_BIT;

public final class MeshConstants {
    public static final int FLOATS_PER_VERTEX = BufferObjectType.POSITION_BUFFER.getAttributeSize() + BufferObjectType.COLOR_BUFFER.getAttributeSize() + BufferObjectType.TEXTURE_BUFFER.getAttributeSize() + BufferObjectType.NORMAL_BUFFER.getAttributeSize();
    public static final boolean DEFAULT_USE_ELEMENT_BUFFER_OBJECT = false; // 廃止
    public static final boolean DEFAULT_USE_INDIRECT = false; // 廃止
    public static ProjectionType DEFAULT_PROJECTION_TYPE = MESH_CONSTANTS_DEFAULT_PROJECTION_TYPE;
    public static int DEFAULT_FLAGS_HINT = GL_MAP_WRITE_BIT | GL_MAP_PERSISTENT_BIT | GL_MAP_COHERENT_BIT;
    public static int DEFAULT_BUFFER_COUNT = MESH_CONSTANTS_BUFFER_COUNT;

    private MeshConstants() {
    }
}