package me.hannsi.example.wikiPage4;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.frame.LFJGFrame;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.utils.graphics.color.Color;

public class MainFrame implements LFJGFrame {
    GLRect glRect1;
    private Frame frame;

    public static void main(String[] args) {
        new MainFrame().setFrame();
    }

    @Override
    public void init() {
        frame.updateLFJGLContext();

        glRect1 = new GLRect("Rect1");
        glRect1.rect(0, 0, 500, 500, Color.of(255, 0, 255, 255));
    }

    @Override
    public void drawFrame() {
        glRect1.draw();
    }

    @Override
    public void stopFrame() {
        glRect1.cleanup();
    }

    @Override
    public void setFrameSetting() {

    }

    public void setFrame() {
        frame = new Frame(this, "MainFrame");
    }
}