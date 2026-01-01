package me.hannsi.lfjg.render;

import me.hannsi.lfjg.core.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.toolkit.Camera;
import me.hannsi.lfjg.render.system.mesh.MeshConstants;
import me.hannsi.lfjg.render.system.mesh.TestMesh;
import me.hannsi.lfjg.render.system.mesh.persistent.*;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.rendering.VAORendering;
import me.hannsi.lfjg.render.system.rendering.texture.SparseTexture2DArray;
import me.hannsi.lfjg.render.system.rendering.texture.atlas.AtlasPacker;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.render.uitl.id.GLObjectPool;
import org.joml.Matrix4f;

import static me.hannsi.lfjg.core.Core.projection2D;
import static me.hannsi.lfjg.core.Core.projection3D;
import static me.hannsi.lfjg.render.RenderSystemSetting.*;
import static org.lwjgl.opengl.ARBSparseTexture.GL_VIRTUAL_PAGE_SIZE_X_ARB;
import static org.lwjgl.opengl.ARBSparseTexture.GL_VIRTUAL_PAGE_SIZE_Y_ARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_MAX_ARRAY_TEXTURE_LAYERS;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL42.glGetInternalformativ;

public class LFJGRenderContext {
    public static final int NO_ATTACH_TEXTURE = -1;
    public static final int VIRTUAL_PAGE_SIZE_X;
    public static final int VIRTUAL_PAGE_SIZE_Y;
    public static final int MAX_ARRAY_TEXTURE_LAYERS;
    public static final int MAX_TEXTURE_SIZE;
    public static GLObjectPool glObjectPool;
    public static Camera mainCamera;
    public static Matrix4f precomputedViewProjection2D;
    public static Matrix4f precomputedViewProjection3D;
    public static GLStateCache glStateCache;
    public static AtlasPacker atlasPacker;
    public static SparseTexture2DArray sparseTexture2DArray;
    public static ShaderProgram shaderProgram;
    public static TestPersistentMappedVBO persistentMappedVBO;
    public static TestPersistentMappedEBO persistentMappedEBO;
    public static TestPersistentMappedIBO persistentMappedIBO;
    public static TestPersistentMappedSSBO persistentMappedSSBO;
    public static TestPersistentMappedPBO persistentMappedPBO;
    public static TestMesh mesh;
    public static VAORendering vaoRendering;
    public static TextureCache textureCache;

    static {
        int[] x = new int[1];
        int[] y = new int[1];
        glGetInternalformativ(GL_TEXTURE_2D_ARRAY, GL_RGBA8, GL_VIRTUAL_PAGE_SIZE_X_ARB, x);
        glGetInternalformativ(GL_TEXTURE_2D_ARRAY, GL_RGBA8, GL_VIRTUAL_PAGE_SIZE_Y_ARB, y);
        VIRTUAL_PAGE_SIZE_X = x[0];
        VIRTUAL_PAGE_SIZE_Y = y[0];
        MAX_ARRAY_TEXTURE_LAYERS = glGetInteger(GL_MAX_ARRAY_TEXTURE_LAYERS);
        MAX_TEXTURE_SIZE = glGetInteger(GL_MAX_TEXTURE_SIZE);
    }

    public static void init() {
        glObjectPool = new GLObjectPool();

        mainCamera = new Camera();

        precomputedViewProjection2D = projection2D.getMatrix4f().mul(mainCamera.getViewMatrix());
        precomputedViewProjection3D = projection3D.getMatrix4f().mul(mainCamera.getViewMatrix());

        glStateCache = new GLStateCache();

        atlasPacker = new AtlasPacker(ATLAS_PACKER_ATLAS_WIDTH, ATLAS_PACKER_ATLAS_HEIGHT, ATLAS_PACKER_ATLAS_LAYER, 0, 0, 0);

        sparseTexture2DArray = new SparseTexture2DArray(atlasPacker);

        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Location.fromResource("shader/VertexShader.vsh"));
        shaderProgram.createFragmentShader(Location.fromResource("shader/FragmentShader.fsh"));
        shaderProgram.link();

        persistentMappedVBO = new TestPersistentMappedVBO(MeshConstants.DEFAULT_FLAGS_HINT, LFJG_RENDER_CONTEXT_INITIAL_VBO_CAPACITY);
        persistentMappedEBO = new TestPersistentMappedEBO(MeshConstants.DEFAULT_FLAGS_HINT, LFJG_RENDER_CONTEXT_INITIAL_EBO_CAPACITY);
        persistentMappedIBO = new TestPersistentMappedIBO(MeshConstants.DEFAULT_FLAGS_HINT, LFJG_RENDER_CONTEXT_INITIAL_IBO_CAPACITY);
        persistentMappedSSBO = new TestPersistentMappedSSBO(MeshConstants.DEFAULT_FLAGS_HINT, LFJG_RENDER_CONTEXT_INITIAL_SSBO_CAPACITY);
        persistentMappedPBO = new TestPersistentMappedPBO(MeshConstants.DEFAULT_FLAGS_HINT, LFJG_RENDER_CONTEXT_INITIAL_PBO_CAPACITY);

        mesh = TestMesh.createMesh(
                LFJG_RENDER_CONTEXT_INITIAL_VBO_CAPACITY,
                LFJG_RENDER_CONTEXT_INITIAL_EBO_CAPACITY,
                LFJG_RENDER_CONTEXT_INITIAL_IBO_CAPACITY
        );


        vaoRendering = new VAORendering();


        textureCache = TextureCache.createTextureCache();
    }

    public static void update() {
        if (mainCamera.isDirtyFlag()) {
            precomputedViewProjection2D = projection2D.getMatrix4f().mul(mainCamera.getViewMatrix());
            precomputedViewProjection3D = projection3D.getMatrix4f().mul(mainCamera.getViewMatrix());
            mainCamera.setDirtyFlag(false);
        }
    }
}
