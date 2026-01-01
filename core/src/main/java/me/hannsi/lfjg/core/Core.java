package me.hannsi.lfjg.core;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.manager.EventManager;
import me.hannsi.lfjg.core.manager.WorkspaceManager;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.math.Projection;
import me.hannsi.lfjg.core.utils.math.map.string2intMap.String2IntMap;
import me.hannsi.lfjg.core.utils.reflection.ClassUtil;
import me.hannsi.lfjg.core.utils.toolkit.FastStringBuilder;
import me.hannsi.lfjg.core.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.core.utils.toolkit.MouseInfo;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;
import sun.misc.Unsafe;

import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static me.hannsi.lfjg.core.Core.LFJGRenderContext.GL_STATE_CACHE;
import static me.hannsi.lfjg.core.CoreSystemSetting.CORE_SYSTEM_DEBUG;

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
    public static final String DEFAULT_LFJG_RENDER_TEXT_CORE_CLASS_NAME = ".RenderTextCore";
    public static final String DEFAULT_LFJG_RENDER_VIDEO_CORE_CLASS_NAME = ".RenderVideoCore";

    public static final String DEFAULT_LFJG_AUDIO_CONTEXT_CLASS_NAME = ".LFJGAudioContext";
    public static final String DEFAULT_LFJG_FRAME_CONTEXT_CLASS_NAME = ".LFJGFrameContext";
    public static final String DEFAULT_LFJG_JCEF_CONTEXT_CLASS_NAME = ".LFJGJCefContext";
    public static final String DEFAULT_LFJG_PHYSIC_CONTEXT_CLASS_NAME = ".LFJGPhysicContext";
    public static final String DEFAULT_LFJG_RENDER_CONTEXT_CLASS_NAME = ".LFJGRenderContext";
    public static final String DEFAULT_LFJG_RENDER_TEXT_CONTEXT_CLASS_NAME = ".LFJGRenderTextContext";
    public static final String DEFAULT_LFJG_RENDER_VIDEO_CONTEXT_CLASS_NAME = ".LFJGRenderVideoContext";

    public static final int LWJGL_VERSION;

    public static final boolean ENABLE_LFJG_AUDIO_SYSTEM;
    public static final boolean ENABLE_LFJG_FRAME_SYSTEM;
    public static final boolean ENABLE_LFJG_JCEF_SYSTEM;
    public static final boolean ENABLE_LFJG_PHYSIC_SYSTEM;
    public static final boolean ENABLE_LFJG_RENDER_SYSTEM;
    public static final boolean ENABLE_LFJG_RENDER_TEXT_SYSTEM;
    public static final boolean ENABLE_LFJG_RENDER_VIDEO_SYSTEM;
    public static final Unsafe UNSAFE;
    public static final String2IntMap OPEN_GL_PARAMETER_NAME_MAP = new String2IntMap();
    public static final EventManager EVENT_MANAGER;
    public static final WorkspaceManager WORKSPACE_MANAGER;
    public static String VENDOR = "unknown";
    public static String RENDERER = "unknown";
    public static String VERSION = "unknown";
    public static String SHADING_LANGUAGE_VERSION = "unknown";
    public static FastStringBuilder stringBuilder;
    public static ServiceData lfjgAudioServiceData = null;
    public static ServiceData lfjgFrameServiceData = null;
    public static ServiceData lfjgJCefServiceData = null;
    public static ServiceData lfjgPhysicServiceData = null;
    public static ServiceData lfjgRenderServiceData = null;
    public static ServiceData lfjgRenderTextServiceData = null;
    public static ServiceData lfjgRenderVideoServiceData = null;
    public static Projection projection2D;
    public static Projection projection3D;
    public static Vector2i frameBufferSize;
    public static float devicePixelRatio;
    public static MouseInfo mouseInfo;
    public static KeyboardInfo keyboardInfo;

    static {
        if (isLWJGL3()) {
            LWJGL_VERSION = 3;
            DebugLog.debug(Core.class, "LWJGL3 founded");
        } else if (isLWJGL2()) {
            LWJGL_VERSION = 2;
            DebugLog.debug(Core.class, "LWJGL2 founded");
        } else {
            LWJGL_VERSION = -1;
            DebugLog.error(Core.class, "LWJGL not found");
        }

        ENABLE_LFJG_AUDIO_SYSTEM = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_AUDIO_SYSTEM_PATH + DEFAULT_LFJG_AUDIO_CORE_CLASS_NAME);
        ENABLE_LFJG_FRAME_SYSTEM = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_FRAME_SYSTEM_PATH + DEFAULT_LFJG_FRAME_CORE_CLASS_NAME);
        ENABLE_LFJG_JCEF_SYSTEM = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_JCEF_SYSTEM_PATH + DEFAULT_LFJG_JCEF_CORE_CLASS_NAME);
        ENABLE_LFJG_PHYSIC_SYSTEM = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_PHYSIC_SYSTEM_PATH + DEFAULT_LFJG_PHYSIC_CORE_CLASS_NAME);
        ENABLE_LFJG_RENDER_SYSTEM = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_CORE_CLASS_NAME);
        ENABLE_LFJG_RENDER_TEXT_SYSTEM = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_TEXT_CORE_CLASS_NAME);
        ENABLE_LFJG_RENDER_VIDEO_SYSTEM = ClassUtil.isClassAvailable(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_VIDEO_CORE_CLASS_NAME);

        if (ENABLE_LFJG_AUDIO_SYSTEM) {
            Object instance = ClassUtil.createInstanceWithoutArgs(DEFAULT_LFJG_PATH + DEFAULT_LFJG_AUDIO_SYSTEM_PATH + DEFAULT_LFJG_AUDIO_CORE_CLASS_NAME);
            lfjgAudioServiceData = (ServiceData) ClassUtil.invokeMethodExact(instance, "execute");
            DebugLog.debug(instance.getClass(), lfjgAudioServiceData.toString());
        }
        if (ENABLE_LFJG_FRAME_SYSTEM) {
            Object instance = ClassUtil.createInstanceWithoutArgs(DEFAULT_LFJG_PATH + DEFAULT_LFJG_FRAME_SYSTEM_PATH + DEFAULT_LFJG_FRAME_CORE_CLASS_NAME);
            lfjgFrameServiceData = (ServiceData) ClassUtil.invokeMethodExact(instance, "execute");
            DebugLog.debug(instance.getClass(), lfjgFrameServiceData.toString());
        }
        if (ENABLE_LFJG_JCEF_SYSTEM) {
            Object instance = ClassUtil.createInstanceWithoutArgs(DEFAULT_LFJG_PATH + DEFAULT_LFJG_JCEF_SYSTEM_PATH + DEFAULT_LFJG_JCEF_CORE_CLASS_NAME);
            lfjgJCefServiceData = (ServiceData) ClassUtil.invokeMethodExact(instance, "execute");
            DebugLog.debug(instance.getClass(), lfjgJCefServiceData.toString());
        }
        if (ENABLE_LFJG_PHYSIC_SYSTEM) {
            Object instance = ClassUtil.createInstanceWithoutArgs(DEFAULT_LFJG_PATH + DEFAULT_LFJG_PHYSIC_SYSTEM_PATH + DEFAULT_LFJG_PHYSIC_CORE_CLASS_NAME);
            lfjgPhysicServiceData = (ServiceData) ClassUtil.invokeMethodExact(instance, "execute");
            DebugLog.debug(instance.getClass(), lfjgPhysicServiceData.toString());
        }
        if (ENABLE_LFJG_RENDER_SYSTEM) {
            Object instance = ClassUtil.createInstanceWithoutArgs(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_CORE_CLASS_NAME);
            lfjgRenderServiceData = (ServiceData) ClassUtil.invokeMethodExact(instance, "execute");
            DebugLog.debug(instance.getClass(), lfjgRenderServiceData.toString());
        }
        if (ENABLE_LFJG_RENDER_TEXT_SYSTEM) {
            Object instance = ClassUtil.createInstanceWithoutArgs(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_TEXT_CORE_CLASS_NAME);
            lfjgRenderTextServiceData = (ServiceData) ClassUtil.invokeMethodExact(instance, "execute");
            DebugLog.debug(instance.getClass(), lfjgRenderTextServiceData.toString());
        }
        if (ENABLE_LFJG_RENDER_VIDEO_SYSTEM) {
            Object instance = ClassUtil.createInstanceWithoutArgs(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_VIDEO_CORE_CLASS_NAME);
            lfjgRenderVideoServiceData = (ServiceData) ClassUtil.invokeMethodExact(instance, "execute");
            DebugLog.debug(instance.getClass(), lfjgRenderVideoServiceData.toString());
        }

        EVENT_MANAGER = new EventManager();
        WORKSPACE_MANAGER = new WorkspaceManager();

        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (Unsafe) f.get(null);
        } catch (
                Exception e) {
            throw new RuntimeException(e);
        }

        stringBuilder = new FastStringBuilder();

        Class<?>[] glClasses;
        try {
            glClasses = new Class<?>[]{
                    Class.forName(GL11.PACKAGE),
                    Class.forName(GL12.PACKAGE),
                    Class.forName(GL13.PACKAGE),
                    Class.forName(GL14.PACKAGE),
                    Class.forName(GL15.PACKAGE),
                    Class.forName(GL20.PACKAGE),
                    Class.forName(GL21.PACKAGE),
                    Class.forName(GL30.PACKAGE),
                    Class.forName(GL31.PACKAGE),
                    Class.forName(GL32.PACKAGE),
                    Class.forName(GL33.PACKAGE),
                    Class.forName(GL40.PACKAGE),
                    Class.forName(GL41.PACKAGE),
                    Class.forName(GL42.PACKAGE),
                    Class.forName(GL43.PACKAGE),
                    Class.forName(GL44.PACKAGE),
                    Class.forName(GL45.PACKAGE),
                    Class.forName(GL46.PACKAGE)
            };
        } catch (
                ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (Class<?> glClass : glClasses) {
            Field[] fields = glClass.getFields();
            for (Field field : fields) {
                if (field.getType() == int.class) {
                    try {
                        int fieldInt = field.getInt(null);
                        String name = field.getName();
                        OPEN_GL_PARAMETER_NAME_MAP.put(name, fieldInt);
                    } catch (
                            IllegalAccessException e) {
                        DebugLog.warning(Core.class, e);
                    }
                }
            }
        }
    }

    private static void initLFJGContext() {
        final String METHOD = "init";
        if (ENABLE_LFJG_RENDER_SYSTEM) {
            invokeStaticMethod(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_CONTEXT_CLASS_NAME, METHOD);
        }
        if (ENABLE_LFJG_FRAME_SYSTEM) {
            invokeStaticMethod(DEFAULT_LFJG_PATH + DEFAULT_LFJG_FRAME_SYSTEM_PATH + DEFAULT_LFJG_FRAME_CONTEXT_CLASS_NAME, METHOD);
        }
        if (ENABLE_LFJG_RENDER_TEXT_SYSTEM) {
            invokeStaticMethod(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_TEXT_CONTEXT_CLASS_NAME, METHOD);
        }
        if (ENABLE_LFJG_RENDER_VIDEO_SYSTEM) {
            invokeStaticMethod(DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + DEFAULT_LFJG_RENDER_VIDEO_CONTEXT_CLASS_NAME, METHOD);
        }
        if (ENABLE_LFJG_AUDIO_SYSTEM) {
            invokeStaticMethod(DEFAULT_LFJG_PATH + DEFAULT_LFJG_AUDIO_SYSTEM_PATH + DEFAULT_LFJG_AUDIO_CONTEXT_CLASS_NAME, METHOD);
        }
        if (ENABLE_LFJG_JCEF_SYSTEM) {
            invokeStaticMethod(DEFAULT_LFJG_PATH + DEFAULT_LFJG_JCEF_SYSTEM_PATH + DEFAULT_LFJG_JCEF_CONTEXT_CLASS_NAME, METHOD);
        }
        if (ENABLE_LFJG_PHYSIC_SYSTEM) {
            invokeStaticMethod(DEFAULT_LFJG_PATH + DEFAULT_LFJG_PHYSIC_SYSTEM_PATH + DEFAULT_LFJG_PHYSIC_CONTEXT_CLASS_NAME, METHOD);
        }
    }

    public static void init(int frameBufferWidth, int frameBufferHeight, int windowWidth, int windowHeight) {
        if (ENABLE_LFJG_RENDER_SYSTEM) {
            VENDOR = GPUUtil.getVendor();
            RENDERER = GPUUtil.getRenderer();
            VERSION = GPUUtil.getVersion();
            SHADING_LANGUAGE_VERSION = GPUUtil.getShadingLanguageVersion();

            DebugLog.debug(Core.class, "Vendor: " + VENDOR);
            DebugLog.debug(Core.class, "GPU: " + RENDERER);
            DebugLog.debug(Core.class, "OpenGL: " + VERSION);
            DebugLog.debug(Core.class, "ShadingLanguageVersion: " + SHADING_LANGUAGE_VERSION);
        }

        updateLFJGLContext(frameBufferWidth, frameBufferHeight, windowWidth, windowHeight);
        initLFJGContext();
    }

    public static void updateLFJGLContext(int frameBufferWidth, int frameBufferHeight, int windowWidth, int windowHeight) {
        float devicePixelRatioX = (float) frameBufferWidth / windowWidth;
        float devicePixelRatioY = (float) frameBufferHeight / windowHeight;

        frameBufferSize = new Vector2i(frameBufferWidth, frameBufferHeight);
        devicePixelRatio = MathHelper.max(devicePixelRatioX, devicePixelRatioY);

        if (projection2D == null) {
            projection2D = new Projection(ProjectionType.ORTHOGRAPHIC_PROJECTION, Projection.DEFAULT_FOV, frameBufferWidth, frameBufferHeight, Projection.DEFAULT_Z_FAR, Projection.DEFAULT_Z_NEAR);
        } else {
            projection2D.updateProjMatrix(ProjectionType.ORTHOGRAPHIC_PROJECTION, Projection.DEFAULT_FOV, frameBufferWidth, frameBufferHeight, Projection.DEFAULT_Z_FAR, Projection.DEFAULT_Z_NEAR);
        }

        if (projection3D == null) {
            projection3D = new Projection(ProjectionType.PERSPECTIVE_PROJECTION, Projection.DEFAULT_FOV, frameBufferWidth, frameBufferHeight, Projection.DEFAULT_Z_FAR, Projection.DEFAULT_Z_NEAR);
        } else {
            projection3D.updateProjMatrix(ProjectionType.PERSPECTIVE_PROJECTION, Projection.DEFAULT_FOV, frameBufferWidth, frameBufferHeight, Projection.DEFAULT_Z_FAR, Projection.DEFAULT_Z_NEAR);
        }
    }

    private static boolean isLWJGL3() {
        try {
            Class.forName("org.lwjgl.system.MemoryUtil");
            return true;
        } catch (
                ClassNotFoundException e) {
            return false;
        }
    }

    private static boolean isLWJGL2() {
        try {
            Class.forName("org.lwjgl.BufferUtils");
            return true;
        } catch (
                ClassNotFoundException e) {
            return false;
        }
    }

    private static Object invokeStaticMethod(String className, String methodName, Object... args) {
        Object result = null;
        try {
            result = ClassUtil.invokeStaticMethod(className, methodName, args);
        } catch (
                Exception e) {
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
        } catch (
                Exception e) {
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

    public static class GL46 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL46";
    }

    public static class GL45 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL45";
    }

    public static class GL44 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL44";
    }

    public static class GL43 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL43";

        public static void glInvalidateFramebuffer(int target, int[] attachments) {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glInvalidateFramebuffer", target, attachments);
        }
    }

    public static class GL42 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL42";
    }

    public static class GL41 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL41";
    }

    public static class GL40 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL40";
    }

    public static class GL33 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL33";
    }

    public static class GL32 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL32";
    }

    public static class GL31 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL31";
    }

    public static class GL30 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL30";
        public static final GLClearBufferfvArrayCall GL_CLEAR_BUFFERFV_ARRAY_CALL;
        public static final GLClearBufferfvFloatBufferCall GL_CLEAR_BUFFERFV_FLOAT_BUFFER_CALL;

        static {
            GL_CLEAR_BUFFERFV_ARRAY_CALL = ClassUtil.bindStatic(PACKAGE, "glClearBufferfv", GLClearBufferfvArrayCall.class, MethodType.methodType(void.class, int.class, int.class, float[].class));
            GL_CLEAR_BUFFERFV_FLOAT_BUFFER_CALL = ClassUtil.bindStatic(PACKAGE, "glClearBufferfv", GLClearBufferfvFloatBufferCall.class, MethodType.methodType(void.class, int.class, int.class, FloatBuffer.class));
        }

        public static void glGenerateMipmap(int target) {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glGenerateMipmap", target);
        }

        public static void glClearBufferfv(int buffer, int drawbuffer, float[] value) {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return;
            }

            GL_CLEAR_BUFFERFV_ARRAY_CALL.call(buffer, drawbuffer, value);
        }

        public static void glClearBufferfv(int buffer, int drawbuffer, FloatBuffer value) {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return;
            }

            GL_CLEAR_BUFFERFV_FLOAT_BUFFER_CALL.call(buffer, drawbuffer, value);
        }

        @FunctionalInterface
        public interface GLClearBufferfvArrayCall {
            void call(int buffer, int drawbuffer, float[] value);

        }

        @FunctionalInterface
        public interface GLClearBufferfvFloatBufferCall {
            void call(int buffer, int drawbuffer, FloatBuffer value);
        }
    }

    public static class GL21 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL21";
    }

    public static class GL20 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL20";
    }

    public static class GL15 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL15";
    }

    public static class GL14 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL14";
    }

    public static class GL13 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL13";
    }

    public static class GL12 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL12";
    }

    public static class GL11 {
        public static final String PACKAGE = "org.lwjgl.opengl.GL11";
        public static final GLClearCall GL_CLEAR_CALL;

        static {
            GL_CLEAR_CALL = ClassUtil.bindStatic(PACKAGE, "glClear", GLClearCall.class, MethodType.methodType(void.class, int.class));
        }

        public static void glClearColor(float red, float green, float blue, float alpha) {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glClearColor", red, green, blue, alpha);
        }

        public static void glClear(int mask) {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return;
            }

            GL_CLEAR_CALL.call(mask);
        }

        public static void glDeleteTextures(int texture) {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glDeleteTextures", texture);
        }

        public static int glGenTextures() {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return -1;
            }

            Object result = invokeStaticMethod(PACKAGE, "glGenTextures");
            if (result == null) {
                return -1;
            }

            return (int) result;
        }

        public static void glPixelStorei(int pname, int param) {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glPixelStorei", pname, param);
        }

        public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, @Nullable ByteBuffer pixels) {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glTexImage2D", target, level, internalformat, width, height, border, format, type, pixels);
        }

        public static void glTexParameteri(int target, int pname, int param) {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "glTexParameteri", target, pname, param);
        }

        @FunctionalInterface
        public interface GLClearCall {
            void call(int mask);
        }
    }

    public static class GL {
        public static final String PACKAGE = "org.lwjgl.opengl.GL";

        public static void createCapabilities() {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "createCapabilities");
        }

        public static Object getCapabilities() {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return null;
            }

            return invokeStaticMethod(PACKAGE, "getCapabilities");
        }
    }

    public static class GLFW {
        public static final String PACKAGE = "org.lwjgl.glfw.GLFW";

        public static final int GLFW_MOUSE_BUTTON_1 = getStaticIntField(PACKAGE, "GLFW_MOUSE_BUTTON_1");
        public static final int GLFW_MOUSE_BUTTON_LEFT = GLFW_MOUSE_BUTTON_1;
        public static final int GLFW_MOUSE_BUTTON_2 = getStaticIntField(PACKAGE, "GLFW_MOUSE_BUTTON_2");
        public static final int GLFW_MOUSE_BUTTON_RIGHT = GLFW_MOUSE_BUTTON_2;
        public static final int GLFW_MOUSE_BUTTON_3 = getStaticIntField(PACKAGE, "GLFW_MOUSE_BUTTON_3");
        public static final int GLFW_MOUSE_BUTTON_MIDDLE = GLFW_MOUSE_BUTTON_3;
        public static final int GLFW_PRESS = getStaticIntField(PACKAGE, "GLFW_PRESS");
        public static final int GLFW_RELEASE = getStaticIntField(PACKAGE, "GLFW_RELEASE");

        public static final int GLFW_MOD_SHIFT = getStaticIntField(PACKAGE, "GLFW_MOD_SHIFT");
        public static final int GLFW_MOD_CONTROL = getStaticIntField(PACKAGE, "GLFW_MOD_CONTROL");
        public static final int GLFW_MOD_ALT = getStaticIntField(PACKAGE, "GLFW_MOD_ALT");
        public static final int GLFW_MOD_SUPER = getStaticIntField(PACKAGE, "GLFW_MOD_SUPER");

        public static final int GLFW_KEY_ENTER = getStaticIntField(PACKAGE, "GLFW_KEY_ENTER");
        public static final int GLFW_KEY_BACKSPACE = getStaticIntField(PACKAGE, "GLFW_KEY_BACKSPACE");
        public static final int GLFW_KEY_TAB = getStaticIntField(PACKAGE, "GLFW_KEY_TAB");
        public static final int GLFW_KEY_ESCAPE = getStaticIntField(PACKAGE, "GLFW_KEY_ESCAPE");
        public static final int GLFW_KEY_SPACE = getStaticIntField(PACKAGE, "GLFW_KEY_SPACE");
        public static final int GLFW_KEY_LEFT = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT");
        public static final int GLFW_KEY_RIGHT = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT");
        public static final int GLFW_KEY_UP = getStaticIntField(PACKAGE, "GLFW_KEY_UP");
        public static final int GLFW_KEY_DOWN = getStaticIntField(PACKAGE, "GLFW_KEY_DOWN");
        public static final int GLFW_KEY_DELETE = getStaticIntField(PACKAGE, "GLFW_KEY_DELETE");
        public static final int GLFW_KEY_HOME = getStaticIntField(PACKAGE, "GLFW_KEY_HOME");
        public static final int GLFW_KEY_END = getStaticIntField(PACKAGE, "GLFW_KEY_END");
        public static final int GLFW_KEY_PAGE_UP = getStaticIntField(PACKAGE, "GLFW_KEY_PAGE_UP");
        public static final int GLFW_KEY_PAGE_DOWN = getStaticIntField(PACKAGE, "GLFW_KEY_PAGE_DOWN");
        public static final int GLFW_KEY_INSERT = getStaticIntField(PACKAGE, "GLFW_KEY_INSERT");
        public static final int GLFW_KEY_CAPS_LOCK = getStaticIntField(PACKAGE, "GLFW_KEY_CAPS_LOCK");
        public static final int GLFW_KEY_SCROLL_LOCK = getStaticIntField(PACKAGE, "GLFW_KEY_SCROLL_LOCK");
        public static final int GLFW_KEY_NUM_LOCK = getStaticIntField(PACKAGE, "GLFW_KEY_NUM_LOCK");
        public static final int GLFW_KEY_PRINT_SCREEN = getStaticIntField(PACKAGE, "GLFW_KEY_PRINT_SCREEN");
        public static final int GLFW_KEY_PAUSE = getStaticIntField(PACKAGE, "GLFW_KEY_PAUSE");

        public static final int GLFW_KEY_A = getStaticIntField(PACKAGE, "GLFW_KEY_A");
        public static final int GLFW_KEY_B = getStaticIntField(PACKAGE, "GLFW_KEY_B");
        public static final int GLFW_KEY_C = getStaticIntField(PACKAGE, "GLFW_KEY_C");
        public static final int GLFW_KEY_D = getStaticIntField(PACKAGE, "GLFW_KEY_D");
        public static final int GLFW_KEY_E = getStaticIntField(PACKAGE, "GLFW_KEY_E");
        public static final int GLFW_KEY_F = getStaticIntField(PACKAGE, "GLFW_KEY_F");
        public static final int GLFW_KEY_G = getStaticIntField(PACKAGE, "GLFW_KEY_G");
        public static final int GLFW_KEY_H = getStaticIntField(PACKAGE, "GLFW_KEY_H");
        public static final int GLFW_KEY_I = getStaticIntField(PACKAGE, "GLFW_KEY_I");
        public static final int GLFW_KEY_J = getStaticIntField(PACKAGE, "GLFW_KEY_J");
        public static final int GLFW_KEY_K = getStaticIntField(PACKAGE, "GLFW_KEY_K");
        public static final int GLFW_KEY_L = getStaticIntField(PACKAGE, "GLFW_KEY_L");
        public static final int GLFW_KEY_M = getStaticIntField(PACKAGE, "GLFW_KEY_M");
        public static final int GLFW_KEY_N = getStaticIntField(PACKAGE, "GLFW_KEY_N");
        public static final int GLFW_KEY_O = getStaticIntField(PACKAGE, "GLFW_KEY_O");
        public static final int GLFW_KEY_P = getStaticIntField(PACKAGE, "GLFW_KEY_P");
        public static final int GLFW_KEY_Q = getStaticIntField(PACKAGE, "GLFW_KEY_Q");
        public static final int GLFW_KEY_R = getStaticIntField(PACKAGE, "GLFW_KEY_R");
        public static final int GLFW_KEY_S = getStaticIntField(PACKAGE, "GLFW_KEY_S");
        public static final int GLFW_KEY_T = getStaticIntField(PACKAGE, "GLFW_KEY_T");
        public static final int GLFW_KEY_U = getStaticIntField(PACKAGE, "GLFW_KEY_U");
        public static final int GLFW_KEY_V = getStaticIntField(PACKAGE, "GLFW_KEY_V");
        public static final int GLFW_KEY_W = getStaticIntField(PACKAGE, "GLFW_KEY_W");
        public static final int GLFW_KEY_X = getStaticIntField(PACKAGE, "GLFW_KEY_X");
        public static final int GLFW_KEY_Y = getStaticIntField(PACKAGE, "GLFW_KEY_Y");
        public static final int GLFW_KEY_Z = getStaticIntField(PACKAGE, "GLFW_KEY_Z");

        public static final int GLFW_KEY_0 = getStaticIntField(PACKAGE, "GLFW_KEY_0");
        public static final int GLFW_KEY_1 = getStaticIntField(PACKAGE, "GLFW_KEY_1");
        public static final int GLFW_KEY_2 = getStaticIntField(PACKAGE, "GLFW_KEY_2");
        public static final int GLFW_KEY_3 = getStaticIntField(PACKAGE, "GLFW_KEY_3");
        public static final int GLFW_KEY_4 = getStaticIntField(PACKAGE, "GLFW_KEY_4");
        public static final int GLFW_KEY_5 = getStaticIntField(PACKAGE, "GLFW_KEY_5");
        public static final int GLFW_KEY_6 = getStaticIntField(PACKAGE, "GLFW_KEY_6");
        public static final int GLFW_KEY_7 = getStaticIntField(PACKAGE, "GLFW_KEY_7");
        public static final int GLFW_KEY_8 = getStaticIntField(PACKAGE, "GLFW_KEY_8");
        public static final int GLFW_KEY_9 = getStaticIntField(PACKAGE, "GLFW_KEY_9");

        public static final int GLFW_KEY_F1 = getStaticIntField(PACKAGE, "GLFW_KEY_F1");
        public static final int GLFW_KEY_F2 = getStaticIntField(PACKAGE, "GLFW_KEY_F2");
        public static final int GLFW_KEY_F3 = getStaticIntField(PACKAGE, "GLFW_KEY_F3");
        public static final int GLFW_KEY_F4 = getStaticIntField(PACKAGE, "GLFW_KEY_F4");
        public static final int GLFW_KEY_F5 = getStaticIntField(PACKAGE, "GLFW_KEY_F5");
        public static final int GLFW_KEY_F6 = getStaticIntField(PACKAGE, "GLFW_KEY_F6");
        public static final int GLFW_KEY_F7 = getStaticIntField(PACKAGE, "GLFW_KEY_F7");
        public static final int GLFW_KEY_F8 = getStaticIntField(PACKAGE, "GLFW_KEY_F8");
        public static final int GLFW_KEY_F9 = getStaticIntField(PACKAGE, "GLFW_KEY_F9");
        public static final int GLFW_KEY_F10 = getStaticIntField(PACKAGE, "GLFW_KEY_F10");
        public static final int GLFW_KEY_F11 = getStaticIntField(PACKAGE, "GLFW_KEY_F11");
        public static final int GLFW_KEY_F12 = getStaticIntField(PACKAGE, "GLFW_KEY_F12");
        public static final int GLFW_KEY_F13 = getStaticIntField(PACKAGE, "GLFW_KEY_F13");
        public static final int GLFW_KEY_F14 = getStaticIntField(PACKAGE, "GLFW_KEY_F14");
        public static final int GLFW_KEY_F15 = getStaticIntField(PACKAGE, "GLFW_KEY_F15");
        public static final int GLFW_KEY_F16 = getStaticIntField(PACKAGE, "GLFW_KEY_F16");
        public static final int GLFW_KEY_F17 = getStaticIntField(PACKAGE, "GLFW_KEY_F17");
        public static final int GLFW_KEY_F18 = getStaticIntField(PACKAGE, "GLFW_KEY_F18");
        public static final int GLFW_KEY_F19 = getStaticIntField(PACKAGE, "GLFW_KEY_F19");
        public static final int GLFW_KEY_F20 = getStaticIntField(PACKAGE, "GLFW_KEY_F20");
        public static final int GLFW_KEY_F21 = getStaticIntField(PACKAGE, "GLFW_KEY_F21");
        public static final int GLFW_KEY_F22 = getStaticIntField(PACKAGE, "GLFW_KEY_F22");
        public static final int GLFW_KEY_F23 = getStaticIntField(PACKAGE, "GLFW_KEY_F23");
        public static final int GLFW_KEY_F24 = getStaticIntField(PACKAGE, "GLFW_KEY_F24");
        public static final int GLFW_KEY_F25 = getStaticIntField(PACKAGE, "GLFW_KEY_F25");

        public static final int GLFW_KEY_KP_0 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_0");
        public static final int GLFW_KEY_KP_1 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_1");
        public static final int GLFW_KEY_KP_2 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_2");
        public static final int GLFW_KEY_KP_3 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_3");
        public static final int GLFW_KEY_KP_4 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_4");
        public static final int GLFW_KEY_KP_5 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_5");
        public static final int GLFW_KEY_KP_6 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_6");
        public static final int GLFW_KEY_KP_7 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_7");
        public static final int GLFW_KEY_KP_8 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_8");
        public static final int GLFW_KEY_KP_9 = getStaticIntField(PACKAGE, "GLFW_KEY_KP_9");
        public static final int GLFW_KEY_KP_DECIMAL = getStaticIntField(PACKAGE, "GLFW_KEY_KP_DECIMAL");
        public static final int GLFW_KEY_KP_DIVIDE = getStaticIntField(PACKAGE, "GLFW_KEY_KP_DIVIDE");
        public static final int GLFW_KEY_KP_MULTIPLY = getStaticIntField(PACKAGE, "GLFW_KEY_KP_MULTIPLY");
        public static final int GLFW_KEY_KP_SUBTRACT = getStaticIntField(PACKAGE, "GLFW_KEY_KP_SUBTRACT");
        public static final int GLFW_KEY_KP_ADD = getStaticIntField(PACKAGE, "GLFW_KEY_KP_ADD");
        public static final int GLFW_KEY_KP_ENTER = getStaticIntField(PACKAGE, "GLFW_KEY_KP_ENTER");
        public static final int GLFW_KEY_KP_EQUAL = getStaticIntField(PACKAGE, "GLFW_KEY_KP_EQUAL");

        public static final int GLFW_KEY_LEFT_SHIFT = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT_SHIFT");
        public static final int GLFW_KEY_RIGHT_SHIFT = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT_SHIFT");
        public static final int GLFW_KEY_LEFT_CONTROL = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT_CONTROL");
        public static final int GLFW_KEY_RIGHT_CONTROL = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT_CONTROL");
        public static final int GLFW_KEY_LEFT_ALT = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT_ALT");
        public static final int GLFW_KEY_RIGHT_ALT = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT_ALT");
        public static final int GLFW_KEY_LEFT_SUPER = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT_SUPER");
        public static final int GLFW_KEY_RIGHT_SUPER = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT_SUPER");
        public static final int GLFW_KEY_MENU = getStaticIntField(PACKAGE, "GLFW_KEY_MENU");

        public static final int GLFW_KEY_MINUS = getStaticIntField(PACKAGE, "GLFW_KEY_MINUS");
        public static final int GLFW_KEY_EQUAL = getStaticIntField(PACKAGE, "GLFW_KEY_EQUAL");
        public static final int GLFW_KEY_BACKSLASH = getStaticIntField(PACKAGE, "GLFW_KEY_BACKSLASH");
        public static final int GLFW_KEY_SEMICOLON = getStaticIntField(PACKAGE, "GLFW_KEY_SEMICOLON");
        public static final int GLFW_KEY_APOSTROPHE = getStaticIntField(PACKAGE, "GLFW_KEY_APOSTROPHE");
        public static final int GLFW_KEY_COMMA = getStaticIntField(PACKAGE, "GLFW_KEY_COMMA");
        public static final int GLFW_KEY_PERIOD = getStaticIntField(PACKAGE, "GLFW_KEY_PERIOD");
        public static final int GLFW_KEY_SLASH = getStaticIntField(PACKAGE, "GLFW_KEY_SLASH");
        public static final int GLFW_KEY_GRAVE_ACCENT = getStaticIntField(PACKAGE, "GLFW_KEY_GRAVE_ACCENT");
        public static final int GLFW_KEY_LEFT_BRACKET = getStaticIntField(PACKAGE, "GLFW_KEY_LEFT_BRACKET");
        public static final int GLFW_KEY_RIGHT_BRACKET = getStaticIntField(PACKAGE, "GLFW_KEY_RIGHT_BRACKET");
        public static final int GLFW_KEY_WORLD_1 = getStaticIntField(PACKAGE, "GLFW_KEY_WORLD_1");
        public static final int GLFW_KEY_WORLD_2 = getStaticIntField(PACKAGE, "GLFW_KEY_WORLD_2");

        public static double glfwGetTime() {
            if (!ENABLE_LFJG_FRAME_SYSTEM) {
                return -1d;
            }

            Object result = invokeStaticMethod(PACKAGE, "glfwGetTime");
            if (result == null) {
                return -1d;
            }

            return (double) result;
        }
    }

    public static class GPUUtil {
        public static final String PACKAGE = DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + ".uitl.GPUUtil";

        public static String getVendor() {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return "unknown";
            }

            return (String) invokeStaticMethod(PACKAGE, "getVendor");
        }

        public static String getRenderer() {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return "unknown";
            }

            return (String) invokeStaticMethod(PACKAGE, "getRenderer");
        }

        public static String getVersion() {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return "unknown";
            }

            return (String) invokeStaticMethod(PACKAGE, "getVersion");
        }

        public static String getShadingLanguageVersion() {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return "unknown";
            }

            return (String) invokeStaticMethod(PACKAGE, "getShadingLanguageVersion");
        }

        public static String[] getExtensions() {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return new String[0];
            }

            return (String[]) invokeStaticMethod(PACKAGE, "getExtensions");
        }
    }

    public static class OpenGLDebug {
        public static final String PACKAGE = DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + ".debug.OpenGLDebug";

        public static void getOpenGLDebug(String mainThreadName, int[] severitiesId) {
            if (!ENABLE_LFJG_RENDER_SYSTEM) {
                return;
            }

            Object ignore = invokeStaticMethod(PACKAGE, "getOpenGLDebug", mainThreadName, severitiesId);
        }
    }

    public static class GLStateCache {
        public static final String PACKAGE = DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + ".system.rendering.GLStateCache";
        public static final GetLastFrameBufferCall GET_LAST_FRAME_BUFFER;
        public static final BindFrameBufferCall BIND_FRAME_BUFFER_CALL;

        static {
            GET_LAST_FRAME_BUFFER = ClassUtil.bindInstance(PACKAGE, "getLastFrameBuffer", GetLastFrameBufferCall.class, MethodType.methodType(int.class));
            BIND_FRAME_BUFFER_CALL = ClassUtil.bindInstance(PACKAGE, "bindFrameBuffer", BindFrameBufferCall.class, MethodType.methodType(void.class, int.class));
        }

        public static int getLastFrameBuffer() {
            if (!ENABLE_LFJG_RENDER_SYSTEM || GL_STATE_CACHE == null) {
                return -1;
            }

            return GET_LAST_FRAME_BUFFER.call(GL_STATE_CACHE);
        }

        public static void bindFrameBuffer(int frameBuffer) {
            if (!ENABLE_LFJG_RENDER_SYSTEM || GL_STATE_CACHE == null) {
                return;
            }

            BIND_FRAME_BUFFER_CALL.call(GL_STATE_CACHE, frameBuffer);
        }

        @FunctionalInterface
        public interface GetLastFrameBufferCall {
            int call(Object o);
        }

        @FunctionalInterface
        public interface BindFrameBufferCall {
            void call(Object o, int frameBuffer);
        }
    }

    public static class LFJGRenderContext {
        public static final String PACKAGE = DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + ".LFJGRenderContext";
        public static final Object GL_STATE_CACHE;

        static {
            if (ENABLE_LFJG_RENDER_SYSTEM) {
                GL_STATE_CACHE = getStaticFieldValue(PACKAGE, "GL_STATE_CACHE");
            } else {
                GL_STATE_CACHE = null;
            }
        }

        public static void enable(int target) {
            if (!ENABLE_LFJG_RENDER_SYSTEM || GL_STATE_CACHE == null) {
                return;
            }

            ClassUtil.invokeMethodExact(GL_STATE_CACHE, "enable", target);
        }

        public static void disable(int target) {
            if (!ENABLE_LFJG_RENDER_SYSTEM || GL_STATE_CACHE == null) {
                return;
            }

            ClassUtil.invokeMethodExact(GL_STATE_CACHE, "disable", target);
        }

        public static void bindTexture(int target, int texture) {
            if (!ENABLE_LFJG_RENDER_SYSTEM || GL_STATE_CACHE == null) {
                return;
            }

            ClassUtil.invokeMethodExact(GL_STATE_CACHE, "bindTexture", target, texture);
        }
    }

    public static class CreatingTextureException {
        public static final String PACKAGE = DEFAULT_LFJG_PATH + DEFAULT_LFJG_RENDER_SYSTEM_PATH + ".debug.exceptions.texture.CreatingTextureException";

        public static RuntimeException createCreatingTextureException(String message) {
            try {
                Class<?> clazz = Class.forName(PACKAGE);
                Constructor<?> constructor = clazz.getConstructor(String.class);
                return (RuntimeException) constructor.newInstance(message);
            } catch (
                    Exception e) {
                throw new RuntimeException("Failed to create CreatingTextureException via reflection", e);
            }
        }
    }
}