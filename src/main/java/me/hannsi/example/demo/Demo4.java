package me.hannsi.example.demo;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.renderers.video.GLVideo;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;

public class Demo4 implements IScene {
    Frame frame;
    Scene scene;

    GLVideo glVideo;

    public Demo4(Frame frame) {
        this.frame = frame;
        this.scene = new Scene("Demo4", this);
    }

    @Override
    public void init() {
        glVideo = new GLVideo("GLVideo1");
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
    public Scene getScene() {
        return scene;
    }
}
