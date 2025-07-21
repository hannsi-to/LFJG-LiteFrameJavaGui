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

    public static class GLFW {
        public static final String PACKAGE = "org.lwjgl.glfw.GLFW";

        public static final int GLFW_MOUSE_BUTTON_LEFT;
        public static final int GLFW_MOUSE_BUTTON_MIDDLE;
        public static final int GLFW_MOUSE_BUTTON_RIGHT;

        public static final int GLFW_PRESS;
        public static final int GLFW_RELEASE;

        public static final int GLFW_MOD_SHIFT;
        public static final int GLFW_MOD_CONTROL;
        public static final int GLFW_MOD_ALT;
        public static final int GLFW_MOD_SUPER;

        public static final int GLFW_KEY_ENTER;
        public static final int GLFW_KEY_BACKSPACE;
        public static final int GLFW_KEY_TAB;
        public static final int GLFW_KEY_ESCAPE;
        public static final int GLFW_KEY_SPACE;
        public static final int GLFW_KEY_LEFT;
        public static final int GLFW_KEY_RIGHT;
        public static final int GLFW_KEY_UP;
        public static final int GLFW_KEY_DOWN;
        public static final int GLFW_KEY_DELETE;
        public static final int GLFW_KEY_HOME;
        public static final int GLFW_KEY_END;
        public static final int GLFW_KEY_PAGE_UP;
        public static final int GLFW_KEY_PAGE_DOWN;
        public static final int GLFW_KEY_INSERT;

        public static final int GLFW_KEY_A;
        public static final int GLFW_KEY_B;
        public static final int GLFW_KEY_C;
        public static final int GLFW_KEY_D;
        public static final int GLFW_KEY_E;
        public static final int GLFW_KEY_F;
        public static final int GLFW_KEY_G;
        public static final int GLFW_KEY_H;
        public static final int GLFW_KEY_I;
        public static final int GLFW_KEY_J;
        public static final int GLFW_KEY_K;
        public static final int GLFW_KEY_L;
        public static final int GLFW_KEY_M;
        public static final int GLFW_KEY_N;
        public static final int GLFW_KEY_O;
        public static final int GLFW_KEY_P;
        public static final int GLFW_KEY_Q;
        public static final int GLFW_KEY_R;
        public static final int GLFW_KEY_S;
        public static final int GLFW_KEY_T;
        public static final int GLFW_KEY_U;
        public static final int GLFW_KEY_V;
        public static final int GLFW_KEY_W;
        public static final int GLFW_KEY_X;
        public static final int GLFW_KEY_Y;
        public static final int GLFW_KEY_Z;

        public static final int GLFW_KEY_0;
        public static final int GLFW_KEY_1;
        public static final int GLFW_KEY_2;
        public static final int GLFW_KEY_3;
        public static final int GLFW_KEY_4;
        public static final int GLFW_KEY_5;
        public static final int GLFW_KEY_6;
        public static final int GLFW_KEY_7;
        public static final int GLFW_KEY_8;
        public static final int GLFW_KEY_9;

        public static final int GLFW_KEY_F1;
        public static final int GLFW_KEY_F2;
        public static final int GLFW_KEY_F3;
        public static final int GLFW_KEY_F4;
        public static final int GLFW_KEY_F5;
        public static final int GLFW_KEY_F6;
        public static final int GLFW_KEY_F7;
        public static final int GLFW_KEY_F8;
        public static final int GLFW_KEY_F9;
        public static final int GLFW_KEY_F10;
        public static final int GLFW_KEY_F11;
        public static final int GLFW_KEY_F12;

        public static final int GLFW_KEY_LEFT_SHIFT;
        public static final int GLFW_KEY_RIGHT_SHIFT;
        public static final int GLFW_KEY_LEFT_CONTROL;
        public static final int GLFW_KEY_RIGHT_CONTROL;
        public static final int GLFW_KEY_LEFT_ALT;
        public static final int GLFW_KEY_RIGHT_ALT;
        public static final int GLFW_KEY_LEFT_SUPER;
        public static final int GLFW_KEY_RIGHT_SUPER;

        public static final int GLFW_KEY_MINUS;
        public static final int GLFW_KEY_EQUAL;
        public static final int GLFW_KEY_BACKSLASH;
        public static final int GLFW_KEY_SEMICOLON;
        public static final int GLFW_KEY_APOSTROPHE;
        public static final int GLFW_KEY_COMMA;
        public static final int GLFW_KEY_PERIOD;
        public static final int GLFW_KEY_SLASH;
        public static final int GLFW_KEY_GRAVE_ACCENT;

        static {
            GLFW_MOUSE_BUTTON_LEFT = getStaticIntField(PACKAGE, "GLFW_MOUSE_BUTTON_LEFT");
            GLFW_MOUSE_BUTTON_MIDDLE = getStaticIntField(PACKAGE, "GLFW_MOUSE_BUTTON_MIDDLE");
            GLFW_MOUSE_BUTTON_RIGHT = getStaticIntField(PACKAGE, "GLFW_MOUSE_BUTTON_RIGHT ");

            GLFW_PRESS = getStaticIntField(PACKAGE, "GLFW_PRESS");
            GLFW_RELEASE = getStaticIntField(PACKAGE, "GLFW_RELEASE");

            GLFW_MOD_SHIFT = getStaticIntField(PACKAGE, "GLFW_MOD_SHIFT");
            GLFW_MOD_CONTROL = getStaticIntField(PACKAGE, "GLFW_MOD_CONTROL");
            GLFW_MOD_ALT = getStaticIntField(PACKAGE, "GLFW_MOD_ALT");
            GLFW_MOD_SUPER = getStaticIntField(PACKAGE, "GLFW_MOD_SUPER");

            GLFW_KEY_ENTER = getStaticIntField(PACKAGE, "GLFW_KEY_ENTER");
            GLFW_KEY_BACKSPACE = getStaticIntField(PACKAGE, "GLFW_KEY_BACKSPACE");
            GLFW_KEY_TAB = getStaticIntField(PACKAGE, "GLFW_KEY_TAB");
            GLFW_KEY_ESCAPE = getStaticIntField(PACKAGE, "GLFW_KEY_ESCAPE");
            GLFW_KEY_SPACE = getStaticIntField(PACKAGE, "GLFW_KEY_SPACE");
            GLFW_KEY_LEFT = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT");
            GLFW_KEY_RIGHT = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT");
            GLFW_KEY_UP = getStaticIntField(PACKAGE, "GLFW_KEY_UP");
            GLFW_KEY_DOWN = getStaticIntField(PACKAGE, "GLFW_KEY_DOWN");
            GLFW_KEY_DELETE = getStaticIntField(PACKAGE, "GLFW_KEY_DELETE");
            GLFW_KEY_HOME = getStaticIntField(PACKAGE, "GLFW_KEY_HOME");
            GLFW_KEY_END = getStaticIntField(PACKAGE, "GLFW_KEY_END");
            GLFW_KEY_PAGE_UP = getStaticIntField(PACKAGE, "GLFW_KEY_PAGE_UP");
            GLFW_KEY_PAGE_DOWN = getStaticIntField(PACKAGE, "GLFW_KEY_PAGE_DOWN");
            GLFW_KEY_INSERT = getStaticIntField(PACKAGE, "GLFW_KEY_INSERT");

            GLFW_KEY_A = getStaticIntField(PACKAGE, "GLFW_KEY_A");
            GLFW_KEY_B = getStaticIntField(PACKAGE, "GLFW_KEY_B");
            GLFW_KEY_C = getStaticIntField(PACKAGE, "GLFW_KEY_C");
            GLFW_KEY_D = getStaticIntField(PACKAGE, "GLFW_KEY_D");
            GLFW_KEY_E = getStaticIntField(PACKAGE, "GLFW_KEY_E");
            GLFW_KEY_F = getStaticIntField(PACKAGE, "GLFW_KEY_F");
            GLFW_KEY_G = getStaticIntField(PACKAGE, "GLFW_KEY_G");
            GLFW_KEY_H = getStaticIntField(PACKAGE, "GLFW_KEY_H");
            GLFW_KEY_I = getStaticIntField(PACKAGE, "GLFW_KEY_I");
            GLFW_KEY_J = getStaticIntField(PACKAGE, "GLFW_KEY_J");
            GLFW_KEY_K = getStaticIntField(PACKAGE, "GLFW_KEY_K");
            GLFW_KEY_L = getStaticIntField(PACKAGE, "GLFW_KEY_L");
            GLFW_KEY_M = getStaticIntField(PACKAGE, "GLFW_KEY_M");
            GLFW_KEY_N = getStaticIntField(PACKAGE, "GLFW_KEY_N");
            GLFW_KEY_O = getStaticIntField(PACKAGE, "GLFW_KEY_O");
            GLFW_KEY_P = getStaticIntField(PACKAGE, "GLFW_KEY_P");
            GLFW_KEY_Q = getStaticIntField(PACKAGE, "GLFW_KEY_Q");
            GLFW_KEY_R = getStaticIntField(PACKAGE, "GLFW_KEY_R");
            GLFW_KEY_S = getStaticIntField(PACKAGE, "GLFW_KEY_S");
            GLFW_KEY_T = getStaticIntField(PACKAGE, "GLFW_KEY_T");
            GLFW_KEY_U = getStaticIntField(PACKAGE, "GLFW_KEY_U");
            GLFW_KEY_V = getStaticIntField(PACKAGE, "GLFW_KEY_V");
            GLFW_KEY_W = getStaticIntField(PACKAGE, "GLFW_KEY_W");
            GLFW_KEY_X = getStaticIntField(PACKAGE, "GLFW_KEY_X");
            GLFW_KEY_Y = getStaticIntField(PACKAGE, "GLFW_KEY_Y");
            GLFW_KEY_Z = getStaticIntField(PACKAGE, "GLFW_KEY_Z");

            GLFW_KEY_0 = getStaticIntField(PACKAGE, "GLFW_KEY_0");
            GLFW_KEY_1 = getStaticIntField(PACKAGE, "GLFW_KEY_1");
            GLFW_KEY_2 = getStaticIntField(PACKAGE, "GLFW_KEY_2");
            GLFW_KEY_3 = getStaticIntField(PACKAGE, "GLFW_KEY_3");
            GLFW_KEY_4 = getStaticIntField(PACKAGE, "GLFW_KEY_4");
            GLFW_KEY_5 = getStaticIntField(PACKAGE, "GLFW_KEY_5");
            GLFW_KEY_6 = getStaticIntField(PACKAGE, "GLFW_KEY_6");
            GLFW_KEY_7 = getStaticIntField(PACKAGE, "GLFW_KEY_7");
            GLFW_KEY_8 = getStaticIntField(PACKAGE, "GLFW_KEY_8");
            GLFW_KEY_9 = getStaticIntField(PACKAGE, "GLFW_KEY_9");

            GLFW_KEY_F1 = getStaticIntField(PACKAGE, "GLFW_KEY_F1");
            GLFW_KEY_F2 = getStaticIntField(PACKAGE, "GLFW_KEY_F2");
            GLFW_KEY_F3 = getStaticIntField(PACKAGE, "GLFW_KEY_F3");
            GLFW_KEY_F4 = getStaticIntField(PACKAGE, "GLFW_KEY_F4");
            GLFW_KEY_F5 = getStaticIntField(PACKAGE, "GLFW_KEY_F5");
            GLFW_KEY_F6 = getStaticIntField(PACKAGE, "GLFW_KEY_F6");
            GLFW_KEY_F7 = getStaticIntField(PACKAGE, "GLFW_KEY_F7");
            GLFW_KEY_F8 = getStaticIntField(PACKAGE, "GLFW_KEY_F8");
            GLFW_KEY_F9 = getStaticIntField(PACKAGE, "GLFW_KEY_F9");
            GLFW_KEY_F10 = getStaticIntField(PACKAGE, "GLFW_KEY_F10");
            GLFW_KEY_F11 = getStaticIntField(PACKAGE, "GLFW_KEY_F11");
            GLFW_KEY_F12 = getStaticIntField(PACKAGE, "GLFW_KEY_F12");

            GLFW_KEY_LEFT_SHIFT = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT_SHIFT");
            GLFW_KEY_RIGHT_SHIFT = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT_SHIFT");
            GLFW_KEY_LEFT_CONTROL = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT_CONTROL");
            GLFW_KEY_RIGHT_CONTROL = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT_CONTROL");
            GLFW_KEY_LEFT_ALT = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT_ALT");
            GLFW_KEY_RIGHT_ALT = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT_ALT");
            GLFW_KEY_LEFT_SUPER = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT_SUPER");
            GLFW_KEY_RIGHT_SUPER = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT_SUPER");

            GLFW_KEY_MINUS = getStaticIntField(PACKAGE, "GLFW_KEY_MINUS");
            GLFW_KEY_EQUAL = getStaticIntField(PACKAGE, "GLFW_KEY_EQUAL");
            GLFW_KEY_BACKSLASH = getStaticIntField(PACKAGE, "GLFW_KEY_BACKSLASH");
            GLFW_KEY_SEMICOLON = getStaticIntField(PACKAGE, "GLFW_KEY_SEMICOLON");
            GLFW_KEY_APOSTROPHE = getStaticIntField(PACKAGE, "GLFW_KEY_APOSTROPHE");
            GLFW_KEY_COMMA = getStaticIntField(PACKAGE, "GLFW_KEY_COMMA");
            GLFW_KEY_PERIOD = getStaticIntField(PACKAGE, "GLFW_KEY_PERIOD");
            GLFW_KEY_SLASH = getStaticIntField(PACKAGE, "GLFW_KEY_SLASH");
            GLFW_KEY_GRAVE_ACCENT = getStaticIntField(PACKAGE, "GLFW_KEY_GRAVE_ACCENT");
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
