package me.hannsi.lfjg.utils.graphics.video;

import me.hannsi.lfjg.debug.debug.system.DebugLog;
import me.hannsi.lfjg.utils.math.ANSIFormat;
import me.hannsi.lfjg.utils.reflection.FileLocation;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.time.TimeCalculator;
import me.hannsi.lfjg.utils.toolkit.StringUtil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Class for extracting video frames and audio data from a video file.
 */
public class VideoFrameExtractor {
    private final ResourcesLocation videoFileLocation;
    private final int threadCount = 100;
    private Frame frame;
    private FFmpegFrameGrabber fFmpegFrameGrabber;
    private Java2DFrameConverter java2DFrameConverter;
    private int frameNumber;
    private VideoCache videoCache;
    private boolean finished;

    /**
     * Constructs a VideoFrameExtractor instance with the specified video file location.
     *
     * @param videoFileLocation the location of the video file
     */
    public VideoFrameExtractor(ResourcesLocation videoFileLocation) {
        this.videoFileLocation = videoFileLocation;
        this.videoCache = new VideoCache();
    }

    /**
     * Creates a cache of video frames and audio data from the video file.
     */
    public void createVideoCache() {
        DebugLog.debug(getClass(), "Start extract video frame: " + videoFileLocation.getPath());
        long tookTime = TimeCalculator.calculateMillis(() -> {
            this.finished = false;

            try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFileLocation.getPath()); Java2DFrameConverter converter = new Java2DFrameConverter()) {
                grabber.setOption("threads", String.valueOf(threadCount));
                grabber.setOption("avioflags", "direct");

                try {
                    grabber.start();

                    int frameCount = 0;

                    double totalDuration = grabber.getLengthInTime() / 1000000.0;

                    while ((frame = grabber.grab()) != null) {
//                        while (videoCache.getFrames().size() > grabber.getFrameRate() * 4) {
//                        }

                        BufferedImage image = converter.convert(frame);

                        if (image == null) {
                            continue;
                        }

                        videoCache.createCache(new FrameData(image), null);

                        frameCount++;

                        double currentTime = grabber.getTimestamp() / 1000000.0;
                        int nowStep = (int) ((100 * currentTime) / totalDuration);

                        String bar = "                    ";
                        for (int i = 0; i < nowStep; i++) {
                            if ((i % 5) == 0) {
                                bar = StringUtil.addInsertChar(bar, "■");
                            }
                        }
                        bar = StringUtil.getFirstNCharacters(bar, 20);

                        System.out.print(ANSIFormat.MAGENTA + "Video convert: " + "[" + bar + "] " + nowStep + "% | Total Duration: " + totalDuration + "s | Processed: " + frameCount + ANSIFormat.RESET + "\r");
                    }

                    int nowStep = 100;
                    System.out.println(ANSIFormat.MAGENTA + "Video convert: " + "[■■■■■■■■■■■■■■■■■■■■] " + nowStep + "% | Total Duration: " + totalDuration + "s | Processed: " + frameCount + ANSIFormat.RESET);

                    grabber.stop();
                } catch (FFmpegFrameGrabber.Exception e) {
                    DebugLog.error(getClass(), e);
                }
            } catch (IOException e) {
                DebugLog.error(getClass(), e);
            }
        });
        this.finished = true;
        DebugLog.debug(getClass(), "End extract video frame: " + videoFileLocation.getPath() + " | took: " + tookTime + "ms");
    }

    /**
     * Retrieves the next frame data from the video cache.
     *
     * @return the next frame data
     */
    public VideoCache.Frame frame() {
        if (videoCache.getFrames().isEmpty()) {
            return null;
        }

        if (finished && videoCache.getFrames().isEmpty()) {
            cleanup();
            return null;
        }

        VideoCache.Frame frame = videoCache.getFrames().get(0);
        videoCache.getFrames().remove(0);

        return frame;
    }

    public void cleanup() {
        try {
            videoCache.cleanup();
            videoFileLocation.cleanup();
            frame.close();
            fFmpegFrameGrabber.close();
            java2DFrameConverter.close();
        } catch (FrameGrabber.Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the video file location.
     *
     * @return the video file location
     */
    public FileLocation getVideoFileLocation() {
        return videoFileLocation;
    }

    /**
     * Gets the current frame.
     *
     * @return the current frame
     */
    public Frame getFrame() {
        return frame;
    }

    /**
     * Sets the current frame.
     *
     * @param frame the new frame
     */
    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    /**
     * Gets the FFmpeg frame grabber.
     *
     * @return the FFmpeg frame grabber
     */
    public FFmpegFrameGrabber getfFmpegFrameGrabber() {
        return fFmpegFrameGrabber;
    }

    /**
     * Sets the FFmpeg frame grabber.
     *
     * @param fFmpegFrameGrabber the new FFmpeg frame grabber
     */
    public void setfFmpegFrameGrabber(FFmpegFrameGrabber fFmpegFrameGrabber) {
        this.fFmpegFrameGrabber = fFmpegFrameGrabber;
    }

    /**
     * Gets the Java2D frame converter.
     *
     * @return the Java2D frame converter
     */
    public Java2DFrameConverter getJava2DFrameConverter() {
        return java2DFrameConverter;
    }

    /**
     * Sets the Java2D frame converter.
     *
     * @param java2DFrameConverter the new Java2D frame converter
     */
    public void setJava2DFrameConverter(Java2DFrameConverter java2DFrameConverter) {
        this.java2DFrameConverter = java2DFrameConverter;
    }

    /**
     * Gets the current frame number.
     *
     * @return the current frame number
     */
    public int getFrameNumber() {
        return frameNumber;
    }

    /**
     * Sets the current frame number.
     *
     * @param frameNumber the new frame number
     */
    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    /**
     * Gets the video cache.
     *
     * @return the video cache
     */
    public VideoCache getVideoCache() {
        return videoCache;
    }

    /**
     * Sets the video cache.
     *
     * @param videoCache the new video cache
     */
    public void setVideoCache(VideoCache videoCache) {
        this.videoCache = videoCache;
    }
}