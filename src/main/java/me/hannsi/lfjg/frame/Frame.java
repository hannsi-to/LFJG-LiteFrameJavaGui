package me.hannsi.lfjg.frame;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.event.events.render.DrawFrameWithNanoVGEvent;
import me.hannsi.lfjg.event.events.render.DrawFrameWithOpenGLEvent;
import me.hannsi.lfjg.event.events.user.*;
import me.hannsi.lfjg.event.system.EventHandler;
import me.hannsi.lfjg.frame.manager.managers.FrameSettingManager;
import me.hannsi.lfjg.frame.manager.managers.LoggerManager;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.render.nanoVG.system.NanoVGUtil;
import me.hannsi.lfjg.utils.graphics.GLFWUtil;
import me.hannsi.lfjg.utils.time.TimeCalculator;
import me.hannsi.lfjg.utils.time.TimeSourceUtil;
import me.hannsi.lfjg.utils.toolkit.RuntimeUtil;
import me.hannsi.lfjg.utils.type.types.AntiAliasingType;
import me.hannsi.lfjg.utils.type.types.RenderingType;
import me.hannsi.lfjg.utils.type.types.VSyncType;
import org.joml.Vector2i;
import org.lwjgl.glfw.*;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.system.MemoryUtil.memUTF8;

public class Frame implements IFrame {
    private final LFJGFrame lfjgFrame;
    private FrameSettingManager frameSettingManager;
    private long windowID = -1L;
    private int fps;
    private int windowWidth;
    private int windowHeight;
    private long currentTime;
    private long lastTime;
    private long startTime;
    private long finishTime;
    private long nvg;

    public Frame(LFJGFrame lfjgFrame, String threadName) {
        this.lfjgFrame = lfjgFrame;

        new Thread(this::createFrame, threadName).start();
    }

    private void registerManagers() {
        DebugLog.debug(getClass(), "Managers loading...");
        long tookTime = TimeCalculator.calculate(() -> {
            this.frameSettingManager = new FrameSettingManager(this);
        });
        DebugLog.debug(getClass(), "Managers took " + tookTime + "ms to load!");
    }

    public void createFrame() {
        registerManagers();

        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        lfjgFrame.setFrameSetting();
        glfwWindowHints();

        Vector2i windowSizes = GLFWUtil.getWindowSizes(this, getFrameSettingValue(MonitorSetting.class));
        windowWidth = windowSizes.x();
        windowHeight = windowSizes.y();

        windowID = GLFW.glfwCreateWindow(windowWidth, windowHeight, getFrameSettingValue(TitleSetting.class).toString(), GLFWUtil.getMonitorTypeCode(getFrameSettingValue(MonitorSetting.class)), MemoryUtil.NULL);

        if (windowID == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        GLFW.glfwMakeContextCurrent(windowID);
        GLFW.glfwSwapInterval(((VSyncType) getFrameSettingValue(VSyncSetting.class)).getId());
        GLFW.glfwShowWindow(windowID);

        initializeRendering();

        updateViewport();
        updateFrameSetting(false);

        glfwInvoke();
        mainLoop();
    }

    private void initializeRendering() {
        switch ((RenderingType) getFrameSettingValue(RenderingTypeSetting.class)) {
            case OpenGL -> {
                GL.createCapabilities();
            }
            case NanoVG -> {
                GL.createCapabilities();

                nvg = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS);
                if (nvg == MemoryUtil.NULL) {
                    throw new RuntimeException("Failed to create NanoVG context");
                }
            }
            case Vulkan -> {

            }
            case LibGDX -> {
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + getFrameSettingValue(RenderingTypeSetting.class));
        }
    }

    private void glfwWindowHints() {
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_CENTER_CURSOR, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_FOCUS_ON_SHOW, GLFW.GLFW_TRUE);

        updateFrameSetting(true);
    }

    public void updateFrameSetting(boolean windowHint) {
        DebugLog.debug(getClass(), "FrameSettings Updating...");
        long tookTime = TimeCalculator.calculate(() -> {
            frameSettingManager.updateFrameSettings(windowHint);
        });
        DebugLog.debug(getClass(), "FrameSettings took " + tookTime + "ms to update!");
    }

    private void glfwInvoke() {
        GLFW.glfwSetWindowFocusCallback(windowID, new GLFWWindowFocusCallbackI() {
            @Override
            public void invoke(long window, boolean focused) {

            }
        });
        GLFW.glfwSetFramebufferSizeCallback(windowID, new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                Vector2i windowSizes = GLFWUtil.getWindowSizes(Frame.this, getFrameSettingValue(MonitorSetting.class));
                windowWidth = windowSizes.x();
                windowHeight = windowSizes.y();

                updateViewport();
            }
        });

        GLFW.glfwSetKeyCallback(windowID, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW.GLFW_PRESS) {
                    eventManager.call(new KeyPressEvent(key, scancode, mods, window));
                } else if (action == GLFW.GLFW_RELEASE) {
                    eventManager.call(new KeyReleasedEvent(key, scancode, mods, window));
                }
            }
        });

        GLFW.glfwSetCursorPosCallback(windowID, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                eventManager.call(new CursorPosEvent(xpos, ypos, window));
            }
        });

        GLFW.glfwSetMouseButtonCallback(windowID, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (action == GLFW.GLFW_PRESS) {
                    eventManager.call(new MouseButtonPressEvent(button, mods, window));
                } else if (action == GLFW.GLFW_RELEASE) {
                    eventManager.call(new MouseButtonReleasedEvent(button, mods, window));
                }
            }
        });

        if (GL.getCapabilities().OpenGL43) {
            GL43.glEnable(GL43.GL_DEBUG_OUTPUT);
            GL43.glDebugMessageCallback(new GLDebugMessageCallback() {
                @Override
                public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
                    String errorMessage = memUTF8(message);

                    String sourceString = getSourceString(source);
                    String typeString = getTypeString(type);
                    String severityString = getSeverityString(severity);

                    DebugLog.debug(getClass(), "OpenGL Debug Message: \n" + "Source: " + sourceString + "\n" + "Type: " + typeString + "\n" + "ID: " + id + "\n" + "Severity: " + severityString + "\n" + "Message: " + errorMessage);
                }
            }, 0);
        } else {
            DebugLog.debug(getClass(), "OpenGL 4.3 or higher is required for debug messages.");
        }
    }

    private String getSourceString(int source) {
        return switch (source) {
            case GL43.GL_DEBUG_SOURCE_API -> "API";
            case GL43.GL_DEBUG_SOURCE_WINDOW_SYSTEM -> "Window System";
            case GL43.GL_DEBUG_SOURCE_SHADER_COMPILER -> "Shader Compiler";
            case GL43.GL_DEBUG_SOURCE_THIRD_PARTY -> "Third Party";
            case GL43.GL_DEBUG_SOURCE_APPLICATION -> "Application";
            case GL43.GL_DEBUG_SOURCE_OTHER -> "Other";
            default -> "Unknown";
        };
    }

    private String getTypeString(int type) {
        return switch (type) {
            case GL43.GL_DEBUG_TYPE_ERROR -> "Error";
            case GL43.GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR -> "Deprecated Behavior";
            case GL43.GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR -> "Undefined Behavior";
            case GL43.GL_DEBUG_TYPE_PORTABILITY -> "Portability";
            case GL43.GL_DEBUG_TYPE_PERFORMANCE -> "Performance";
            case GL43.GL_DEBUG_TYPE_OTHER -> "Other";
            default -> "Unknown";
        };
    }

    private String getSeverityString(int severity) {
        return switch (severity) {
            case GL43.GL_DEBUG_SEVERITY_NOTIFICATION -> "Notification";
            case GL43.GL_DEBUG_SEVERITY_LOW -> "Low";
            case GL43.GL_DEBUG_SEVERITY_MEDIUM -> "Medium";
            case GL43.GL_DEBUG_SEVERITY_HIGH -> "High";
            default -> "Unknown";
        };
    }

    private void mainLoop() {
        lfjgFrame.init();

        eventManager.register(this);

        long lastTime2 = TimeSourceUtil.getNanoTime(this);
        double deltaTime2 = 0;
        double targetTime = 1_000_000_000.0 / (int) getFrameSettingValue(RefreshRateSetting.class);
        int frames2 = 0;
        long fpsLsatTime = TimeSourceUtil.getNanoTime(this);

        startTime = TimeSourceUtil.getTimeMills(this);

        while (!GLFW.glfwWindowShouldClose(windowID)) {
            currentTime = TimeSourceUtil.getTimeMills(this);

            long currentTime2 = TimeSourceUtil.getNanoTime(this);
            deltaTime2 += currentTime2 - lastTime2;
            lastTime2 = currentTime2;

            if (deltaTime2 >= targetTime) {
                GL11.glClearColor(0.0f,0.0f,0.0f,0.0f);
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
                setAntiAliasing();

                draw();

                int error = GL11.glGetError();
                if (error != GL11.GL_NO_ERROR) {
                    DebugLog.error(getClass(), "OpenGL Error: " + error);
                }

                GLFW.glfwSwapBuffers(windowID);
                GLFW.glfwPollEvents();

                frames2++;

                if (currentTime2 - fpsLsatTime >= 1_000_000_000.0) {
                    fps = frames2;
                    frames2 = 0;
                    fpsLsatTime = currentTime2;
                }

                deltaTime2 -= targetTime;
            }

            double sleepTime = (targetTime - deltaTime2) / 1_000_000_000.0;
            if (sleepTime > 0) {
                try {
                    Thread.sleep((long) sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            lastTime = TimeSourceUtil.getTimeMills(this);
        }

        finishTime = TimeSourceUtil.getTimeMills(this);

        lfjgFrame.stopFrame();

        breakFrame();
    }

    private void draw() {
        switch ((RenderingType) getFrameSettingValue(RenderingTypeSetting.class)) {
            case OpenGL -> {
                eventManager.call(new DrawFrameWithOpenGLEvent());
            }
            case NanoVG -> {
                NanoVGUtil.framePush(nvg, ((Number) getFrameSettingValue(WidthSetting.class)).floatValue(), ((Number) getFrameSettingValue(HeightSetting.class)).floatValue(), 1);

                NanoVG.nvgTranslate(nvg, 0, ((Number) getFrameSettingValue(HeightSetting.class)).floatValue());
                NanoVG.nvgScale(nvg, 1, -1);

                eventManager.call(new DrawFrameWithNanoVGEvent(nvg));

                NanoVGUtil.framePop(nvg);
            }
            case Vulkan -> {
            }
            case LibGDX -> {
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + getFrameSettingValue(RenderingTypeSetting.class));
        }
    }

    @EventHandler
    public void drawFrameWidthOpenGLEvent(DrawFrameWithOpenGLEvent event) {
        lfjgFrame.drawFrame(nvg);
    }

    @EventHandler
    public void drawFrameWidthNanoVGEvent(DrawFrameWithNanoVGEvent event) {
        lfjgFrame.drawFrame(nvg);
    }

    public void stopFrame() {
        GLFW.glfwSetWindowShouldClose(windowID, true);
    }

    private void setAntiAliasing() {
        switch (((AntiAliasingType) getFrameSettingValue(AntiAliasingSetting.class))) {
            case MSAA -> {
                GL13.glEnable(GL13.GL_MULTISAMPLE);
            }
            case OFF -> {
                GL13.glDisable(GL13.GL_MULTISAMPLE);
            }
        }
    }

    private void breakFrame() {
        Callbacks.glfwFreeCallbacks(windowID);

        switch ((RenderingType) getFrameSettingValue(RenderingTypeSetting.class)) {
            case OpenGL -> {
            }
            case NanoVG -> {
                NanoVGGL3.nvgDelete(nvg);
            }
            case Vulkan -> {

            }
            case LibGDX -> {
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + getFrameSettingValue(RenderingTypeSetting.class));
        }

        GLFW.glfwDestroyWindow(windowID);

        GLFW.glfwTerminate();

        GLFWErrorCallback callback = GLFW.glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

    public void updateViewport() {
        GL11.glViewport(0, 0, windowWidth, windowHeight);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, windowWidth, 0, windowHeight, -1, 1);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }

    public String getMaxMemory() {
        return "MaxMemory: " + Math.round(RuntimeUtil.getMaxMemoryMB()) + "MB";
    }

    public String getAllocatedMemory() {
        return "AllocatedMemory: " + Math.round(RuntimeUtil.getAllocatedMemoryMB()) + "MB";
    }

    public String getFreeMemory() {
        return "FreeMemory: " + Math.round(RuntimeUtil.getFreeMemoryMB()) + "MB";
    }

    public String getUseMemory() {
        return "UsedMemory: " + Math.round(RuntimeUtil.getUseMemoryMB()) + "MB";
    }

    public long getWindowID() {
        return windowID;
    }

    @SuppressWarnings("unchecked")
    public <T> FrameSettingBase<T> getFrameSettingBase(Class<? extends FrameSettingBase<?>> frameSettingBase) {
        return ((FrameSettingBase<T>) frameSettingManager.getFrameSetting(frameSettingBase));
    }

    @SuppressWarnings("unchecked")
    public <T> T getFrameSettingValue(Class<? extends FrameSettingBase<?>> frameSettingBase) {
        return (T) getFrameSettingBase(frameSettingBase).getValue();
    }

    public <T> void setFrameSettingValue(Class<? extends FrameSettingBase<?>> frameSettingBase, T value) {
        getFrameSettingBase(frameSettingBase).setValue(value);
    }

    public LoggerManager getLoggerManager() {
        return loggerManager;
    }

    public LFJGFrame getLfjgFrame() {
        return lfjgFrame;
    }

    public int getFps() {
        return fps;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public long getLastTime() {
        return lastTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }
}
