package me.hannsi.lfjg.frame;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.event.EventHandler;
import me.hannsi.lfjg.core.utils.math.list.IntArrayList;
import me.hannsi.lfjg.core.utils.time.TimeCalculator;
import me.hannsi.lfjg.core.utils.time.TimeSourceUtil;
import me.hannsi.lfjg.core.utils.toolkit.ANSIFormat;
import me.hannsi.lfjg.core.utils.toolkit.RuntimeUtil;
import me.hannsi.lfjg.core.utils.type.types.TimeSourceType;
import me.hannsi.lfjg.frame.event.events.monitor.window.FramebufferSizeEvent;
import me.hannsi.lfjg.frame.event.events.monitor.window.WindowSizeEvent;
import me.hannsi.lfjg.frame.event.system.GLFWCallback;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.system.*;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.lwjgl.system.MemoryUtil;

import java.util.concurrent.locks.LockSupport;

import static me.hannsi.lfjg.core.Core.*;
import static me.hannsi.lfjg.core.Core.GL.createCapabilities;
import static me.hannsi.lfjg.core.Core.GL11.glClear;
import static me.hannsi.lfjg.core.Core.GL11.glClearColor;
import static me.hannsi.lfjg.core.Core.GL30.glClearBufferfv;
import static me.hannsi.lfjg.core.Core.GL43.glInvalidateFramebuffer;
import static me.hannsi.lfjg.core.Core.GLStateCache.bindFrameBuffer;
import static me.hannsi.lfjg.core.Core.GLStateCache.viewport;
import static me.hannsi.lfjg.core.Core.LFJGRenderContext.disable;
import static me.hannsi.lfjg.core.Core.LFJGRenderContext.enable;
import static org.lwjgl.glfw.GLFW.*;

public class Frame implements IFrame {
    private static final IntArrayList DISCARD_ATTACHMENTS = new IntArrayList(4);
    protected RenderingType renderingType;
    protected TimeSourceType timeSourceType;
    private final LFJGFrame lfjgFrame;
    private final String threadName;
    private boolean shouldCleanup = false;
    private long windowID = -1L;
    private int fps;
    private int windowWidth;
    private int windowHeight;
    private int frameBufferWidth;
    private int frameBufferHeight;
    private float contentScaleX;
    private float contentScaleY;
    private long currentTime;
    private long lastTime;
    private long startTime;
    private long finishTime;

    public Frame(LFJGFrame lfjgFrame, String threadName) {
        this.lfjgFrame = lfjgFrame;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            shouldCleanup = true;
            glfwPostEmptyEvent();
        }));

        this.threadName = threadName;
        Thread renderThread = new Thread(this::createFrame, threadName);
        renderThread.setUncaughtExceptionHandler((t, e) -> {
            DebugLog.error(getClass(), "FATAL ERROR in thread: " + t.getName());
            DebugLog.error(getClass(), e);
            System.exit(1);
        });
        renderThread.start();
    }

    private static void begin(RenderPass pass) {
        bindFrameBuffer(pass.fbo);

        for (Attachment a : pass.attachments) {
            if (a.loadOp == Attachment.LoadOp.CLEAR) {
                if (a.attachment == OPEN_GL_PARAMETER_NAME_MAP.get("GL_DEPTH_ATTACHMENT")) {
                    glClearBufferfv(OPEN_GL_PARAMETER_NAME_MAP.get("GL_DEPTH"), 0, a.clearValue);
                } else {
                    glClearBufferfv(OPEN_GL_PARAMETER_NAME_MAP.get("GL_COLOR"), a.attachment - OPEN_GL_PARAMETER_NAME_MAP.get("GL_COLOR_ATTACHMENT0"), a.clearValue);
                }
            }
        }
    }

    private static void end(RenderPass pass) {
        if (pass.fbo == 0) {
            return;
        }

        DISCARD_ATTACHMENTS.clear();

        for (Attachment a : pass.attachments) {
            if (a.storeOp != Attachment.StoreOp.DONT_CARE) {
                continue;
            }

            int att = a.attachment;

            if (att == OPEN_GL_PARAMETER_NAME_MAP.get("GL_COLOR") || att == OPEN_GL_PARAMETER_NAME_MAP.get("GL_DEPTH") || att == OPEN_GL_PARAMETER_NAME_MAP.get("GL_STENCIL")) {
                continue;
            }

            DISCARD_ATTACHMENTS.add(att);
        }

        if (!DISCARD_ATTACHMENTS.isEmpty()) {
            glInvalidateFramebuffer(OPEN_GL_PARAMETER_NAME_MAP.get("GL_FRAMEBUFFER"), DISCARD_ATTACHMENTS.toIntArray());
        }
    }

    public void createFrame() {
        WORKSPACE_MANAGER.createDirectories();
        WORKSPACE_MANAGER.copyResourcesToWorkspace();

        EVENT_MANAGER.register(this);

        registerManagers();

        lfjgFrame.setFrameSetting();

        initFrameSettingValue();
        initGLFW();
        initRendering();

        init(frameBufferWidth, frameBufferHeight, windowWidth, windowHeight);

        frameSettingManager.updateFrameSettings(false);

        GLFWCallback glfwCallback = new GLFWCallback(this);
        glfwCallback.glfwInvoke();

        lfjgFrame.init();
        mainLoop();
    }

    private void initFrameSettingValue() {
        renderingType = getFrameSettingValue(RenderingTypeSetting.class);
        timeSourceType = getFrameSettingValue(TimeSourceSetting.class);
    }

    private void initGLFW() {
        GLFWDebug.getGLFWDebug(this);

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwWindowHints();

        windowID = glfwCreateWindow(getFrameSettingValue(WidthSetting.class), getFrameSettingValue(HeightSetting.class), getFrameSettingValue(TitleSetting.class).toString(), GLFWUtil.getMonitorTypeCode(getFrameSettingValue(MonitorSetting.class)), MemoryUtil.NULL);
        if (windowID == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        Vector2i windowSizes = GLFWUtil.getWindowSize(getWindowID());
        Vector2i frameBufferSize = GLFWUtil.getFrameBufferSize(getWindowID());
        Vector2f contentScale = GLFWUtil.getContentScale(this);
        windowWidth = windowSizes.x();
        windowHeight = windowSizes.y();
        frameBufferWidth = frameBufferSize.x();
        frameBufferHeight = frameBufferSize.y();
        contentScaleX = contentScale.x();
        contentScaleY = contentScale.y();

        glfwMakeContextCurrent(windowID);
        glfwSwapInterval(((VSyncType) getFrameSettingValue(VSyncSetting.class)).getId());
        glfwShowWindow(windowID);
    }

    private void registerManagers() {
        DebugLog.debug(getClass(), "Managers loading...\n");
        long tookTime = TimeCalculator.calculateMillis(() -> {
            frameSettingManager.setFrame(this);
            frameSettingManager.loadFrameSettings();
        });
        DebugLog.debug(getClass(), ANSIFormat.GREEN + "Managers took " + tookTime + "ms to load!\n");
    }

    private void initRendering() {
        switch (renderingType) {
            case OPEN_GL:
                if (glfwGetCurrentContext() == 0) {
                    throw new IllegalStateException("OpenGL context must be current before createCapabilities!");
                }
                createCapabilities();
                System.out.println(
                        "[SIZE] window = " + windowWidth + " x " + windowHeight +
                                " | framebuffer = " + frameBufferWidth + " x " + frameBufferHeight +
                                " | scale = " + contentScaleX + ", " + contentScaleY
                );
                viewport(0, 0, frameBufferWidth, frameBufferHeight);
                break;
            case LIB_GDX:
            case VULKAN:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + renderingType);
        }
    }

    private void glfwWindowHints() {
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);
        glfwWindowHint(GLFW_CENTER_CURSOR, GLFW_FALSE);
        glfwWindowHint(GLFW_FOCUS_ON_SHOW, GLFW_TRUE);

        frameSettingManager.updateFrameSettings(true);
    }

    private void mainLoop() {
        long lastTime2 = TimeSourceUtil.getNanoTime(timeSourceType);
        double deltaTime2 = 0;
        double targetTime = 1_000_000_000.0 / (int) getFrameSettingValue(RefreshRateSetting.class);
        int frames2 = 0;
        long fpsLsatTime = TimeSourceUtil.getNanoTime(timeSourceType);

        startTime = TimeSourceUtil.getTimeMills(timeSourceType);

        setAntiAliasing();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        while (!glfwWindowShouldClose(windowID)) {
            currentTime = TimeSourceUtil.getTimeMills(timeSourceType);

            long currentTime2 = TimeSourceUtil.getNanoTime(timeSourceType);
            deltaTime2 += currentTime2 - lastTime2;
            lastTime2 = currentTime2;

            if (deltaTime2 >= targetTime) {
//                begin(renderPass);
                glClear(OPEN_GL_PARAMETER_NAME_MAP.get("GL_COLOR_BUFFER_BIT") | OPEN_GL_PARAMETER_NAME_MAP.get("GL_DEPTH_BUFFER_BIT"));

                draw();

//                end(renderPass);

                glfwSwapBuffers(windowID);
                glfwPollEvents();

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
                long sleepNanos = (long) (sleepTime * 1_000_000_000L);
                LockSupport.parkNanos(sleepNanos);
            }

            lastTime = TimeSourceUtil.getTimeMills(timeSourceType);

            if (shouldCleanup) {
                stopFrame();
            }
        }

        finishTime = TimeSourceUtil.getTimeMills(timeSourceType);

        finished();
    }

    @EventHandler
    public void windowSizeEvent(WindowSizeEvent event) {
        windowWidth = event.getWidth();
        windowHeight = event.getHeight();

        updateLFJGLContext(frameBufferWidth, frameBufferHeight, event.getWidth(), event.getHeight());
    }

    @EventHandler
    public void framebufferSizeEvent(FramebufferSizeEvent event) {
        frameBufferWidth = event.getWidth();
        frameBufferHeight = event.getHeight();

        viewport(0, 0, frameBufferWidth, frameBufferHeight);
        updateLFJGLContext(frameBufferWidth, frameBufferHeight, windowWidth, windowHeight);
    }

    private void finished() {
        breakFrame();
        lfjgFrame.stopFrame();
        cleanupManager();

        DebugLog.debug(getClass(), "Finished");
    }

    private void cleanupManager() {
        frameSettingManager.cleanup();
    }

    private void draw() {
        switch (renderingType) {
            case OPEN_GL:
                viewport(0, 0, frameBufferWidth, frameBufferHeight);
                lfjgFrame.drawFrame();
                break;
            case LIB_GDX:
            case VULKAN:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + renderingType);
        }
    }

    public void stopFrame() {
        glfwSetWindowShouldClose(windowID, true);
    }

    private void setAntiAliasing() {
        switch (((AntiAliasingType) getFrameSettingValue(AntiAliasingSetting.class))) {
            case MSAA:
                enable(OPEN_GL_PARAMETER_NAME_MAP.get("GL_MULTISAMPLE"));
                break;
            case OFF:
                disable(OPEN_GL_PARAMETER_NAME_MAP.get("GL_MULTISAMPLE"));
                break;
        }
    }

    private void breakFrame() {
        Callbacks.glfwFreeCallbacks(windowID);

        switch (renderingType) {
            case OPEN_GL:
                break;
            case VULKAN:
                break;
            case LIB_GDX:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + renderingType);
        }

        glfwDestroyWindow(windowID);

        glfwTerminate();

        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

    public long getWin32Window() {
        return GLFWNativeWin32.glfwGetWin32Window(windowID);
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

    public LFJGFrame getLfjgFrame() {
        return lfjgFrame;
    }

    public String getThreadName() {
        return threadName;
    }

    public boolean isShouldCleanup() {
        return shouldCleanup;
    }

    public void setShouldCleanup(boolean shouldCleanup) {
        this.shouldCleanup = shouldCleanup;
    }

    public long getWindowID() {
        return windowID;
    }

    public void setWindowID(long windowID) {
        this.windowID = windowID;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getFrameBufferWidth() {
        return frameBufferWidth;
    }

    public void setFrameBufferWidth(int frameBufferWidth) {
        this.frameBufferWidth = frameBufferWidth;
    }

    public int getFrameBufferHeight() {
        return frameBufferHeight;
    }

    public void setFrameBufferHeight(int frameBufferHeight) {
        this.frameBufferHeight = frameBufferHeight;
    }

    public float getContentScaleX() {
        return contentScaleX;
    }

    public void setContentScaleX(float contentScaleX) {
        this.contentScaleX = contentScaleX;
    }

    public float getContentScaleY() {
        return contentScaleY;
    }

    public void setContentScaleY(float contentScaleY) {
        this.contentScaleY = contentScaleY;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }
}
