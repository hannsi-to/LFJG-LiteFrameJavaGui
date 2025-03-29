package me.hannsi.lfjg.utils.graphics;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.MonitorType;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

/**
 * Utility class for various GLFW operations.
 */
public class GLFWUtil {

    /**
     * Gets the GLFW video mode for the specified window.
     *
     * @param windowId the ID of the window
     * @return the GLFW video mode
     */
    public static GLFWVidMode getGLFWVidMode(long windowId) {
        GLFWVidMode videoMode;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(windowId, pWidth, pHeight);
            videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        }

        return videoMode;
    }

    /**
     * Sets a GLFW window hint with a boolean value.
     *
     * @param hint the hint to set
     * @param value the boolean value of the hint
     */
    public static void windowHintBoolean(int hint, boolean value) {
        windowHintValue(hint, (value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE));
    }

    /**
     * Sets a GLFW window hint with an integer value.
     *
     * @param hint the hint to set
     * @param value the integer value of the hint
     */
    public static void windowHintValue(int hint, int value) {
        GLFW.glfwWindowHint(hint, value);
    }

    /**
     * Sets the window icon using a resource location.
     *
     * @param windowID the ID of the window
     * @param resourcesLocation the location of the icon resource
     */
    public static void setWindowIcon(long windowID, ResourcesLocation resourcesLocation) {
        GLFW.glfwSetWindowIcon(windowID, loadIconImageBuffer(resourcesLocation));
    }

    /**
     * Sets the window icon using a GLFWImage buffer.
     *
     * @param windowID the ID of the window
     * @param iconBuffer the GLFWImage buffer containing the icon
     */
    public static void setWindowIcon(long windowID, GLFWImage.Buffer iconBuffer) {
        GLFW.glfwSetWindowIcon(windowID, iconBuffer);
    }

    /**
     * Loads an icon image buffer from a resource location.
     *
     * @param resourcesLocation the location of the icon resource
     * @return the GLFWImage buffer containing the icon
     */
    public static GLFWImage.Buffer loadIconImageBuffer(ResourcesLocation resourcesLocation) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer image = STBImage.stbi_load(resourcesLocation.getPath(), width, height, channels, 4);
            if (image == null) {
                throw new RuntimeException("Failed to load icon image: " + resourcesLocation.getPath());
            }

            GLFWImage.Buffer iconBuffer = GLFWImage.malloc(1);
            GLFWImage icon = iconBuffer.get(0);
            icon.set(width.get(0), height.get(0), image);

            STBImage.stbi_image_free(image);

            return iconBuffer;
        }
    }

    /**
     * Gets the window size as a Vector2i.
     *
     * @param windowId the ID of the window
     * @return the window size as a Vector2i
     */
    public static Vector2i getWindowSize(long windowId) {
        int[] width = new int[1];
        int[] height = new int[1];
        GLFW.glfwGetWindowSize(windowId, width, height);

        return new Vector2i(width[0], height[0]);
    }

    /**
     * Gets the center position of the monitor for the specified window.
     *
     * @param windowId the ID of the window
     * @return the center position of the monitor as a Vector2i
     */
    public static Vector2i getMonitorCenter(long windowId) {
        int cx;
        int cy;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(windowId, pWidth, pHeight);
            GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

            cx = (Objects.requireNonNull(videoMode).width() - pWidth.get(0)) / 2;
            cy = (videoMode.height() - pHeight.get(0)) / 2;

            return new Vector2i(cx, cy);
        }
    }

    /**
     * Gets the monitor type code for the specified monitor type.
     *
     * @param monitorType the monitor type
     * @return the monitor type code
     */
    public static long getMonitorTypeCode(MonitorType monitorType) {
        long monitor;

        switch (monitorType) {
            case Window -> monitor = MemoryUtil.NULL;
            case FullScreen -> monitor = GLFW.glfwGetPrimaryMonitor();
            case Borderless -> {
                GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
                monitor = MemoryUtil.NULL;
            }
            default -> throw new IllegalStateException("Unexpected value: " + monitorType);
        }

        return monitor;
    }

    /**
     * Gets the content scale of the specified frame.
     *
     * @param frame the frame
     * @return the content scale as a Vector2f
     */
    public static Vector2f getContentScale(Frame frame) {
        Vector2f contentScale;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer width = stack.mallocFloat(1);
            FloatBuffer height = stack.mallocFloat(1);

            GLFW.glfwGetWindowContentScale(frame.getWindowID(), width, height);

            contentScale = new Vector2f(width.get(0), height.get(0));
        }

        return contentScale;
    }

    public static Vector2i getFrameBufferSize(long windowId) {
        int[] width = new int[1];
        int[] height = new int[1];
        GLFW.glfwGetFramebufferSize(windowId, width, height);

        return new Vector2i(width[0], height[0]);
    }

    public static void maximizeWindow(long windowId) {
        GLFW.glfwMaximizeWindow(windowId);
    }

    public static void minimizeWindow(long windowId) {
        GLFW.glfwIconifyWindow(windowId);
    }

    public static void restoreWindow(long windowId) {
        GLFW.glfwRestoreWindow(windowId);
    }

    public static void hideCursor(long windowId) {
        GLFW.glfwSetInputMode(windowId, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
    }

    public static void showCursor(long windowId) {
        GLFW.glfwSetInputMode(windowId, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
    }

    public static void lockCursor(long windowId) {
        GLFW.glfwSetInputMode(windowId, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
    }

    public static void setWindowResizable(boolean resizable) {
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public static Vector2f getDpiScale(long windowId) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer xScale = stack.mallocFloat(1);
            FloatBuffer yScale = stack.mallocFloat(1);
            GLFW.glfwGetWindowContentScale(windowId, xScale, yScale);
            return new Vector2f(xScale.get(0), yScale.get(0));
        }
    }

    public static void setWindowTitle(long windowId, String title) {
        GLFW.glfwSetWindowTitle(windowId, title);
    }

    public static void setWindowOpacity(long windowId, float opacity) {
        GLFW.glfwSetWindowOpacity(windowId, opacity);
    }

    public static void setWindowVisible(boolean value) {
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public static Vector2i getWindowPosition(long windowId) {
        int[] x = new int[1];
        int[] y = new int[1];
        GLFW.glfwGetWindowPos(windowId, x, y);
        return new Vector2i(x[0], y[0]);
    }

    public static void setWindowPosition(long windowId, int x, int y) {
        GLFW.glfwSetWindowPos(windowId, x, y);
    }

    public static void focusWindow(long windowId) {
        GLFW.glfwFocusWindow(windowId);
    }

    public static boolean isWindowFocused(long windowId) {
        return GLFW.glfwGetWindowAttrib(windowId, GLFW.GLFW_FOCUSED) == GLFW.GLFW_TRUE;
    }

    public static long getMonitorForWindow(long windowId) {
        int[] xPos = new int[1];
        int[] yPos = new int[1];
        GLFW.glfwGetWindowPos(windowId, xPos, yPos);

        int monitorCount;
        PointerBuffer monitors = GLFW.glfwGetMonitors();
        assert monitors != null;
        monitorCount = monitors.remaining();

        long bestMonitor = MemoryUtil.NULL;
        int bestOverlap = 0;

        for (int i = 0; i < monitorCount; i++) {
            long monitor = monitors.get(i);
            GLFWVidMode videoMode = GLFW.glfwGetVideoMode(monitor);
            int[] mx = new int[1];
            int[] my = new int[1];
            GLFW.glfwGetMonitorPos(monitor, mx, my);

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
        return GLFW.glfwWindowShouldClose(windowId);
    }

    public static void setAlwaysOnTop(long windowId, boolean alwaysOnTop) {
        GLFW.glfwSetWindowAttrib(windowId, GLFW.GLFW_FLOATING, alwaysOnTop ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public static void setMinWindowSize(long windowId, int minWidth, int minHeight) {
        GLFW.glfwSetWindowSizeLimits(windowId, minWidth, minHeight, GLFW.GLFW_DONT_CARE, GLFW.GLFW_DONT_CARE);
    }

    public static void setMaxWindowSize(long windowId, int maxWidth, int maxHeight) {
        GLFW.glfwSetWindowSizeLimits(windowId, GLFW.GLFW_DONT_CARE, GLFW.GLFW_DONT_CARE, maxWidth, maxHeight);
    }

    public static void setInputEnabled(long windowId, boolean enabled) {
        GLFW.glfwSetInputMode(windowId, GLFW.GLFW_CURSOR, enabled ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_DISABLED);
    }
}