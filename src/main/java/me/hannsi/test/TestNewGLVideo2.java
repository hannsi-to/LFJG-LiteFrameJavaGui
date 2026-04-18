package me.hannsi.test;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.graphics.image.ImageCapture;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.ColorFormatType;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.system.video.NewVideoDecoder;

import static me.hannsi.lfjg.frame.LFJGFrameContext.frame;

public class TestNewGLVideo2 implements LFJGFrame {
    NewVideoDecoder videoDecoder;

    public static void main(String... args) {
        new TestNewGLVideo2().setFrame();
    }

    @Override
    public void init() {
        Core.init(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), frame.getWindowWidth(), frame.getWindowHeight());
        try {
            videoDecoder = new NewVideoDecoder(Location.fromResource("video/2025-12-18 01-19-44.mp4"));
            videoDecoder.grabberStart(true, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void drawFrame() {
        NewVideoDecoder.TimedVideoFrame videoFrame;
        try {
            videoFrame = videoDecoder.getVideoQueue().take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ImageCapture capture = new ImageCapture(Location.fromFile("C:/Users/hanns/idea-project/LFJG-LiteFrameJavaGui/logs"));
        capture.setWidth(videoFrame.width());
        capture.setHeight(videoFrame.height());
        capture.setFlip(false);

        capture.setWidth(videoFrame.width());
        capture.setHeight(videoFrame.height());
        capture.setColorFormatType(ColorFormatType.RED);
        capture.saveImage(videoFrame.y(), "Ay");

        capture.setWidth(videoFrame.width() / 2);
        capture.setHeight(videoFrame.height() / 2);
        capture.setColorFormatType(ColorFormatType.RG);
        capture.saveImage(videoFrame.uv(), "Auv");
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public void setFrameSetting() {

    }

    public void setFrame() {
        frame = new Frame(this, "TestNewGLVideo2");
    }
}
