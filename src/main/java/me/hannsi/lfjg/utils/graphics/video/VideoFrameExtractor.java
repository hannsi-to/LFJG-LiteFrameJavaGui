package me.hannsi.lfjg.utils.graphics.video;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.utils.math.StringUtil;
import me.hannsi.lfjg.utils.reflection.FileLocation;
import me.hannsi.lfjg.utils.time.TimeCalculator;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VideoFrameExtractor {
    private int threadCount = 100;

    private FileLocation videLocation;
    private FileLocation outputLocation;

    public VideoFrameExtractor(FileLocation videLocation, FileLocation outputLocation) {
        this.videLocation = videLocation;
        this.outputLocation = outputLocation;
    }

    public void init() {
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

                            System.out.print("\rVideo convert: " + "[" + bar + "] " + nowStep + "% | Total Duration: " + totalDuration + "s | Processed: " + frameNumber);
                        }

                        int nowStep = 100;
                        System.out.print("\rVideo convert: " + "[■■■■■■■■■■■■■■■■■■■■] " + nowStep + "% | Total Duration: " + totalDuration + "s | Processed: " + frameNumber);
                        System.out.print("\n");

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

    public FileLocation getVideLocation() {
        return videLocation;
    }

    public void setVideLocation(FileLocation videLocation) {
        this.videLocation = videLocation;
    }

    public FileLocation getOutputLocation() {
        return outputLocation;
    }

    public void setOutputLocation(FileLocation outputLocation) {
        this.outputLocation = outputLocation;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }
}
