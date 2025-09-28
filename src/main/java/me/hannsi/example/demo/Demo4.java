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
        glVideo = GLVideo.createGLVideo("GLVideo1")
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
    public Scene getScene() {
        return scene;
    }
}
