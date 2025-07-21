package me.hannsi.example.wikiPage5;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.type.types.MonitorType;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.MonitorSetting;
import me.hannsi.lfjg.frame.setting.settings.RefreshRateSetting;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;

public class ChangeValueVertexBufferObjectExample implements LFJGFrame {
    GLRect glRect1;
    int x = 0;
    private Frame frame;

    public static void main(String[] args) {
        new ChangeValueVertexBufferObjectExample().setFrame();
    }

    @Override
    public void init() {
        frame.updateLFJGLContext();

        glRect1 = new GLRect("Rect1");
        glRect1.rect(x, 0, 500, 500, Color.of(255, 0, 255, 255));
    }

    @Override
    public void drawFrame() {
        glRect1.rectWH(x++, 0, 500, 500, Color.of(255, 0, 255, 255));
        glRect1.draw();
    }

    @Override
    public void stopFrame() {
        glRect1.cleanup();
    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(MonitorSetting.class, MonitorType.BORDERLESS);
        frame.setFrameSettingValue(RefreshRateSetting.class, -1);
    }

    public void setFrame() {
        frame = new Frame(this, "ChangeValueVertexBufferObjectExample");
    }
}