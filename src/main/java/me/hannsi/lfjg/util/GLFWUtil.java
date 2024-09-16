package me.hannsi.lfjg.util;

import me.hannsi.lfjg.util.type.types.MonitorType;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
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
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer image = ImageLoader.loadImage(resourcesLocation, width, height, channels);

        int w = width.get(0);
        int h = height.get(0);
        int stride = w * 4;

        ByteBuffer flippedImage = BufferUtils.createByteBuffer(image.capacity());
        for (int y = 0; y < h; y++) {
            int srcPos = (h - 1 - y) * stride;
            int destPos = y * stride;
            byte[] row = new byte[stride];
            image.position(srcPos);
            image.get(row);
            flippedImage.position(destPos);
            flippedImage.put(row);
        }
        flippedImage.position(0);

        GLFWImage glfwImage = GLFWImage.malloc();
        glfwImage.width(w);
        glfwImage.height(h);
        glfwImage.pixels(flippedImage);

        GLFWImage.Buffer buffer = GLFWImage.malloc(1);
        buffer.put(0, glfwImage);

        STBImage.stbi_image_free(image);

        return buffer;
    }

    public static Vec2i getMonitorCenter(long windowId) {
        int cx;
        int cy;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(windowId, pWidth, pHeight);
            GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

            cx = (Objects.requireNonNull(videoMode).width() - pWidth.get(0)) / 2;
            cy = (videoMode.height() - pHeight.get(0)) / 2;

            return new Vec2i(cx, cy);
        }
    }

    public static long getMonitorTypeCode(MonitorType monitorType) {
        long monitor;

        switch (monitorType) {
            case Window -> monitor = MemoryUtil.NULL;
            case FullScreen -> monitor = GLFW.glfwGetPrimaryMonitor();
            case Borderless -> {
                GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
                monitor = GLFW.glfwGetPrimaryMonitor();
            }
            default -> throw new IllegalStateException("Unexpected value: " + monitorType);
        }

        return monitor;
    }
}
