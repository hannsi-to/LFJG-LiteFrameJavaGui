package me.hannsi.lfjg.frame;

import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.frame.manager.Manager;
import me.hannsi.lfjg.frame.manager.managers.FrameSettingManager;
import me.hannsi.lfjg.frame.manager.managers.LoggerManager;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.util.*;
import me.hannsi.lfjg.util.type.types.AntiAliasingType;
import me.hannsi.lfjg.util.type.types.VSyncType;
import me.hannsi.test.TestGuiFrame;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Frame {
    private final LFJGFrame lfjgFrame;
    private final LoggerManager loggerManager = new LoggerManager(this);
    private FrameSettingManager frameSettingManager;
    private long windowID = -1L;
    private int fps;
    private long currentTime;
    private long lastTime;
    private long startTime;
    private long finishTime;

    public Frame(LFJGFrame lfjgFrame) {
        this.lfjgFrame = lfjgFrame;

        new Thread(this::createFrame).start();
    }

    private void registerManagers() {
        DebugLog.debug(this, "Managers loading...");
        long tookTime = TimeCalculator.calculate(() -> {
            frameSettingManager = new FrameSettingManager(this);
            DebugLog.debug(this, "Loaded FrameSettingManager");
        });
        DebugLog.debug(this, "Managers took " + tookTime + "ms to load!");
    }

    public void createFrame() {
        registerManagers();

        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        lfjgFrame.setFrameSetting();
        glfwWindowHints();

        windowID = GLFW.glfwCreateWindow(getFrameSettingValue(WidthSetting.class), getFrameSettingValue(HeightSetting.class), getFrameSettingValue(TitleSetting.class).toString(), GLFWUtil.getMonitorTypeCode(getFrameSettingValue(MonitorSetting.class)), MemoryUtil.NULL);

        if (windowID == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        GLFW.glfwMakeContextCurrent(windowID);
        GLFW.glfwSwapInterval(((VSyncType) getFrameSettingValue(VSyncSetting.class)).getId());
        GLFW.glfwShowWindow(windowID);

        GL.createCapabilities();

        updateViewport();
        updateFrameSetting(false);
        glfwInvoke();
        drawFrame();
    }

    private void glfwWindowHints(){
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED,GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_CENTER_CURSOR,GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_FOCUS_ON_SHOW,GLFW.GLFW_TRUE);

        updateFrameSetting(true);
    }

    public void updateFrameSetting(boolean windowHint){
        DebugLog.debug(this, "FrameSettings Updating...");
        long tookTime = TimeCalculator.calculate(() -> {
            frameSettingManager.updateFrameSettings(windowHint);
        });
        DebugLog.debug(this, "FrameSettings took " + tookTime + "ms to update!");
    }

    private void glfwInvoke(){
        GLFW.glfwSetWindowFocusCallback(windowID, new GLFWWindowFocusCallbackI() {
            @Override
            public void invoke(long window, boolean focused) {

            }
        });
        GLFW.glfwSetFramebufferSizeCallback(windowID, new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                updateViewport();
            }
        });

        GLFW.glfwSetKeyCallback(windowID, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW.GLFW_PRESS) {
                    lfjgFrame.keyPress(key, scancode, mods,window);
                } else if (action == GLFW.GLFW_RELEASE) {
                    lfjgFrame.keyReleased(key, scancode, mods,window);
                }
            }
        });

        GLFW.glfwSetCursorPosCallback(windowID, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                lfjgFrame.cursorPos(xpos, ypos, window);
            }
        });

        GLFW.glfwSetMouseButtonCallback(windowID, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (action == GLFW.GLFW_PRESS) {
                    lfjgFrame.mouseButtonPress(button, mods,window);
                } else if (action == GLFW.GLFW_RELEASE) {
                    lfjgFrame.mouseButtonReleased(button, mods,window);
                }
            }
        });
    }

    private void drawFrame() {
        lfjgFrame.init();

        long lastTime2 = TimeSourceUtil.getNanoTime(this);
        double deltaTime2 = 0;
        double targetTime = 1_000_000_000.0 / (int)getFrameSettingValue(RefreshRateSetting.class);
        int frames2 = 0;
        long fpsLsatTime = TimeSourceUtil.getNanoTime(this);

        startTime = TimeSourceUtil.getTimeMills(this);

        while (!GLFW.glfwWindowShouldClose(windowID)) {
            currentTime = TimeSourceUtil.getTimeMills(this);

            long currentTime2 = TimeSourceUtil.getNanoTime(this);
            deltaTime2 += currentTime2 - lastTime2;
            lastTime2 = currentTime2;

            if(deltaTime2 >= targetTime){
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
                setAntiAliasing();

                lfjgFrame.drawFrame();

                GLFW.glfwSwapBuffers(windowID);
                GLFW.glfwPollEvents();

                frames2++;

                if(currentTime2 - fpsLsatTime >= 1_000_000_000.0){
                    fps = frames2;
                    frames2 = 0;
                    fpsLsatTime = currentTime2;
                }

                deltaTime2 -= targetTime;
            }

            double sleepTime = (targetTime - deltaTime2) / 1_000_000_000.0;
            if(sleepTime > 0){
                try {
                    Thread.sleep((long) sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            lastTime = TimeSourceUtil.getTimeMills(this);
        }

        finishTime = TimeSourceUtil.getTimeMills(this);

        breakFrame();
    }

    public void stopFrame(){
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
        GLFW.glfwDestroyWindow(windowID);

        GLFW.glfwTerminate();

        GLFWErrorCallback callback = GLFW.glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

    public void updateViewport() {
        int width = getFrameSettingValue(WidthSetting.class);
        int height = getFrameSettingValue(HeightSetting.class);

        GL11.glViewport(0, 0, width, height);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, 0, height, -1, 1);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
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
}
