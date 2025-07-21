package me.hannsi.lfjg.frame;

import lombok.Data;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.manager.WorkspaceManager;
import me.hannsi.lfjg.core.utils.graphics.GLFWUtil;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.math.Projection;
import me.hannsi.lfjg.core.utils.time.TimeCalculator;
import me.hannsi.lfjg.core.utils.time.TimeSourceUtil;
import me.hannsi.lfjg.core.utils.toolkit.ANSIFormat;
import me.hannsi.lfjg.core.utils.toolkit.RuntimeUtil;
import me.hannsi.lfjg.core.utils.type.types.AntiAliasingType;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.core.utils.type.types.RenderingType;
import me.hannsi.lfjg.core.utils.type.types.VSyncType;
import me.hannsi.lfjg.frame.event.events.render.DrawFrameWithOpenGLEvent;
import me.hannsi.lfjg.frame.event.system.EventHandler;
import me.hannsi.lfjg.frame.event.system.GLFWCallback;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.system.GLFWDebug;
import me.hannsi.lfjg.frame.system.IFrame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

/**
 * The Frame class is responsible for creating and managing the main application window, handling rendering, and managing frame settings.
 */
@Data
public class Frame implements IFrame {
    private final LFJGFrame lfjgFrame;
    private String threadName;
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

    /**
     * Constructs a Frame object with the specified LFJGFrame and thread name.
     *
     * @param lfjgFrame  The LFJGFrame associated with this Frame.
     * @param threadName The name of the thread to create for the frame.
     */
    public Frame(LFJGFrame lfjgFrame, String threadName) {
        this.lfjgFrame = lfjgFrame;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            shouldCleanup = true;
            GLFW.glfwPostEmptyEvent();
        }));

        this.threadName = threadName;
        new Thread(this::createFrame, threadName).start();
    }

    /**
     * Creates the frame, initializes GLFW, sets up rendering, and starts the main loop.
     */
    public void createFrame() {
        workspaceManager.createDirectories();
        workspaceManager.copyResourcesToWorkspace();

        registerManagers();

        lfjgFrame.setFrameSetting();

        initGLFW();
        initRendering();
        updateViewport();

        frameSettingManager.updateFrameSettings(false);

        GLFWCallback glfwCallback = new GLFWCallback(this);
        glfwCallback.glfwInvoke();

        lfjgFrame.init();
        eventManager.register(this);

        mainLoop();
    }

    /**
     * Initializes GLFW, creates the window, and sets up the context.
     */
    private void initGLFW() {
        GLFWDebug.getGLFWDebug(this);

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwWindowHints();

        windowID = GLFW.glfwCreateWindow(getFrameSettingValue(WidthSetting.class), getFrameSettingValue(HeightSetting.class), getFrameSettingValue(TitleSetting.class).toString(), GLFWUtil.getMonitorTypeCode(getFrameSettingValue(MonitorSetting.class)), MemoryUtil.NULL);
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

        GLFW.glfwMakeContextCurrent(windowID);
        GLFW.glfwSwapInterval(((VSyncType) getFrameSettingValue(VSyncSetting.class)).getId());
        GLFW.glfwShowWindow(windowID);
    }

    /**
     * Registers the managers required for the frame.
     */
    private void registerManagers() {
        DebugLog.debug(getClass(), "Managers loading...\n");
        long tookTime = TimeCalculator.calculateMillis(() -> {
            frameSettingManager.setFrame(this);
            frameSettingManager.loadFrameSettings();
        });
        DebugLog.debug(getClass(), ANSIFormat.GREEN + "Managers took " + tookTime + "ms to load!\n");
    }

    /**
     * Initializes the rendering context based on the selected rendering type.
     */
    private void initRendering() {
        switch ((RenderingType) getFrameSettingValue(RenderingTypeSetting.class)) {
            case OPEN_GL -> {
                GL.createCapabilities();
                LFJGContext.nanoVGContext = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS);
                if (LFJGContext.nanoVGContext == MemoryUtil.NULL) {
                    throw new RuntimeException("Failed to create NanoVG context");
                }
            }
            case NANO_VG, LIB_GDX -> {
            }
            case VULKAN -> {

            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + getFrameSettingValue(RenderingTypeSetting.class));
        }
    }

    /**
     * Sets GLFW window hints based on the frame settings.
     */
    private void glfwWindowHints() {
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_CENTER_CURSOR, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_FOCUS_ON_SHOW, GLFW.GLFW_TRUE);

        frameSettingManager.updateFrameSettings(true);
    }

    /**
     * The main loop of the frame, responsible for rendering and handling events.
     */
    private void mainLoop() {
        long lastTime2 = TimeSourceUtil.getNanoTime(getFrameSettingValue(TimeSourceSetting.class));
        double deltaTime2 = 0;
        double targetTime = 1_000_000_000.0 / (int) getFrameSettingValue(RefreshRateSetting.class);
        int frames2 = 0;
        long fpsLsatTime = TimeSourceUtil.getNanoTime(getFrameSettingValue(TimeSourceSetting.class));

        startTime = TimeSourceUtil.getTimeMills(getFrameSettingValue(TimeSourceSetting.class));

        setAntiAliasing();
        while (!GLFW.glfwWindowShouldClose(windowID)) {
            currentTime = TimeSourceUtil.getTimeMills(getFrameSettingValue(TimeSourceSetting.class));

            long currentTime2 = TimeSourceUtil.getNanoTime(getFrameSettingValue(TimeSourceSetting.class));
            deltaTime2 += currentTime2 - lastTime2;
            lastTime2 = currentTime2;

            if (deltaTime2 >= targetTime) {
                updateViewport();
                updateLFJGLContext();
                glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                draw();

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
                    DebugLog.warning(Frame.class, e);
                }
            }

            lastTime = TimeSourceUtil.getTimeMills(getFrameSettingValue(TimeSourceSetting.class));

            if (shouldCleanup) {
                stopFrame();
            }
        }

        finishTime = TimeSourceUtil.getTimeMills(getFrameSettingValue(TimeSourceSetting.class));

        finished();
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

    public void updateLFJGLContext() {
        LFJGContext.frameBufferSize = new Vector2i(getFrameBufferWidth(), getFrameBufferHeight());
        LFJGContext.windowSize = new Vector2i(getWindowWidth(), getWindowHeight());
        float devicePixelRatioX = (float) frameBufferWidth / windowWidth;
        float devicePixelRatioY = (float) frameBufferHeight / windowHeight;
        LFJGContext.devicePixelRatio = MathHelper.max(devicePixelRatioX, devicePixelRatioY);

        if (LFJGContext.projection2D == null) {
            LFJGContext.projection2D = new Projection(ProjectionType.ORTHOGRAPHIC_PROJECTION, getFrameBufferWidth(), getFrameBufferHeight());
        } else {
            LFJGContext.projection2D.updateProjMatrix(Projection.DEFAULT_FOV, getFrameBufferWidth(), getFrameBufferHeight(), Projection.DEFAULT_Z_FAR, Projection.DEFAULT_Z_NEAR);
        }

        if (LFJGContext.projection3D == null) {
            LFJGContext.projection3D = new Projection(ProjectionType.PERSPECTIVE_PROJECTION, getFrameBufferWidth(), getFrameBufferHeight());
        } else {
            LFJGContext.projection3D.updateProjMatrix(Projection.DEFAULT_FOV, getFrameBufferWidth(), getFrameBufferHeight(), Projection.DEFAULT_Z_FAR, Projection.DEFAULT_Z_NEAR);
        }
    }

    /**
     * Draws the frame based on the selected rendering type.
     */
    private void draw() {
        switch ((RenderingType) getFrameSettingValue(RenderingTypeSetting.class)) {
            case OPEN_GL -> eventManager.call(new DrawFrameWithOpenGLEvent());
            case NANO_VG, LIB_GDX, VULKAN -> {
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + getFrameSettingValue(RenderingTypeSetting.class));
        }
    }

    /**
     * Event handler for drawing a frame with OpenGL.
     *
     * @param event The DrawFrameWithOpenGLEvent.
     */
    @EventHandler
    public void drawFrameWidthOpenGLEvent(DrawFrameWithOpenGLEvent event) {
        lfjgFrame.drawFrame();
    }

    /**
     * Stops the frame by setting the window should close flag.
     */
    public void stopFrame() {
        GLFW.glfwSetWindowShouldClose(windowID, true);
    }

    /**
     * Sets the anti-aliasing mode based on the frame settings.
     */
    private void setAntiAliasing() {
        switch (((AntiAliasingType) getFrameSettingValue(AntiAliasingSetting.class))) {
            case MSAA -> glEnable(GL_MULTISAMPLE);
            case OFF -> glDisable(GL_MULTISAMPLE);
        }
    }

    /**
     * Cleans up resources and terminates GLFW.
     */
    private void breakFrame() {
        Callbacks.glfwFreeCallbacks(windowID);

        switch ((RenderingType) getFrameSettingValue(RenderingTypeSetting.class)) {
            case OPEN_GL -> NanoVGGL3.nvgDelete(LFJGContext.nanoVGContext);
            case VULKAN -> {

            }
            case LIB_GDX -> {
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

    /**
     * Updates the viewport based on the window size and content scale.
     */
    public void updateViewport() {
        glViewport(0, 0, frameBufferWidth, frameBufferHeight);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, frameBufferWidth / contentScaleX, 0, frameBufferHeight / contentScaleY, -1, 1);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    public long getWin32Window() {
        return GLFWNativeWin32.glfwGetWin32Window(windowID);
    }

    /**
     * Retrieves the maximum memory available to the JVM.
     *
     * @return The maximum memory in MB.
     */
    public String getMaxMemory() {
        return "MaxMemory: " + Math.round(RuntimeUtil.getMaxMemoryMB()) + "MB";
    }

    /**
     * Retrieves the allocated memory in the JVM.
     *
     * @return The allocated memory in MB.
     */
    public String getAllocatedMemory() {
        return "AllocatedMemory: " + Math.round(RuntimeUtil.getAllocatedMemoryMB()) + "MB";
    }

    /**
     * Retrieves the free memory in the JVM.
     *
     * @return The free memory in MB.
     */
    public String getFreeMemory() {
        return "FreeMemory: " + Math.round(RuntimeUtil.getFreeMemoryMB()) + "MB";
    }

    /**
     * Retrieves the used memory in the JVM.
     *
     * @return The used memory in MB.
     */
    public String getUseMemory() {
        return "UsedMemory: " + Math.round(RuntimeUtil.getUseMemoryMB()) + "MB";
    }

    /**
     * Retrieves the frame setting base for the specified frame setting class.
     *
     * @param frameSettingBase The frame setting class.
     * @param <T>              The type of the frame setting value.
     * @return The frame setting base.
     */
    @SuppressWarnings("unchecked")
    public <T> FrameSettingBase<T> getFrameSettingBase(Class<? extends FrameSettingBase<?>> frameSettingBase) {
        return ((FrameSettingBase<T>) frameSettingManager.getFrameSetting(frameSettingBase));
    }

    /**
     * Retrieves the frame setting value for the specified frame setting class.
     *
     * @param frameSettingBase The frame setting class.
     * @param <T>              The type of the frame setting value.
     * @return The frame setting value.
     */
    @SuppressWarnings("unchecked")
    public <T> T getFrameSettingValue(Class<? extends FrameSettingBase<?>> frameSettingBase) {
        return (T) getFrameSettingBase(frameSettingBase).getValue();
    }

    /**
     * Sets the frame setting value for the specified frame setting class.
     *
     * @param frameSettingBase The frame setting class.
     * @param value            The value to set.
     * @param <T>              The type of the frame setting value.
     */
    public <T> void setFrameSettingValue(Class<? extends FrameSettingBase<?>> frameSettingBase, T value) {
        getFrameSettingBase(frameSettingBase).setValue(value);
    }

    public WorkspaceManager getWorkspaceManager() {
        return workspaceManager;
    }
}
