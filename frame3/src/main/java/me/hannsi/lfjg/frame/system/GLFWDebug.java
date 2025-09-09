package me.hannsi.lfjg.frame.system;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.GLFWDebugSetting;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;

public class GLFWDebug {
    public static void getGLFWDebug(Frame frame) {
        if (frame.getFrameSettingValue(GLFWDebugSetting.class)) {
            GLFWErrorCallback errorCallback = new GLFWErrorCallback() {
                @Override
                public void invoke(int error, long description) {
                    String errorMessage = getError(error);

                    new LogGenerator(
                            "GLFW Error Message",
                            "Source: GLFW",
                            "Type: Error",
                            "ID: " + Integer.toHexString(error),
                            "Severity: Error",
                            "Message: " + errorMessage
                    ).logging(DebugLevel.ERROR);
                }
            };
            glfwSetErrorCallback(errorCallback);
        }
    }

    public static String getError(int error) {
        switch (error) {
            case org.lwjgl.glfw.GLFW.GLFW_NOT_INITIALIZED:
                return "GLFW has not been initialized. Call glfwInit() before using other GLFW functions.";
            case org.lwjgl.glfw.GLFW.GLFW_NO_CURRENT_CONTEXT:
                return "No OpenGL or OpenGL ES context is current for this thread.";
            case org.lwjgl.glfw.GLFW.GLFW_INVALID_ENUM:
                return "An invalid enum value was passed to a GLFW function.";
            case org.lwjgl.glfw.GLFW.GLFW_INVALID_VALUE:
                return "An invalid value was passed to a GLFW function.";
            case org.lwjgl.glfw.GLFW.GLFW_OUT_OF_MEMORY:
                return "A memory allocation failed.";
            case org.lwjgl.glfw.GLFW.GLFW_API_UNAVAILABLE:
                return "The requested API is not available on the system.";
            case org.lwjgl.glfw.GLFW.GLFW_VERSION_UNAVAILABLE:
                return "The requested OpenGL or OpenGL ES version is not available.";
            case org.lwjgl.glfw.GLFW.GLFW_PLATFORM_ERROR:
                return "A platform-specific error occurred. See the error description for details.";
            case org.lwjgl.glfw.GLFW.GLFW_FORMAT_UNAVAILABLE:
                return "The requested pixel format is not supported.";
            case org.lwjgl.glfw.GLFW.GLFW_NO_WINDOW_CONTEXT:
                return "The specified window does not have an OpenGL or OpenGL ES context.";
            case org.lwjgl.glfw.GLFW.GLFW_CURSOR_UNAVAILABLE:
                return "The requested cursor functionality is not available on this platform.";
            case org.lwjgl.glfw.GLFW.GLFW_FEATURE_UNIMPLEMENTED:
                return "The requested feature is not implemented.";
            case org.lwjgl.glfw.GLFW.GLFW_FEATURE_UNAVAILABLE:
                return "The requested feature is not available in the current context or configuration.";
            case org.lwjgl.glfw.GLFW.GLFW_PLATFORM_UNAVAILABLE:
                return "A platform-specific platform function is not available (e.g., initialization failure).";
            default:
                return "Unknown error code: 0x" + Integer.toHexString(error);
        }
    }
}
