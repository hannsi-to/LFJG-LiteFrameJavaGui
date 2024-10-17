package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.LFJGFrame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.render.renderer.vertex.Point;
import me.hannsi.lfjg.util.ResourcesLocation;
import me.hannsi.lfjg.util.type.types.MonitorType;
import me.hannsi.lfjg.util.type.types.VSyncType;
import me.hannsi.lfjg.util.vertex.Color;

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

    @Override
    public void drawFrame() {
        Point point = new Point(frame);
        point.point(500, 500, 250, new Color(255, 255, 255, 255));
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
        frame.setFrameSettingValue(MonitorSetting.class, MonitorType.Borderless);
        frame.setFrameSettingValue(VSyncSetting.class, VSyncType.VSyncOn);
        frame.setFrameSettingValue(FloatingSetting.class, false);
        frame.setFrameSettingValue(IconSetting.class, new ResourcesLocation("salad_x32.png"));
    }
}
