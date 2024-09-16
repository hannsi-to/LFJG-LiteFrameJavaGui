package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 1)
public class ResizableSetting extends FrameSettingBase<Boolean> {
    public ResizableSetting(Frame frame) {
        super(frame, "ResizableSetting",1,true,true);
    }

    @Override
    public void updateSetting() {
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, getValue() ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);

        super.updateSetting();
    }
}
