package me.hannsi.lfjg.frame.glfw;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.GLFWDebugSetting;
import org.lwjgl.glfw.GLFWErrorCallback;

public class GLFWDebug {
    public static void getGLFWDebug(Frame frame) {
        if (frame.getFrameSettingValue(GLFWDebugSetting.class)) {
            GLFWErrorCallback.createPrint(System.err).set();
        }
    }
}
