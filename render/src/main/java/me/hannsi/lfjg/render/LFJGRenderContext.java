package me.hannsi.lfjg.render;

import me.hannsi.lfjg.render.system.GLStateCache;

import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL32.GL_CONTEXT_PROFILE_MASK;

public class LFJGRenderContext {
    public static final int CONTEXT_PROFILE_MASK;
    public static GLStateCache glStateCache;

    static {
        CONTEXT_PROFILE_MASK = glGetInteger(GL_CONTEXT_PROFILE_MASK);
    }

    public static void init() {
        glStateCache = new GLStateCache();
    }

    public static void update() {

    }
}
