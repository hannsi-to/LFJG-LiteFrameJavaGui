package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;
import me.hannsi.lfjg.render.system.video.VideoFrameSystem;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;

public class TestVideo1 implements IScene {
    public Scene scene;
    public Frame frame;
    public VideoFrameSystem videoFrameSystem;

    public TestVideo1(Frame frame) {
        this.scene = new Scene("TestVideo1", this);
        this.frame = frame;
    }

    @Override
    public void init() {
        videoFrameSystem = VideoFrameSystem.initVideoFrameSystem()
                .createFFmpegFrameGrabber(new ResourcesLocation("video/test.mp4"))
                .createJava2DFrameConverter()
                .createVideoCache()
                .startVideoLoad();
    }

    @Override
    public void drawFrame() {
        videoFrameSystem.drawFrame();
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
