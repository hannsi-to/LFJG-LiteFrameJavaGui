package me.hannsi.lfjg.frame.setting.settings;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.util.type.types.VSyncType;
import org.lwjgl.glfw.GLFW;

@ReflectionsLevel(level = 7)
public class VSyncSetting extends FrameSettingBase<VSyncType> {
    public VSyncSetting(Frame frame) {
        super(frame, "VSyncSetting", 7, VSyncType.VSyncOff);
    }

    @Override
    public void updateSetting() {
        if(getFrame().getWindowID() != -1L){
            GLFW.glfwSwapInterval(getValue().getId());
        }
    }
}
