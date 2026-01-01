package me.hannsi.lfjg.render;

import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import org.joml.Vector4f;

import static me.hannsi.lfjg.render.LFJGRenderContext.MAX_TEXTURE_SIZE;

public class RenderSystemSetting {
    public static boolean MESH_DEBUG_DIRECT_DELETE_OBJECT_REINSERTION = false;

    public static ProjectionType MESH_CONSTANTS_DEFAULT_PROJECTION_TYPE = ProjectionType.ORTHOGRAPHIC_PROJECTION;
    public static int MESH_CONSTANTS_BUFFER_COUNT = 3;

    public static boolean POLYGON_TRIANGULATOR_DEBUG = false;
    public static float POLYGON_TRIANGULATOR_MITER_LIMIT = 4.0f;
    public static float POLYGON_TRIANGULATOR_TOLERANCE = 1.0f;

    public static Vector4f MATERIAL_DEFAULT_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);

    public static float GL_OBJECT_POOL_REMOVE_RATIO_THRESHOLD = 0.3f;

    public static int LFJG_RENDER_CONTEXT_INITIAL_VBO_CAPACITY = 10000;
    public static int LFJG_RENDER_CONTEXT_INITIAL_EBO_CAPACITY = 8000;
    public static int LFJG_RENDER_CONTEXT_INITIAL_IBO_CAPACITY = 2500;
    public static int LFJG_RENDER_CONTEXT_INITIAL_SSBO_CAPACITY = 500;
    public static int LFJG_RENDER_CONTEXT_INITIAL_PBO_CAPACITY = 10000;

    public static boolean VAO_RENDERING_FRONT_AND_BACK = false;
    public static float VAO_RENDERING_FRONT_AND_BACK_LINE_WIDTH = 0.1f;

    public static int ATLAS_PACKER_ATLAS_WIDTH = MAX_TEXTURE_SIZE;
    public static int ATLAS_PACKER_ATLAS_HEIGHT = MAX_TEXTURE_SIZE;
    public static int ATLAS_PACKER_ATLAS_LAYER = 1;
}
