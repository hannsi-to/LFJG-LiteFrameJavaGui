package me.hannsi.lfjg.core;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.math.Projection;
import me.hannsi.lfjg.core.utils.reflection.ClassUtil;
import me.hannsi.lfjg.core.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.core.utils.toolkit.MouseInfo;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;

public class Core {
    public static final String DEFAULT_LFJG_PATH = "me.hannsi.lfjg";

    public static final String DEFAULT_LFJG_AUDIO_SYSTEM_PATH = ".audio";
    public static final String DEFAULT_LFJG_FRAME_SYSTEM_PATH = ".frame";
    public static final String DEFAULT_LFJG_JCEF_SYSTEM_PATH = ".jcef";
    public static final String DEFAULT_LFJG_PHYSIC_SYSTEM_PATH = ".physic";
    public static final String DEFAULT_LFJG_RENDER_SYSTEM_PATH = ".render";

    public static final String DEFAULT_LFJG_AUDIO_CORE_CLASS_NAME = ".AudioCore";
    public static final String DEFAULT_LFJG_FRAME2_CORE_CLASS_NAME = ".Frame2Core";
    public static final String DEFAULT_LFJG_FRAME3_CORE_CLASS_NAME = ".Frame3Core";
    public static final String DEFAULT_LFJG_JCEF_CORE_CLASS_NAME = ".JCefCore";
    public static final String DEFAULT_LFJG_PHYSIC_CORE_CLASS_NAME = ".PhysicCore";
    public static final String DEFAULT_LFJG_RENDER_CORE_CLASS_NAME = ".RenderCore";
    public static final String DEFAULT_LFJG_RENDER_TEXT_CORE_CLASS_NAME = ".RenderTextCore";
    public static final String DEFAULT_LFJG_RENDER_VIDEO_CORE_CLASS_NAME = ".RenderVideoCore";

    public static final int LWJGL_VERSION;

    public static final boolean enableLFJGAudioSystem;
    public static final boolean enableLFJGFrame2System;
    public static final boolean enableLFJGFrame3System;
    public static final boolean enableLFJGJCefSystem;
    public static final boolean enableLFJGPhysicSystem;
    public static final boolean enableLFJGRenderSystem;
    public static final boolean enableLFJGRenderTextSystem;
    public static final boolean enableLFJGRenderVideoSystem;

    public static ServiceData lfjgAudioServiceData = null;
    public static ServiceData lfjgFrameServiceData = null;
    public static ServiceData lfjgJCefServiceData = null;
    public static ServiceData lfjgPhysicServiceData = null;
    public static ServiceData lfjgRenderServiceData = null;
    public static ServiceData lfjgRenderTextServiceData = null;
    public static ServiceData lfjgRenderVideoServiceData = null;

    public static boolean CORE_SYSTEM_DEBUG = true;

    public static Projection projection2D;
    public static Projection projection3D;
    public static Vector2i frameBufferSize;
    public static float devicePixelRatio;
    public static MouseInfo mouseInfo;
    public static KeyboardInfo keyboardInfo;

    static {
        if (isLWJGL3()) {
            LWJGL_VERSION = 3;
            DebugLog.info(Core.class, "LWJGL3 is loaded");
        } else if (isLWJGL2()) {
            LWJGL_VERSION = 2;
            DebugLog.info(Core.class, "LWJGL2 is loaded");
        } else {
            LWJGL_VERSION = -1;
            DebugLog.error(Core.class, "LWJGL not found");
        }

        enableLFJGAudioSystem = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_AUDIO_SYSTEM_PATH + DEFAULT_LFJG_AUDIO_CORE_CLASS_NAME);
        enableLFJGFrame2System = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_FRAME_SYSTEM_PATH + DEFAULT_LFJG_FRAME2_CORE_CLASS_NAME);
        enableLFJGFrame3System = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_FRAME_SYSTEM_PATH + DEFAULT_LFJG_FRAME3_CORE_CLASS_NAME);
        enableLFJGJCefSystem = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_JCEF_SYSTEM_PATH + DEFAULT_LFJG_JCEF_CORE_CLASS_NAME);
        enableLFJGPhysicSystem = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_PHYSIC_SYSTEM_PATH + DEFAULT_LFJG_PHYSIC_CORE_CLASS_NAME);
        enableLFJGRenderSystem = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_CORE_CLASS_NAME);
        enableLFJGRenderTextSystem = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_TEXT_CORE_CLASS_NAME);
        enableLFJGRenderVideoSystem = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_VIDEO_CORE_CLASS_NAME);

        if (enableLFJGAudioSystem) {
            Object instance = ClassUtil.createInstanceWithoutArgs(DEFAULT_LFJG_PATH + DEFAULT_LFJG_AUDIO_SYSTEM_PATH + DEFAULT_LFJG_AUDIO_CORE_CLASS_NAME);
            lfjgAudioServiceData = (ServiceData) ClassUtil.invokeMethodExact(instance, "execute");
            DebugLog.info(instance.getClass(), lfjgAudioServiceData.toString());
        }
        if (enableLFJGFrame2System) {
            Object instance = ClassUtil.createInstanceWithoutArgs(DEFAULT_LFJG_PATH + DEFAULT_LFJG_FRAME_SYSTEM_PATH + DEFAULT_LFJG_FRAME2_CORE_CLASS_NAME);
            lfjgFrameServiceData = (ServiceData) ClassUtil.invokeMethodExact(instance, "execute");
            DebugLog.info(instance.getClass(), lfjgFrameServiceData.toString());
        }
        if (enableLFJGFrame3System) {
            Object instance = ClassUtil.createInstanceWithoutArgs(DEFAULT_LFJG_PATH + DEFAULT_LFJG_FRAME_SYSTEM_PATH + DEFAULT_LFJG_FRAME3_CORE_CLASS_NAME);
            lfjgFrameServiceData = (ServiceData) ClassUtil.invokeMethodExact(instance, "execute");
            DebugLog.info(instance.getClass(), lfjgFrameServiceData.toString());
        }
        if (enableLFJGJCefSystem) {
            Object instance = ClassUtil.createInstanceWithoutArgs(DEFAULT_LFJG_PATH + DEFAULT_LFJG_JCEF_SYSTEM_PATH + DEFAULT_LFJG_JCEF_CORE_CLASS_NAME);
            lfjgJCefServiceData = (ServiceData) ClassUtil.invokeMethodExact(instance, "execute");
            DebugLog.info(instance.getClass(), lfjgJCefServiceData.toString());
        }
        if (enableLFJGPhysicSystem) {
            Object instance = ClassUtil.createInstanceWithoutArgs(DEFAULT_LFJG_PATH + DEFAULT_LFJG_PHYSIC_SYSTEM_PATH + DEFAULT_LFJG_PHYSIC_CORE_CLASS_NAME);
            lfjgPhysicServiceData = (ServiceData) ClassUtil.invokeMethodExact(instance, "execute");
            DebugLog.info(instance.getClass(), lfjgPhysicServiceData.toString());
        }
        if (enableLFJGRenderSystem) {
            Object instance = ClassUtil.createInstanceWithoutArgs(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_CORE_CLASS_NAME);
            lfjgRenderServiceData = (ServiceData) ClassUtil.invokeMethodExact(instance, "execute");
            DebugLog.info(instance.getClass(), lfjgRenderServiceData.toString());
        }
        if (enableLFJGRenderTextSystem) {
            Object instance = ClassUtil.createInstanceWithoutArgs(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_TEXT_CORE_CLASS_NAME);
            lfjgRenderTextServiceData = (ServiceData) ClassUtil.invokeMethodExact(instance, "execute");
            DebugLog.info(instance.getClass(), lfjgRenderTextServiceData.toString());
        }
        if (enableLFJGRenderVideoSystem) {
            Object instance = ClassUtil.createInstanceWithoutArgs(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_VIDEO_CORE_CLASS_NAME);
            lfjgRenderVideoServiceData = (ServiceData) ClassUtil.invokeMethodExact(instance, "execute");
            DebugLog.info(instance.getClass(), lfjgRenderVideoServiceData.toString());
        }
    }

    private static boolean isLWJGL3() {
        try {
            Class.forName("org.lwjgl.system.MemoryUtil");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static boolean isLWJGL2() {
        try {
            Class.forName("org.lwjgl.BufferUtils");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static Object invokeStaticMethod(String className, String methodName, Object... args) {
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

    public static class GL30 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL30";

        public static final int GL_RG;
        public static final int GL_RGB16F;
        public static final int GL_RGBA16F;
        public static final int GL_RGB32F;
        public static final int GL_RGBA32F;
        public static final int GL_DEPTH_STENCIL;

        static {
            GL_RG = getStaticIntField(PACKAGE, "GL_RG");
            GL_RGB16F = getStaticIntField(PACKAGE, "GL_RGB16F");
            GL_RGBA16F = getStaticIntField(PACKAGE, "GL_RGBA16F");
            GL_RGB32F = getStaticIntField(PACKAGE, "GL_RGB32F");
            GL_RGBA32F = getStaticIntField(PACKAGE, "GL_RGBA32F");
            GL_DEPTH_STENCIL = getStaticIntField(PACKAGE, "GL_DEPTH_STENCIL");
        }

        public static void glGenerateMipmap(int target) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glGenerateMipmap", target);
        }
    }

    public static class GL13 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL13";

        public static final int GL_MULTISAMPLE;

        static {
            GL_MULTISAMPLE = getStaticIntField(PACKAGE, "GL_MULTISAMPLE");
        }
    }

    public static class GL12 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL12";


        public static final int GL_CLAMP_TO_EDGE;

        static {
            GL_CLAMP_TO_EDGE = getStaticIntField(PACKAGE, "GL_CLAMP_TO_EDGE");
        }
    }

    public static class GL11 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL11";

        public static final int GL_ALL_ATTRIB_BITS;
        public static final int GL_TEXTURE_2D;

        public static final int GL_COLOR_BUFFER_BIT;
        public static final int GL_DEPTH_BUFFER_BIT;

        public static final int GL_UNSIGNED_BYTE;

        public static final int GL_RED;
        public static final int GL_RGB;
        public static final int GL_RGBA;
        public static final int GL_RGB8;
        public static final int GL_RGBA8;
        public static final int GL_DEPTH_COMPONENT;

        public static final int GL_UNPACK_ALIGNMENT;

        public static final int GL_TEXTURE_WRAP_S;
        public static final int GL_TEXTURE_WRAP_T;
        public static final int GL_TEXTURE_MIN_FILTER;
        public static final int GL_TEXTURE_MAG_FILTER;
        public static final int GL_NEAREST;

        public static final int GL_PROJECTION;
        public static final int GL_MODELVIEW;

        static {
            GL_ALL_ATTRIB_BITS = getStaticIntField(PACKAGE, "GL_ALL_ATTRIB_BITS");
            GL_TEXTURE_2D = getStaticIntField(PACKAGE, "GL_TEXTURE_2D");

            GL_COLOR_BUFFER_BIT = getStaticIntField(PACKAGE, "GL_COLOR_BUFFER_BIT");
            GL_DEPTH_BUFFER_BIT = getStaticIntField(PACKAGE, "GL_DEPTH_BUFFER_BIT");

            GL_UNSIGNED_BYTE = getStaticIntField(PACKAGE, "GL_UNSIGNED_BYTE");

            GL_RED = getStaticIntField(PACKAGE, "GL_RED");
            GL_RGB = getStaticIntField(PACKAGE, "GL_RGB");
            GL_RGBA = getStaticIntField(PACKAGE, "GL_RGBA");
            GL_RGB8 = getStaticIntField(PACKAGE, "GL_RGB8");
            GL_RGBA8 = getStaticIntField(PACKAGE, "GL_RGBA8");
            GL_DEPTH_COMPONENT = getStaticIntField(PACKAGE, "GL_DEPTH_COMPONENT");

            GL_UNPACK_ALIGNMENT = getStaticIntField(PACKAGE, "GL_UNPACK_ALIGNMENT");

            GL_TEXTURE_WRAP_S = getStaticIntField(PACKAGE, "GL_TEXTURE_WRAP_S");
            GL_TEXTURE_WRAP_T = getStaticIntField(PACKAGE, "GL_TEXTURE_WRAP_T");
            GL_TEXTURE_MIN_FILTER = getStaticIntField(PACKAGE, "GL_TEXTURE_MIN_FILTER");
            GL_TEXTURE_MAG_FILTER = getStaticIntField(PACKAGE, "GL_TEXTURE_MAG_FILTER");
            GL_NEAREST = getStaticIntField(PACKAGE, "GL_NEAREST");

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

        public static void glPushAttrib(int mask) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glPushAttrib", mask);
        }

        public static void glPopAttrib() {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glPopAttrib");
        }

        public static void glDeleteTextures(int texture) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glDeleteTextures", texture);
        }

        public static int glGenTextures() {
            if (!enableLFJGRenderSystem) {
                return -1;
            }

            Object result = invokeStaticMethod(PACKAGE, "glGenTextures");
            if (result == null) {
                return -1;
            }

            return (int) result;
        }

        public static void glBindTexture(int target, int texture) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glBindTexture", target, texture);
        }

        public static void glPixelStorei(int pname, int param) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glPixelStorei", pname, param);
        }

        public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, @Nullable ByteBuffer pixels) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glTexImage2D", target, level, internalformat, width, height, border, format, type, pixels);
        }

        public static void glTexParameteri(int target, int pname, int param) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glTexParameteri", target, pname, param);
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

        public static final int GLFW_MOUSE_BUTTON_1;
        public static final int GLFW_MOUSE_BUTTON_2;
        public static final int GLFW_MOUSE_BUTTON_3;
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
        public static final int GLFW_KEY_CAPS_LOCK;
        public static final int GLFW_KEY_SCROLL_LOCK;
        public static final int GLFW_KEY_NUM_LOCK;
        public static final int GLFW_KEY_PRINT_SCREEN;
        public static final int GLFW_KEY_PAUSE;

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
        public static final int GLFW_KEY_F13;
        public static final int GLFW_KEY_F14;
        public static final int GLFW_KEY_F15;
        public static final int GLFW_KEY_F16;
        public static final int GLFW_KEY_F17;
        public static final int GLFW_KEY_F18;
        public static final int GLFW_KEY_F19;
        public static final int GLFW_KEY_F20;
        public static final int GLFW_KEY_F21;
        public static final int GLFW_KEY_F22;
        public static final int GLFW_KEY_F23;
        public static final int GLFW_KEY_F24;
        public static final int GLFW_KEY_F25;

        public static final int GLFW_KEY_KP_0;
        public static final int GLFW_KEY_KP_1;
        public static final int GLFW_KEY_KP_2;
        public static final int GLFW_KEY_KP_3;
        public static final int GLFW_KEY_KP_4;
        public static final int GLFW_KEY_KP_5;
        public static final int GLFW_KEY_KP_6;
        public static final int GLFW_KEY_KP_7;
        public static final int GLFW_KEY_KP_8;
        public static final int GLFW_KEY_KP_9;
        public static final int GLFW_KEY_KP_DECIMAL;
        public static final int GLFW_KEY_KP_DIVIDE;
        public static final int GLFW_KEY_KP_MULTIPLY;
        public static final int GLFW_KEY_KP_SUBTRACT;
        public static final int GLFW_KEY_KP_ADD;
        public static final int GLFW_KEY_KP_ENTER;
        public static final int GLFW_KEY_KP_EQUAL;

        public static final int GLFW_KEY_LEFT_SHIFT;
        public static final int GLFW_KEY_RIGHT_SHIFT;
        public static final int GLFW_KEY_LEFT_CONTROL;
        public static final int GLFW_KEY_RIGHT_CONTROL;
        public static final int GLFW_KEY_LEFT_ALT;
        public static final int GLFW_KEY_RIGHT_ALT;
        public static final int GLFW_KEY_LEFT_SUPER;
        public static final int GLFW_KEY_RIGHT_SUPER;
        public static final int GLFW_KEY_MENU;

        public static final int GLFW_KEY_MINUS;
        public static final int GLFW_KEY_EQUAL;
        public static final int GLFW_KEY_BACKSLASH;
        public static final int GLFW_KEY_SEMICOLON;
        public static final int GLFW_KEY_APOSTROPHE;
        public static final int GLFW_KEY_COMMA;
        public static final int GLFW_KEY_PERIOD;
        public static final int GLFW_KEY_SLASH;
        public static final int GLFW_KEY_GRAVE_ACCENT;
        public static final int GLFW_KEY_LEFT_BRACKET;
        public static final int GLFW_KEY_RIGHT_BRACKET;
        public static final int GLFW_KEY_WORLD_1;
        public static final int GLFW_KEY_WORLD_2;

        static {
            GLFW_MOUSE_BUTTON_1 = getStaticIntField(PACKAGE, "GLFW_MOUSE_BUTTON_1");
            GLFW_MOUSE_BUTTON_2 = getStaticIntField(PACKAGE, "GLFW_MOUSE_BUTTON_2");
            GLFW_MOUSE_BUTTON_3 = getStaticIntField(PACKAGE, "GLFW_MOUSE_BUTTON_3");
            GLFW_MOUSE_BUTTON_LEFT = GLFW_MOUSE_BUTTON_1;
            GLFW_MOUSE_BUTTON_RIGHT = GLFW_MOUSE_BUTTON_2;
            GLFW_MOUSE_BUTTON_MIDDLE = GLFW_MOUSE_BUTTON_3;

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
            GLFW_KEY_CAPS_LOCK = getStaticIntField(PACKAGE, "GLFW_KEY_CAPS_LOCK");
            GLFW_KEY_SCROLL_LOCK = getStaticIntField(PACKAGE, "GLFW_KEY_SCROLL_LOCK");
            GLFW_KEY_NUM_LOCK = getStaticIntField(PACKAGE, "GLFW_KEY_NUM_LOCK");
            GLFW_KEY_PRINT_SCREEN = getStaticIntField(PACKAGE, "GLFW_KEY_PRINT_SCREEN");
            GLFW_KEY_PAUSE = getStaticIntField(PACKAGE, "GLFW_KEY_PAUSE");

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
            GLFW_KEY_F13 = getStaticIntField(PACKAGE, "GLFW_KEY_F13");
            GLFW_KEY_F14 = getStaticIntField(PACKAGE, "GLFW_KEY_F14");
            GLFW_KEY_F15 = getStaticIntField(PACKAGE, "GLFW_KEY_F15");
            GLFW_KEY_F16 = getStaticIntField(PACKAGE, "GLFW_KEY_F16");
            GLFW_KEY_F17 = getStaticIntField(PACKAGE, "GLFW_KEY_F17");
            GLFW_KEY_F18 = getStaticIntField(PACKAGE, "GLFW_KEY_F18");
            GLFW_KEY_F19 = getStaticIntField(PACKAGE, "GLFW_KEY_F19");
            GLFW_KEY_F20 = getStaticIntField(PACKAGE, "GLFW_KEY_F20");
            GLFW_KEY_F21 = getStaticIntField(PACKAGE, "GLFW_KEY_F21");
            GLFW_KEY_F22 = getStaticIntField(PACKAGE, "GLFW_KEY_F22");
            GLFW_KEY_F23 = getStaticIntField(PACKAGE, "GLFW_KEY_F23");
            GLFW_KEY_F24 = getStaticIntField(PACKAGE, "GLFW_KEY_F24");
            GLFW_KEY_F25 = getStaticIntField(PACKAGE, "GLFW_KEY_F25");

            GLFW_KEY_KP_0 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_0");
            GLFW_KEY_KP_1 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_1");
            GLFW_KEY_KP_2 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_2");
            GLFW_KEY_KP_3 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_3");
            GLFW_KEY_KP_4 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_4");
            GLFW_KEY_KP_5 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_5");
            GLFW_KEY_KP_6 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_6");
            GLFW_KEY_KP_7 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_7");
            GLFW_KEY_KP_8 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_8");
            GLFW_KEY_KP_9 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_9");
            GLFW_KEY_KP_DECIMAL = getStaticIntField(PACKAGE, "GLFW_KEY_KP_DECIMAL");
            GLFW_KEY_KP_DIVIDE = getStaticIntField(PACKAGE, "GLFW_KEY_KP_DIVIDE");
            GLFW_KEY_KP_MULTIPLY = getStaticIntField(PACKAGE, "GLFW_KEY_KP_MULTIPLY");
            GLFW_KEY_KP_SUBTRACT = getStaticIntField(PACKAGE, "GLFW_KEY_KP_SUBTRACT");
            GLFW_KEY_KP_ADD = getStaticIntField(PACKAGE, "GLFW_KEY_KP_ADD");
            GLFW_KEY_KP_ENTER = getStaticIntField(PACKAGE, "GLFW_KEY_KP_ENTER");
            GLFW_KEY_KP_EQUAL = getStaticIntField(PACKAGE, "GLFW_KEY_KP_EQUAL");

            GLFW_KEY_LEFT_SHIFT = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT_SHIFT");
            GLFW_KEY_RIGHT_SHIFT = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT_SHIFT");
            GLFW_KEY_LEFT_CONTROL = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT_CONTROL");
            GLFW_KEY_RIGHT_CONTROL = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT_CONTROL");
            GLFW_KEY_LEFT_ALT = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT_ALT");
            GLFW_KEY_RIGHT_ALT = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT_ALT");
            GLFW_KEY_LEFT_SUPER = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT_SUPER");
            GLFW_KEY_RIGHT_SUPER = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT_SUPER");
            GLFW_KEY_MENU = getStaticIntField(PACKAGE, "GLFW_KEY_MENU");

            GLFW_KEY_MINUS = getStaticIntField(PACKAGE, "GLFW_KEY_MINUS");
            GLFW_KEY_EQUAL = getStaticIntField(PACKAGE, "GLFW_KEY_EQUAL");
            GLFW_KEY_BACKSLASH = getStaticIntField(PACKAGE, "GLFW_KEY_BACKSLASH");
            GLFW_KEY_SEMICOLON = getStaticIntField(PACKAGE, "GLFW_KEY_SEMICOLON");
            GLFW_KEY_APOSTROPHE = getStaticIntField(PACKAGE, "GLFW_KEY_APOSTROPHE");
            GLFW_KEY_COMMA = getStaticIntField(PACKAGE, "GLFW_KEY_COMMA");
            GLFW_KEY_PERIOD = getStaticIntField(PACKAGE, "GLFW_KEY_PERIOD");
            GLFW_KEY_SLASH = getStaticIntField(PACKAGE, "GLFW_KEY_SLASH");
            GLFW_KEY_GRAVE_ACCENT = getStaticIntField(PACKAGE, "GLFW_KEY_GRAVE_ACCENT");
            GLFW_KEY_LEFT_BRACKET = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT_BRACKET");
            GLFW_KEY_RIGHT_BRACKET = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT_BRACKET");
            GLFW_KEY_WORLD_1 = getStaticIntField(PACKAGE, "GLFW_KEY_WORLD_1");
            GLFW_KEY_WORLD_2 = getStaticIntField(PACKAGE, "GLFW_KEY_WORLD_2");
        }

        public static double glfwGetTime() {
            if (!enableLFJGFrame3System) {
                return -1d;
            }

            Object result = invokeStaticMethod(PACKAGE, "glfwGetTime");
            if (result == null) {
                return -1d;
            }

            return (double) result;
        }
    }

    public static class OpenGLDebug {
        public static final String PACKAGE = DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + ".debug.OpenGLDebug";

        public static void getOpenGLDebug(String mainThreadName, int[] severitiesId) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "getOpenGLDebug", mainThreadName, severitiesId);
        }
    }

    public static class GLStateCache {
        public static final String PACKAGE = DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + ".system.rendering.GLStateCache";

        public static void enable(int target) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "enable", target);
        }

        public static void disable(int target) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "disable", target);
        }

        public static void bindTexture(int target, int texture) {
            if (!enableLFJGRenderSystem) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "bindTexture", target, texture);
        }
    }

    public static class CreatingTextureException {
        public static final String PACKAGE = DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + ".debug.exceptions.texture.CreatingTextureException";

        public static RuntimeException createCreatingTextureException(String message) {
            try {
                Class<?> clazz = Class.forName(PACKAGE);
                Constructor<?> constructor = clazz.getConstructor(String.class);
                return (RuntimeException) constructor.newInstance(message);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create CreatingTextureException via reflection", e);
            }
        }
    }
}
