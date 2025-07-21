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

    public static Object invokeStaticMethod(String className, String methodName, Object... args) {
        Object result = null;
        try {
            result = ClassUtil.invokeStaticMethod(className, methodName, args);
        } catch (Exception e) {
            if (!CORE_SYSTEM_DEBUG) {
                return null;
            }

            DebugLog.warning(Core.class, e);
        }

        return result;
    }

    public static Object getStaticFieldValue(String className, String fieldName) {
        Object result = null;
        try {
            result = ClassUtil.getStaticFieldValue(className, fieldName);
        } catch (Exception e) {
            if (!CORE_SYSTEM_DEBUG) {
                return null;
            }

            DebugLog.warning(Core.class, e);
        }

        return result;
    }

    public static int getStaticIntField(String className, String fieldName) {
        Object value = getStaticFieldValue(className, fieldName);
        if (value == null) {
            throw new NullPointerException("Static int field: " + fieldName + " value: null");
        }

        return (int) value;
    }

    public static class NanoVGGL3 {
        public static final String PACKAGE = "org.lwjgl.nanovg.NanoVGGL3";

        public static final int NVG_ANTIALIAS;

        static {
            NVG_ANTIALIAS = getStaticIntField(PACKAGE, "NVG_ANTIALIAS");
        }

        public static long nvgCreate(int flags) {
            if (!enableLFJGRenderSystem) {
                return -1L;
            }

            Object result = invokeStaticMethod(PACKAGE, "nvgCreate", flags);
            if (result == null) {
                return -1L;
            }

            return (long) result;
        }

        public static void nvgDelete(long ctx) {
            Object ignore = invokeStaticMethod(PACKAGE, "nvgDelete", ctx);
        }
    }


    public static class GL13 {
        public static final int GL_MULTISAMPLE;
        private static final String PACKAGE = "org.lwjgl.opengl.GL13";

        static {
            GL_MULTISAMPLE = getStaticIntField(PACKAGE, "GL_MULTISAMPLE");
        }
    }

    public static class GL11 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL11";

        public static final int GL_COLOR_BUFFER_BIT;
        public static final int GL_DEPTH_BUFFER_BIT;

        public static final int GL_PROJECTION;
        public static final int GL_MODELVIEW;

        static {
            GL_COLOR_BUFFER_BIT = getStaticIntField(PACKAGE, "GL_COLOR_BUFFER_BIT");
            GL_DEPTH_BUFFER_BIT = getStaticIntField(PACKAGE, "GL_DEPTH_BUFFER_BIT");

            GL_PROJECTION = getStaticIntField(PACKAGE, "GL_PROJECTION");
            GL_MODELVIEW = getStaticIntField(PACKAGE, "GL_MODELVIEW");
        }

        public static void glClearColor(float red, float green, float blue, float alpha) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glClearColor", red, green, blue, alpha);
        }

        public static void glClear(int mask) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glClear", mask);
        }

        public static void glEnable(int target) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glEnable", target);
        }

        public static void glDisable(int target) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glDisable", target);
        }

        public static void glViewport(int x, int y, int w, int h) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glViewport", x, y, w, h);
        }

        public static void glMatrixMode(int mode) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glMatrixMode", mode);
        }

        public static void glLoadIdentity() {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glLoadIdentity");
        }

        public static void glOrtho(double l, double r, double b, double t, double n, double f) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glOrtho", l, r, b, t, n, f);
        }
    }

    public static class GL {
        public static final String PACKAGE = "org.lwjgl.opengl.GL";

        public static void createCapabilities() {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "createCapabilities");
        }
    }

    public static class OpenGLDebug {
        public static void getOpenGLDebug(String mainThreadName, int[] severitiesId) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + ".debug.OpenGLDebug", "getOpenGLDebug", mainThreadName, severitiesId);
        }
    }
}
