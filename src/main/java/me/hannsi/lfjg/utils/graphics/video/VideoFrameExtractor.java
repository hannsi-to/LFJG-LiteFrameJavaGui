package me.hannsi.lfjg.utils.graphics.video;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.utils.graphics.audio.AudioFrameData;
import me.hannsi.lfjg.utils.math.ANSIFormat;
import me.hannsi.lfjg.utils.math.StringUtil;
import me.hannsi.lfjg.utils.reflection.FileLocation;
import me.hannsi.lfjg.utils.time.TimeCalculator;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VideoFrameExtractor {
    private final List<FrameData> videoCache;
    private int threadCount = 100;
    private FileLocation videLocation;
    private int frameCount;

    public VideoFrameExtractor(FileLocation videLocation) {
        this.videLocation = videLocation;
        this.frameCount = 0;

        videoCache = new ArrayList<>();
    }

    public void createVideoCache() {
        DebugLog.debug(getClass(), "Start extract video frame: " + videLocation.getPath());
        long tookTime = TimeCalculator.calculate(() -> {

            try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videLocation.getPath()); Java2DFrameConverter converter = new Java2DFrameConverter(); ExecutorService executorService = Executors.newFixedThreadPool(threadCount)) {
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

                            videoCache.add(new FrameData(new VideoFrameData(image), new AudioFrameData(samplesBuffer, sampleRate, channels)));

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
        DebugLog.debug(getClass(), "End extract video frame: " + videLocation.getPath() + " | took: " + tookTime + "ms");
    }

    public FrameData getFrameRender() {
        if (videoCache.size() - 1 < frameCount) {
            return null;
        }

        FrameData frameData = videoCache.get(frameCount);

        frameCount++;

        return frameData;
    }

    public FileLocation getVideLocation() {
        return videLocation;
    }

    public void setVideLocation(FileLocation videLocation) {
        this.videLocation = videLocation;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public List<FrameData> getVideoCache() {
        return videoCache;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }
}
