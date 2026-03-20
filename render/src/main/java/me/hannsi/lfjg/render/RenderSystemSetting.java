package me.hannsi.lfjg.render;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import org.joml.Vector4f;

import static me.hannsi.lfjg.render.LFJGRenderContext.*;

public class RenderSystemSetting {
    public static ProjectionType MESH_CONSTANTS_DEFAULT_PROJECTION_TYPE = ProjectionType.ORTHOGRAPHIC_PROJECTION;
    public static int MESH_CONSTANTS_BUFFER_COUNT = 3;

    public static boolean POLYGON_TRIANGULATOR_DEBUG = false;
    public static float POLYGON_TRIANGULATOR_MITER_LIMIT = 4.0f;
    public static float POLYGON_TRIANGULATOR_TOLERANCE = 1.0f;

    public static Vector4f MATERIAL_DEFAULT_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);

    public static Color VERTEX_DEFAULT_COLOR = Color.RED;

    public static int CORNER_DEFAULT_SEGMENT_COUNT = 16;

    public static int GL_CIRCLE_DEFAULT_SEGMENT_COUNT = 16;

    public static float GL_OBJECT_POOL_REMOVE_RATIO_THRESHOLD = 0.3f;

    public static int GL_BEZIER_LINE_MIN_SEGMENT = 4;
    public static int GL_BEZIER_LINE_MAX_SEGMENT = 64;
    public static float GL_BEZIER_LINE_SEGMENTS_PER_UNIT = 0.5f;

    public static int LFJG_RENDER_CONTEXT_INITIAL_VBO_CAPACITY = 40960;
    public static int LFJG_RENDER_CONTEXT_INITIAL_EBO_CAPACITY = 32000;
    public static int LFJG_RENDER_CONTEXT_INITIAL_IBO_CAPACITY = 4096;
    public static int LFJG_RENDER_CONTEXT_INITIAL_SSBO_CAPACITY = 4096;
    public static int LFJG_RENDER_CONTEXT_INITIAL_SSBO_DATA_CAPACITY = 1024;
    public static int LFJG_RENDER_CONTEXT_INITIAL_PUBO_CAPACITY = 10240;
    public static int LFJG_RENDER_CONTEXT_INITIAL_PUBO_DATA_CAPACITY = 1024;

    public static boolean VAO_RENDERING_FRONT_AND_BACK = false;
    public static float VAO_RENDERING_FRONT_AND_BACK_LINE_WIDTH = 0.1f;

    public static int ATLAS_PACKER_ATLAS_WIDTH = MAX_TEXTURE_SIZE;
    public static int ATLAS_PACKER_ATLAS_HEIGHT = MAX_TEXTURE_SIZE;
    public static int ATLAS_PACKER_ATLAS_LAYER = 1;

    public static ProjectionType INSTANCE_PARAMETER_DEFAULT_PROJECTION_TYPE = ProjectionType.ORTHOGRAPHIC_PROJECTION;
    public static int INSTANCE_PARAMETER_DEFAULT_SPRITE_INDEX = NO_ATTACH_TEXTURE;
    public static Color INSTANCE_PARAMETER_DEFAULT_COLOR = new Color(1f, 1f, 1f, 1f);

    public static int PERSISTENT_MAPPED_VBO_ALIGNMENT = MIN_MAP_BUFFER_ALIGNMENT;
    public static boolean PERSISTENT_MAPPED_VBO_DEBUG = false;

    public static int PERSISTENT_MAPPED_EBO_ALIGNMENT = MIN_MAP_BUFFER_ALIGNMENT;
    public static boolean PERSISTENT_MAPPED_EBO_DEBUG = false;

    public static int PERSISTENT_MAPPED_IBO_ALIGNMENT = MIN_MAP_BUFFER_ALIGNMENT;
    public static boolean PERSISTENT_MAPPED_IBO_DEBUG = false;

    public static int PERSISTENT_MAPPED_SSBO_ALIGNMENT = MIN_MAP_BUFFER_ALIGNMENT;
    public static boolean PERSISTENT_MAPPED_SSBO_DEBUG = false;

    public static int PERSISTENT_MAPPED_PUBO_ALIGNMENT = MIN_MAP_BUFFER_ALIGNMENT;
    public static boolean PERSISTENT_MAPPED_PUBO_DEBUG = false;

    public static boolean RENDER_DEBUG_THROW_ERROR = true;
}