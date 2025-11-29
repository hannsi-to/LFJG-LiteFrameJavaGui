package me.hannsi.lfjg.render;

import me.hannsi.lfjg.core.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.toolkit.Camera;
import me.hannsi.lfjg.render.system.mesh.TestMesh;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;

import static me.hannsi.lfjg.core.SystemSetting.*;

public class LFJGRenderContext {
    public static final IdPool ID_POOL;
    public static final GLObjectPool GL_OBJECT_POOL;
    public static final Camera MAIN_CAMERA;
    public static final ShaderProgram SHADER_PROGRAM;
    public static final TestMesh MESH;
    public static final GLStateCache GL_STATE_CACHE;
    public static final TextureCache TEXTURE_CACHE;

    static {
        ID_POOL = new IdPool();
        GL_OBJECT_POOL = new GLObjectPool();

        MAIN_CAMERA = new Camera();

        GL_STATE_CACHE = new GLStateCache();

        SHADER_PROGRAM = new ShaderProgram();
        SHADER_PROGRAM.createVertexShader(Location.fromResource("shader/VertexShader.vsh"));
        SHADER_PROGRAM.createFragmentShader(Location.fromResource("shader/FragmentShader.fsh"));
        SHADER_PROGRAM.link();

        MESH = TestMesh.createMesh(
                LFJG_RENDER_CONTEXT_INITIAL_VBO_CAPACITY,
                LFJG_RENDER_CONTEXT_INITIAL_EBO_CAPACITY,
                LFJG_RENDER_CONTEXT_INITIAL_IBO_CAPACITY
        );

        TEXTURE_CACHE = TextureCache.createTextureCache();
    }
}
