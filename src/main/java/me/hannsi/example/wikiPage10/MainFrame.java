package me.hannsi.example.wikiPage10;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.renderers.video.GLVideo;

public class MainFrame implements LFJGFrame {
    GLVideo glVideo;
    private Frame frame;

    public static void main(String[] args) {
        new MainFrame().setFrame();
    }

    @Override
    public void init() {
        Core.init(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), frame.getWindowWidth(), frame.getWindowHeight());

        glVideo = GLVideo.createGLVideo("TestVideo1")
                .location(Location.fromResource("video/sample.mp4"))
                .x1_y1_color1_2p(0, 0)
                .width3_height3_color3_2p(frame.getWindowWidth(), frame.getWindowHeight())
                .update();
    }

    @Override
    public void drawFrame() {
        glVideo.draw();
    }

    @Override
    public void stopFrame() {
        glVideo.cleanup();
    }

    @Override
    public void setFrameSetting() {

    }

    public void setFrame() {
        frame = new Frame(this, "MainFrame");
    }
}