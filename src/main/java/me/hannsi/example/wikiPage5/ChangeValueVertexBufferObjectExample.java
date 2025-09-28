package me.hannsi.example.wikiPage5;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.MonitorSetting;
import me.hannsi.lfjg.frame.setting.settings.MonitorType;
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

        glRect1 = GLRect.createGLRect("Rect1")
                .x1_y1_color1_2p(x, 0, Color.of(255, 0, 255, 255))
                .width3_height3_color3_2p(500, 500, Color.of(255, 0, 255, 255))
                .fill()
                .update();
    }

    @Override
    public void drawFrame() {
        glRect1.getBuilder()
                .x1_y1_color1_2p(x++, 0, Color.of(255, 0, 255, 255))
                .width3_height3_color3_2p(500, 500, Color.of(255, 0, 255, 255))
                .fill()
                .update();
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