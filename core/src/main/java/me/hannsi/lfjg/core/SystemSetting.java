package me.hannsi.lfjg.core;

import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import org.joml.Vector4f;

public class SystemSetting {
    public static boolean CORE_SYSTEM_DEBUG = true;
    public static boolean DEBUG_LOG_FABRIC_DEBUG = false;
    public static int LOG_GENERATOR_BAR_COUNT = 30;
    public static String WORKSPACE_MANAGER_DEFAULT_WORKSPACE_NAME = "lfjg/workspace";
    public static String STRING_UTIL_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static String UNICODE_BLOCKS_URL = "https://www.unicode.org/Public/UNIDATA/Blocks.txt";
    public static boolean UNICODE_BLOCKS_DEBUG_UNICODE = true;

    public static ProjectionType MESH_CONSTANTS_DEFAULT_PROJECTION_TYPE = ProjectionType.ORTHOGRAPHIC_PROJECTION;
    public static int MESH_CONSTANTS_FLAGS_HINT = Core.GL30.GL_MAP_WRITE_BIT | Core.GL44.GL_MAP_PERSISTENT_BIT | Core.GL44.GL_MAP_COHERENT_BIT;
    public static int MESH_CONSTANTS_BUFFER_COUNT = 3;

    public static boolean POLYGON_TRIANGULATOR_DEBUG = false;
    public static float POLYGON_TRIANGULATOR_MITER_LIMIT = 4.0f;
    public static float POLYGON_TRIANGULATOR_TOLERANCE = 1.0f;

    public static Vector4f MATERIAL_DEFAULT_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);

    public static float GL_OBJECT_POOL_REMOVE_RATIO_THRESHOLD = 0.3f;

    public static int LFJG_RENDER_CONTEXT_INITIAL_VBO_CAPACITY = 10000;
    public static int LFJG_RENDER_CONTEXT_INITIAL_EBO_CAPACITY = 8000;
    public static int LFJG_RENDER_CONTEXT_INITIAL_IBO_CAPACITY = 2500;

    public static boolean GL_INTERCEPTOR_DEBUG = true;
}
