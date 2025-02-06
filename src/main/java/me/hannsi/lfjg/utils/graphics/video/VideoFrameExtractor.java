package me.hannsi.lfjg.utils.graphics.video;

import me.hannsi.lfjg.utils.reflection.FileLocation;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;

public class VideoFrameExtractor {
    private final FileLocation videoFileLocation;
    private Frame frame;
    private FFmpegFrameGrabber fFmpegFrameGrabber;
    private Java2DFrameConverter java2DFrameConverter;
    private int frameNumber;

    public VideoFrameExtractor(FileLocation videoFileLocation) {
        this.videoFileLocation = videoFileLocation;
    }

    public FrameData frame() {
        try {
            if ((frame = fFmpegFrameGrabber.grab()) != null) {
                BufferedImage bufferedImage = java2DFrameConverter.convert(frame);
                if (bufferedImage == null) {
                    return null;
                }

                FrameData frameData = new FrameData(bufferedImage);
                frameData.createTexture();

                frameNumber++;
                return frameData;
            } else {
                stop();
            }
        } catch (FFmpegFrameGrabber.Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void start() {
        try {
            fFmpegFrameGrabber = new FFmpegFrameGrabber(videoFileLocation.getInputStream());
            java2DFrameConverter = new Java2DFrameConverter();
            fFmpegFrameGrabber.start();
            frameNumber = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        try {
            fFmpegFrameGrabber.stop();
        } catch (FFmpegFrameGrabber.Exception e) {
            e.printStackTrace();
        }
    }
}
