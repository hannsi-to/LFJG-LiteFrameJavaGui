package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.LFJGFrame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.render.renderer.vertex.Circle;
import me.hannsi.lfjg.util.ColorUtil;
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
        Color color = ColorUtil.getRainbow(25, 255, 255);
        Color color2 = ColorUtil.getRainbow(25, 0.25f, 255, 255);
        Color color3 = ColorUtil.getRainbow(25, 0.5f, 255, 255);
        Color color4 = ColorUtil.getRainbow(25, 0.75f, 255, 255);
        Color color5 = ColorUtil.getRainbow(25, 1f, 255, 255);

        Circle circle = new Circle(frame);
        circle.circleOutLine(500, 500, 250, 250, 360, 1f, color, color2, color3, color4, color5);
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
    }
}
