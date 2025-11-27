package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.utils.type.types.ProjectionType;

import static me.hannsi.lfjg.core.SystemSetting.*;

public final class MeshConstants {
    public static final int FLOATS_PER_VERTEX = BufferObjectType.POSITION_BUFFER.getAttributeSize() + BufferObjectType.COLOR_BUFFER.getAttributeSize() + BufferObjectType.TEXTURE_BUFFER.getAttributeSize() + BufferObjectType.NORMAL_BUFFER.getAttributeSize();
    public static final boolean DEFAULT_USE_ELEMENT_BUFFER_OBJECT = false; // 廃止
    public static final boolean DEFAULT_USE_INDIRECT = false; // 廃止
    public static ProjectionType DEFAULT_PROJECTION_TYPE = MESH_CONSTANTS_DEFAULT_PROJECTION_TYPE;
    public static int DEFAULT_FLAGS_HINT = MESH_CONSTANTS_FLAGS_HINT;
    public static int DEFAULT_BUFFER_COUNT = MESH_CONSTANTS_BUFFER_COUNT;

    private MeshConstants() {
    }
}