package me.hannsi.example;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;

public class HelloWorld implements LFJGFrame {
    public GLRect glRect1;
    private Frame frame;

    public static void main(String[] args) {
        new HelloWorld().setFrame();
    }

    @Override
    public void init() {
        Core.init(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), frame.getWindowWidth(), frame.getWindowHeight());

        glRect1 = GLRect.createGLRect("Rect1")
                .x1_y1_color1_2p(200, 200, Color.of(255, 0, 255, 255))
                .width3_height3_color3_2p(700, 700, Color.of(255, 0, 255, 255))
                .fill()
                .update();
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
        frame = new Frame(this, "HelloWorld");
    }
}
