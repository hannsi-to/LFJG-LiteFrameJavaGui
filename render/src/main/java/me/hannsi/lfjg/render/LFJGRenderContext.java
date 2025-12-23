package me.hannsi.lfjg.render;

import me.hannsi.lfjg.core.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.toolkit.Camera;
import me.hannsi.lfjg.render.system.mesh.MeshConstants;
import me.hannsi.lfjg.render.system.mesh.TestMesh;
import me.hannsi.lfjg.render.system.mesh.persistent.TestPersistentMappedEBO;
import me.hannsi.lfjg.render.system.mesh.persistent.TestPersistentMappedIBO;
import me.hannsi.lfjg.render.system.mesh.persistent.TestPersistentMappedSSBO;
import me.hannsi.lfjg.render.system.mesh.persistent.TestPersistentMappedVBO;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.rendering.VAORendering;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import org.joml.Matrix4f;

import static me.hannsi.lfjg.core.Core.projection2D;
import static me.hannsi.lfjg.core.Core.projection3D;
import static me.hannsi.lfjg.core.SystemSetting.*;
import static org.lwjgl.opengl.ARBSparseTexture.GL_VIRTUAL_PAGE_SIZE_X_ARB;
import static org.lwjgl.opengl.ARBSparseTexture.GL_VIRTUAL_PAGE_SIZE_Y_ARB;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL42.glGetInternalformativ;

public class LFJGRenderContext {
    public static final int PAGE_SIZE_X;
    public static final int PAGE_SIZE_Y;
    public static IdPool ID_POOL;
    public static GLObjectPool GL_OBJECT_POOL;
    public static Camera MAIN_CAMERA;
    public static Matrix4f precomputedViewProjection2D;
    public static Matrix4f precomputedViewProjection3D;
    public static ShaderProgram SHADER_PROGRAM;
    public static TestPersistentMappedVBO PERSISTENT_MAPPED_VBO;
    public static TestPersistentMappedEBO PERSISTENT_MAPPED_EBO;
    public static TestPersistentMappedIBO PERSISTENT_MAPPED_IBO;
    public static TestPersistentMappedSSBO PERSISTENT_MAPPED_SSBO;
    public static TestMesh MESH;
    public static VAORendering VAO_RENDERING;
    public static GLStateCache GL_STATE_CACHE;
    public static TextureCache TEXTURE_CACHE;

    static {
        int[] x = new int[1];
        int[] y = new int[1];
        glGetInternalformativ(GL_TEXTURE_2D_ARRAY, GL_RGBA8, GL_VIRTUAL_PAGE_SIZE_X_ARB, x);
        glGetInternalformativ(GL_TEXTURE_2D_ARRAY, GL_RGBA8, GL_VIRTUAL_PAGE_SIZE_Y_ARB, y);
        PAGE_SIZE_X = x[0];
        PAGE_SIZE_Y = y[0];
    }

    public static void init() {
        ID_POOL = new IdPool();
        GL_OBJECT_POOL = new GLObjectPool();

        MAIN_CAMERA = new Camera();

        precomputedViewProjection2D = projection2D.getMatrix4f().mul(MAIN_CAMERA.getViewMatrix());
        precomputedViewProjection3D = projection3D.getMatrix4f().mul(MAIN_CAMERA.getViewMatrix());

        GL_STATE_CACHE = new GLStateCache();

        SHADER_PROGRAM = new ShaderProgram();
        SHADER_PROGRAM.createVertexShader(Location.fromResource("shader/VertexShader.vsh"));
        SHADER_PROGRAM.createFragmentShader(Location.fromResource("shader/FragmentShader.fsh"));
        SHADER_PROGRAM.link();

        PERSISTENT_MAPPED_VBO = new TestPersistentMappedVBO(MeshConstants.DEFAULT_FLAGS_HINT, LFJG_RENDER_CONTEXT_INITIAL_VBO_CAPACITY);
        PERSISTENT_MAPPED_EBO = new TestPersistentMappedEBO(MeshConstants.DEFAULT_FLAGS_HINT, LFJG_RENDER_CONTEXT_INITIAL_EBO_CAPACITY);
        PERSISTENT_MAPPED_IBO = new TestPersistentMappedIBO(MeshConstants.DEFAULT_FLAGS_HINT, LFJG_RENDER_CONTEXT_INITIAL_IBO_CAPACITY);
        PERSISTENT_MAPPED_SSBO = new TestPersistentMappedSSBO(MeshConstants.DEFAULT_FLAGS_HINT, LFJG_RENDER_CONTEXT_INITIAL_SSBO_CAPACITY);

        MESH = TestMesh.createMesh(
                LFJG_RENDER_CONTEXT_INITIAL_VBO_CAPACITY,
                LFJG_RENDER_CONTEXT_INITIAL_EBO_CAPACITY,
                LFJG_RENDER_CONTEXT_INITIAL_IBO_CAPACITY
        );


        VAO_RENDERING = new VAORendering();

        TEXTURE_CACHE = TextureCache.createTextureCache();
    }
}
