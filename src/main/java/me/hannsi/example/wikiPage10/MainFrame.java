package me.hannsi.example.wikiPage10;

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
        frame.updateLFJGLContext();

        glVideo = new GLVideo("TestVideo1");
        glVideo.video(Location.fromResource("video/sample.mp4"), 0, 0, frame.getWindowWidth(), frame.getWindowHeight());
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