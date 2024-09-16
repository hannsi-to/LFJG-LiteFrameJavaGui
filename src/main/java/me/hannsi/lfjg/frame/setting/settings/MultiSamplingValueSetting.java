package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 10)
public class MultiSamplingValueSetting extends FrameSettingBase<Integer> {
    public MultiSamplingValueSetting(Frame frame) {
        super(frame, "MultiSamplingValueSetting", 10, 64, true);
    }

    @Override
    public void updateSetting() {
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, getValue());
    }
}
