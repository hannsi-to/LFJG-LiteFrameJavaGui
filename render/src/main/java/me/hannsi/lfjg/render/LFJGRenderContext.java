package me.hannsi.lfjg.render;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.system.GLFenceTracker;
import me.hannsi.lfjg.render.system.GLStateCache;
import me.hannsi.lfjg.render.system.memory.Allocation;
import me.hannsi.lfjg.render.system.shader.ShaderManager;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT;
import static org.lwjgl.opengl.GL32.GL_CONTEXT_PROFILE_MASK;
import static org.lwjgl.opengl.GL42.GL_MIN_MAP_BUFFER_ALIGNMENT;
import static org.lwjgl.opengl.GL43.GL_SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT;

public class LFJGRenderContext {
    public static final GLCapabilities GL_CAPABILITIES;
    public static final int CONTEXT_PROFILE_MASK;
    public static final int VERTEX_BUFFER_OBJECT_ALIGNMENT;
    public static final int ELEMENT_BUFFER_OBJECT_ALIGNMENT;
    public static final int DRAW_INDIRECT_BUFFER_OBJECT_ALIGNMENT;
    public static final int UNIFORM_BUFFER_OBJECT_ALIGNMENT;
    public static final int SHADER_STORAGE_BUFFER_OBJECT_ALIGNMENT;
    public static final int MIN_MAP_BUFFER_ALIGNMENT;
    public static GLStateCache glStateCache;
    public static GLFenceTracker glFenceTracker;
    public static ShaderManager shaderManager;
    public static Allocation currentAllocation;

    static {
        GL_CAPABILITIES = GL.getCapabilities();

        CONTEXT_PROFILE_MASK = glGetInteger(GL_CONTEXT_PROFILE_MASK);
        MIN_MAP_BUFFER_ALIGNMENT = glGetInteger(GL_MIN_MAP_BUFFER_ALIGNMENT);
        VERTEX_BUFFER_OBJECT_ALIGNMENT = max(16, MIN_MAP_BUFFER_ALIGNMENT);
        ELEMENT_BUFFER_OBJECT_ALIGNMENT = max(Integer.BYTES, MIN_MAP_BUFFER_ALIGNMENT);
        DRAW_INDIRECT_BUFFER_OBJECT_ALIGNMENT = max(16, MIN_MAP_BUFFER_ALIGNMENT);
        UNIFORM_BUFFER_OBJECT_ALIGNMENT = max(glGetInteger(GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT), MIN_MAP_BUFFER_ALIGNMENT);
        SHADER_STORAGE_BUFFER_OBJECT_ALIGNMENT = max(glGetInteger(GL_SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT), MIN_MAP_BUFFER_ALIGNMENT);
    }

    public static void init() {
        glStateCache = new GLStateCache();
        glFenceTracker = new GLFenceTracker(3);
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
