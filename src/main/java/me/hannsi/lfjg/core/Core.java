package me.hannsi.lfjg.core;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.reflection.ClassUtil;

public class Core {
    public static final String DEFAULT_LFJG_PATH = "me.hannsi.lfjg";

    public static final String DEFAULT_LFJG_AUDIO_SYSTEM_PATH = ".audio";
    public static final String DEFAULT_LFJG_FRAME_SYSTEM_PATH = ".frame";
    public static final String DEFAULT_LFJG_JCEF_SYSTEM_PATH = ".jcef";
    public static final String DEFAULT_LFJG_PHYSIC_SYSTEM_PATH = ".physic";
    public static final String DEFAULT_LFJG_RENDER_SYSTEM_PATH = ".render";

    public static final String DEFAULT_LFJG_AUDIO_CORE_CLASS_NAME = ".AudioCore";
    public static final String DEFAULT_LFJG_FRAME_CORE_CLASS_NAME = ".FrameCore";
    public static final String DEFAULT_LFJG_JCEF_CORE_CLASS_NAME = ".JCefCore";
    public static final String DEFAULT_LFJG_PHYSIC_CORE_CLASS_NAME = ".PhysicCore";
    public static final String DEFAULT_LFJG_RENDER_CORE_CLASS_NAME = ".RenderCore";
    private static final boolean enableLFJGAudioSystem;
    private static final boolean enableLFJGFrameSystem;
    private static final boolean enableLFJGJCefSystem;
    private static final boolean enableLFJGPhysicSystem;
    private static final boolean enableLFJGRenderSystem;
    public static boolean CORE_SYSTEM_DEBUG = true;

    static {
        enableLFJGAudioSystem = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_AUDIO_SYSTEM_PATH + DEFAULT_LFJG_AUDIO_CORE_CLASS_NAME);
        enableLFJGFrameSystem = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_FRAME_SYSTEM_PATH + DEFAULT_LFJG_FRAME_CORE_CLASS_NAME);
        enableLFJGJCefSystem = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_JCEF_SYSTEM_PATH + DEFAULT_LFJG_JCEF_CORE_CLASS_NAME);
        enableLFJGPhysicSystem = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_PHYSIC_SYSTEM_PATH + DEFAULT_LFJG_PHYSIC_CORE_CLASS_NAME);
        enableLFJGRenderSystem = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_CORE_CLASS_NAME);
    }

    public static void OpenGLDebug_getOpenGLDebug(String mainThreadName, int[] severitiesId) {
        if (!enableLFJGRenderSystem) {
            return;
        }

        try {
            Object ignore = ClassUtil.invokeStaticMethod(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + ".debug.OpenGLDebug", "getOpenGLDebug", mainThreadName, severitiesId);
        } catch (Exception e) {
            if (!CORE_SYSTEM_DEBUG) {
                return;
            }

            DebugLog.warning(Core.class, e);
        }
    }
}
