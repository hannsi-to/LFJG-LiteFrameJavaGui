package me.hannsi.lfjg.render;

import me.hannsi.lfjg.core.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.core.utils.toolkit.Camera;
import me.hannsi.lfjg.render.event.RenderCleanupEvent;
import me.hannsi.lfjg.render.manager.AssetManager;
import me.hannsi.lfjg.render.system.mesh.MeshConstants;
import me.hannsi.lfjg.render.system.mesh.TestMesh;
import me.hannsi.lfjg.render.system.mesh.persistent.*;
import me.hannsi.lfjg.render.system.rendering.DrawBatch;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.rendering.VAORendering;
import me.hannsi.lfjg.render.system.rendering.texture.SparseTexture2DArray;
import me.hannsi.lfjg.render.system.rendering.texture.atlas.AtlasPacker;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import me.hannsi.lfjg.render.uitl.id.GLObjectPool;
import org.joml.Matrix4f;

import java.util.HashSet;
import java.util.Set;

import static me.hannsi.lfjg.core.Core.*;
import static me.hannsi.lfjg.render.RenderSystemSetting.*;
import static org.lwjgl.opengl.ARBSparseTexture.GL_VIRTUAL_PAGE_SIZE_X_ARB;
import static org.lwjgl.opengl.ARBSparseTexture.GL_VIRTUAL_PAGE_SIZE_Y_ARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_MAX_ARRAY_TEXTURE_LAYERS;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL42.GL_MIN_MAP_BUFFER_ALIGNMENT;
import static org.lwjgl.opengl.GL42.glGetInternalformativ;
import static org.lwjgl.opengl.GL43.GL_SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT;

public class LFJGRenderContext {
    public static final int NO_ATTACH_TEXTURE = -1;
    public static final int VIRTUAL_PAGE_SIZE_X;
    public static final int VIRTUAL_PAGE_SIZE_Y;
    public static final int MAX_ARRAY_TEXTURE_LAYERS;
    public static final int MAX_TEXTURE_SIZE;
    public static final int MIN_MAP_BUFFER_ALIGNMENT;
    public static final int SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT;
    public static final int OBJECT_DATA_BIDING_POINT;
    public static final int INSTANCE_PARAMETERS_BINDING_POINT;
    public static final int SPRITE_DATUM_BINDING_POINT;
    public static GLObjectPool glObjectPool;
    public static DrawBatch drawBatch;
    public static Set<IntRef> needUpdateBuilders;
    public static Camera mainCamera;
    public static Matrix4f precomputedViewProjection2D;
    public static Matrix4f precomputedViewProjection3D;
    public static GLStateCache glStateCache;
    public static AtlasPacker atlasPacker;
    public static SparseTexture2DArray sparseTexture2DArray;
    public static ShaderProgram shaderProgram;
    public static Test2PersistentMappedVBO persistentMappedVBO;
    public static Test2PersistentMappedEBO persistentMappedEBO;
    public static Test2PersistentMappedIBO persistentMappedIBO;
    public static Test2PersistentMappedSSBO persistentMappedSSBO;
    public static Test2PersistentMappedPUBO persistentMappedPUBO;
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
        MIN_MAP_BUFFER_ALIGNMENT = glGetInteger(GL_MIN_MAP_BUFFER_ALIGNMENT);
        SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT = glGetInteger(GL_SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT);
        OBJECT_DATA_BIDING_POINT = 1;
        INSTANCE_PARAMETERS_BINDING_POINT = 2;
        SPRITE_DATUM_BINDING_POINT = 3;
    }

    public static void init() {
        EVENT_MANAGER.register(AssetManager.class);

        glObjectPool = new GLObjectPool();
        EVENT_MANAGER.register(glObjectPool);

        drawBatch = new DrawBatch();
        needUpdateBuilders = new HashSet<>();

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

        persistentMappedVBO = new Test2PersistentMappedVBO(MeshConstants.DEFAULT_FLAGS_HINT, LFJG_RENDER_CONTEXT_INITIAL_VBO_CAPACITY);
        persistentMappedEBO = new Test2PersistentMappedEBO(MeshConstants.DEFAULT_FLAGS_HINT, LFJG_RENDER_CONTEXT_INITIAL_EBO_CAPACITY);
        persistentMappedIBO = new Test2PersistentMappedIBO(MeshConstants.DEFAULT_FLAGS_HINT, LFJG_RENDER_CONTEXT_INITIAL_IBO_CAPACITY);
        persistentMappedSSBO = new Test2PersistentMappedSSBO(MeshConstants.DEFAULT_FLAGS_HINT, LFJG_RENDER_CONTEXT_INITIAL_SSBO_CAPACITY, LFJG_RENDER_CONTEXT_INITIAL_SSBO_DATA_CAPACITY);
        persistentMappedPUBO = new Test2PersistentMappedPUBO(MeshConstants.DEFAULT_FLAGS_HINT, LFJG_RENDER_CONTEXT_INITIAL_PUBO_CAPACITY, LFJG_RENDER_CONTEXT_INITIAL_PUBO_DATA_CAPACITY);

        mesh = TestMesh.createMesh();

        vaoRendering = new VAORendering();

        textureCache = TextureCache.createTextureCache();
    }

    public static void update() {
        if (mainCamera.isDirtyFlag()) {
            precomputedViewProjection2D = projection2D.getMatrix4f().mul(mainCamera.getViewMatrix());
            precomputedViewProjection3D = projection3D.getMatrix4f().mul(mainCamera.getViewMatrix());
            mainCamera.setDirtyFlag(false);
        }

        shaderProgram.bind();
        shaderProgram.setUniform("uTextArray", UploadUniformType.ONCE, 0);
        shaderProgram.setUniform("uTextureBlendMode", UploadUniformType.ONCE, LFJG_RENDER_CONTEXT_TEXTURE_BLEND_MODE.getId());
        shaderProgram.setUniform("uSpriteBlendMode", UploadUniformType.ONCE, LFJG_RENDER_CONTEXT_SPRITE_BLEND_MODE.getId());

        vaoRendering.draw();
    }

    public static void finish() {
        EVENT_MANAGER.call(new RenderCleanupEvent());
    }
}
