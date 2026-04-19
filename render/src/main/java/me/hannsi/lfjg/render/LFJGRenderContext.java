package me.hannsi.lfjg.render;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.system.GLStateCache;
import me.hannsi.lfjg.render.system.shader.ShaderManager;

import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL32.GL_CONTEXT_PROFILE_MASK;

public class LFJGRenderContext {
    public static final int CONTEXT_PROFILE_MASK;
    public static GLStateCache glStateCache;
    public static ShaderManager shaderManager;

    static {
        CONTEXT_PROFILE_MASK = glGetInteger(GL_CONTEXT_PROFILE_MASK);
    }

    public static void init() {
        glStateCache = new GLStateCache();
        shaderManager = new ShaderManager();
        shaderManager.buildShader(
                "Default",
                Location.fromResource(LFJGRenderContext.class, "shader/VertexShader.vsh"),
                Location.fromResource(LFJGRenderContext.class, "shader/FragmentShader.fsh")
        );
    }

    public static void update() {

    }
}
