package me.hannsi.example.wikiPage5;

import me.hannsi.lfjg.core.Core;
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
        Core.init(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), frame.getWindowWidth(), frame.getWindowHeight());

        glRect1 = GLRect.createGLRect("Rect1")
                .x1_y1_color1_2p(0, 0, Color.of(255, 0, 255, 255))
                .x3_y3_color3_2p(500, 500, Color.of(255, 0, 255, 255))
                .fill()
                .update();

        copyGlObject = glRect1.copy("CopyRect1");
    }

    @Override
    public void drawFrame() {
//        copyGlObject.draw();
    }

    @Override
    public void stopFrame() {
//        glRect1.cleanup();
//        copyGlObject.cleanup();
    }

    @Override
    public void setFrameSetting() {

    }

    public void setFrame() {
        frame = new Frame(this, "ObjectCopyExample");
    }
}
