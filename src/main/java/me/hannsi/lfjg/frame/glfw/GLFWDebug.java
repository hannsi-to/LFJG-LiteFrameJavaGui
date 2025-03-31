package me.hannsi.lfjg.frame.glfw;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.GLFWDebugSetting;
import org.lwjgl.glfw.GLFWErrorCallback;

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
            GLFWErrorCallback.createPrint(System.err).set();
        }
    }
}
