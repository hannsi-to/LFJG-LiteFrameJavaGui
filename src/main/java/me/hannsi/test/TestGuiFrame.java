package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.LFJGFrame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.render.nanoVG.renderers.polygon.NanoVGRect;
import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.MonitorType;
import me.hannsi.lfjg.utils.type.types.RenderingType;
import me.hannsi.lfjg.utils.type.types.VSyncType;

public class TestGuiFrame implements LFJGFrame {
    private Frame frame;

    public static void main(String[] args) {
        new TestGuiFrame().setFrame();
    }

    public void setFrame() {
        frame = new Frame(this, "TestGuiFrame");
    }

    @Override
    public void init() {
    }

    //@Override
    //public void drawFrameWithOpenGL() {
    //    RoundedRect roundedRect = new RoundedRect(frame);
    //    roundedRect.roundedRectOutLine(0f, 0f, 500f, 500f, 1f, true, true, true, true, 10f, 10f, 10f, 10f, 5, ColorUtil.getRainbow(1, 0, 255, 255), ColorUtil.getRainbow(1, 0.25f, 255, 255), ColorUtil.getRainbow(1, 0.5f, 255, 255), ColorUtil.getRainbow(1, 0.75f, 255, 255));
    //}

    @Override
    public void drawFrameWithNanoVG(long nvg) {
        NanoVGRect nanoVGRect = new NanoVGRect(nvg);
        nanoVGRect.setSize(0, 0, 500, 500);
        nanoVGRect.setBase(true, new Color(255, 255, 0, 255));
        nanoVGRect.draw();
    }

    @Override
    public void keyPress(int key, int scancode, int mods, long window) {
    }

    @Override
    public void keyReleased(int key, int scancode, int mods, long window) {

    }

    @Override
    public void cursorPos(double xpos, double ypos, long window) {

    }

    @Override
    public void mouseButtonPress(int button, int mods, long window) {

    }

    @Override
    public void mouseButtonReleased(int button, int mods, long window) {

    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, 60);
        frame.setFrameSettingValue(MonitorSetting.class, MonitorType.Window);
        frame.setFrameSettingValue(VSyncSetting.class, VSyncType.VSyncOn);
        frame.setFrameSettingValue(FloatingSetting.class, false);
        frame.setFrameSettingValue(IconSetting.class, new ResourcesLocation("salad_x32.png"));
        frame.setFrameSettingValue(RenderingTypeSetting.class, RenderingType.NanoVG);
    }
}
