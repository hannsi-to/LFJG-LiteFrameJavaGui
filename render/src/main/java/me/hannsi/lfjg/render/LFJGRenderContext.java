package me.hannsi.lfjg.render;

import me.hannsi.lfjg.core.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.toolkit.Camera;
import me.hannsi.lfjg.render.system.mesh.TestMesh;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;

public class LFJGRenderContext {
    public static final int INITIAL_VBO_CAPACITY = 10000;
    public static final int INITIAL_EBO_CAPACITY = 8000;
    public static final int INITIAL_IBO_CAPACITY = 2500;

    public static IdPool idPool;
    public static GLObjectPool glObjectPool;
    public static Camera mainCamera;
    public static ShaderProgram shaderProgram;
    public static TestMesh mesh;
    public static TextureCache textureCache;

    static {
        idPool = new IdPool();
        glObjectPool = new GLObjectPool();

        mainCamera = new Camera();

        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Location.fromResource("shader/VertexShader.vsh"));
        shaderProgram.createFragmentShader(Location.fromResource("shader/FragmentShader.fsh"));
        shaderProgram.link();

        mesh = TestMesh.createMesh(
                INITIAL_VBO_CAPACITY,
                INITIAL_EBO_CAPACITY,
                INITIAL_IBO_CAPACITY
        );

        textureCache = TextureCache.createTextureCache();
    }
}
