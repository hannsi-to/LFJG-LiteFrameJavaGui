package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.util.GLFWUtil;
import me.hannsi.lfjg.util.Vec2i;
import me.hannsi.lfjg.util.type.types.MonitorType;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Objects;

@ReflectionsLevel(level = 5)
public class MonitorSetting extends FrameSettingBase<MonitorType> {
    public MonitorSetting(Frame frame) {
        super(frame, "MonitorSetting", 5, MonitorType.Window, true);
    }

    @Override
    public void updateSetting() {
        if (getFrame().getWindowID() != -1L) {
            Vec2i center = GLFWUtil.getMonitorCenter(getFrame().getWindowID());
            GLFW.glfwSetWindowMonitor(getFrame().getWindowID(), GLFWUtil.getMonitorTypeCode(getValue()), center.getX(), center.getY(), getFrame().getFrameSettingValue(WidthSetting.class), getFrame().getFrameSettingValue(HeightSetting.class), getFrame().getFrameSettingValue(RefreshRateSetting.class));

            if (getValue() == MonitorType.Window) {
                try (MemoryStack stack = MemoryStack.stackPush()) {
                    IntBuffer pWidth = stack.mallocInt(1);
                    IntBuffer pHeight = stack.mallocInt(1);

                    GLFW.glfwGetWindowSize(getFrame().getWindowID(), pWidth, pHeight);
                    GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

                    GLFW.glfwSetWindowPos(getFrame().getWindowID(), (Objects.requireNonNull(videoMode).width() - pWidth.get(0)) / 2, (videoMode.height() - pHeight.get(0)) / 2);
                }
            }
        }

        if (getValue() == MonitorType.FullScreen) {
            GLFW.glfwWindowHint(GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_TRUE);
        }
        if (getValue() == MonitorType.Borderless) {
            GLFW.glfwWindowHint(GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);
        }
    }
}