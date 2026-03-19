package me.hannsi.lfjg.frame;

import static me.hannsi.lfjg.core.CoreSystemSetting.DEBUG_RUN;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_COMPAT_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;

public class FrameSystemSetting {
    public static int FRAME_GLFW_CONTEXT_VERSION_MAJOR = 4;
    public static int FRAME_GLFW_CONTEXT_VERSION_MINOR = 6;
    public static int FRAME_GLFW_OPENGL_PROFILE = DEBUG_RUN ? GLFW_OPENGL_COMPAT_PROFILE : GLFW_OPENGL_CORE_PROFILE;
}
