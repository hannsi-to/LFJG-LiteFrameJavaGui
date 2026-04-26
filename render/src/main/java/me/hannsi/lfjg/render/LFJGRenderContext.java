package me.hannsi.lfjg.render;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.system.GLFenceTracker;
import me.hannsi.lfjg.render.system.GLStateCache;
import me.hannsi.lfjg.render.system.buffer.AllocationBufferMode;
import me.hannsi.lfjg.render.system.buffer.BufferBindingMode;
import me.hannsi.lfjg.render.system.shader.ShaderManager;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import static me.hannsi.lfjg.render.RenderSystemSetting.LFJG_RENDER_CONTEXT_ALLOCATION_BUFFER_MODE;
import static me.hannsi.lfjg.render.RenderSystemSetting.LFJG_RENDER_CONTEXT_BUFFER_BINDING_MODE;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL32.GL_CONTEXT_PROFILE_MASK;
import static org.lwjgl.opengl.GL42.GL_MIN_MAP_BUFFER_ALIGNMENT;

public class LFJGRenderContext {
    public static final GLCapabilities GL_CAPABILITIES;
    public static final BufferBindingMode BUFFER_BINDING_MODE;
    public static final AllocationBufferMode ALLOCATION_BUFFER_MODE;
    public static final int CONTEXT_PROFILE_MASK;
    public static final int MIN_MAP_BUFFER_ALIGNMENT;
    public static GLStateCache glStateCache;
    public static GLFenceTracker glFenceTracker;
    public static ShaderManager shaderManager;

    static {
        GL_CAPABILITIES = GL.getCapabilities();
        switch (LFJG_RENDER_CONTEXT_BUFFER_BINDING_MODE) {
            case AUTO ->
                    BUFFER_BINDING_MODE = BufferBindingMode.choose(GL_CAPABILITIES);
            case LEGACY, ATTRIB_BINDING, DSA, NV_UNIFIED_MEMORY -> {
                if (LFJG_RENDER_CONTEXT_BUFFER_BINDING_MODE.supported(GL_CAPABILITIES)) {
                    BUFFER_BINDING_MODE = LFJG_RENDER_CONTEXT_BUFFER_BINDING_MODE;
                } else {
                    throw new UnsupportedOperationException(LFJG_RENDER_CONTEXT_BUFFER_BINDING_MODE.getName() + " is not supported.");
                }
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + LFJG_RENDER_CONTEXT_BUFFER_BINDING_MODE);
        }
        switch (LFJG_RENDER_CONTEXT_ALLOCATION_BUFFER_MODE) {
            case AUTO ->
                    ALLOCATION_BUFFER_MODE = AllocationBufferMode.choose(GL_CAPABILITIES);
            case LEGACY, PERSISTENT_MAP, DSA, NV_UNIFIED_MEMORY -> {
                if (LFJG_RENDER_CONTEXT_ALLOCATION_BUFFER_MODE.supported(GL_CAPABILITIES)) {
                    ALLOCATION_BUFFER_MODE = LFJG_RENDER_CONTEXT_ALLOCATION_BUFFER_MODE;
                } else {
                    throw new UnsupportedOperationException(LFJG_RENDER_CONTEXT_ALLOCATION_BUFFER_MODE.getName() + " is not supported.");
                }
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + LFJG_RENDER_CONTEXT_ALLOCATION_BUFFER_MODE);
        }

        CONTEXT_PROFILE_MASK = glGetInteger(GL_CONTEXT_PROFILE_MASK);
        MIN_MAP_BUFFER_ALIGNMENT = glGetInteger(GL_MIN_MAP_BUFFER_ALIGNMENT);
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
