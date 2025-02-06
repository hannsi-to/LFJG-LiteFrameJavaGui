package me.hannsi.lfjg.utils.graphics;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.MonitorType;
import org.joml.Vector2f;
import org.joml.Vector2i;
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

public class GLFWUtil {
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

    public static void windowHintBoolean(int hint, boolean value) {
        windowHintValue(hint, (value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE));
    }

    public static void windowHintValue(int hint, int value) {
        GLFW.glfwWindowHint(hint, value);
    }

    public static void setWindowIcon(long windowID, ResourcesLocation resourcesLocation) {
        GLFW.glfwSetWindowIcon(windowID, loadIconImageBuffer(resourcesLocation));
    }

    public static void setWindowIcon(long windowID, GLFWImage.Buffer iconBuffer) {
        GLFW.glfwSetWindowIcon(windowID, iconBuffer);
    }

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

    public static Vector2i getWindowSize(long windowId) {
        int[] width = new int[1];
        int[] height = new int[1];
        GLFW.glfwGetWindowSize(windowId, width, height);

        return new Vector2i(width[0], height[0]);
    }

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
}
