package me.hannsi.lfjg.frame.system;

import me.hannsi.lfjg.core.utils.Util;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.MonitorType;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;

public class GLFWUtil extends Util {
    public static GLFWVidMode getGLFWVidMode(long windowId) {
        GLFWVidMode videoMode;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(windowId, pWidth, pHeight);
            videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        }

        return videoMode;
    }

    public static void windowHintBoolean(int hint, boolean value) {
        windowHintValue(hint, (value ? GLFW_TRUE : GLFW_FALSE));
    }

    public static void windowHintValue(int hint, int value) {
        glfwWindowHint(hint, value);
    }

    public static void setWindowIcon(long windowID, Location location) {
        GLFWImage.Buffer iconBuffer = loadIconImageBuffer(location);
        glfwSetWindowIcon(windowID, iconBuffer);
        iconBuffer.free();
    }

    public static void setWindowIcon(long windowID, GLFWImage.Buffer iconBuffer) {
        glfwSetWindowIcon(windowID, iconBuffer);
    }

    public static GLFWImage.Buffer loadIconImageBuffer(Location location) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer image = stbi_load_from_memory(location.getByteBuffer(), width, height, channels, 4);
            if (image == null) {
                throw new RuntimeException("Failed to load icon image: " + location.path());
            }

            GLFWImage.Buffer iconBuffer = GLFWImage.malloc(1);
            GLFWImage icon = iconBuffer.get(0);
            icon.set(width.get(0), height.get(0), image);

            stbi_image_free(image);

            return iconBuffer;
        }
    }

    public static Vector2i getWindowSize(long windowId) {
        int[] width = new int[1];
        int[] height = new int[1];
        glfwGetWindowSize(windowId, width, height);

        return new Vector2i(width[0], height[0]);
    }

    public static Vector2i getMonitorCenter(long windowId) {
        int cx;
        int cy;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(windowId, pWidth, pHeight);
            GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            cx = (Objects.requireNonNull(videoMode).width() - pWidth.get(0)) / 2;
            cy = (videoMode.height() - pHeight.get(0)) / 2;

            return new Vector2i(cx, cy);
        }
    }

    public static long getMonitorTypeCode(MonitorType monitorType) {
        long monitor;

        switch (monitorType) {
            case WINDOW:
                monitor = MemoryUtil.NULL;
                break;
            case FULL_SCREEN:
                monitor = glfwGetPrimaryMonitor();
                break;
            case BORDERLESS:
                glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
                monitor = MemoryUtil.NULL;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + monitorType);
        }

        return monitor;
    }

    public static Vector2f getContentScale(Frame frame) {
        Vector2f contentScale;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer width = stack.mallocFloat(1);
            FloatBuffer height = stack.mallocFloat(1);

            glfwGetWindowContentScale(frame.getWindowID(), width, height);

            contentScale = new Vector2f(width.get(0), height.get(0));
        }

        return contentScale;
    }

    public static Vector2i getFrameBufferSize(long windowId) {
        int[] width = new int[1];
        int[] height = new int[1];
        glfwGetFramebufferSize(windowId, width, height);

        return new Vector2i(width[0], height[0]);
    }

    public static void maximizeWindow(long windowId) {
        glfwMaximizeWindow(windowId);
    }

    public static void minimizeWindow(long windowId) {
        glfwIconifyWindow(windowId);
    }

    public static void restoreWindow(long windowId) {
        glfwRestoreWindow(windowId);
    }

    public static void hideCursor(long windowId) {
        glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }

    public static void showCursor(long windowId) {
        glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    public static void lockCursor(long windowId) {
        glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public static void setWindowResizable(boolean resizable) {
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
    }

    public static Vector2f getDpiScale(long windowId) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer xScale = stack.mallocFloat(1);
            FloatBuffer yScale = stack.mallocFloat(1);
            glfwGetWindowContentScale(windowId, xScale, yScale);
            return new Vector2f(xScale.get(0), yScale.get(0));
        }
    }

    public static void setWindowTitle(long windowId, String title) {
        glfwSetWindowTitle(windowId, title);
    }

    public static void setWindowOpacity(long windowId, float opacity) {
        glfwSetWindowOpacity(windowId, opacity);
    }

    public static void setWindowVisible(boolean value) {
        glfwWindowHint(GLFW_VISIBLE, value ? GLFW_TRUE : GLFW_FALSE);
    }

    public static Vector2i getWindowPosition(long windowId) {
        int[] x = new int[1];
        int[] y = new int[1];
        glfwGetWindowPos(windowId, x, y);
        return new Vector2i(x[0], y[0]);
    }

    public static void setWindowPosition(long windowId, int x, int y) {
        glfwSetWindowPos(windowId, x, y);
    }

    public static void focusWindow(long windowId) {
        glfwFocusWindow(windowId);
    }

    public static boolean isWindowFocused(long windowId) {
        return glfwGetWindowAttrib(windowId, GLFW_FOCUSED) == GLFW_TRUE;
    }

    public static long getMonitorForWindow(long windowId) {
        int[] xPos = new int[1];
        int[] yPos = new int[1];
        glfwGetWindowPos(windowId, xPos, yPos);

        int monitorCount;
        PointerBuffer monitors = glfwGetMonitors();
        assert monitors != null;
        monitorCount = monitors.remaining();

        long bestMonitor = MemoryUtil.NULL;
        int bestOverlap = 0;

        for (int i = 0; i < monitorCount; i++) {
            long monitor = monitors.get(i);
            GLFWVidMode videoMode = glfwGetVideoMode(monitor);
            int[] mx = new int[1];
            int[] my = new int[1];
            glfwGetMonitorPos(monitor, mx, my);

            assert videoMode != null;
            int mw = videoMode.width();
            int mh = videoMode.height();

            int overlap = Math.max(0, Math.min(xPos[0] + mw, mx[0] + mw) - Math.max(xPos[0], mx[0])) *
                    Math.max(0, Math.min(yPos[0] + mh, my[0] + mh) - Math.max(yPos[0], my[0]));

            if (overlap > bestOverlap) {
                bestMonitor = monitor;
                bestOverlap = overlap;
            }
        }

        return bestMonitor;
    }

    public static boolean shouldClose(long windowId) {
        return glfwWindowShouldClose(windowId);
    }

    public static void setAlwaysOnTop(long windowId, boolean alwaysOnTop) {
        glfwSetWindowAttrib(windowId, GLFW_FLOATING, alwaysOnTop ? GLFW_TRUE : GLFW_FALSE);
    }

    public static void setMinWindowSize(long windowId, int minWidth, int minHeight) {
        glfwSetWindowSizeLimits(windowId, minWidth, minHeight, GLFW_DONT_CARE, GLFW_DONT_CARE);
    }

    public static void setMaxWindowSize(long windowId, int maxWidth, int maxHeight) {
        glfwSetWindowSizeLimits(windowId, GLFW_DONT_CARE, GLFW_DONT_CARE, maxWidth, maxHeight);
    }

    public static void setInputEnabled(long windowId, boolean enabled) {
        glfwSetInputMode(windowId, GLFW_CURSOR, enabled ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_DISABLED);
    }
}