package me.hannsi.example.wikiPage5;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;

public class ObjectCopyExample implements LFJGFrame {
    GLRect glRect1;
    GLObject copyGlObject;
    private Frame frame;

    public static void main(String[] args) {
        new ObjectCopyExample().setFrame();
    }

    @Override
    public void init() {
        frame.updateLFJGLContext();

        glRect1 = new GLRect("Rect1");
        glRect1.rect(0, 0, 500, 500, Color.of(255, 0, 255, 255));

        copyGlObject = glRect1.copy("CopyRect1");
    }

    @Override
    public void drawFrame() {
        copyGlObject.draw();
    }

    @Override
    public void stopFrame() {
        glRect1.cleanup();
        copyGlObject.cleanup();
    }

    @Override
    public void setFrameSetting() {

    }

    public void setFrame() {
        frame = new Frame(this, "ObjectCopyExample");
    }
}
