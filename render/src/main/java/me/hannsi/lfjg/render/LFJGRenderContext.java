package me.hannsi.lfjg.render;

import me.hannsi.lfjg.core.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.toolkit.Camera;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;

public class LFJGRenderContext {
    public static IdPool idPool;
    public static GLObjectPool glObjectPool;
    public static Camera mainCamera;
    public static ShaderProgram shaderProgram;
    public static TextureCache textureCache;

    static {
        idPool = new IdPool();
        glObjectPool = new GLObjectPool();

        mainCamera = new Camera();

        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Location.fromResource("shader/VertexShader.vsh"));
        shaderProgram.createFragmentShader(Location.fromResource("shader/FragmentShader.fsh"));
        shaderProgram.link();

        textureCache = TextureCache.createTextureCache();
    }
}
