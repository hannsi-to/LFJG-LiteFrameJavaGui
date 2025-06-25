package me.hannsi.example.wikiPage5;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.frame.LFJGFrame;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.utils.graphics.color.Color;

public class ChangeValueModelMatrixExample implements LFJGFrame {
    GLRect glRect1;
    private Frame frame;

    public static void main(String[] args) {
        new ChangeValueModelMatrixExample().setFrame();
    }

    @Override
    public void init() {
        frame.updateLFJGLContext();

        glRect1 = new GLRect("Rect1");
        glRect1.rect(0, 0, 500, 500, Color.of(255, 0, 255, 255));
    }

    @Override
    public void drawFrame() {
        glRect1.getTransform().translate(1, 0, 0);
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
        frame = new Frame(this, "ChangeValueModelMatrixExample");
    }
}