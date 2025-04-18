package me.hannsi.lfjg.frame.glfw;

import me.hannsi.lfjg.debug.debug.logger.LogGenerator;
import me.hannsi.lfjg.debug.debug.system.DebugLevel;
import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.GLFWDebugSetting;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;

/**
 * The GLFWDebug class is responsible for managing GLFW error callbacks and enabling debug output.
 */
public class GLFWDebug {

    /**
     * Enables GLFW debug output if the GLFWDebugSetting is enabled in the frame settings.
     *
     * @param frame The Frame object associated with this debug output.
     */
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
        return switch (error) {
            case org.lwjgl.glfw.GLFW.GLFW_NOT_INITIALIZED ->
                    "GLFW has not been initialized. Call glfwInit() before using other GLFW functions.";
            case org.lwjgl.glfw.GLFW.GLFW_NO_CURRENT_CONTEXT ->
                    "No OpenGL or OpenGL ES context is current for this thread.";
            case org.lwjgl.glfw.GLFW.GLFW_INVALID_ENUM -> "An invalid enum value was passed to a GLFW function.";
            case org.lwjgl.glfw.GLFW.GLFW_INVALID_VALUE -> "An invalid value was passed to a GLFW function.";
            case org.lwjgl.glfw.GLFW.GLFW_OUT_OF_MEMORY -> "A memory allocation failed.";
            case org.lwjgl.glfw.GLFW.GLFW_API_UNAVAILABLE -> "The requested API is not available on the system.";
            case org.lwjgl.glfw.GLFW.GLFW_VERSION_UNAVAILABLE ->
                    "The requested OpenGL or OpenGL ES version is not available.";
            case org.lwjgl.glfw.GLFW.GLFW_PLATFORM_ERROR ->
                    "A platform-specific error occurred. See the error description for details.";
            case org.lwjgl.glfw.GLFW.GLFW_FORMAT_UNAVAILABLE -> "The requested pixel format is not supported.";
            case org.lwjgl.glfw.GLFW.GLFW_NO_WINDOW_CONTEXT ->
                    "The specified window does not have an OpenGL or OpenGL ES context.";
            case org.lwjgl.glfw.GLFW.GLFW_CURSOR_UNAVAILABLE ->
                    "The requested cursor functionality is not available on this platform.";
            case org.lwjgl.glfw.GLFW.GLFW_FEATURE_UNIMPLEMENTED -> "The requested feature is not implemented.";
            case org.lwjgl.glfw.GLFW.GLFW_FEATURE_UNAVAILABLE ->
                    "The requested feature is not available in the current context or configuration.";
            case org.lwjgl.glfw.GLFW.GLFW_PLATFORM_UNAVAILABLE ->
                    "A platform-specific platform function is not available (e.g., initialization failure).";
            default -> "Unknown error code: 0x" + Integer.toHexString(error);
        };
    }
}
