package me.hannsi.example.wikiPage5;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.frame.LFJGFrame;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.utils.graphics.color.Color;

public class ChangeValueVertexBufferObjectExample implements LFJGFrame {
    GLRect glRect1;
    int x = 0;
    boolean revers;
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

    }

    public void setFrame() {
        frame = new Frame(this, "ChangeValueVertexBufferObjectExample");
    }
}