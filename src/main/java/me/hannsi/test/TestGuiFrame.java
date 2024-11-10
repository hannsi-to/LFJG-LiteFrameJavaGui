package me.hannsi.test;

import me.hannsi.lfjg.event.events.*;
import me.hannsi.lfjg.event.system.EventHandler;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.IFrame;
import me.hannsi.lfjg.frame.LFJGFrame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.render.openGL.effect.effects.ColorCorrection;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.AntiAliasingType;
import me.hannsi.lfjg.utils.type.types.MonitorType;
import me.hannsi.lfjg.utils.type.types.VSyncType;

public class TestGuiFrame implements LFJGFrame {
    private Frame frame;

    private GLRect glRect;
    private GLRect glRect2;


    public static void main(String[] args) {
        new TestGuiFrame().setFrame();
    }

    public void setFrame() {
        frame = new Frame(this, "TestGuiFrame");
    }

    @Override
    public void init() {
        IFrame.eventManager.register(this);

        glRect = new GLRect(frame);
        glRect.init();

        glRect2 = new GLRect(frame);
        glRect2.init();
    }

    @Override
    public void drawFrame(long nvg) {
        //glRect.addEffect(new Translate(100,100f));
        //glRect.addEffect(new Size(2f,2f,0,0));
        //glRect.rectWH(0, 0, 500, 500, new Color(255, 0, 255, 255));

        //glRect2.addEffect(new Translate(100,100f));
        glRect2.addEffect(new ColorCorrection(0f, 1, 2, 0, 2.2f));
        glRect2.rectWH(0, 0, 500, 500, new Color(255, 0, 255, 255));
    }

    @Override
    public void stopFrame() {
        glRect.cleanUp();
        glRect2.cleanUp();
    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, 60);
        frame.setFrameSettingValue(MonitorSetting.class, MonitorType.Window);
        frame.setFrameSettingValue(VSyncSetting.class, VSyncType.VSyncOn);
        frame.setFrameSettingValue(FloatingSetting.class, false);
        frame.setFrameSettingValue(IconSetting.class, new ResourcesLocation("salad_x32.png"));
        frame.setFrameSettingValue(AntiAliasingSetting.class, AntiAliasingType.MSAA);
    }

    @EventHandler
    public void keyPressEvent(KeyPressEvent event) {

    }

    @EventHandler
    public void keyReleasedEvent(KeyReleasedEvent event) {

    }

    @EventHandler
    public void cursorPosEvent(CursorPosEvent event) {

    }

    @EventHandler
    public void mouseButtonPressEvent(MouseButtonPressEvent event) {

    }

    @EventHandler
    public void mouseButtonReleasedEvent(MouseButtonReleasedEvent event) {

    }
}
