package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.util.DisplayUtil;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 17)
public class PosYSetting extends FrameSettingBase<Integer> {
    public PosYSetting(Frame frame) {
        super(frame, "PosYSetting",17, 0);
    }

    @Override
    public void updateSetting() {
        if(getFrame().getWindowID() != -1L){
            GLFW.glfwSetWindowPos(getFrame().getWindowID(), getFrame().getFrameSettingValue(PosXSetting.class),getValue());
        }

        super.updateSetting();
    }
}
