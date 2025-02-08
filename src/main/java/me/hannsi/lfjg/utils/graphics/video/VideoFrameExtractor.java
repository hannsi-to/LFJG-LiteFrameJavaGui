package me.hannsi.lfjg.utils.graphics.video;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.utils.math.ANSIFormat;
import me.hannsi.lfjg.utils.math.StringUtil;
import me.hannsi.lfjg.utils.reflection.FileLocation;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.time.TimeCalculator;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class for extracting video frames and audio data from a video file.
 */
public class VideoFrameExtractor {
    private final ResourcesLocation videoFileLocation;
    private Frame frame;
    private FFmpegFrameGrabber fFmpegFrameGrabber;
    private Java2DFrameConverter java2DFrameConverter;
    private int frameNumber;
    private VideoCache videoCache;
    private final int threadCount = 100;

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
        long tookTime = TimeCalculator.calculate(() -> {

            try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFileLocation.getPath()); Java2DFrameConverter converter = new Java2DFrameConverter(); ExecutorService executorService = Executors.newFixedThreadPool(threadCount)) {
                executorService.submit(() -> {
                    try {
                        grabber.start();

                        int frameNumber = 0;
                        Frame frame;

                        double totalDuration = grabber.getLengthInTime() / 1000000.0;

                        while ((frame = grabber.grab()) != null) {
                            BufferedImage image = converter.convert(frame);
                            ShortBuffer samplesBuffer = null;
                            int sampleRate = -1;
                            int channels = -1;

                            if ((frame = grabber.grabSamples()) != null) {
                                samplesBuffer = (ShortBuffer) frame.samples[0];
                                sampleRate = grabber.getSampleRate();
                                channels = grabber.getAudioChannels();
                            }

                            videoCache.createCache(new FrameData(image), new AudioData(samplesBuffer, sampleRate, channels));

                            frameNumber++;

                            double currentTime = grabber.getTimestamp() / 1000000.0;
                            int nowStep = (int) ((100 * currentTime) / totalDuration);

                            String bar = "                    ";
                            for (int i = 0; i < nowStep; i++) {
                                if ((i % 5) == 0) {
                                    bar = StringUtil.addInsertChar(bar, "■");
                                }
                            }
                            bar = StringUtil.getFirstNCharacters(bar, 20);

                            System.out.print(ANSIFormat.MAGENTA + "\rVideo convert: " + "[" + bar + "] " + nowStep + "% | Total Duration: " + totalDuration + "s | Processed: " + frameNumber + ANSIFormat.RESET);
                        }

                        int nowStep = 100;
                        System.out.println(ANSIFormat.MAGENTA + "\rVideo convert: " + "[■■■■■■■■■■■■■■■■■■■■] " + nowStep + "% | Total Duration: " + totalDuration + "s | Processed: " + frameNumber + ANSIFormat.RESET);

                        grabber.stop();
                    } catch (FFmpegFrameGrabber.Exception e) {
                        DebugLog.error(getClass(), e);
                    }
                });

                executorService.shutdown();
            } catch (IOException e) {
                DebugLog.error(getClass(), e);
            }
        });
        DebugLog.debug(getClass(), "End extract video frame: " + videoFileLocation.getPath() + " | took: " + tookTime + "ms");
    }

    /**
     * Retrieves the next frame data from the video cache.
     *
     * @return the next frame data
     */
    public FrameData frame() {
        FrameData frameData = videoCache.getFrames().get(frameNumber).getFrameData();
        frameNumber++;
        return frameData;
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