package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.util.GLFWUtil;
import me.hannsi.lfjg.util.ResourcesLocation;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 8)
public class IconSetting extends FrameSettingBase<ResourcesLocation> {
    public IconSetting(Frame frame) {
        super(frame, "IconSetting",8,null);
    }

    @Override
    public void updateSetting() {
        if (getFrame().getWindowID() != -1L) {
            if (getValue() == null) {
                GLFW.glfwSetWindowIcon(getFrame().getWindowID(), null);
            } else {
                GLFWUtil.setWindowIcon(getFrame().getWindowID(), getValue());
            }
        }
    }
}
