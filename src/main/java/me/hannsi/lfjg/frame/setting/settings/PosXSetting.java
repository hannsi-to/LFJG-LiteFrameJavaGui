package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.util.DisplayUtil;
import me.hannsi.lfjg.util.GLFWUtil;
import me.hannsi.lfjg.util.Vec2i;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 16)
public class PosXSetting extends FrameSettingBase<Integer> {
    public PosXSetting(Frame frame) {
        super(frame, "PosXSetting",16, 0);
    }

    @Override
    public void updateSetting() {
        if(getFrame().getWindowID() != -1L){
            GLFW.glfwSetWindowPos(getFrame().getWindowID(), getValue(), getFrame().getFrameSettingValue(PosYSetting.class));
        }

        super.updateSetting();
    }
}
